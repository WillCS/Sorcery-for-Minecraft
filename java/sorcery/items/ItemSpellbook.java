package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sorcery.api.ISpellBook;
import sorcery.api.ISpellCollection;
import sorcery.api.SorceryAPI;
import sorcery.api.spellcasting.Spell;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpellbook extends ItemArcane implements ISpellBook, ISpellCollection {
	
	public ItemSpellbook() {
		setMaxStackSize(1);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	protected IIcon[] IIcons;
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "sorcery.item.spellbook";
	}
	
	@Override
	public IIcon getIconFromDamage(int i) {
		if(this.IIcons[i] != null)
			return this.IIcons[i];
		else
			return this.IIcons[0];
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[256];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellbookColour");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellbookOverlay");
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
		if(!this.hasColour(stack) && pass == 0)
			return Items.book.getIconFromDamage(0);
		if(pass == 0) return this.IIcons[0];
		else return this.IIcons[1];
	}
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int i) {
		if(this.hasColour(par1ItemStack) && i == 0)
			return ItemDye.field_150922_c[this.getColour(par1ItemStack) - 1];
		else return Utils.encodeColour(1.0F, 1.0F, 1.0F, 1.0F);
		
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 15; i++) {
			ItemStack item = new ItemStack(par1);
			this.setColour(item, i);
			par3List.add(item);
		}
		
		ItemStack testificate = new ItemStack(this);
		this.setColour(testificate, -1);
		par3List.add(testificate);
		
		ItemStack testing = new ItemStack(this);
		this.setColour(testing, 5);
		testing.setStackDisplayName("TESTING FUCK");
		for(int i = 0; i < 20; i++) {
			Spell spell = new Spell();
			spell.name = "dick" + i;
			this.addSpell(testing, spell);
		}
		par3List.add(testing);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		SorceryAPI.openSpellbookGUI(world, player);
		return item;
	}

	@Override
	public ResourceLocation getSpellbookBackground(ItemStack item) {
		return Utils.getResource(Properties.GUI_TEXTURE_FOLDER + "spellbookBackground.png");
	}

	@Override
	public ResourceLocation getSpellbookForeground(ItemStack item) {
		return Utils.getResource(Properties.GUI_TEXTURE_FOLDER + "spellbookPage.png");
	}

	@Override
	public boolean drawPagesSeperately(ItemStack item) {
		return true;
	}

	@Override
	public int colourBackground(ItemStack item) {
		if(!this.hasColour(item))
			return ItemDye.field_150922_c[3];
		return ItemDye.field_150922_c[this.getColour(item) - 1];
	}
	
	public int getColour(ItemStack item) {
		if(!this.hasColour(item))
			return 0;
		
		else return(item.getTagCompound().getInteger("colour"));
	}
	
	public void setColour(ItemStack item, int colour) {
		if(!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
		
		item.getTagCompound().setInteger("colour", colour + 1);
	}
	
	public boolean hasColour(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.stackTagCompound = new NBTTagCompound();
			return false;
		}
		
		return(item.getTagCompound().getInteger("colour") != 0);
	}

	@Override
	public Spell[] getSpells(ItemStack item) {
		if(this.hasSpells(item)) {
			NBTTagList list = item.getTagCompound().getTagList("spells", 10);
			
			Spell[] ret = new Spell[list.tagCount()];
					
			for(int i = 0; i < list.tagCount(); i++) {
				ret[i] = Spell.readFromNBT(list.getCompoundTagAt(i));
			}
			
			return ret;
		}
		
		return new Spell[0];
	}

	@Override
	public Spell[] removeSpells(ItemStack item) {
		Spell[] ret = this.getSpells(item);
		
		if(this.hasSpells(item)) {
			item.getTagCompound().removeTag("spells");
		}
		
		return ret;
	}

	@Override
	public Spell removeSpell(ItemStack item, Spell spell) {
		if(this.hasSpells(item)) {
			Spell[] spells = this.getSpells(item);
			for(int i = 0; i < spells.length; i++) {
				if(spell == spells[i]) {
					Spell ret = spells[i];
					spells[i] = null;
					this.setSpells(item, spells);
					return ret;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean hasSpells(ItemStack item) {
		if(!item.hasTagCompound()) return false;
		if(item.getTagCompound().getTagList("spells", 10).tagCount() == 0) return false;
		
		return true;
	}

	@Override
	public boolean setSpells(ItemStack item, Spell[] spells) {
		if(!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
			
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < spells.length; i++) {
			list.appendTag(spells[i].writeToNBT());
		}
		
		item.getTagCompound().setTag("spells", list);
		
		return true;
	}

	@Override
	public boolean addSpell(ItemStack item, Spell spell) {
		if(!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
			
		NBTTagList list = item.getTagCompound().getTagList("spells", 10);
		list.appendTag(spell.writeToNBT());
		item.getTagCompound().setTag("spells", list);
		
		return true;
	}

	@Override
	public boolean hasSpell(ItemStack item, Spell spell) {
		if(this.hasSpells(item)) {
			Spell[] spells = this.getSpells(item);
			for(int i = 0; i < spells.length; i++) {
				if(spell == spells[i]) {
					return true;
				}
			}
		}
		return false;
	}
}
