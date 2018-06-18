package sorcery.api;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** A potion with 3 doses. Used by the Mojo and Antidote bottles
 * 
 * @author Vroominator */
public class Item3DosePotion extends Item {
	public Item3DosePotion() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}
	
	private IIcon[] Icons;
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + 1);
		
		return par1ItemStack.getItemDamage() >= 3 ? new ItemStack(Items.glass_bottle) : par1ItemStack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.drink;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		IIcon contents;
		switch(par1) {
			default:
				contents = this.Icons[1];
				break;
			case 1:
				contents = this.Icons[2];
				break;
			case 2:
				contents = this.Icons[3];
				break;
		}
		
		return par2 == 1 ? this.Icons[0] : contents;
	}
	
	@Override
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.Icons = new IIcon[4];
		this.Icons[0] = par1IIconRegister.registerIcon("potion_bottle_empty");
		this.Icons[1] = par1IIconRegister.registerIcon("potion_overlay");
		this.Icons[2] = par1IIconRegister.registerIcon("sorcery:" + "potion1");
		this.Icons[3] = par1IIconRegister.registerIcon("sorcery:" + "potion2");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return 16777215;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		switch(item.getItemDamage()) {
			default:
				list.add("3 doses remaining");
				return;
			case 1:
				list.add("2 doses remaining");
				return;
			case 2:
				list.add("1 dose remaining");
				return;
		}
	}
}
