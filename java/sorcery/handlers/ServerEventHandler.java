package sorcery.handlers;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import sorcery.api.event.MojoRegenEvent;
import sorcery.api.event.SpellCastEvent;
import sorcery.blocks.BlockMagicSapling;
import sorcery.core.Sorcery;
import sorcery.entities.EntityPhoenix;
import sorcery.items.ItemWand;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.SorcerySpells;
import sorcery.lib.SpellHelper;
import sorcery.network.ChatPacket;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHandler {
	@SubscribeEvent
	public void onEntityLivingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.entityLiving;
		DamageSource dmg = event.source;
		if(entity != null && entity instanceof EntityPhoenix && entity.isRiding() && dmg != null && dmg.equals(DamageSource.inWall)) {
			event.setCanceled(true);
		}
		
		if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityLiving && event.entityLiving != null) {
			ItemStack item = ((EntityLiving)event.source.getEntity()).getHeldItem();
			
			if(item != null) {
				if(item.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(SorcerySpells.frost.effectId, item) != 0) {
					int level = EnchantmentHelper.getEnchantmentLevel(SorcerySpells.frost.effectId, item);
					
					event.entityLiving.addPotionEffect(new PotionEffect(SorcerySpells.frostbite.id, 5 * 20, level));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityLivingJumped(LivingJumpEvent event) {
		if(!(event.entityLiving instanceof EntityPlayer))
			return;
		
		EntityLivingBase player = event.entityLiving;
		
		if(((EntityPlayer)player).inventory.armorInventory[0] != null && ((EntityPlayer)player).inventory.armorInventory[0].getItem() == SorceryItems.windBoots) {
			player.motionY = 0.6D;
		} else if(((EntityPlayer)player).inventory.armorInventory[0] != null && ((EntityPlayer)player).inventory.armorInventory[0].getItem() == SorceryItems.phoenixBoots) {
			player.motionY = 0.6D;
		}
	}
	
	/*@SubscribeEvent
	public void onEntityLivingDied(LivingDeathEvent event) {
		if(event.entityLiving instanceof EntityLiving) {
			int xp = ((EntityLiving)event.entityLiving).experienceValue;
			
			if(event.entityLiving != null && !(event.entityLiving instanceof EntityPlayer)) {
				if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer && ((EntityPlayer)event.source.getEntity()).getCurrentEquippedItem() != null) {
					EntityPlayer entity = (EntityPlayer)event.source.getEntity();
					if(entity.getCurrentEquippedItem().isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(Properties.insightEnchantmentID, entity.getCurrentEquippedItem()) != 0) {
						if(!entity.worldObj.isRemote) {
							int enchantLevel = EnchantmentHelper.getEnchantmentLevel(Properties.insightEnchantmentID, entity.getCurrentEquippedItem());
							int newXP = enchantLevel * xp - entity.worldObj.rand.nextInt(enchantLevel * 2) - (xp / 2);
							while(newXP > 0) {
								int var6 = EntityXPOrb.getXPSplit(newXP);
								newXP -= var6;
								entity.worldObj.spawnEntityInWorld(new EntityXPOrb(entity.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, var6));
							}
						}
					}
				} //TODO See if this needs fixing
			}
		}
	}*/
	
	@SubscribeEvent
	public void onPlayerCollectXP(PlayerPickupXpEvent event) {
		int value = event.orb.xpValue;
		
		if(event.entityPlayer != null && event.entityPlayer instanceof EntityPlayer && event.entityPlayer.getCurrentEquippedItem() != null) {
			EntityPlayer entity = event.entityPlayer;
			if(entity.getCurrentEquippedItem().isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(Properties.insightEnchantmentID, entity.getCurrentEquippedItem()) != 0) {
				if(!entity.worldObj.isRemote) {
					int enchantLevel = EnchantmentHelper.getEnchantmentLevel(Properties.insightEnchantmentID, entity.getCurrentEquippedItem());
					int newXP = enchantLevel * value - entity.worldObj.rand.nextInt(enchantLevel * 2) - (value / 2);
				}
			}
		}
		
		event.orb.xpValue = value;
	}
	
	@SubscribeEvent
	public void onEntityLivingUpdate(LivingUpdateEvent event) {
		if(event.entityLiving != null && event.entityLiving.isPotionActive(SorcerySpells.frostbite.id)) {
			if(event.entityLiving.getActivePotionEffect(SorcerySpells.frostbite).getDuration() == 0)
				event.entityLiving.removePotionEffect(SorcerySpells.frostbite.id);
			
			// TODO fix boots of wind / phoenix
			// event.entityLiving.landMovementFactor -=
			// event.entityLiving.landMovementFactor / 3;
			event.entityLiving.jumpMovementFactor -= event.entityLiving.jumpMovementFactor / 3;
		}
		
		if(event.entityLiving != null && event.entityLiving.isBurning() && event.entityLiving.isPotionActive(SorcerySpells.frostbite.id)) {
			event.entityLiving.removePotionEffect(SorcerySpells.frostbite.id);
		}
	}
	
	@SubscribeEvent
	public void onEntityLivingEnderPorted(EnderTeleportEvent event) {
		if(event.entityLiving != null) {
			AxisAlignedBB box = AxisAlignedBB.getBoundingBox(event.entity.posX - 4, event.entity.posY - 4, event.entity.posZ - 4, event.entity.posX + 4, event.entity.posY + 4, event.entity.posZ + 4);
			List<Entity> list = event.entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(event.entityLiving, box);
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i) != null && list.get(i) instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)list.get(i);
					if(ItemStackHelper.instance.doesInventoryContainItemStackInRange(player.inventory.mainInventory, new ItemStack(SorceryItems.teleblockRing, 1), 0, 9)) {
						event.setCanceled(true);
						if(event.entityLiving instanceof EntityPlayer) {
							Sorcery.packetPipeline.sendTo(
									new ChatPacket("You are being blocked from teleporting by " + player.getCommandSenderName()+ "!"),((EntityPlayerMP)event.entityLiving));
						}
						return;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityLivingCastSpell(SpellCastEvent event) {
		/*if(event.entityLiving != null && event.spell != null && event.spell.isTPSpell) {
			AxisAlignedBB box = AxisAlignedBB.getBoundingBox(event.entity.posX - 4, event.entity.posY - 4, event.entity.posZ - 4, event.entity.posX + 4, event.entity.posY + 4, event.entity.posZ + 4);
			List<Entity> list = event.entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(event.entityLiving, box);
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i) != null && list.get(i) instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)list.get(i);
					if(ItemStackHelper.instance.doesInventoryContainItemStackInRange(player.inventory.mainInventory, new ItemStack(SorceryItems.teleblockRing, 1), 0, 9)) {
						event.setCanceled(true);
						if(event.entityLiving instanceof EntityPlayer) {
							Sorcery.packetPipeline.sendTo(
									new ChatPacket("You are being blocked from teleporting by " + player.getCommandSenderName() + "!"), (EntityPlayerMP)event.entityLiving);
						}
						return;
					}
				}
			}
		}*/
	}
	
	@SubscribeEvent
	public void onRegenMojo(MojoRegenEvent event) {
		if(event.entityPlayer.inventory.armorInventory[0] != null && event.entityPlayer.inventory.armorInventory[0].getItem() == SorceryItems.fireBoots) {
			if(Sorcery.playerControls.isJumpPressed(event.entityPlayer) || !event.entityPlayer.onGround) {
				event.setCanceled(true);
			}
		}
		
		if(event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.isUsingItem()) {
			Item item = event.entityPlayer.getCurrentEquippedItem().getItem();
			if(item instanceof ItemWand) {
				event.setCanceled(true);
			}
		}
		
		if(SpellHelper.getInstance().getIsPlayerBurntOut(event.entityPlayer))
			event.setCanceled(true);
	}			
	
	@SubscribeEvent
	public void onBonemeal(BonemealEvent event) {
		if(event.block == Sorcery.magicSapling) {
			if(!event.world.isRemote) {
				((BlockMagicSapling)Sorcery.magicSapling).markOrGrowMarked(event.world, event.x, event.y, event.z, event.world.rand);
				event.world.playAuxSFX(2005, event.x, event.y, event.z, 0);
				event.setResult(Result.ALLOW);
			}
		}
	}
}
