package sorcery.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import sorcery.lib.NodeTransportHelper.NodeRequestDetails;
import sorcery.lib.Pos3D;
import sorcery.tileentities.nodes.TileEntityNodeBase;

public class EntityNodeItem extends Entity {
	public Pos3D[] directions;
	public Pos3D origin;
	private EntityItem item;
	public int rotation;
	public int colourCode;
	private boolean isRequest;
	public boolean isDeInit;
	public int deInitTicks;
	
	public EntityNodeItem(World par1World) {
		super(par1World);
		this.setSize(0.5F, 0.5F);
	}
	
	@Override
	protected void entityInit() {
	}
	
	public EntityItem getItemEntity() {
		return this.item;
	}
	
	public void insertItemIntoNode(TileEntityNodeBase node) {
		node.insertIntoBuffer(item.getEntityItem());
		if(this.isRequest)
			node.addRequest(new NodeRequestDetails(this.item.getEntityItem(), this.directions));
	}
	
	public void setDirections(int x, int y, int z) {
		this.directions = new Pos3D[1];
		this.directions[0] = new Pos3D(x, y, z);
	}
	
	public void setDirections(int[][] directions) {
		this.directions = new Pos3D[directions.length];
		for(int i = 0; i < directions.length; i++) {
			this.directions[i] = new Pos3D(directions[i][0], directions[i][1], directions[i][2]);
		}
	}
	
	public void setDirections(Pos3D directions) {
		this.directions = new Pos3D[1];
		this.directions[0] = directions;
	}
	
	public void setDirections(Pos3D[] directions) {
		this.directions = new Pos3D[directions.length];
		for(int i = 0; i < directions.length; i++) {
			this.directions[i] = directions[i];
		}
	}
	
	public void setOrigin(TileEntityNodeBase node) {
		this.origin = new Pos3D(node.xCoord, node.yCoord, node.zCoord);
	}
	
	public void setItem(ItemStack item) {
		if(item == null)
			return;
		
		if(this.item == null)
			this.item = new EntityItem(this.worldObj);
		
		this.item.setEntityItemStack(item);
	}
	
	public void setRequest(NodeRequestDetails request) {
		this.directions = request.getDirections();
		this.isRequest = true;
	}
	
	@Override
	public void onUpdate() {
		this.rotation++;
		if(this.item != null)
			this.item.age = this.rotation;
		
		this.setPosition(this.posX + (this.motionX / 10), this.posY + (this.motionY / 10), this.posZ + (this.motionZ / 10));
		
		if(this.isDeInit)
			this.deInitTicks++;
	}
	
	public void setColourCode(int c) {
		this.colourCode = c;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.rotation = nbttagcompound.getInteger("rotation");
		this.isRequest = nbttagcompound.getBoolean("isRequest");
		this.colourCode = nbttagcompound.getInteger("colourCode");
		
		this.isDeInit = nbttagcompound.getBoolean("isDeInit");
		this.deInitTicks = nbttagcompound.getInteger("deInitTicks");
		
		this.item = new EntityItem(this.worldObj);
		this.item.readFromNBT((NBTTagCompound)nbttagcompound.getTag("item"));
		
		this.directions = new Pos3D[nbttagcompound.getTagList("directions", 10).tagCount()];
		for(int i = 0; i < (nbttagcompound.getTagList("directions", 10).tagCount()); i++) {
			NBTTagCompound tag = (NBTTagCompound)nbttagcompound.getTagList("directions", 10).getCompoundTagAt(i);
			this.directions[i] = new Pos3D(tag.getIntArray(i + ""));
		}
		
		this.origin = Pos3D.readFromNBT(nbttagcompound, "origin");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("rotation", this.rotation);
		nbttagcompound.setBoolean("isRequest", this.isRequest);
		nbttagcompound.setInteger("colourCode", this.colourCode);
		
		nbttagcompound.setBoolean("isDeInit", this.isDeInit);
		nbttagcompound.setInteger("deInitTicks", this.deInitTicks);
		
		NBTTagCompound item = new NBTTagCompound();
		
		if(this.item != null) {
			this.item.writeToNBT(item);
		}
		
		NBTTagList directions = new NBTTagList();
		
		if(this.directions != null) {
			for(int i = 0; i < this.directions.length; i++) {
				if(this.directions[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setIntArray(i + "", this.directions[i].getPos());
					directions.appendTag(tag);
				}
			}
		}
		
		if(this.origin != null)
			origin.writeToNBT(nbttagcompound, "origin");
		
		nbttagcompound.setTag("directions", directions);
		nbttagcompound.setTag("item", item);
	}
	
	public void dropItemAndKill() {
		if(!this.worldObj.isRemote) {
			EntityItem entity = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.item.getEntityItem());
			this.worldObj.spawnEntityInWorld(entity);
		}
		
		this.isDead = true;
	}
}
