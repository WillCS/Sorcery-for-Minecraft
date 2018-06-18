package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.element.ItemElementStorage;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemElementBottle extends ItemElementStorage {
	private IIcon bottle;
	private IIcon contents;
	
	public ItemElementBottle() {
		this.setHasSubtypes(true);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
		this.setMaxStackSize(1);
	}

	@Override
	public ElementStack[] getElements(ItemStack item) {
		if(item.getItemDamage() == 0) return null;
		if(!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
		return new ElementStack[]{new ElementStack(item.getItemDamage(), item.stackTagCompound.getInteger("amount"))};
	}

	@Override
	public ItemStack consume(ItemStack item, ElementStack element) {
		int oldAmount = item.stackTagCompound.getInteger("amount");
		item.stackTagCompound.setInteger("amount", oldAmount - element.amount);
		if(item.stackTagCompound.getInteger("amount") <= 0)
			return new ItemStack(Items.glass_bottle);
		else return item;
	}
	
	@Override
	public ItemStack add(ItemStack item, ElementStack element) {
		return item;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IIconRegister) {
		this.bottle = par1IIconRegister.registerIcon("potion_bottle_empty");
		this.contents = par1IIconRegister.registerIcon("potion_overlay");
    }
    
    @SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
		if(pass == 1) return this.contents;
		else return this.bottle;
	}
    
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
    
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String elementName = Element.elementsList[par1ItemStack.getItemDamage()].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int i) {
		if(i != 1)
			return 16777215;
		if(par1ItemStack.getItemDamage() == 32767)
			return Element.water.getIntColour();
		if(Element.elementsList[par1ItemStack.getItemDamage()] != null)
			return(Element.elementsList[par1ItemStack.getItemDamage()].getIntColour());
		
		return 0;
	}
	
	public static ItemStack getFullBottle(int meta) {
		ItemStack item = new ItemStack(SorceryItems.energyBottle, 1, meta);
		item.stackTagCompound = new NBTTagCompound();
		item.stackTagCompound.setInteger("amount", 10);
		return item;
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null) {
				par3List.add(getFullBottle(i));
			}
		}
	}
}
