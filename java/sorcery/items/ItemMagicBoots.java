package sorcery.items;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryEnums;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMagicBoots extends ItemArmor implements ISpecialArmor {
	public ItemMagicBoots(int material) {
		super(SorceryEnums.armorMISC, material, 3);
		this.setCreativeTab(Sorcery.tabSorceryTools);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack item) {
		return EnumRarity.uncommon;
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return 16777215;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return false;
	}
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if(stack.getItem() == SorceryItems.phoenixBoots)
			return Properties.ASSET_PREFIX + Properties.ARMOUR_TEXTURE_FOLDER + "phoenix.png";
		else if(stack.getItem() == SorceryItems.frostBoots)
			return Properties.ASSET_PREFIX + Properties.ARMOUR_TEXTURE_FOLDER + "frost.png";
		else if(stack.getItem() == SorceryItems.energyBoots)
			return Properties.ASSET_PREFIX + Properties.ARMOUR_TEXTURE_FOLDER + "energy.png";
		else if(stack.getItem() == SorceryItems.windBoots)
			return Properties.ASSET_PREFIX + Properties.ARMOUR_TEXTURE_FOLDER + "wind.png";
		else if(stack .getItem()== SorceryItems.fireBoots)
			return Properties.ASSET_PREFIX + Properties.ARMOUR_TEXTURE_FOLDER + "fire.png";
		else
			return "";
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
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		String newName = this.getUnlocalizedName().replace("sorcery.item.", "");
		this.itemIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + newName);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		Item item = player.inventory.armorInventory[0].getItem();
		Random rand = world.rand;
		if(item == SorceryItems.fireBoots) {
			if(SpellHelper.instance.getPlayerCanUseMojo(player) && Sorcery.playerControls.isJumpPressed(player)) {
				if(Sorcery.playerControls.isJumpPressed(player)) {
					if(!player.isOnLadder() && !player.isInWater()) {
						if(player.motionY <= 0.6)
							player.motionY += 0.2;
						if(player.motionY <= 0.0)
							player.fallDistance = 0.0F;
						if(!player.capabilities.isCreativeMode)
							SpellHelper.instance.consumePlayerMojo(player, 4);
						
						world.spawnParticle("flame", player.posX + 0.2, player.posY - 1.7F, player.posZ, 0F, 0F, 0F);
						world.spawnParticle("flame", player.posX - 0.2, player.posY - 1.7F, player.posZ, 0F, 0F, 0F);
						world.spawnParticle("flame", player.posX, player.posY - 1.7F, player.posZ + 0.2, 0F, 0F, 0F);
						world.spawnParticle("flame", player.posX, player.posY - 1.7F, player.posZ - 0.2, 0F, 0F, 0F);
					}
				}
			}
		} else if (item == SorceryItems.phoenixBoots) {
			player.fallDistance = 0F;
			if (!player.onGround && !player.capabilities.isFlying && !player.isOnLadder() && player.motionY < 0 && !player.isInWater() && !player.isSneaking()) {
				player.motionY = -0.1D;
				
				if(!player.isRiding()) {
					for(int var1 = 0; var1 < 2; ++var1) {
						double var2 = rand.nextGaussian() * 0.02D;
						double var4 = rand.nextGaussian() * 0.02D;
						double var6 = rand.nextGaussian() * 0.02D;
						double var8 = 10.0D;
						player.worldObj.spawnParticle("explode", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, var2, var4, var6);
					}
				}
			}
			
			player.jumpMovementFactor *= 1.5;
		} else if (item == SorceryItems.frostBoots) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					for(int k = 0; k < 3; k++) {
						if(player.worldObj.getBlock((int) (player.posX - 1) + i, (int) (player.posY - 2) + j, (int) (player.posZ - 1) + k) == Blocks.water) {
							player.worldObj.setBlock((int) (player.posX - 1) + i, (int) (player.posY - 2) + j, (int) (player.posZ - 1) + k, Blocks.ice, 0, 2);
						}
					}
				}
				if(rand.nextInt(5) == 0) {
					double var2 = rand.nextGaussian() * 0.02D;
					double var4 = rand.nextGaussian() * 0.02D;
					double var6 = rand.nextGaussian() * 0.02D;
					double var8 = 10.0D;
					float[] rgb = Element.elementsList[5].getFloatColour();
					player.worldObj.spawnParticle("mobSpell", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, rgb[0], rgb[1], rgb[2]);
				}
			}

			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 5; j++) {
					for(int k = 0; k < 5; k++) {
						if(player.worldObj.getBlock((int) (player.posX - 2) + i, (int) (player.posY - 2) + j, (int) (player.posZ - 2) + k) == Blocks.fire) {
							player.worldObj.setBlockToAir((int) (player.posX - 2) + i, (int) (player.posY - 2) + j, (int) (player.posZ - 2) + k);
						}
					}
				}
			}
		} else if(item == SorceryItems.energyBoots) {
			// TODO  fix boots of wind / phoenix
			// player.landMovementFactor *= 1.5;
			player.jumpMovementFactor *= 1.5;
			if(rand.nextInt(5) == 0) {
				double var2 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = 10.0D;
				float[] rgb = Element.elementsList[1].getFloatColour();
				player.worldObj.spawnParticle("mobSpell", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, rgb[0], rgb[1], rgb[2]);
			}
		} else if(item == SorceryItems.windBoots) {
			if(rand.nextInt(5) == 0) {
				double var2 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = 10.0D;
				float[] rgb = Element.elementsList[4].getFloatColour();
				player.worldObj.spawnParticle("mobSpell", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, rgb[0], rgb[1], rgb[2]);
			}
		}
	}
}
