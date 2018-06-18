package sorcery.items;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.models.ModelWizardEquipment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWizardHat extends ItemArmor implements ISpecialArmor {
	public ItemWizardHat(int material) {
		super(ArmorMaterial.CLOTH, material, 0);
		setCreativeTab(Sorcery.tabSorcerySpellcasting);
		setMaxDamage(0);
		// setHasSubtypes(true);
	}
	
	private IIcon[] IIcons;
	
	@Override
	public EnumRarity getRarity(ItemStack item) {
		return EnumRarity.uncommon;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "sorcery." + super.getUnlocalizedName();
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, 0.0D, 0);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return new ModelWizardEquipment(entityLiving);
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[4];
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "wizardHatOverlay");
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "wizardHat");
		
		// this.IIcons[2] = par1IIconRegister.registerIcon(Properties.MODNAME +
		// ":" + "wizardHoodOverlay");
		// this.IIcons[3] = par1IIconRegister.registerIcon(Properties.MODNAME +
		// ":" + "wizardHood");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		// if(par1 == 0)
		return par2 == 1 ? this.IIcons[1] : this.IIcons[0];
		// else
		// return par2 == 1 ? this.IIcons[2] : this.IIcons[3];
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return Properties.ASSET_PREFIX + Properties.ARMOUR_TEXTURE_FOLDER + "phoenix.png";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if(par2 > 0) {
			return 16777215;
		} else {
			int j = this.getColor(par1ItemStack);
			
			if(j < 0) {
				j = (5 << 16) + (1 << 8) + 203;
			}
			
			return j;
		}
	}
	
	@Override
	public int getColor(ItemStack par1ItemStack) {
		NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
		
		if(nbttagcompound == null) {
			return (5 << 16) + (1 << 8) + 203;
		} else {
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			return nbttagcompound1 == null ? 10511680 : (nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : (5 << 16) + (1 << 8) + 203);
		}
	}
}
