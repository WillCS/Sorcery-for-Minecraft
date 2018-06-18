package sorcery.handlers;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import sorcery.api.spellcasting.Spell;
import sorcery.client.render.RenderUtils;
import sorcery.client.render.geometry.Circle;
import sorcery.client.render.geometry.Sector;
import sorcery.items.ItemWand;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
	@SubscribeEvent
	public void onMouseAction(MouseEvent event) {
		boolean state = (KeyStrokeHandler.spellBindMain.getKeyCode() < 0 ? Mouse.isButtonDown(KeyStrokeHandler.spellBindMain.getKeyCode() + 100) : Keyboard.isKeyDown(KeyStrokeHandler.spellBindMain.getKeyCode()));
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if(event.dwheel != 0 && state && FMLClientHandler.instance().getClient().inGameHasFocus) {
			ItemStack wand = player.getCurrentEquippedItem();
			if(wand != null && wand.getItem() instanceof ItemWand && wand.hasTagCompound()) {
				Spell[] spells = SpellHelper.getInstance().getPlayerSpells(player);
				int dir = event.dwheel / 120;
				event.setCanceled(true);
            	
            	for(int i = 0; i < 9; i++) {
            		int slotToChange = dir == 1 ? (i == 8 ? 0 : i + 1) : (i == 0 ? 8 : i - 1);
            		Spell spell = spells[slotToChange] != null ? spells[slotToChange] : null;
            		if(spell != null) {
            			SpellHelper.getInstance().setPlayerSpell(player, spell, i);
            			KeyStrokeHandler.sendPacket(spell, i, player);
            		}
            	}
			}
		}
		
		if(event.buttonstate && state && player.isSneaking() && FMLClientHandler.instance().getClient().inGameHasFocus) {
			event.setCanceled(true);
			//SpellHelper.getInstance().toggleWrenchMode(player);
			KeyStrokeHandler.sendWrenchPacket();
		}
	}
	
	private static Sector mojo = new Sector();
	private static Sector restricted = new Sector();
	private static Sector darkMojo = new Sector();
	private static Circle background = new Circle();
	private static int burnoutMax = 0;
	private static RenderItem itemRenderer = new RenderItem();
	
	@SubscribeEvent
	public void onHUDRendered(RenderGameOverlayEvent event) {
		if(event.type == ElementType.HOTBAR) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			if(player.capabilities.isCreativeMode)
				return;
			
			GL11.glPushMatrix();
			RenderUtils.bindTexture(Properties.hudTexture);
			GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor3f(1F, 1F, 1F);
			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();
			int scale = 2;
			float scale2 = scale / 2F;
			int mojoX = 0;
			int mojoY = 0;
            
			if(!SpellHelper.getInstance().getPlayerHasDarkMojo(player)) {
				mojoX = 30 + (10 * scale);
				mojoY = 10 + (int)(scale2);
				
				int playerMojo = SpellHelper.getInstance().getPlayerMojo(player);
				int playerMaxMojo = SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player);
				int playerDarkMojo = SpellHelper.getInstance().getPlayerDarkMojo(player);
				int playerMaxDarkMojo = SpellHelper.getInstance().getPlayerMaxDarkMojo(player);
				int playerRestricted = SpellHelper.getInstance().getPlayerTotalRestrictedMojo(player);
				int playerBurnout = SpellHelper.getInstance().getPlayerBurnOutTimer(player);
				int playerTotalMax = playerMaxMojo + playerRestricted;
				
				if(playerBurnout > burnoutMax)
					burnoutMax += playerBurnout;
				else if(playerBurnout == 0)
					burnoutMax = 0;
				
				float radius = 22 * scale2;
				float scaledAmount = (float)playerDarkMojo / (float)playerMaxDarkMojo;
				float rad = (float)(scaledAmount * (2 * Math.PI));
				
				background.setColour(Utils.encodeColour(0, 0, 0, 100), Utils.encodeColour(0, 0, 0, 100));
				background.draw(radius - 1, width - mojoX + radius - 4, mojoY + radius - 5);
				
				darkMojo.setColour(Utils.encodeColour(150, 0, 140, 255), Utils.encodeColour(150, 0, 140, 255));
				//darkMojo.setAngle(rad + (Math.PI / 100),  Math.PI - (Math.PI / 100) + 0.01);
				darkMojo.draw(radius - 1, width - mojoX + radius -4, mojoY + radius - 5);
				
				radius = 18 * scale2;
				float resScaledAmount = (float)playerRestricted / (float)playerMaxMojo;
				float resRad = (float)(resScaledAmount * (2 * Math.PI));
				scaledAmount = (float)playerMojo / (float)playerMaxMojo;
				
				if(!SpellHelper.getInstance().getIsPlayerBurntOut(player)) {
					mojo.setColour(Utils.encodeColour(0, 10, 207, 255), Utils.encodeColour(74, 79, 180, 255));
				} else {
					scaledAmount = 1 - ((float)playerBurnout / (float)burnoutMax) * ((float)playerMaxMojo / (float)playerTotalMax);
					resRad = 0F;
					mojo.setColour(Utils.encodeColour(157, 10, 0, 255), Utils.encodeColour(180, 79, 74, 255));
				}
				
				rad = (float)(scaledAmount * (2 * Math.PI));
				
				background.setColour(Utils.encodeColour(0, 0, 0, 255), Utils.encodeColour(0, 0, 0, 255));
				background.draw(radius - 1, width - mojoX + radius, mojoY + radius - 1);
				
				mojo.setAngle(rad + (Math.PI / 100),  Math.PI - (Math.PI / 100) + 0.01 + resRad);
				mojo.draw(radius - 1, width - mojoX + radius, mojoY + radius - 1);
				
				if(playerRestricted != 0) {
					restricted.setColour(Utils.encodeColour(157, 10, 0, 255), Utils.encodeColour(180, 79, 74, 255));
					restricted.setAngle(resRad + (Math.PI / 100) + 0.01, -Math.PI  - (Math.PI / 100) + 0.01);
					restricted.draw(radius - 1, width - mojoX + radius, mojoY + radius);
				}
				
				RenderUtils.drawTexturedQuad(width - mojoX - 4, 6, 72, 0, 44, 44, 100, scale);
	            RenderUtils.drawTexturedQuad(width - mojoX - 4, 6, 116, 0, 44, 44, 100, scale);
			} else {
				mojoX = 30 + (10 * scale);
				mojoY = 10 + (int)(scale2);
				
				int playerMojo = SpellHelper.getInstance().getPlayerMojo(player);
				int playerMaxMojo = SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player);
				int playerRestricted = SpellHelper.getInstance().getPlayerTotalRestrictedMojo(player);
				int playerBurnout = SpellHelper.getInstance().getPlayerBurnOutTimer(player);
				int playerTotalMax = playerMaxMojo + playerRestricted;
				
				if(playerBurnout > burnoutMax)
					burnoutMax += playerBurnout;
				else if(playerBurnout == 0)
					burnoutMax = 0;
				
				float radius = 18 * scale2;
				
				background.setColour(Utils.encodeColour(0, 0, 0, 100), Utils.encodeColour(0, 0, 0, 100));
				background.draw(radius - 1, width - mojoX + radius, mojoY + radius);
				
				RenderUtils.drawTexturedQuad(width - mojoX, 10, 0, 0, 36, 36, 100, scale);
				
				float scaledAmount = (float)playerMojo / (float)playerMaxMojo;
				float resScaledAmount = (float)playerRestricted / (float)playerMaxMojo;
				float resRad = (float)(resScaledAmount * (2 * Math.PI));
				
				if(!SpellHelper.getInstance().getIsPlayerBurntOut(player)) {
					mojo.setColour(Utils.encodeColour(0, 10, 207, 255), Utils.encodeColour(74, 79, 180, 255));
				} else {
					scaledAmount = 1 - ((float)playerBurnout / (float)burnoutMax) * ((float)playerMaxMojo / (float)playerTotalMax);
					resRad = 0F;
					mojo.setColour(Utils.encodeColour(157, 10, 0, 255), Utils.encodeColour(180, 79, 74, 255));
				}
				
				float rad = (float)(scaledAmount * (2 * Math.PI));
				
				mojo.setAngle(rad + (Math.PI / 100),  Math.PI - (Math.PI / 100) + 0.01 + resRad);
				mojo.draw(radius - 1, width - mojoX + radius, mojoY + radius - 1);
				
				if(playerRestricted != 0) {
					restricted.setColour(Utils.encodeColour(157, 10, 0, 255), Utils.encodeColour(180, 79, 74, 255));
					restricted.setAngle(resRad + (Math.PI / 100) + 0.01, -Math.PI  - (Math.PI / 100) + 0.01);
					restricted.draw(radius - 1, width - mojoX + radius, mojoY + radius);
				}
				
	            RenderUtils.drawTexturedQuad(width - mojoX, 10, 36, 0, 36, 36, 98, scale);
			}
			
			
			int elementsX = (int)Math.ceil((40 + (10 * scale)) * scale2);
			int elementsY = (int)Math.ceil(15 * scale2);
			FontRenderer fontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
			itemRenderer.zLevel = 100F;
            
			ItemStack wand = player.getCurrentEquippedItem();
			if(wand != null && wand.getItem() instanceof ItemWand) {
				Spell[] spells = SpellHelper.getInstance().getPlayerSpells(player);
				
				if(spells.length != 0) {
	            	boolean keyState = KeyStrokeHandler.spellBindMain.getKeyCode() < 0 ? Mouse.isButtonDown(KeyStrokeHandler.spellBindMain.getKeyCode() + 100) : Keyboard.isKeyDown(KeyStrokeHandler.spellBindMain.getKeyCode());
		            if(keyState && FMLClientHandler.instance().getClient().inGameHasFocus) {
		            	RenderUtils.drawTexturedQuad(width - (width / 2) - 12, 5, 0, 51, 24, 24, 96, 2);
		            	if(spells[0] != null)
		            		fontRenderer.drawStringWithShadow(spells[0].name, width - (width / 2) - fontRenderer.getStringWidth(spells[0].name) / 2, 35, Utils.encodeColour(255, 255, 255));
		            	RenderUtils.bindTexture(Properties.itemTexture);
		            	if(spells[0] != null)
		            		spells[0].drawIcon();
		            		//itemRenderer.renderIcon(width - (width / 2) - 12, 5, Spell.IIcons[spells[0].spellID], 24, 24);
		            	
		            	int i = 1;
	        			for(int k = 0; k < 4; k++) {
	        				RenderUtils.bindTexture(Properties.hudTexture);
	        				RenderUtils.drawTexturedQuad(width - (width / 2) + 14 + (k * 22), 7, 24, 51, 20, 20, 96, 2);
	        				RenderUtils.bindTexture(Properties.itemTexture);
	        				if(spells[i] != null)
	        					spells[i].drawIcon();
	        					//itemRenderer.renderIcon(width - (width / 2) + 14 + (k * 22) + 2, 9, Spell.IIcons[spells[i].spellID], 16, 16);
	        				i++;
	        			}
	        			for(int k = 0; k < 4; k++) {
	        				RenderUtils.bindTexture(Properties.hudTexture);
	        				RenderUtils.drawTexturedQuad(width - (width / 2) - 100 + (k * 22), 7, 24, 51, 20, 20, 96, 2);
	        				RenderUtils.bindTexture(Properties.itemTexture);
	        				if(spells[i] != null)
	        					spells[i].drawIcon();
	        					//itemRenderer.renderIcon(width - (width / 2) - 100 + (k * 22) + 2, 9, Spell.IIcons[spells[i].spellID], 16, 16);
	        				i++;
	        			}
		            }
		            
		            RenderUtils.bindTexture(Properties.hudTexture);
		            
					if(spells.length > 0 && spells[0] != null) {
		            	
						int barWidth = 50;
						
						RenderUtils.drawTexturedQuad(width - elementsX + (int)Math.ceil(3 * scale2), elementsY, 57, 36, 3, 1, 96, 2);
						
						int i;
						int xOffset = 0;
						int yOffset = 0;
						for(i = 0; i < spells[0].getCastInfo().elements.length; i++) {
							xOffset = (int)((i * 3) * scale2);
							yOffset = (int)((i * 6) * scale2);
							float numFound = SpellHelper.getInstance().getElementAmountInPlayerInv(player, spells[0].getCastInfo().elements[i].element);
							if(numFound == -1)
								numFound = 999;
							RenderUtils.drawTexturedQuad(width - elementsX - xOffset, elementsY + (int)Math.ceil(1 * scale2) + yOffset, 54, 37, 8, 6, 96, 2);
							
							if(numFound < spells[0].getCastInfo().elements[i].amount)
								GL11.glColor3f(255F / 255F, 0F / 255F, 0F / 255F);
							else if(numFound <= ((spells[0].getCastInfo().elements[i].amount) * 2))
								GL11.glColor3f(255F / 255F, 150F / 255F, 0F / 255F);
							else if(numFound <= ((spells[0].getCastInfo().elements[i].amount) * 3))
								GL11.glColor3f(255F / 255F, 255F / 255F, 0F / 255F);
							else GL11.glColor3f(0F / 255F, 255F / 255F, 0F / 255F);
							
							RenderUtils.drawTexturedQuad(width - elementsX - xOffset - 5 - barWidth, elementsY + yOffset, 0, 46, 9, 5, 96, 2);
							
							GL11.glColor3f(1F, 1F, 1F);
							RenderUtils.drawTexturedQuad(width - elementsX - xOffset - barWidth + 1, elementsY + 1 + yOffset, 0, 36, 51, 5, 96, 2);
							float[] rgb = spells[0].getCastInfo().elements[i].element.getFloatColour();
							numFound = numFound > spells[0].getCastInfo().elements[i].amount ? spells[0].getCastInfo().elements[i].amount : numFound;
							GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
							int scaledLength = (int)(Math.ceil((double)(48F * (float)(numFound / spells[0].getCastInfo().elements[i].amount))) * scale2);
							RenderUtils.drawTexturedQuad(width - elementsX + 3 - xOffset + (49 - scaledLength) - barWidth, elementsY + 2 + yOffset, 2 + (49 - scaledLength), 42, scaledLength, 3, 97, 2);
							RenderUtils.drawTexturedQuad(width - elementsX - xOffset, elementsY + yOffset + 2, 10, 48, 4, 3, 96, 2);
							GL11.glColor3f(1F, 1F, 1F);
						}
						
						RenderUtils.drawTexturedQuad(width - elementsX + 1 - xOffset, elementsY + 7 + yOffset, 55, 43, 3, 1, 96, 2);
					}
				}
			}
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
}
