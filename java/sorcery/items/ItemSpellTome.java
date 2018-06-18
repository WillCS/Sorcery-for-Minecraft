package sorcery.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import sorcery.api.spellcasting.Spell;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpellTome extends ItemSpellbook {

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "sorcery.item.spellTome";
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[256];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellTome");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellTomeOverlay");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "spellTomePages");
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 3;
	}

	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
		if(pass == 2)
			return this.IIcons[1];
		else if(pass == 1)
			return this.IIcons[0];
		else return this.IIcons[2];
	}
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int i) {
		if(this.hasColour(par1ItemStack)) {
			if(i == 2) {
				return ItemDye.field_150922_c[par1ItemStack.stackTagCompound.getInteger("secondColour") - 1];
			} else if(i == 1) {
				return ItemDye.field_150922_c[this.getColour(par1ItemStack) - 1];
			}
		}
		return Utils.encodeColour(1.0F, 1.0F, 1.0F, 1.0F);	
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(i != j)
					par3List.add(getTomeWithColours(i + 1, j + 1));
			}
		}
	}
	
	public static ItemStack generateRandomTome() {
		Random rand = new Random();
		int colour1 = rand.nextInt(15) + 1;
		int colour2 = rand.nextInt(15) + 1;
		while(colour2 == colour1) {
			colour2 = rand.nextInt(15) + 1;
		}
		
		return getTomeWithColours(colour1, colour2);
	}
	
	public static ItemStack getTomeWithColours(int colour1, int colour2) {
		ItemStack ret = new ItemStack(SorceryItems.spellTome);
		ret.stackTagCompound = new NBTTagCompound();
		ret.stackTagCompound.setInteger("colour", colour1);
		ret.stackTagCompound.setInteger("secondColour", colour2);
		return ret;
	}
}
