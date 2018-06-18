package sorcery.tileentities.nodes;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import sorcery.client.render.RenderUtils;
import sorcery.entities.EntityNodeItem;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.NodeTransportHelper;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;
import sorcery.lib.NodeTransportHelper.NodeRequestDetails;
import sorcery.lib.Pos3D;
import sorcery.tileentities.TileEntitySorcery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityNodeBase extends TileEntitySorcery implements IInventory {
	public static int filterSlotIDs = 300;
	
	public static int codeSlotID = 400;
	public static int targetSlotID = 401;
	
	public static int extraStorageIDs = 500;
	
	protected int connectedFace;
	public int colourCode;
	public int colourTarget;
	protected int priority = 1;
	public int delay;
	protected int redstoneStatus;
	protected int redstonePulses;
	protected int outputMode;
	protected int extraData;
	
	public float standRotation;
	public float prevStandRotation;
	public float nodeRotation;
	public float prevNodeRotation;
	
	public boolean isInput;
	public boolean isOutput;
	public boolean isAdvanced;
	public boolean useAdvancedModel;
	
	public boolean isSending;
	public boolean rotationFinished;
	public int slotToSendFrom;
	
	public Pos3D lastNodeReceivedFrom;
	
	public Pos3D nextNodeToSendTo;
	
	public NodeGUIDetails guiDetails;
	
	protected int bufferSize = 3;
	
	protected ItemStack[] buffer;
	
	protected ItemStack[] colours;
	
	protected ItemStack[] filter;
	
	protected ItemStack[] extraStorage;
	
	protected ArrayList<NodeRequestDetails> requests = new ArrayList<NodeRequestDetails>();
	
	public ArrayList<EntityNodeItem> sentItems = new ArrayList<EntityNodeItem>();
	
	public TileEntityNodeBase() {
		this.bufferSize = 3;
		this.colourCode = -1;
		this.colourTarget = -1;
		this.buffer = new ItemStack[this.bufferSize];
		this.colours = new ItemStack[2];
		this.filter = new ItemStack[9];
		this.isInput = false;
		this.isOutput = false;
		this.isAdvanced = false;
		this.useAdvancedModel = false;
		
		this.guiDetails = new NodeGUIDetails(0, 0, false, 0, 0, false, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public void handlePacketData(NBTTagCompound tag, boolean isClient) {
		int action = tag.getInteger("action");
		
		switch(action) {
			case 0:
				this.setPriority(tag.getInteger("data"));
				break;
			case 1:
				this.setRedstoneStatus(tag.getInteger("data"));
				break;
			case 2:
				this.setOutputMode(tag.getInteger("data"));
				break;
		}
	}
	
	@Override
	public void updateEntity() {
		this.updateColours();
		
		this.delay++;
		
		if(this.delay >= 20 * 3) {
			if(this.doesRedstonePermitAction() && !this.worldObj.isRemote && !this.isAdvanced) {
				if(this.isOutput) {
					TileEntityNodeBase node = this.getNodeToSendTo();
					
					if(!this.isInput) {
						if(this.isSending && this.nextNodeToSendTo.isNode(this.worldObj))
							node = (TileEntityNodeBase)this.nextNodeToSendTo.getTileEntityAtPos(this.worldObj);
						
						TileEntity tile = this.worldObj.getTileEntity(this.getConnectedBlock().xCoord(), this.getConnectedBlock().yCoord(), this.getConnectedBlock().zCoord());
						if(node != null && this.isNodeAccessible(node) && node.isInput && tile != null && tile instanceof IInventory && !(tile instanceof TileEntityNodeBase)) {
							if(this.isSending && (((IInventory)tile).getStackInSlot(this.slotToSendFrom) != null)) {
								EntityNodeItem entity = new EntityNodeItem(this.worldObj);
								entity.setItem(((IInventory)tile).getStackInSlot(this.slotToSendFrom));
								((IInventory)tile).decrStackSize(this.slotToSendFrom, ((IInventory)tile).getStackInSlot(this.slotToSendFrom).stackSize);
								Pos3D dirToNode = this.getDirectionToNode(node);
								entity.setPosition(this.xCoord + 0.5 + dirToNode.xCoord(), this.yCoord + 0.2 + dirToNode.yCoord(), this.zCoord + 0.5 + dirToNode.zCoord());
								entity.setVelocity(dirToNode.xCoord(), dirToNode.yCoord(), dirToNode.zCoord());
								entity.setDirections(node.xCoord, node.yCoord, node.zCoord);
								entity.setOrigin(this);
								entity.setColourCode(this.colourTarget);
								
								// this.setRotation(this.getStandRotationTo(node),
								// this.getNodeRotationTo(node));
								this.sentItems.add(entity);
								this.isSending = false;
								this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
							}
							
							node = this.getNodeToSendTo();
							
							if(tile instanceof ISidedInventory) {
								int[] sides = ((ISidedInventory)tile).getAccessibleSlotsFromSide(Facing.oppositeSide[this.getConnectedSide()]);
								for(int i = 0; i < sides.length; i++) {
									if(((IInventory)tile).getStackInSlot(sides[i]) != null && ((ISidedInventory)tile).canExtractItem(sides[i], ((IInventory)tile).getStackInSlot(sides[i]), Facing.oppositeSide[this.connectedFace])) {
										this.beginSendingTo(node, sides[i]);
										this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
										
										break;
									}
								}
							} else
								for(int i = 0; i < ((IInventory)tile).getSizeInventory(); i++) {
									if(((IInventory)tile).getStackInSlot(i) != null && node.canAcceptInput(((IInventory)tile).getStackInSlot(i)) && this.isItemInFilter(((IInventory)tile).getStackInSlot(i))) {
										this.beginSendingTo(node, i);
										this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
										
										break;
									}
								}
						}
					} else if(this.isInput && this.hasBuffer()) {
						if(node != null && this.isNodeAccessible(node) && node.isInput) {
							if(this.isSending && this.nextNodeToSendTo.isNode(this.worldObj))
								node = (TileEntityNodeBase)this.nextNodeToSendTo.getTileEntityAtPos(this.worldObj);
							
							if(this.isSending && this.getBufferItem(this.slotToSendFrom) != null) {
								EntityNodeItem entity = new EntityNodeItem(this.worldObj);
								entity.setItem(this.getBufferItem(this.slotToSendFrom));
								this.decrStackSize(this.slotToSendFrom, this.getBufferItem(this.slotToSendFrom).stackSize);
								Pos3D dirToNode = this.getDirectionToNode(node);
								entity.setPosition(this.xCoord + 0.5 + dirToNode.xCoord(), this.yCoord + 0.2 + dirToNode.yCoord(), this.zCoord + 0.5 + dirToNode.zCoord());
								entity.setVelocity(dirToNode.xCoord(), dirToNode.yCoord(), dirToNode.zCoord());
								entity.setDirections(node.xCoord, node.yCoord, node.zCoord);
								entity.setOrigin(this);
								entity.setColourCode(this.colourTarget);
								
								// this.setRotation(this.getStandRotationTo(node),
								// this.getNodeRotationTo(node));
								this.sentItems.add(entity);
								this.isSending = false;
								this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
							}
							
							node = this.getNodeToSendTo();
							
							for(int i = 0; i < this.getBufferSize(); i++) {
								if(this.getBufferItem(i) != null && node.canAcceptInput(this.getBufferItem(i))) {
									this.beginSendingTo(node, i);
									this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
									
									break;
								}
							}
						}
					}
				} else if(this.isInput && !this.isOutput) {
					for(int i = 0; i < this.getBufferSize(); i++) {
						TileEntity tile = this.worldObj.getTileEntity(this.getConnectedBlock().xCoord(), this.getConnectedBlock().yCoord(), this.getConnectedBlock().zCoord());
						if(this.getBufferItem(i) != null && tile != null && tile instanceof IInventory) {
							this.buffer[i] = TileEntityHopper.func_145889_a((IInventory)tile, this.getBufferItem(i), Facing.oppositeSide[this.getConnectedSide()]);
							break;
						}
					}
				}
			} else if(this.isAdvanced && this.doesRedstonePermitAction() && !this.worldObj.isRemote) {
				this.updateNode();
			}
			
			this.delay = 0;
		}
		
		if(this.redstoneStatus != 2 && this.redstonePulses != 0)
			this.redstonePulses = 0;
		
		if(!this.sentItems.isEmpty()) {
			for(int i = 0; i < this.sentItems.size(); i++) {
				if(this.sentItems.get(i).worldObj == null)
					this.sentItems.get(i).worldObj = this.worldObj;
				
				this.sentItems.get(i).onUpdate();
				
				if(this.sentItems.get(i).getItemEntity() == null) {
					this.sentItems.remove(i);
					continue;
				}
				
				if(this.sentItems.get(i).rotation >= 60 * 20) {
					this.sentItems.get(i).dropItemAndKill();
					this.sentItems.remove(i);
					continue;
				}
				
				if(this.getNodeAt(this.sentItems.get(i).directions[0]) == null) {
					this.sentItems.get(i).dropItemAndKill();
					this.sentItems.remove(i);
					continue;
				}
				
				if(!NodeTransportHelper.isDestinationReachable(this.sentItems.get(i).directions[0], new Pos3D((int)(Math.floor(this.sentItems.get(i).posX)), (int)(Math.rint(this.sentItems.get(i).posY)), (int)(Math.floor(this.sentItems.get(i).posZ))), this.worldObj)) {
					this.sentItems.get(i).dropItemAndKill();
					this.sentItems.remove(i);
					continue;
				}
				
				if(this.sentItems.get(i).worldObj.getTileEntity((int)Math.floor(this.sentItems.get(i).posX), (int)Math.floor(this.sentItems.get(i).posY), (int)Math.floor(this.sentItems.get(i).posZ)) != null) {
					if(this.sentItems.get(i).worldObj.getTileEntity((int)Math.floor(this.sentItems.get(i).posX), (int)Math.floor(this.sentItems.get(i).posY), (int)Math.floor(this.sentItems.get(i).posZ)) instanceof TileEntityNodeBase) {
						TileEntityNodeBase node = (TileEntityNodeBase)this.sentItems.get(i).worldObj.getTileEntity((int)Math.floor(this.sentItems.get(i).posX), (int)Math.floor(this.sentItems.get(i).posY), (int)Math.floor(this.sentItems.get(i).posZ));
						if(this.sentItems.get(i).boundingBox.intersectsWith(node.getBlockType().getCollisionBoundingBoxFromPool(this.sentItems.get(i).worldObj, (int)Math.floor(this.sentItems.get(i).posX), (int)Math.floor(this.sentItems.get(i).posY), (int)Math.floor(this.sentItems.get(i).posZ)))) {
							this.sentItems.get(i).isDeInit = true;
							
							Pos3D dirToNode = this.getDirectionToNode(node);
							this.sentItems.get(i).setVelocity(dirToNode.xCoord() / 3D, dirToNode.yCoord() / 3D, dirToNode.zCoord() / 3D);
						}
						
						if(this.sentItems.get(i).isDeInit && this.sentItems.get(i).deInitTicks >= 10) {
							if(!this.worldObj.isRemote)
								node.insertIntoBuffer(this.sentItems.get(i).getItemEntity().getEntityItem());
							
							node.lastNodeReceivedFrom = this.sentItems.get(i).origin;
							this.sentItems.remove(i);
							continue;
						}
					}
				}
				
				if(this.sentItems.get(i).isDeInit && this.sentItems.get(i).deInitTicks >= 10) {
					if(this.sentItems.get(i).worldObj.getTileEntity((int)Math.floor(this.sentItems.get(i).posX), (int)Math.floor(this.sentItems.get(i).posY), (int)Math.floor(this.sentItems.get(i).posZ)) == null) {
						this.sentItems.get(i).dropItemAndKill();
						this.sentItems.remove(i);
						continue;
					}
				}
			}
		}
		
		if(this.isSending) {
			TileEntityNodeBase node = (TileEntityNodeBase)this.nextNodeToSendTo.getTileEntityAtPos(this.worldObj);
			
			if(this.delay >= 10 && this.delay <= 40 && !this.rotationFinished) {
				float difBetweenStandRot = 0F;
				float difBetweenNodeRot = 0F;
				
				difBetweenStandRot = this.getAmountToRotate(this.getStandRotationTo(node) - this.prevStandRotation);
				difBetweenNodeRot = this.getAmountToRotate(this.getNodeRotationTo(node) - this.prevNodeRotation);
				
				this.addRotation(difBetweenStandRot, difBetweenNodeRot);
			}
			
			if(this.delay >= 40) {
				this.setRotation(this.getStandRotationTo(node), this.getNodeRotationTo(node));
				this.rotationFinished = true;
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		if(this.worldObj.isRemote)
			this.doRenderUpdate();
	}
	
	public abstract void updateNode();
	
	public void doRenderUpdate() {
	};
	
	public void updateColours() {
		if(this.colours != null) {
			if(this.colours[0] != null)
				this.colourCode = this.colours[0].getItemDamage();
			else
				this.colourCode = -1;
			
			if(this.colours[1] != null)
				this.colourTarget = this.colours[1].getItemDamage();
			else
				this.colourTarget = -1;
		}
	}
	
	public void beginSendingTo(TileEntityNodeBase node, int slot) {
		this.isSending = true;
		this.rotationFinished = false;
		this.nextNodeToSendTo = node.getPos3D();
		this.slotToSendFrom = slot;
		this.prevNodeRotation = this.nodeRotation;
		this.prevStandRotation = this.standRotation;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.connectedFace = par1NBTTagCompound.getInteger("connectedFace");
		this.colourCode = par1NBTTagCompound.getInteger("colourCode");
		this.colourTarget = par1NBTTagCompound.getInteger("colourTarget");
		this.bufferSize = par1NBTTagCompound.getInteger("bufferSize");
		this.priority = par1NBTTagCompound.getInteger("priority");
		this.delay = par1NBTTagCompound.getInteger("delay");
		
		this.redstoneStatus = par1NBTTagCompound.getInteger("redstoneStatus");
		this.redstonePulses = par1NBTTagCompound.getInteger("redstonePulses");
		this.outputMode = par1NBTTagCompound.getInteger("outputMode");
		this.extraData = par1NBTTagCompound.getInteger("extraData");
		
		this.standRotation = par1NBTTagCompound.getFloat("standRotation");
		this.prevStandRotation = par1NBTTagCompound.getFloat("prevStandRotation");
		this.nodeRotation = par1NBTTagCompound.getFloat("nodeRotation");
		this.prevNodeRotation = par1NBTTagCompound.getFloat("prevNodeRotation");
		this.rotationFinished = par1NBTTagCompound.getBoolean("rotationFinished");
		
		this.isSending = par1NBTTagCompound.getBoolean("isSending");
		this.slotToSendFrom = par1NBTTagCompound.getInteger("slotToSendFrom");
		
		this.lastNodeReceivedFrom = Pos3D.readFromNBT(par1NBTTagCompound, "lastNode");
		this.nextNodeToSendTo = Pos3D.readFromNBT(par1NBTTagCompound, "nextNode");
		
		this.buffer = (new ItemStack[this.getBufferSize()]);
		NBTTagList buffer = par1NBTTagCompound.getTagList("buffer", 10);
		for(int i = 0; i < buffer.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)buffer.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;
			this.getBuffer()[slot] = ItemStack.loadItemStackFromNBT((NBTTagCompound)buffer.getCompoundTagAt(i));
		}
		
		NBTTagList colours = par1NBTTagCompound.getTagList("colours", 10);
		for(int i = 0; i < colours.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)colours.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;
			this.colours[slot] = ItemStack.loadItemStackFromNBT((NBTTagCompound)colours.getCompoundTagAt(i));
		}
		
		NBTTagList filter = par1NBTTagCompound.getTagList("filter", 10);
		for(int i = 0; i < filter.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)filter.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;
			this.filter[slot] = ItemStack.loadItemStackFromNBT((NBTTagCompound)filter.getCompoundTagAt(i));
		}
		
		NBTTagList extra = par1NBTTagCompound.getTagList("extra", 10);
		for(int i = 0; i < extra.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)extra.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;
			this.extraStorage[slot] = ItemStack.loadItemStackFromNBT((NBTTagCompound)extra.getCompoundTagAt(i));
		}
		
		NBTTagList sentItems = par1NBTTagCompound.getTagList("sentItems", 10);
		this.sentItems.clear();
		for(int i = 0; i < sentItems.tagCount(); i++) {
			EntityNodeItem entity = new EntityNodeItem(this.worldObj);
			entity.readFromNBT((NBTTagCompound)sentItems.getCompoundTagAt(i));
			this.sentItems.add(entity);
		}
		
		NBTTagList requests = par1NBTTagCompound.getTagList("requests", 10);
		this.requests.clear();
		for(int i = 0; i < requests.tagCount(); i++) {
			NodeRequestDetails request = new NodeRequestDetails((NBTTagCompound)requests.getCompoundTagAt(i));
			this.requests.add(request);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("connectedFace", this.connectedFace);
		par1NBTTagCompound.setInteger("colourCode", this.colourCode);
		par1NBTTagCompound.setInteger("colourTarget", this.colourTarget);
		par1NBTTagCompound.setInteger("bufferSize", this.bufferSize);
		par1NBTTagCompound.setInteger("delay", this.delay);
		
		par1NBTTagCompound.setInteger("redstoneStatus", this.redstoneStatus);
		par1NBTTagCompound.setInteger("redstonePulses", this.redstonePulses);
		par1NBTTagCompound.setInteger("outputMode", this.outputMode);
		par1NBTTagCompound.setInteger("extraData", this.extraData);
		par1NBTTagCompound.setInteger("priority", this.priority);
		
		par1NBTTagCompound.setFloat("standRotation", this.standRotation);
		par1NBTTagCompound.setFloat("prevStandRotation", this.prevStandRotation);
		par1NBTTagCompound.setFloat("nodeRotation", this.nodeRotation);
		par1NBTTagCompound.setFloat("prevNodeRotation", this.prevNodeRotation);
		par1NBTTagCompound.setBoolean("rotationFinished", this.rotationFinished);
		
		par1NBTTagCompound.setInteger("slotToSendFrom", this.slotToSendFrom);
		par1NBTTagCompound.setBoolean("isSending", this.isSending);
		
		if(this.lastNodeReceivedFrom != null)
			lastNodeReceivedFrom.writeToNBT(par1NBTTagCompound, "lastNode");
		
		if(this.nextNodeToSendTo != null)
			nextNodeToSendTo.writeToNBT(par1NBTTagCompound, "nextNode");
		
		NBTTagList sentItems = new NBTTagList();
		for(int i = 0; i < this.sentItems.size(); i++) {
			if(this.sentItems.get(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				this.sentItems.get(i).writeToNBT(tag);
				sentItems.appendTag(tag);
			}
		}
		
		NBTTagList buffer = new NBTTagList();
		if(this.getBuffer() != null) {
			for(int i = 0; i < this.getBufferSize(); i++) {
				if(this.getBufferItem(i) != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Slot", (byte)i);
					this.getBufferItem(i).writeToNBT(tag);
					buffer.appendTag(tag);
				}
			}
		}
		
		NBTTagList colours = new NBTTagList();
		if(this.colours != null) {
			for(int i = 0; i < this.colours.length; i++) {
				if(this.colours[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Slot", (byte)i);
					this.colours[i].writeToNBT(tag);
					colours.appendTag(tag);
				}
			}
		}
		
		NBTTagList filter = new NBTTagList();
		if(this.filter != null) {
			for(int i = 0; i < this.filter.length; i++) {
				if(this.filter[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Slot", (byte)i);
					this.filter[i].writeToNBT(tag);
					filter.appendTag(tag);
				}
			}
		}
		
		NBTTagList extra = new NBTTagList();
		if(this.extraStorage != null) {
			for(int i = 0; i < this.extraStorage.length; i++) {
				if(this.extraStorage[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Slot", (byte)i);
					this.extraStorage[i].writeToNBT(tag);
					extra.appendTag(tag);
				}
			}
		}
		
		NBTTagList requests = new NBTTagList();
		for(int i = 0; i < this.requests.size(); i++) {
			if(this.requests.get(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				this.requests.get(i).writeToNBT(tag);
				requests.appendTag(tag);
			}
		}
		
		par1NBTTagCompound.setTag("sentItems", sentItems);
		par1NBTTagCompound.setTag("buffer", buffer);
		par1NBTTagCompound.setTag("colours", colours);
		par1NBTTagCompound.setTag("filter", filter);
		par1NBTTagCompound.setTag("requests", requests);
	}
	
	public void addRotation(float standRotation, float nodeRotation) {
		this.standRotation += standRotation;
		this.nodeRotation += nodeRotation;
	}
	
	public void setRotation(float standRotation, float nodeRotation) {
		this.standRotation = standRotation;
		this.nodeRotation = nodeRotation;
	}
	
	public float getAmountToRotate(float totalRotation) {
		return totalRotation / 30;
	}
	
	public float getStandRotationTo(TileEntityNodeBase node) {
		if(this.isNodeAccessible(node)) {
			if(this.connectedFace == 0) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
					case 1:
						return 0F;
					case 2:
						return 0F;
					case 3:
						return RenderUtils.rightAngle * 2;
					case 4:
						return -(RenderUtils.rightAngle * 1);
					case 5:
						return RenderUtils.rightAngle * 1;
				}
			}
			if(this.connectedFace == 1) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
					case 1:
						return 0F;
					case 2:
						return 0F;
					case 3:
						return RenderUtils.rightAngle * 2;
					case 4:
						return RenderUtils.rightAngle * 1;
					case 5:
						return -(RenderUtils.rightAngle * 1);
				}
			}
			if(this.connectedFace == 2) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
						return RenderUtils.rightAngle * 2;
					case 1:
						return 0F;
					case 2:
					case 3:
						return 0F;
					case 4:
						return RenderUtils.rightAngle * 1;
					case 5:
						return -(RenderUtils.rightAngle * 1);
				}
			}
			if(this.connectedFace == 3) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
						return 0F;
					case 1:
						return RenderUtils.rightAngle * 2;
					case 2:
					case 3:
						return 0F;
					case 4:
						return -(RenderUtils.rightAngle * 1);
					case 5:
						return RenderUtils.rightAngle * 1;
				}
			}
			if(this.connectedFace == 4) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
						return RenderUtils.rightAngle * 1;
					case 1:
						return -(RenderUtils.rightAngle * 1);
					case 2:
						return 0F;
					case 3:
						return RenderUtils.rightAngle * 2;
					case 4:
					case 5:
						return 0F;
				}
			}
			if(this.connectedFace == 5) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
						return -(RenderUtils.rightAngle * 1);
					case 1:
						return RenderUtils.rightAngle * 1;
					case 2:
						return 0F;
					case 3:
						return RenderUtils.rightAngle * 2;
					case 4:
					case 5:
						return 0F;
				}
			}
		}
		
		return 0F;
	}
	
	public float getNodeRotationTo(TileEntityNodeBase node) {
		if(this.isNodeAccessible(node)) {
			if(this.connectedFace == 0) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
						return RenderUtils.rightAngle * 1;
					case 1:
						return -(RenderUtils.rightAngle * 1);
				}
			} else if(this.connectedFace == 1) {
				switch(this.getSideNodeIsOn(node)) {
					case 0:
						return -(RenderUtils.rightAngle * 1);
					case 1:
						return RenderUtils.rightAngle * 1;
				}
			} else if(this.connectedFace == 2) {
				switch(this.getSideNodeIsOn(node)) {
					case 3:
						return -(RenderUtils.rightAngle * 1);
				}
			} else if(this.connectedFace == 3) {
				switch(this.getSideNodeIsOn(node)) {
					case 2:
						return -(RenderUtils.rightAngle * 1);
				}
			} else if(this.connectedFace == 4) {
				switch(this.getSideNodeIsOn(node)) {
					case 4:
						return RenderUtils.rightAngle * 1;
					case 5:
						return -(RenderUtils.rightAngle * 1);
				}
			} else if(this.connectedFace == 5) {
				switch(this.getSideNodeIsOn(node)) {
					case 4:
						return -(RenderUtils.rightAngle * 1);
					case 5:
						return RenderUtils.rightAngle * 1;
				}
			}
		}
		
		return 0F;
	}
	
	public boolean isNodeAccessible(TileEntityNodeBase node) {
		if(this.connectedFace == this.getSideNodeIsOn(node))
			return false;
		
		return(this.canSendTo(node));
	}
	
	public int getSideNodeIsOn(TileEntityNodeBase node) {
		Pos3D pos = this.getDirectionToNode(node);
		
		if(pos.xCoord() == -1)
			return 4;
		if(pos.xCoord() == 1)
			return 5;
		if(pos.yCoord() == -1)
			return 0;
		if(pos.yCoord() == 1)
			return 1;
		if(pos.zCoord() == -1)
			return 2;
		if(pos.zCoord() == 1)
			return 3;
		
		return -1;
	}
	
	public void setConnectedSide(int side) {
		this.connectedFace = side;
	}
	
	public int getConnectedSide() {
		return this.connectedFace;
	}
	
	public int getRedstoneStatus() {
		return this.redstoneStatus;
	}
	
	public void setRedstoneStatus(int red) {
		this.redstoneStatus = red;
	}
	
	public void setOutputMode(int mode) {
		this.outputMode = mode;
	}
	
	public int getOutputMode() {
		return this.outputMode;
	}
	
	public boolean doesRedstonePermitAction() {
		if(this.redstoneStatus == 2 && this.redstonePulses > 0)
			return true;
		
		if(this.redstoneStatus == 0 && !this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
			return true;
		if(this.redstoneStatus == 1 && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
			return true;
		
		return false;
	}
	
	public void incrementRedstonePulses() {
		this.redstonePulses++;
	}
	
	public void decrementRedstonePulses() {
		this.redstonePulses--;
	}
	
	public void setBufferSize(int size) {
		this.bufferSize = size;
		this.buffer = (new ItemStack[size]);
	}
	
	public int getBufferSize() {
		return this.bufferSize;
	}
	
	public ItemStack getBufferItem(int index) {
		if(this.hasBuffer())
			return this.getBuffer()[index];
		
		return null;
	}
	
	public ItemStack[] getBuffer() {
		return this.buffer;
	}
	
	public void insertIntoBuffer(ItemStack item) {
		if(this.canAcceptInput(item))
			ItemStackHelper.instance.tryToFillInvWithItem(this.getBuffer(), item);
		
		if(item.stackSize != 0 || !this.canAcceptInput(item)) {
			if(!this.worldObj.isRemote) {
				EntityItem entity = new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, item);
				this.worldObj.spawnEntityInWorld(entity);
			}
		}
	}
	
	public boolean hasBuffer() {
		return(this.bufferSize != 0);
	}
	
	public ItemStack[] getColours() {
		return this.colours;
	}
	
	public ItemStack[] getFilter() {
		return this.filter;
	}
	
	public ItemStack[] getExtraStorage() {
		return this.extraStorage;
	}
	
	public Pos3D getConnectedBlock() {
		return new Pos3D(this.xCoord + Facing.offsetsXForSide[this.connectedFace], this.yCoord + Facing.offsetsYForSide[this.connectedFace], this.zCoord + Facing.offsetsZForSide[this.connectedFace]);
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setPriority(int p) {
		int priority = this.getPriority() + p;
		if(priority >= 10)
			priority = 1;
		if(priority <= 0)
			priority = 9;
		this.priority = priority;
	}
	
	public void addRequest(NodeRequestDetails request) {
		this.requests.add(request);
	}
	
	public boolean canAcceptInput() {
		if(this.getBuffer() != null && isInput) {
			for(int i = 0; i < this.getBufferSize(); i++) {
				if(this.getBufferItem(i) == null) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canAcceptInput(ItemStack item) {
		return ItemStackHelper.getInstance().canItemStackFitIntoInventory(this.getBuffer(), item);
	}
	
	public boolean canSendOutput() {
		if(isOutput) {
			return true;
		}
		return false;
	}
	
	public boolean canCompleteRequest(NodeRequestDetails request) {
		if(ItemStackHelper.getInstance().doesInventoryContainItemStack(this.getBuffer(), request.getItem())) {
			ArrayList<TileEntityNodeBase> adjNodes = this.getAccessibleNodesArray();
			
			for(int i = 0; i < adjNodes.size(); i++) {
				if(!adjNodes.get(i).connectsToNodeAt(request.getDestination())) {
					adjNodes.remove(i);
					i++;
				}
			}
			
			if(!adjNodes.isEmpty())
				return true;
		}
		return false;
	}
	
	public TileEntityNodeBase[] getAccessibleNodes() {
		return this.getAccessibleNodesArray().toArray(new TileEntityNodeBase[this.getAccessibleNodesArray().size()]);
	}
	
	public ArrayList<TileEntityNodeBase> getAccessibleNodesArray() {
		ArrayList<TileEntityNodeBase> nodes = new ArrayList<TileEntityNodeBase>();
		
		for(int i = 0; i < 31; i++) {
			TileEntity node = this.worldObj.getTileEntity((this.xCoord - 15) + i, this.yCoord, this.zCoord);
			if(node != null && node instanceof TileEntityNodeBase && node != this) {
				nodes.add((TileEntityNodeBase)node);
			}
			
			node = this.worldObj.getTileEntity(this.xCoord, (this.yCoord - 15) + i, this.zCoord);
			if(node != null && node instanceof TileEntityNodeBase && node != this) {
				nodes.add((TileEntityNodeBase)node);
			}
			
			node = this.worldObj.getTileEntity(this.xCoord, this.yCoord, (this.zCoord - 15) + i);
			if(node != null && node instanceof TileEntityNodeBase && node != this) {
				nodes.add((TileEntityNodeBase)node);
			}
		}
		
		for(int i = 0; i < nodes.size(); i++) {
			if(!this.canSendTo(nodes.get(i))) {
				nodes.remove(i);
			}
		}
		
		return nodes;
	}
	
	public boolean isNodeAt(Pos3D pos) {
		if(this.worldObj.getTileEntity(pos.xCoord(), pos.yCoord(), pos.zCoord()) != null) {
			return this.worldObj.getTileEntity(pos.xCoord(), pos.yCoord(), pos.zCoord()) instanceof TileEntityNodeBase;
		}
		return false;
	}
	
	public TileEntityNodeBase getNodeAt(Pos3D pos) {
		if(this.isNodeAt(pos)) {
			return (TileEntityNodeBase)this.worldObj.getTileEntity(pos.xCoord(), pos.yCoord(), pos.zCoord());
		}
		return null;
	}
	
	public TileEntityNodeBase getNodeToSendTo() {
		ArrayList<TileEntityNodeBase> adjNodes = this.getAccessibleNodesArray();
		
		for(int i = 0; i < this.requests.size(); i++) {
			if(this.canCompleteRequest(this.requests.get(i))) {
				for(int j = 0; j < adjNodes.size(); j++) {
					if(!adjNodes.get(j).canAcceptInput(this.requests.get(i).getItem()) || !adjNodes.get(j).connectsToNodeAt(this.requests.get(i).getDestination())) {
						adjNodes.remove(j);
						j++;
					}
					
					if(adjNodes.get(j).colourCode != this.colourTarget) {
						adjNodes.remove(j);
						j++;
					}
				}
				
				if(!adjNodes.isEmpty()) {
					if(adjNodes.size() != 1) {
						return this.getHighestPriorityFromList(adjNodes);
					} else {
						return adjNodes.get(0);
					}
				}
			}
		}
		
		adjNodes = this.getAccessibleNodesArray();
		if(adjNodes.isEmpty())
			return null;
		return(this.getHighestPriorityFromList(adjNodes));
	}
	
	public boolean isItemInFilter(ItemStack item) {
		if(ItemStackHelper.instance.isInventoryEmpty(this.filter))
			return true;
		
		switch(this.outputMode) {
			case 0:
				return(ItemStackHelper.instance.isItemInInventory(this.filter, item));
			case 1:
				return !(ItemStackHelper.instance.isItemInInventory(this.filter, item));
		}
		
		return false;
	}
	
	public TileEntityNodeBase getHighestPriorityFromList(ArrayList<TileEntityNodeBase> nodes) {
		int highIndex = 0;
		
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).getPriority() > nodes.get(highIndex).getPriority())
				highIndex = i;
			
			if(nodes.get(i).getPriority() == nodes.get(highIndex).getPriority()) {
				int rand = this.worldObj.rand.nextInt(2);
				if(rand == 0)
					highIndex = i;
			}
		}
		
		return nodes.get(highIndex);
	}
	
	public boolean isAdjacentTo(Pos3D pos) {
		if(this.isNodeAt(pos)) {
			ArrayList<TileEntityNodeBase> nodes = this.getAccessibleNodesArray();
			
			if(nodes.contains(this.getNodeAt(pos)))
				return true;
		}
		return false;
	}
	
	public int getDistanceToNode(Pos3D pos) {
		ArrayList<TileEntityNodeBase> nodes = new ArrayList<TileEntityNodeBase>();
		
		nodes.add(this.getNodeAt(pos));
		
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < nodes.size(); j++) {
				ArrayList<TileEntityNodeBase> adjNodes = nodes.get(j).getAccessibleNodesArray();
				for(int k = 0; k < adjNodes.size(); k++) {
					if(adjNodes.get(j) == this)
						return i;
					
					if(!nodes.contains(adjNodes.get(k))) {
						nodes.add(adjNodes.get(k));
					}
				}
			}
		}
		return -1;
	}
	
	public boolean connectsToNodeAt(Pos3D pos) {
		ArrayList<TileEntityNodeBase> nodes = new ArrayList<TileEntityNodeBase>();
		
		nodes.add(this.getNodeAt(pos));
		
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < nodes.size(); j++) {
				ArrayList<TileEntityNodeBase> adjNodes = nodes.get(j).getAccessibleNodesArray();
				for(int k = 0; k < adjNodes.size(); k++) {
					if(adjNodes.get(j) == this)
						return true;
					
					if(!nodes.contains(adjNodes.get(k))) {
						nodes.add(adjNodes.get(k));
					}
				}
			}
		}
		return false;
	}
	
	public boolean canSendTo(TileEntityNodeBase node) {
		Pos3D dirToNode = this.getDirectionToNode(node);
		Pos3D dir = new Pos3D(this.xCoord + dirToNode.xCoord(), this.yCoord + dirToNode.yCoord(), this.zCoord + dirToNode.zCoord());
		
		if(node == null)
			return false;
		
		if(node.getPos3D().isSamePos(this.lastNodeReceivedFrom))
			return false;
		
		if(!NodeTransportHelper.isDestinationReachable(new Pos3D(node.xCoord, node.yCoord, node.zCoord), dir, this.worldObj))
			return false;
		
		if(!node.isInput)
			return false;
		
		if(node.colourCode != this.colourTarget)
			return false;
		
		return true;
	}
	
	public Pos3D getDirectionToNode(TileEntityNodeBase node) {
		if(node != null && this != null) {
			if(node.xCoord == this.xCoord && node.yCoord == this.yCoord)
				if(node.zCoord > this.zCoord)
					return new Pos3D(0, 0, 1);
				else
					return new Pos3D(0, 0, -1);
			
			if(node.xCoord == this.xCoord && node.zCoord == this.zCoord)
				if(node.yCoord > this.yCoord)
					return new Pos3D(0, 1, 0);
				else
					return new Pos3D(0, -1, 0);
			
			if(node.zCoord == this.zCoord && node.yCoord == this.yCoord)
				if(node.xCoord > this.xCoord)
					return new Pos3D(1, 0, 0);
				else
					return new Pos3D(-1, 0, 0);
		}
		
		return new Pos3D(0, 0, 0);
	}
	
	public Pos3D getPos3D() {
		return new Pos3D(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public int getSizeInventory() {
		return this.getBufferSize();
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		if(i == codeSlotID)
			return this.colours[0];
		if(i == targetSlotID)
			return this.colours[1];
		
		if(i >= filterSlotIDs && i < filterSlotIDs + 9) {
			return this.filter[i - filterSlotIDs];
		}
		
		return this.getBufferItem(i);
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(i == codeSlotID || i == targetSlotID) {
			int slot = (i == codeSlotID ? 0 : 1);
			
			ItemStack var3;
			
			if(this.colours[slot].stackSize <= j) {
				var3 = this.colours[slot];
				this.colours[slot] = null;
				return var3;
			} else {
				var3 = this.colours[slot].splitStack(j);
				
				if(this.colours[slot].stackSize == 0) {
					this.colours[slot] = null;
				}
				
				return var3;
			}
		} else if(i >= filterSlotIDs && i < filterSlotIDs + 9) {
			ItemStack var3;
			
			if(this.filter[i - filterSlotIDs].stackSize <= j) {
				var3 = this.filter[i - filterSlotIDs];
				this.filter[i - filterSlotIDs] = null;
				return var3;
			} else {
				var3 = this.filter[i - filterSlotIDs].splitStack(j);
				
				if(this.filter[i - filterSlotIDs].stackSize == 0) {
					this.filter[i - filterSlotIDs] = null;
				}
				
				return var3;
			}
		} else if(this.getBufferItem(i) != null) {
			ItemStack var3;
			
			if(this.buffer[i].stackSize <= j) {
				var3 = this.buffer[i];
				this.buffer[i] = null;
				return var3;
			} else {
				var3 = this.buffer[i].splitStack(j);
				
				if(this.buffer[i].stackSize == 0) {
					this.buffer[i] = null;
				}
				
				return var3;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(i == codeSlotID)
			return this.colours[0];
		if(i == targetSlotID)
			return this.colours[1];
		
		if(i >= filterSlotIDs && i < filterSlotIDs + 9) {
			return this.filter[i - filterSlotIDs];
		}
		
		if(this.getBufferItem(i) != null) {
			ItemStack var2 = this.getBufferItem(i);
			this.buffer[i] = null;
			return var2;
		} else {
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if(i == codeSlotID) {
			this.colours[0] = itemstack;
			return;
		}
		if(i == targetSlotID) {
			this.colours[1] = itemstack;
			return;
		}
		
		if(i >= filterSlotIDs && i < filterSlotIDs + 9) {
			this.filter[i - filterSlotIDs] = itemstack;
			return;
		}
		
		this.buffer[i] = itemstack;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	public String getInventoryName() {
		return "container.node.base";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {

	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}
}