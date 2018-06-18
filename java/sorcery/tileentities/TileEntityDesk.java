package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import sorcery.api.ISpellCollection;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.api.spellcasting.SpellComponentBase.ComponentType;
import sorcery.api.spellcasting.SpellComponentBase.SpellAction;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;
import sorcery.api.spellcasting.SpellComponentBase.SpellEffect;
import sorcery.api.spellcasting.SpellComponentBase.SpellFocus;
import sorcery.api.spellcasting.Wand;
import sorcery.api.spellcasting.WandComponent;
import sorcery.api.spellcasting.WandComponent.WandComponentBody;
import sorcery.api.spellcasting.WandComponent.WandComponentCore;
import sorcery.api.spellcasting.WandComponent.WandComponentGenerator;
import sorcery.core.Sorcery;
import sorcery.items.ItemColourTag;
import sorcery.items.ItemSpellPage;
import sorcery.items.ItemSpellTome;
import sorcery.items.ItemSpellbook;
import sorcery.items.ItemWand;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.Pos3D;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.network.TileEntityPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityDesk extends TileEntitySorcery implements IInventory {

	public boolean isDummy;
	public Pos3D otherHalfPos;
	public int rotation;
	public int cooldown = 0;
	public boolean isInUse = false;
	
	/**0 = wandcrafting, 1 = book writing, 2 = spellcrafting */
	public int currentActivity;
	
	public ItemStack[] IconItems = new ItemStack[3];
	
	public ItemStack[][] inventories = new ItemStack[][] {
			new ItemStack[4],
			new ItemStack[4],
			new ItemStack[4]
	};
	
	/** Wandcrafting Fields */
	public Wand wand;
	
	/** Spellbook Fields */
	public ItemStack spellbook;
	public ArrayList<Spell> spells = new ArrayList<Spell>();
	
	/** Spellcrafting Fields */
	public Spell spell;
	public boolean spellIsComplete;

	@Override
	public void updateEntity() {
		if(this.getOtherHalf() == null && !this.worldObj.isRemote) {
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		}
		
		if(this.isDummy)
			return;
		
		if(this.currentActivity < 0 || this.currentActivity > 2)
			this.currentActivity = 0;
		
		if(this.cooldown != 0)
			this.cooldown--;
			
		switch(this.currentActivity) {
			case 0: this.doWandCraftingUpdate();
				break;
			case 1: this.doBookWritingUpdate();
				break;
			case 2: this.doSpellCraftingUpdate();
				break;
		}
		
		for(int i = 0; i < 3; i++) {
			if(this.inventories[i][0] != null)
				this.IconItems[i] = this.inventories[i][0].copy();
		}
		
		if(this.inventories[0][0] == null)
			this.IconItems[0] = new ItemStack(SorceryItems.wand);
		
		if(this.inventories[1][0] == null)
			this.IconItems[1] = new ItemStack(SorceryItems.spellbook, 1, 8);
		
		if(this.inventories[2][0] == null)
			this.IconItems[2] = new ItemStack(SorceryItems.spellPage);
	}
	
	public void doWandCraftingUpdate() {
		if(this.wand != null) {
			this.setInventorySlotContents(0, ItemWand.getItemStackWithWand(this.wand));
		}
		
		if(this.getStackInSlot(0) == null) {
			this.wand = null;
		}
		
		if(this.wand == null && this.getStackInSlot(0) != null) {
			if(this.getStackInSlot(0).getItem() instanceof ItemWand) {
				this.wand = ItemWand.getWandFromItem(this.getStackInSlot(0));
			}
		}
	}
	
	public void doBookWritingUpdate() {
		if(this.spellbook == null && this.getStackInSlot(0) != null) {
			if(this.getStackInSlot(0).getItem() instanceof ISpellCollection) {
				this.doBookWritingChange();
			}
		}

		if(this.spellbook != null && this.getStackInSlot(0) != null && this.spells != null) {
			((ISpellCollection)this.spellbook.getItem()).setSpells(this.spellbook, this.spells.toArray(new Spell[0]));
			((ISpellCollection)this.getStackInSlot(0).getItem()).setSpells(
					this.getStackInSlot(0), this.spells.toArray(new Spell[0]));
		}
	}
	
	public void doSpellCraftingUpdate() {
	}
	
	@Override
	public void markDirty() {
		switch(this.currentActivity) {
			case 0: this.doWandCraftingChange();
				break;
			case 1: this.doBookWritingChange();
				break;
			case 2: this.doSpellCraftingChange();
				break;
		}
    }
	
	public void doWandCraftingChange() {
		if(this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem() instanceof ItemWand && this.wand == null) {
			Wand wand = ItemWand.getWandFromItem(this.getStackInSlot(0));
			this.wand = wand;
			
			ItemStack body = wand.getBody();
			ItemStack core = wand.getCore();
			ItemStack generator = wand.getGenerator();
			ItemStack colour =  wand.getColour() == -1 ? null : new ItemStack(SorceryItems.colourTag, 1, wand.getColour());
			
			if(this.getStackInSlot(1) == null && this.getStackInSlot(2) == null && this.getStackInSlot(3) == null) {
				this.setInventorySlotContents(1, core);
				this.setInventorySlotContents(2, generator);
				this.setInventorySlotContents(3, colour);
			}
		}
		
		if(this.getStackInSlot(0) == null && this.wand != null) {
			if(this.getStackInSlot(1) != null) {
				ItemStack core = this.getStackInSlot(1);
				if(this.wand.core != null) {
					if(ItemStackHelper.getInstance().areItemsEqual(core, this.wand.core.item))
						this.decrStackSize(1, 1);
				}
			}
			
			if(this.getStackInSlot(2) != null) {
				ItemStack generator = this.getStackInSlot(2);
				if(this.wand.generator != null) {
					if(ItemStackHelper.getInstance().areItemsEqual(generator, this.wand.generator.item))
						this.decrStackSize(2, 1);
				}
			}
			
			if(this.getStackInSlot(3) != null) {
				ItemStack colour = this.getStackInSlot(3);
					if(colour.getItemDamage() == this.wand.colour)
						this.decrStackSize(3, 1);
			}
			this.wand = null;
		}
		
		if(this.getStackInSlot(0) != null && this.wand != null) {
			if(this.getStackInSlot(1) == null && this.getStackInSlot(2) == null) {
				if(this.wand.body != null) {
					this.setInventorySlotContents(0, this.wand.body.item);
					this.wand = null;
				}
			}
		}
		
		if(this.wand != null) {
			if(this.getStackInSlot(1) == null && this.wand.core != null) {
				this.wand.core = null;
			} else if(this.getStackInSlot(1) != null && this.wand.core == null) {
				if(WandComponent.isItemWandCore(this.getStackInSlot(1))) {
					this.wand.core = (WandComponentCore)WandComponent.getWandComponentFromItem(this.getStackInSlot(1));
				}
			}
			
			if(this.getStackInSlot(2) == null && this.wand.generator != null) {
				this.wand.generator = null;
			} else if(this.getStackInSlot(2) != null && this.wand.generator == null) {
				if(WandComponent.isItemWandGenerator(this.getStackInSlot(2))) {
					this.wand.generator = (WandComponentGenerator)WandComponent.getWandComponentFromItem(this.getStackInSlot(2));
				}
			}
			
			if(this.getStackInSlot(3) == null && this.wand.colour != -1) {
				this.wand.colour = -1;
			} else if(this.getStackInSlot(3) != null && this.wand.colour == -1) {
				if(this.getStackInSlot(3).getItem() instanceof ItemColourTag)
					this.wand.colour = this.getStackInSlot(3).getItemDamage();
			}
		}
		
		if(this.wand == null) {
			if(this.getStackInSlot(0) != null && WandComponent.isItemWandBody(this.getStackInSlot(0))) {
				if(this.getStackInSlot(2) != null && WandComponent.isItemWandGenerator(this.getStackInSlot(2))) {
					this.wand = new Wand();
					this.wand.body = (WandComponentBody)WandComponent.getWandComponentFromItem(this.getStackInSlot(0));
					this.setInventorySlotContents(0, ItemWand.getItemStackWithWand(this.wand));
				}
			}
		}
	}
	
	public void doBookWritingChange() {
		if(this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem() instanceof ISpellCollection) {
			if(this.spellbook == null) {
				this.spellbook = this.getStackInSlot(0);
				
				Spell[] spells = ((ISpellCollection)this.spellbook.getItem()).getSpells(this.spellbook);
				this.spells = new ArrayList<Spell>();
				for(int i = 0; i < spells.length; i++) {
					this.spells.add(spells[i]);
				}
				int colour = ((ItemSpellbook)SorceryItems.spellbook).getColour(this.spellbook);
				if(colour != 0 && !(this.spellbook.getItem() instanceof ItemSpellTome))
					this.setInventorySlotContents(3, new ItemStack(SorceryItems.colourTag, 0, colour - 1));
				
			}
			if(!(this.spellbook.getItem() instanceof ItemSpellTome)) {
				if(((ItemSpellbook)SorceryItems.spellbook).hasColour(this.spellbook)) {
					if(this.getStackInSlot(3) == null)
						((ItemSpellbook)SorceryItems.spellbook).setColour(this.spellbook, -1);
				}
				
				if(this.getStackInSlot(3) != null)
					((ItemSpellbook)SorceryItems.spellbook).setColour(this.spellbook, this.getStackInSlot(3).getItemDamage());
				
				if(this.getStackInSlot(3) == null) {
					((ItemSpellbook)SorceryItems.spellbook).setColour(this.spellbook, -1);
				}
			}
			
			if(this.getStackInSlot(1) != null && this.getStackInSlot(0) != null && this.spells != null) {
				if(this.getStackInSlot(1).getItem() instanceof ISpellCollection) {
					Spell[] spells = ((ISpellCollection)this.getStackInSlot(1).getItem()).removeSpells(this.getStackInSlot(1));
					for(int i = 0; i < spells.length; i++)
						this.spells.add(spells[i]);
					
					if(this.getStackInSlot(1) != null && this.getStackInSlot(1).stackSize == 0)
						this.setInventorySlotContents(1, null);
				}
			}
			
			this.setInventorySlotContents(0, this.spellbook);
		}
		
		if(this.getStackInSlot(0) == null) {
			if(this.spellbook != null && ((ItemSpellbook)SorceryItems.spellbook).hasColour(this.spellbook))
				if(this.getStackInSlot(3) != null)
					this.decrStackSize(3, 1);
			
			this.spellbook = null;
			this.spells = null;
		}
	}
	
	public void doSpellCraftingChange() {
		
	}
	
	public void moveSpell(int spellNo, int dir) {
		if(spellNo == -1) return;
		Spell spell = this.spells.get(spellNo);
		Spell spell2 = this.spells.get(spellNo + dir);
		this.spells.set(spellNo, spell2);
		this.spells.set(spellNo + dir, spell);
	}
	
	public void removeSpell(int spellNo) {
		if(spellNo == -1) return;
		Spell spell = this.spells.get(spellNo);
		this.spells.remove(spellNo);
		
		if(this.getStackInSlot(2) == null) {
			this.setInventorySlotContents(2, new ItemStack(SorceryItems.spellPage));
		}
		
		((ISpellCollection)this.getStackInSlot(2).getItem()).addSpell(this.getStackInSlot(2), spell);
	}
	
	public void copySpell(int spellNo) {
		Spell spell = this.spells.get(spellNo);
		
		if(this.getStackInSlot(2) == null) {
			this.setInventorySlotContents(2, new ItemStack(SorceryItems.spellPage));
		}
		
		((ISpellCollection)this.getStackInSlot(2).getItem()).addSpell(this.getStackInSlot(2), spell);
		this.decrStackSize(1, 1);
	}
	
	public void sendDataPacket(byte id, int... data) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("id", id);
		for(int i = 0; i < data.length; i++)
			tag.setInteger("data" + i, data[i]);
			
		this.sendNBTPacket(tag);
	}
	
	public void handlePacketData(NBTTagCompound tag, boolean isClient) {
		int id = tag.getInteger("id");
		switch(id) {
			case 0:
				this.currentActivity = tag.getInteger("data0");
				this.markDirty();
				break;
			case 1:
				this.isInUse = false;
				break;
			case 2:
				this.moveSpell(tag.getInteger("data0"), tag.getInteger("data1"));
				break;
			case 3:
				this.removeSpell(tag.getInteger("data0"));
				break;
			case 4:
				this.copySpell(tag.getInteger("data0"));
				break;
			case 5:
				this.setSpell(tag);
				break;
			case 6:
				int levelCost = tag.getInteger("cost");
				EntityPlayer player = this.worldObj.getPlayerEntityByName(tag.getString("name"));
				if(player != null)
					player.experienceLevel -= levelCost;
				break;
		}
	}
	
	public void sendPlayerPacket(int data) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("data", data);
		
		TileEntityPacket packet = new TileEntityPacket(tag, this.xCoord, this.yCoord, this.zCoord);
		packet.setSpecialID(TileEntityPacket.PLAYER_ID);
		if(this.worldObj.isRemote)
			Sorcery.packetPipeline.sendToServer(packet);
		else
			Sorcery.packetPipeline.sendToAll(packet);
	}
	
	public void handlePlayerPacket(EntityPlayer player, NBTTagCompound data, boolean isClient) {
		this.currentActivity = data.getInteger("data");
		player.openContainer = player.inventoryContainer;
		if(!this.worldObj.isRemote)
			player.openGui(Sorcery.instance, Properties.GUI_DESK, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void setSpell(NBTTagCompound tag) {
		if(this.spell == null)
			this.spell = new Spell();
		this.spellIsComplete = tag.getBoolean("complete");
		
		int field = tag.getInteger("field");
		if(field == 0) {
			this.spell.name = tag.getString("name");
		} else if(field != 3 && field != 7) {
			NBTTagList list = tag.getTagList("components", 10);
			String[] components = new String[list.tagCount()];
			for(int i = 0; i < list.tagCount(); i++) {
				components[i] = list.getCompoundTagAt(i).getString("component");
			}
			
			if(this.spell == null)
				this.spell = new Spell();
			
			if(components.length == 0)
				return;
			
			switch(field) {
				case 1:
					if(SpellComponentBase.getComponentByName(components[0]) != null &&
							SpellComponentBase.getComponentByName(components[0]).getType() == ComponentType.action)
						this.spell.action = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(components[0]));
					break;
				case 2:
					if(SpellComponentBase.getComponentByName(components[0]) != null &&
							SpellComponentBase.getComponentByName(components[0]).getType() == ComponentType.focus) {
						this.spell.focus = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(components[0]));
						
						if(list.getCompoundTagAt(0).getBoolean("custom"))
							this.spell.focus.component.loadCustomDataFromNBT(this.spell.focus, list.getCompoundTagAt(0).getCompoundTag("customData"));
					}
					break;
				case 4:
					if(this.spell.focus != null) {
						ArrayList<SpellAdjective> temp = new ArrayList<SpellAdjective>();
						
						for(int i = 0; i < list.tagCount(); i++) {
							components[i] = list.getCompoundTagAt(i).getString("component");
							if(SpellComponentBase.getComponentByName(components[i]) != null &&
									SpellComponentBase.getComponentByName(components[i]).getType() == ComponentType.focusAdj) {
								
								temp.add((SpellAdjective)SpellComponentBase.getComponentByName(components[i]));
							}
						}
						
						this.spell.focus.adjectives = temp.toArray(new SpellAdjective[temp.size()]);
					}
					break;
					
				case 5:
					if(SpellComponentBase.getComponentByName(components[0]) != null &&
							SpellComponentBase.getComponentByName(components[0]).getType() == ComponentType.effect)
						this.spell.effect = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(components[0]));
					break;
				case 6:
					if(this.spell.effect != null && SpellComponentBase.getComponentByName(components[0]) != null &&
							SpellComponentBase.getComponentByName(components[0]).getType() == ComponentType.effectCondition)
						this.spell.effect.condition = (SpellAdjective)SpellComponentBase.getComponentByName(components[0]);
					break;
			}
		} else {
			if(field == 3 && this.spell.focus != null) {
				this.spell.focus.magnitude = tag.getInteger("magnitude");
			} else if(field == 7 && this.spell.effect != null) {
				this.spell.effect.magnitude = tag.getInteger("magnitude");
			}
		}
		
		if(this.spell != null && this.spellIsComplete &&
				this.spell.action != null &&
				this.spell.focus != null && this.spell.focus.adjectives != null &&
				this.spell.focus.adjectives.length != 0 && this.spell.focus.magnitude != 0 &&
				this.spell.name != null && !this.spell.name.isEmpty() &&
				this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == Items.paper) {
			ItemStack item = new ItemStack(SorceryItems.spellPage, 1);
			((ItemSpellPage)SorceryItems.spellPage).addSpell(item, this.spell);
			this.inventories[2][2] = item;
		} else {
			this.inventories[2][2] = null;
		}
	}
	
	public TileEntityDesk getOtherHalf() {
		if(this.otherHalfPos == null)
			return null;
		
		int x = this.otherHalfPos.xCoord();
		int y = this.otherHalfPos.yCoord();
		int z = this.otherHalfPos.zCoord();
		
		if(this.worldObj.getTileEntity(x, y, z) == null)
			return null;
		
		return (TileEntityDesk)this.worldObj.getTileEntity(x, y, z);
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.cooldown != 0)
			return null;
		
		if(this.inventories[this.currentActivity][i] != null) {
			ItemStack var3;
			
			if(this.inventories[this.currentActivity][i].stackSize <= j) {
				var3 = this.inventories[this.currentActivity][i];
				this.inventories[this.currentActivity][i] = null;
				return var3;
			} else {
				var3 = this.inventories[this.currentActivity][i].splitStack(j);
				
				if(this.inventories[this.currentActivity][i].stackSize == 0) {
					this.inventories[this.currentActivity][i] = null;
				}
				
				return var3;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int par1) {
		if(this.cooldown != 0)
			return null;
		
		if(this.inventories[this.currentActivity][par1] != null) {
			ItemStack var2 = this.inventories[this.currentActivity][par1];
			this.inventories[this.currentActivity][par1] = null;
			return var2;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		if(this.cooldown != 0)
			return;
		
		this.inventories[this.currentActivity][par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public ItemStack getStackInSlot(int var8) {
		if(this.cooldown != 0)
			return null;
		
		return this.inventories[this.currentActivity][var8];
	}
	
	@Override
	public String getInventoryName() {
		return "desk";
	}
	
	public int getSizeInventory() {
		return this.inventories[this.currentActivity].length;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord - 2.0F, this.yCoord - 2.0F, this.zCoord - 2.0F,
				this.xCoord + 2.0F, this.yCoord + 2.0F, this.zCoord + 2.0F);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.rotation = par1NBTTagCompound.getInteger("side");
		this.isDummy = par1NBTTagCompound.getBoolean("dummy");
		this.otherHalfPos = Pos3D.readFromNBT(par1NBTTagCompound, "otherHalf");
		this.currentActivity = par1NBTTagCompound.getInteger("activity");
		
		NBTTagList items = par1NBTTagCompound.getTagList("inv0", 10);
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)items.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.inventories[0].length) {
				this.inventories[0][j] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		
		items = par1NBTTagCompound.getTagList("inv1", 10);
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)items.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.inventories[1].length) {
				this.inventories[1][j] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		
		items = par1NBTTagCompound.getTagList("inv2", 10);
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)items.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.inventories[2].length) {
				this.inventories[2][j] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		if(!par1NBTTagCompound.getCompoundTag("spell").hasNoTags())
			this.spell = Spell.readFromNBT(par1NBTTagCompound.getCompoundTag("spell"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("side", this.rotation);
		par1NBTTagCompound.setBoolean("dummy", this.isDummy);
		par1NBTTagCompound.setInteger("activity", this.currentActivity);
		this.otherHalfPos.writeToNBT(par1NBTTagCompound, "otherHalf");
		
		NBTTagList items = new NBTTagList();
		for(int i = 0; i < this.inventories[0].length; i++) {
			if(this.inventories[0][i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.inventories[0][i].writeToNBT(tag);
				items.appendTag(tag);
			}
		}
		par1NBTTagCompound.setTag("inv0", items);
		
		items = new NBTTagList();
		for(int i = 0; i < this.inventories[1].length; i++) {
			if(this.inventories[1][i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.inventories[1][i].writeToNBT(tag);
				items.appendTag(tag);
			}
		}
		par1NBTTagCompound.setTag("inv1", items);
		
		items = new NBTTagList();
		for(int i = 0; i < this.inventories[2].length; i++) {
			if(this.inventories[2][i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.inventories[2][i].writeToNBT(tag);
				items.appendTag(tag);
			}
		}
		par1NBTTagCompound.setTag("inv2", items);
		
		if(this.spell != null)
			par1NBTTagCompound.setTag("spell", this.spell.writeToNBT());
	}
}
