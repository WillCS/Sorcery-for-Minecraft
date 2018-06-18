package sorcery.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import sorcery.api.spellcasting.Spell;
import sorcery.client.render.RenderUtils;
import sorcery.core.Sorcery;
import sorcery.entities.EntityPhoenix;
import sorcery.items.ItemWand;
import sorcery.lib.Properties;
import sorcery.lib.SorcerySpells;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ClientTickHandler {
	public static int num;
	public static int rotation;
	
	@SubscribeEvent
	public void onTick(TickEvent event) {
		num++;
		if(num == 100) num = 0;
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		for(int i = 0; i < Sorcery.eventScheduler.events.size(); ) {
			if(Sorcery.eventScheduler.events.get(i).delay == 0) {
				Sorcery.eventScheduler.events.get(i).event.call();
				Sorcery.eventScheduler.events.remove(i);
			} else {
				Sorcery.eventScheduler.events.get(i).delay--;
				i++;
			}
		}
		
		if(!Minecraft.getMinecraft().isGamePaused()) {
			rotation += 1;
			rotation %= 360;
		}
		
		Sorcery.playerControls.updateKeys();
		
		if(player.riddenByEntity != null && player.riddenByEntity instanceof EntityPhoenix && !((EntityPhoenix)player.riddenByEntity).isChild()) {
			player.fallDistance = 0F;
			player.riddenByEntity.ignoreFrustumCheck = true;
			if(!player.onGround && !player.capabilities.isFlying && !player.isOnLadder() && player.motionY < 0 && !player.isInWater()) {
				player.motionY = -0.1D;
			}
		}
		
		if(player.isUsingItem() && player.getItemInUse().getItem() instanceof ItemWand) {
			Spell spell = SpellHelper.getInstance().getPlayerEquippedSpell(player);
			for(int i = 0; i < spell.getCastInfo().elements.length; i++) {
				int rand = player.worldObj.rand.nextInt(8);
				float[] rgb = spell.getCastInfo().elements[i].element.getFloatColour();
				
				Vec3 pos = player.getLookVec();
				pos.xCoord *= 1;
				pos.xCoord *= 1;
				pos.zCoord *= 1;
				if(rand == 0)
					player.worldObj.spawnParticle("mobSpell", player.posX + pos.xCoord, player.posY + 1, player.posZ + pos.zCoord, rgb[0], rgb[1], rgb[2]);
			}
		}
		
		/*if(player.inventory.armorInventory[0] != null) {
			if(player.inventory.armorInventory[0].getItem() == SorceryItems.phoenixBoots) {
				player.fallDistance = 0F;
				// TODO  fix boots of wind / phoenix
				 player.landMovementFactor *= 1.5;
				if(!player.onGround && !player.capabilities.isFlying && !player.isOnLadder() && player.motionY < 0 && !player.isInWater() && !player.isSneaking()) {
					player.motionY = -0.1D;
					
					Random rand = new Random();
					
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
			} else if(player.inventory.armorInventory[0].getItem() == SorceryItems.frostBoots) {
				Random rand = new Random();
				double var2 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = 10.0D;
				float[] rgb = Element.elementsList[5].getFloatColour();
				player.worldObj.spawnParticle("mobSpell", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, rgb[0], rgb[1], rgb[2]);
			} else if(player.inventory.armorInventory[0].getItem() == SorceryItems.energyBoots) {
				Random rand = new Random();
				double var2 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = 10.0D;
				float[] rgb = Element.elementsList[1].getFloatColour();
				player.worldObj.spawnParticle("mobSpell", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, rgb[0], rgb[1], rgb[2]);
				// TODO  fix boots of wind / phoenix
				player.landMovementFactor *= 1.5;
			} else if(player.inventory.armorInventory[0].getItem() == SorceryItems.windBoots) {
				Random rand = new Random();
				double var2 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = 10.0D;
				float[] rgb = Element.elementsList[4].getFloatColour();
				player.worldObj.spawnParticle("mobSpell", player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var2 * var8, (player.posY + (double)(rand.nextFloat() * player.height) - var4 * var8) - 2.7, player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width - var6 * var8, rgb[0], rgb[1], rgb[2]);
			}
		}*/
	}
	
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		EntityPlayer thePlayer = mc.thePlayer;
		if(thePlayer == null)
			return;
		
		if(thePlayer.isPotionActive(SorcerySpells.frostbite.id)) {
			if(!FMLClientHandler.instance().getClient().inGameHasFocus)
				return;
			GL11.glPushMatrix();
			ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			GL11.glColor4f(42F / 255F, 190F / 255F, 210F / 255F, 0.5F);
			this.drawQuad(0, 0, sr.getScaledWidth(), sr.getScaledHeight());
			GL11.glPopMatrix();
		}
	}

	private void drawQuad(int x, int y, int width, int height) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		tessellator.startDrawingQuads();
		tessellator.addVertex((double)x, (double)y + height, -99D);
		tessellator.addVertex((double)x + width, (double)y + height, -99D);
		tessellator.addVertex((double)x + width, (double)y, -99D);
		tessellator.addVertex((double)x, (double)y, -99D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
