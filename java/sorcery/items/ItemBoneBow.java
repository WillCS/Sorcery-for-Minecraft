package sorcery.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import sorcery.entities.EntityBoneArrow;
import sorcery.lib.SorceryItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBoneBow extends ItemBow {
	public ItemBoneBow() {
		this.maxStackSize = 1;
		this.setMaxDamage(719);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	private IIcon IIcons[];
	
	public IIcon getIconFromDamage(int i) {
		return this.IIcons[0];
	}
	
	/** called when the player releases the use item button. Args: itemstack,
	 * world, entityplayer, itemInUseCount */
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
		
		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, var6);
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return;
		}
		var6 = event.charge;
		
		boolean creative = par3EntityPlayer.capabilities.isCreativeMode;
		boolean infinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
		
		if(creative || par3EntityPlayer.inventory.hasItem(SorceryItems.bonearrow)) {
			float var7 = (float)var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
			
			if((double)var7 < 0.1D) {
				return;
			}
			
			if(var7 > 1.0F) {
				var7 = 1.0F;
			}
			
			EntityBoneArrow var8 = new EntityBoneArrow(par2World, par3EntityPlayer, var7 * 2.0F);
			
			if(var7 == 1.0F) {
				var8.setIsCritical(true);
			}
			
			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			
			if(var9 > 0) {
				var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 1D);
			}
			
			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
			
			if(var10 > 0) {
				var8.setKnockbackStrength(var10);
			}
			
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
				var8.setFire(100);
			}
			
			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
			
			if(infinity || creative) {
				var8.canBePickedUp = 2;
			} else {
				par3EntityPlayer.inventory.consumeInventoryItem(SorceryItems.bonearrow);
			}
			
			if(!par2World.isRemote) {
				par2World.spawnEntityInWorld(var8);
			}
		} else if(par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			float var7 = (float)var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
			
			if((double)var7 < 0.1D) {
				return;
			}
			
			if(var7 > 1.0F) {
				var7 = 1.0F;
			}
			
			EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0F);
			
			if(var7 == 1.0F) {
				var8.setIsCritical(true);
			}
			
			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			
			if(var9 > 0) {
				var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 0.5D);
			}
			
			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
			
			if(var10 > 0) {
				var8.setKnockbackStrength(var10);
			}
			
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
				var8.setFire(100);
			}
			
			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
			
			if(infinity) {
				var8.canBePickedUp = 2;
			} else {
				par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
			}
			
			if(!par2World.isRemote) {
				par2World.spawnEntityInWorld(var8);
			}
		}
	}
	
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	/** How long it takes to use or consume an item */
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	/** returns the action that specifies what animation to play when the items
	 * is being used */
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	/** Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return event.result;
		}
		
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(SorceryItems.blazearrow) || par3EntityPlayer.inventory.hasItem(SorceryItems.bonearrow) || par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		
		return par1ItemStack;
	}
	
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if(usingItem != null && usingItem.getItem() == SorceryItems.bonebow) {
			if(player.inventory.hasItem(SorceryItems.bonearrow)) {
				int k = usingItem.getMaxItemUseDuration() - useRemaining;
				if(k >= 18)
					return this.IIcons[6];
				if(k > 13)
					return this.IIcons[5];
				if(k > 0)
					return this.IIcons[4];
			} else if(player.inventory.hasItem(Items.arrow)) {
				int k = usingItem.getMaxItemUseDuration() - useRemaining;
				if(k >= 18)
					return this.IIcons[3];
				if(k > 13)
					return this.IIcons[2];
				if(k > 0)
					return this.IIcons[1];
			}
		}
		return this.IIcons[0];
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.IIcons = new IIcon[256];
		this.IIcons[0] = ir.registerIcon("sorcery:bowBoneEmpty");
		this.IIcons[1] = ir.registerIcon("sorcery:bowBoneWooden1");
		this.IIcons[2] = ir.registerIcon("sorcery:bowBoneWooden2");
		this.IIcons[3] = ir.registerIcon("sorcery:bowBoneWooden3");
		this.IIcons[4] = ir.registerIcon("sorcery:bowBoneBone1");
		this.IIcons[5] = ir.registerIcon("sorcery:bowBoneBone2");
		this.IIcons[6] = ir.registerIcon("sorcery:bowBoneBone3");
	}
	
	/** Return the enchantability factor of the item, most of the time is based
	 * on material. */
	public int getItemEnchantability() {
		return 2;
	}
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
}
