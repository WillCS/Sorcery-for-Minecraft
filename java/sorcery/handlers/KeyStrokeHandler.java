package sorcery.handlers;

import java.util.ArrayList;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import sorcery.api.ISpellBook;
import sorcery.api.spellcasting.Spell;
import sorcery.containers.ContainerSpellbook;
import sorcery.core.Sorcery;
import sorcery.items.ItemWand;
import sorcery.lib.Properties;
import sorcery.network.PlayerSpellPacket;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyStrokeHandler {
	public static KeyBinding spellBindMain = new KeyBinding("Spell Swap", Keyboard.KEY_Z, "key.categories.gameplay");
	public static KeyBinding combatMenu = new KeyBinding("Spellbook", Keyboard.KEY_R, "key.categories.inventory");
	
	public KeyStrokeHandler() {
		FMLClientHandler.instance().getClient().gameSettings.keyBindings = (KeyBinding[])ArrayUtils.addAll(
				FMLClientHandler.instance().getClient().gameSettings.keyBindings,
				new KeyBinding[] {this.spellBindMain, this.combatMenu});
	}
	
	@SubscribeEvent
	public void onKeyPressed(KeyInputEvent event) {
		int key = Keyboard.getEventKey();
		if(FMLClientHandler.instance().getClient().inGameHasFocus) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			ItemStack heldItem = player.inventory.getCurrentItem();
			
			if(heldItem != null) {
				if(key != combatMenu.getKeyCode() && heldItem.getItem() instanceof ItemWand && heldItem.hasTagCompound()) {
					if(player.isSneaking()) {
						int spellID = 0;
					} else {

					}
				} else if(key == combatMenu.getKeyCode()) {
					if(heldItem.getItem() instanceof ItemWand || heldItem.getItem() instanceof ISpellBook) {
						ArrayList<ISpellBook> spellbooks = new ArrayList<ISpellBook>();
						
						for(int i = 0; i < player.inventory.mainInventory.length; i++) {
							if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ISpellBook) {
								if(player.inventory.mainInventory[i].getItem() instanceof ISpellBook) {
									spellbooks.add((ISpellBook)(player.inventory.mainInventory[i].getItem()));
								}
							}
						}
						
						if(spellbooks.size() != 0) {
							player.openGui(Sorcery.instance, Properties.GUI_SPELLBOOK, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
						} else {
							player.addChatMessage(new ChatComponentText("You do not currently have any Spellbooks."));
						}
					}
				} 
			}
		} else {
			if(FMLClientHandler.instance().getClient().thePlayer != null) {
				if(key == combatMenu.getKeyCode() && FMLClientHandler.instance().getClient().thePlayer.openContainer instanceof ContainerSpellbook) {
					FMLClientHandler.instance().getClient().thePlayer.closeScreen();
				}
			}
		}
	}
	
	public static void sendPacket(Spell spell, int index, EntityPlayer player) {
		PlayerSpellPacket packet = new PlayerSpellPacket(spell, index);
		Sorcery.packetPipeline.sendToServer(packet);
	}
	
	public static void sendWrenchPacket() {
		PlayerSpellPacket packet = new PlayerSpellPacket(true);
		Sorcery.packetPipeline.sendToServer(packet);
	}
}
