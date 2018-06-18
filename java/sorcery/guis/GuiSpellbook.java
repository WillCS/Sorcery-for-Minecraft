package sorcery.guis;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.api.ISpellBook;
import sorcery.api.ISpellCollection;
import sorcery.api.spellcasting.Spell;
import sorcery.client.render.RenderUtils;
import sorcery.containers.ContainerSpellbook;
import sorcery.core.Sorcery;
import sorcery.guis.button.GuiButtonImage;
import sorcery.guis.button.GuiButtonSpell;
import sorcery.guis.button.GuiButtonSpellbook;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import sorcery.network.PlayerSpellPacket;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiSpellbook extends GuiContainer {
	
	public ItemStack spellBook;
	public Spell spell;
	public EntityPlayer player;
	public Spell[] spells;
	public int page = 1;
	public int pages;
	public int debounce = 0;
	public ResourceLocation background;
	
	public GuiSpellbook(EntityPlayer player, World world, int x, int y, int z) {
		super(new ContainerSpellbook(player.inventory, world, x, y, z));
		this.xSize = 256;
		this.ySize = 166;
		this.player = player;
		this.spellBook = SpellHelper.getInstance().getSpellBook(player);
		this.spells = ((ISpellCollection)this.spellBook.getItem()).getSpells(this.spellBook);
		this.pages = (int)(Math.ceil((float)this.spells.length / 8.0F));
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.spellBook.getDisplayName()), 14, 14, 4210752);
		
		if(this.spell != null) {
			this.fontRendererObj.drawString(/* this.spell.spellType.textColour + */this.spell.name, 136, 14, 4210752);
			this.fontRendererObj.drawString("Requires: ", 136, 24, 4210752);
			String castType = "cast";
			if(this.spell.focus != null) {
				switch(this.spell.focus.component.getCastType(this.spell)) {
					case charge: case instant:
						castType = "cast";
						break;
					case eot: case continuous:
						castType = "second";
						break;
					default:
						castType = "cast";
						break;
				}
			}
			
			this.fontRendererObj.drawString("per " + castType + ".", 136, 54, 4210752);
			this.fontRendererObj.drawString((int)this.spell.getCastTime() + " MP" + " / " + castType + ".", 136, 65, 4210752);
			this.fontRendererObj.drawString("Bind:", 136, 75, 4210752);
		}
		
		if(this.debounce > 0) {
			this.debounce--;
		}
	}
	
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		
		for(int i = 0; i < this.buttonList.size(); i++) {
			if(this.buttonList.get(i) instanceof GuiButtonSpell && ((GuiButtonSpell)this.buttonList.get(i)).isMouseOverThisButton(x, y)) {
				this.drawToolTip(((GuiButtonSpell)this.buttonList.get(i)).spell, x, y, FMLClientHandler.instance().getClient());
			}
		}
	}
	
	public void initGui() {
		if(this.player.worldObj.isRemote) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation((Properties.ASSET_PREFIX + "book.page")), 1.0F));
		}
		
		super.initGui();
		buttonList.clear();
		this.drawSpells();
		
		int xPos = (this.width - this.xSize) / 2;
		int yPos = (this.height - this.ySize) / 2;
		
		ArrayList<ItemStack> spellbooks = new ArrayList<ItemStack>();
		
		for(int i = 0; i < this.player.inventory.mainInventory.length; i++) {
			if(this.player.inventory.mainInventory[i] != null && this.player.inventory.mainInventory[i].getItem() instanceof ISpellBook) {
				spellbooks.add(this.player.inventory.mainInventory[i]);
			}
		}
		
		if(spellbooks.size() > 1) {
			for(int i = 0; i < spellbooks.size(); i++) {
				buttonList.add(new GuiButtonSpellbook(i, spellbooks.get(i), xPos - 38, yPos + (32 * i) + (4 * i)));
			}
		}
		
		if(this.pages != 1) {
			if(this.page != 1)
				this.buttonList.add(new GuiButtonImage(510, this.guiLeft + 95, this.guiTop + 14, Properties.miscTexture, 7, 0, 0, 0));
			if(this.page != this.pages)
				this.buttonList.add(new GuiButtonImage(511, this.guiLeft + 110, this.guiTop + 14, Properties.miscTexture, 6, 0, 0, 0));
		}
	}
	
	@Override
	public void handleKeyboardInput() {
		super.handleKeyboardInput();
		if(Keyboard.getEventKeyState()) {
			if(Keyboard.getEventKey() == FMLClientHandler.instance().getClient().gameSettings.keyBindLeft.getKeyCode()) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 511));
			}
			
			if(Keyboard.getEventKey() == FMLClientHandler.instance().getClient().gameSettings.keyBindRight.getKeyCode()) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 510));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_1) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 501));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_2) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 502));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_3) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 503));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_4) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 504));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_5) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 505));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_6) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 506));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_7) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 507));
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_8) {
				this.actionPerformed(Utils.getGuiButtonWithID(this.buttonList, 508));
			}
		}
	}
	
	protected void actionPerformed(GuiButton button) {
		if(button == null)
			return;
		if(button instanceof GuiButtonSpell) {
			int xPos = (this.width - this.xSize) / 2;
			int yPos = (this.height - this.ySize) / 2;
			
			if(this.debounce != 0) {
				this.sendPacket(this.spell, 0, player);
				SpellHelper.getInstance().setPlayerSpell(player, this.spell, 0);
			}
			
			this.spell = ((GuiButtonSpell)button).spell;
			if(buttonList.size() > 499)
				buttonList.remove(500);
			if(buttonList.size() > 500)
				buttonList.remove(501);
			if(buttonList.size() > 501)
				buttonList.remove(502);
			if(buttonList.size() > 502)
				buttonList.remove(503);
			if(buttonList.size() > 503)
				buttonList.remove(504);
			if(buttonList.size() > 504)
				buttonList.remove(505);
			if(buttonList.size() > 505)
				buttonList.remove(506);
			if(buttonList.size() > 506)
				buttonList.remove(507);
			if(buttonList.size() > 507)
				buttonList.remove(508);
			{
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation((Properties.ASSET_PREFIX + "book.page")), 1.0F));
				buttonList.add(new GuiButton(500, xPos + 140, yPos + 132, 100, 20, "Equip"));
				
				buttonList.add(new GuiButton(501, xPos + 140, yPos + 85, 20, 20, "1"));
				buttonList.add(new GuiButton(502, xPos + 165, yPos + 85, 20, 20, "2"));
				buttonList.add(new GuiButton(503, xPos + 195, yPos + 85, 20, 20, "3"));
				buttonList.add(new GuiButton(504, xPos + 220, yPos + 85, 20, 20, "4"));
				
				buttonList.add(new GuiButton(505, xPos + 140, yPos + 108, 20, 20, "5"));
				buttonList.add(new GuiButton(506, xPos + 165, yPos + 108, 20, 20, "6"));
				buttonList.add(new GuiButton(507, xPos + 195, yPos + 108, 20, 20, "7"));
				buttonList.add(new GuiButton(508, xPos + 220, yPos + 108, 20, 20, "8"));
			}
		} else if(button instanceof GuiButtonSpellbook) {
			this.spellBook = ((GuiButtonSpellbook)button).spellbook;
			this.spells = ((ISpellCollection)this.spellBook.getItem()).getSpells(((GuiButtonSpellbook)button).spellbook);
			this.spell = null;
			this.page = 1;
			this.pages = (int)(Math.ceil((float)this.spells.length / 8.0F));
			
			initGui();
		} else if(button instanceof GuiButtonImage) {
			if(this.debounce == 0) {
				if(button.id == 510) {
					if(this.page != 1)
						this.page--;
				} else if(button.id == 511) {
					if(page != this.pages)
						this.page++;
				}
				
				this.initGui();
				this.spell = null;
			}
		} else {
			if(this.spell != null) {
				this.sendPacket(this.spell, button.id - 500, player);
				SpellHelper.getInstance().setPlayerSpell(player, this.spell, button.id - 500);
			}
		}
		
		if(this.debounce == 0)
			this.debounce = 15;
	}
	
	@SuppressWarnings("unchecked")
	protected void drawSpells() {
		int xPos = (this.width - this.xSize) / 2;
		int yPos = (this.height - this.ySize) / 2;
		
		int j = 0;
		int spellIndex = (this.page - 1) * 8;
		if(this.spells.length != 0) {
			for(int i = (this.page - 1) * 8; i < ((this.page - 1) * 8) + 8; i++) {
				if(this.spells.length != spellIndex) {
					buttonList.add(new GuiButtonSpell(20 + spellIndex, this.spells[spellIndex], xPos + 14 + (1 * 16), yPos + 27 + (j * 16)));
					spellIndex++;
					j++;
				} else {
					break;
				}
			}
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		ISpellBook bookObject = (ISpellBook)this.spellBook.getItem();
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		
		GL11.glPushMatrix();
		
		if(bookObject.drawPagesSeperately(this.spellBook)) {
			float[] colour = Utils.decodeColourF(bookObject.colourBackground(this.spellBook));
			GL11.glColor3f(colour[0], colour[1], colour[2]);
		}
		
		this.background = bookObject.getSpellbookBackground(this.spellBook);
		RenderUtils.bindTexture(this.background);
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(bookObject.drawPagesSeperately(this.spellBook)) {
			RenderUtils.bindTexture(bookObject.getSpellbookForeground(this.spellBook));
			this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		}
		GL11.glPopMatrix();
		
		ArrayList<ItemStack> spellbooks = new ArrayList<ItemStack>();
		
		for(int i = 0; i < this.player.inventory.mainInventory.length; i++) {
			if(this.player.inventory.mainInventory[i] != null && this.player.inventory.mainInventory[i].getItem() instanceof ISpellBook) {
				spellbooks.add(this.player.inventory.mainInventory[i]);
			}
		}
		
		GL11.glPushMatrix();
		RenderUtils.bindTexture(this.background);
		
		if(spellbooks.size() > 1) {
			for(int i = 0; i < spellbooks.size(); i++) {
				this.drawTexturedModalRect(var5 - 38, var6 + (32 * i) + (4 * i), 0, 166, 32, 32);
			}
		}
		GL11.glPopMatrix();
		
		if(this.spell != null) {
			RenderUtils.bindTexture(Properties.itemTexture);
			for(int i = 0; i < this.spell.getCastInfo().elements.length; i++) {
				GuiSpellbook.itemRender.renderIcon(var5 + 136 + (16 * i), var6 + 34, this.spell.getCastInfo().elements[i].element.Icon, 16, 16);
			}
		}
	}
	
	@Override
	public void onGuiClosed() {
		if(this.player.worldObj.isRemote) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation((Properties.ASSET_PREFIX + "book.close")), 1.0F));
		}
	}
	
	protected void drawToolTip(Spell spell, int par2, int par3, Minecraft minecraft) {
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		int var5 = 0;
		
		ArrayList<String> tooltip = new ArrayList<String>();
		tooltip.add(spell.name);
		if(spell.author != null && !spell.author.isEmpty())
			tooltip.add(StatCollector.translateToLocalFormatted(
					"sorcery.spellcasting.spellPageAuthor", spell.author));
		else tooltip.add(StatCollector.translateToLocal("sorcery.spellcasting.spellPageUnknownAuthor"));
		Iterator var6 = tooltip.iterator();
		
		while(var6.hasNext()) {
			String var7 = (String)var6.next();
			int var8 = minecraft.fontRenderer.getStringWidth(var7);
			
			if(var8 > var5) {
				var5 = var8;
			}
		}
		
		int var15 = par2 + 12;
		int var16 = par3 - 12;
		int var9 = 8;
		
		if(tooltip.size() > 1) {
			var9 += 2 + (tooltip.size() - 1) * 10;
		}
		
		this.zLevel = 300.0F;
		itemRender.zLevel = 300.0F;
		int var10 = -267386864;
		this.drawGradientRect(var15 - 3, var16 - 4, var15 + var5 + 3, var16 - 3, var10, var10);
		this.drawGradientRect(var15 - 3, var16 + var9 + 3, var15 + var5 + 3, var16 + var9 + 4, var10, var10);
		this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 + var9 + 3, var10, var10);
		this.drawGradientRect(var15 - 4, var16 - 3, var15 - 3, var16 + var9 + 3, var10, var10);
		this.drawGradientRect(var15 + var5 + 3, var16 - 3, var15 + var5 + 4, var16 + var9 + 3, var10, var10);
		int var11 = 1347420415;
		int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
		this.drawGradientRect(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + var9 + 3 - 1, var11, var12);
		this.drawGradientRect(var15 + var5 + 2, var16 - 3 + 1, var15 + var5 + 3, var16 + var9 + 3 - 1, var11, var12);
		this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 - 3 + 1, var11, var11);
		this.drawGradientRect(var15 - 3, var16 + var9 + 2, var15 + var5 + 3, var16 + var9 + 3, var12, var12);
		
		for(int var13 = 0; var13 < tooltip.size(); ++var13) {
			String var14 = (String)tooltip.get(var13);
			minecraft.fontRenderer.drawStringWithShadow(var14, var15, var16, -1);
			
			if(var13 == 0) {
				var16 += 2;
			}
			
			var16 += 10;
		}
		
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}
	
	public void sendPacket(Spell spell, int index, EntityPlayer player) {
		PlayerSpellPacket packet = new PlayerSpellPacket(spell, index);

		Sorcery.packetPipeline.sendToServer(packet);
	}
}
