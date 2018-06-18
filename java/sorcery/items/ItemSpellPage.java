package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.ISpellCollection;
import sorcery.api.spellcasting.Spell;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpellPage extends ItemArcane implements ISpellCollection {
	public ItemSpellPage() {
		setMaxStackSize(1);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	private IIcon[] IIcons;
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[2];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellPage_1");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellPage_2");
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if(!this.hasSpells(stack))
			return this.IIcons[0];
		
		if(this.getSpells(stack).length > 1)
			return this.IIcons[1];
		else
			return this.IIcons[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		if(!this.hasSpells(stack))
			return;
		
		if(!stack.stackTagCompound.getString("specialName").isEmpty()) {
			list.add(stack.stackTagCompound.getString("specialName"));
			list.add(
					StatCollector.translateToLocalFormatted("sorcery.spellcasting.spellPageItems", this.getSpells(stack).length));
			return;
		}
		
		Spell[] spells = this.getSpells(stack);
		for(int i = 0; i < spells.length; i++) {
			if(i > 4) {
				list.add(
					StatCollector.translateToLocalFormatted("sorcery.spellcasting.spellPageMore", spells.length - i));
				return;
			} else {
				list.add(spells[i].name);
				if(spells[i].author != null && !spells[i].author.isEmpty())
					list.add(StatCollector.translateToLocalFormatted("sorcery.spellcasting.spellPageAuthor", spells[i].author));
			}
		}
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
		
		item.stackSize = 0;
		item = null;
		
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
					
					if(!this.hasSpells(item)) {
						item.stackSize = 0;
						item = null;
					}
					
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
		
		if(this.getSpells(item).length == 0)
			item = null;
		
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
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		//par3List.add(new ItemStack(par1));
	}
}
