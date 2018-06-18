package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStaff extends ItemWand {
	public ItemStaff() {
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	private IIcon[] IIcons;
	
	public boolean shouldRotateAroundWhenRendering() {
		return false;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "sorcery.item.staff";
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String elementName = Element.elementsList[par1ItemStack.getItemDamage()].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return true;
	}
	
	public int getMaxItemUseDuration(ItemStack item) {
		return 100;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
    public int getRenderPasses(int metadata) {
        return 2;
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 1 ? this.IIcons[0] : this.IIcons[1];
	}
	
	@Override
    public IIcon getIcon(ItemStack stack, int pass) {
    	return this.getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
    }
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[256];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "staffPartBody");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "staffPartCore");
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if(par2 != 0)
			return 16777215;
		else
			return(Element.elementsList[par1ItemStack.getItemDamage()].getIntColour());
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				par3List.add(new ItemStack(this, 1, i));
		}
	}
	
	public int getItemEnchantability() {
		return 2;
	}
	
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}
}
