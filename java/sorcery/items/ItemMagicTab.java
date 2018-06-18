package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import sorcery.api.SorceryAPI;
import sorcery.api.element.Element;
import sorcery.api.event.SpellCastEvent;
import sorcery.api.spellcasting.Spell;
import sorcery.core.Sorcery;
import sorcery.guis.GuiNodeBase;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMagicTab extends ItemArcane {
	public ItemMagicTab() {
		setMaxStackSize(8);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	public static IIcon[] IIcons;
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	public int getMaxItemUseDuration(ItemStack item) {
		return 100;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	public int getRenderPasses(int metadata) {
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		switch(par2) {
			case 0:
				return IIcons[0];
			case 1:
				return IIcons[0];
			case 2:
				return IIcons[1];
		}
		return par2 == 0 ? IIcons[0] : IIcons[par1];
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		IIcons = new IIcon[2];
		IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellTablet");
		IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellTabletFront");
		
		GuiNodeBase.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "redtorch");
		GuiNodeBase.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "redtorch_lit");
		GuiNodeBase.IIcons[2] = Items.redstone.getIconFromDamage(0);
		
		for(int i = 0; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				Element.elementsList[i].Icon = par1IIconRegister.registerIcon(SorceryAPI.TEXTURE_FOLDER + ":" + SorceryAPI.ELEMENT_TEXTURE_FOLDER + Element.elementsList[i].getName());
		}
	}/*
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		Spell spell = Spell.spellsList[par1ItemStack.getItemDamage()];
		if(!SpellHelper.instance.isSpellBlackListed(spell)) {
			if(SpellHelper.instance.doesPlayerHaveMojoForSpell(par3EntityPlayer, spell) && spell.canCastSpell(par1ItemStack, par2World, par3EntityPlayer)) {
				SpellCastEvent event = new SpellCastEvent(par3EntityPlayer, par1ItemStack, spell);
				MinecraftForge.EVENT_BUS.post(event);
				if(event.isCanceled())
					return par1ItemStack;
				
				if(!par3EntityPlayer.capabilities.isCreativeMode) {
					par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				} else {
					spell.cast(par1ItemStack, par2World, par3EntityPlayer);
					if(spell.getSpellCastSound() != null && !par2World.isRemote)
						par2World.playSoundAtEntity(par3EntityPlayer, spell.getSpellCastSound(), 1.0F, 1.0F / 1.0F);
				}
			} else {
				par2World.playSoundAtEntity(par3EntityPlayer, "random.fizz", 0.5F, 2.6F + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.8F);
			}
		} else {
			if(par3EntityPlayer.worldObj.isRemote) {
				par3EntityPlayer.addChatMessage(new ChatComponentText("A mysterious force is preventing you from casting that spell."));
			} else {
				Utils.log(Level.INFO, par3EntityPlayer.func_145748_c_().getUnformattedText() + " tried to cast the blacklisted spell " + spell.name + ".");
			}
		}
		
		return par1ItemStack;
	}
	
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		Spell spell = Spell.spellsList[par1ItemStack.getItemDamage()];
		int time = this.getMaxItemUseDuration(par1ItemStack) - par4;
		if(time > (spell.chargeTime / 4)) {
			spell.cast(par1ItemStack, par2World, par3EntityPlayer);
			if(spell.getSpellCastSound() != null && !par2World.isRemote)
				par2World.playSoundAtEntity(par3EntityPlayer, spell.getSpellCastSound(), 1.0F, 1.0F / 1.0F);
			
			if(par1ItemStack.stackSize == 1)
				par3EntityPlayer.setCurrentItemOrArmor(0, null);
			else
				par1ItemStack.stackSize--;
			
			Utils.log(Level.INFO, "" + par1ItemStack.stackSize);
			par3EntityPlayer.getEntityData().setInteger("SpellCoolDown",
			(int)spell.chargeTime +
			par3EntityPlayer.getEntityData().getInteger("SpellCoolDown"));
			SpellHelper.instance.setPlayerMojo(par3EntityPlayer, (int)(spell.chargeTime + SpellHelper.instance.getPlayerMojo(par3EntityPlayer)));
		}
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(Spell.spellsList[par1ItemStack.getItemDamage()].name);
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int x = 0; x < Spell.spellsList.length; x++) {
			if(Spell.spellsList[x] != null)
				par3List.add(new ItemStack(this, 1, x));
		}
	}*/
}
