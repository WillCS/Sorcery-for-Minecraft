package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.element.ItemElementStorage;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnergy extends ItemElementStorage {
	public ItemEnergy() {
		setHasSubtypes(true);
		setMaxStackSize(64);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	public IIcon IIcon;
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
    	if(par1ItemStack.getItemDamage() == 0)
    		return StatCollector.translateToLocal(this.getUnlocalizedName() + ".blank.name");
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String elementName = Element.elementsList[par1ItemStack.getItemDamage()].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}
	
	@Override
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		if(Element.elementsList[meta] != null && pass != 0)
			return Element.elementsList[meta].getIcon();
		
		return this.IIcon;
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "runeBlank");
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				par3List.add(new ItemStack(this, 1, i));
		}
	}
	
	public static ItemStack getRune(String type, int amount) {
		int meta;
		
		if(type == "potential")
			meta = 1;
		else if(type == "rock")
			meta = 2;
		else if(type == "water")
			meta = 3;
		else if(type == "wind")
			meta = 4;
		else if(type == "frost")
			meta = 5;
		else if(type == "organic")
			meta = 6;
		else if(type == "fire")
			meta = 7;
		else if(type == "voodoo")
			meta = 8;
		else if(type == "void")
			meta = 9;
		else if(type == "arcane")
			meta = 10;
		else
			meta = 0;
		
		return new ItemStack(SorceryItems.energy, amount, meta);
	}
	
	@Override
	public ElementStack[] getElements(ItemStack item) {
		if(item.getItemDamage() != 0)
			return new ElementStack[]{new ElementStack(item.getItemDamage(), 1 * item.stackSize)};
		
		return null;
	}
	
	@Override
	public ItemStack consume(ItemStack item, ElementStack element) {
		item.stackSize -= element.amount;
		
		if(item.stackSize == 0)
			return null;
		
		return item;
	}
	
	@Override
	public ItemStack add(ItemStack item, ElementStack element) {
		return item;
	}
}
