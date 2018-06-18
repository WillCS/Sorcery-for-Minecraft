package sorcery.guis;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.client.render.geometry.Sector;
import sorcery.containers.ContainerMojoStorage;
import sorcery.guis.button.GuiButtonSorcery;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityMojoStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMojoStorage extends GuiContainerSorcery {
	private TileEntityMojoStorage inventory;
	private Sector sector =  new Sector();
	
	public GuiMojoStorage(InventoryPlayer player, TileEntityMojoStorage tile, World world, int i, int j, int k) {
		super(new ContainerMojoStorage(player, tile, world, i, j, k), "mojoStorage");
		this.inventory = tile;
		this.requestUpdate(tile);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		this.buttonList.add(new GuiButtonSorcery(0, this.guiLeft + 10, this.guiTop + 25, "<"));
		this.buttonList.add(new GuiButtonSorcery(1, this.guiLeft + 65, this.guiTop + 25, ">"));
		this.buttonList.add(new GuiButtonSorcery(2, this.guiLeft + 10, this.guiTop + 45, "<"));
		this.buttonList.add(new GuiButtonSorcery(3, this.guiLeft + 65, this.guiTop + 45, ">"));
		this.buttonList.add(new GuiButtonSorcery(4, this.guiLeft + 154, this.guiTop + 4, "</>"));
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.mojoStorage"), 8, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		this.fontRendererObj.drawString(inventory.maxInput + " I",
				47 - (this.fontRendererObj.getStringWidth(inventory.maxInput + " I") / 2),
				this.ySize - 136, 4210752);
		
		this.fontRendererObj.drawString(inventory.maxOutput + " O",
				47 - (this.fontRendererObj.getStringWidth(inventory.maxOutput + " O") / 2),
				this.ySize - 116, 4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		
		float rad = (float)(((float)this.inventory.getMojo() / (float)this.inventory.getCapacity()) * (2F * Math.PI));
		ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		
		this.sector.setColour(Utils.encodeColour(0, 10, 207, 255), Utils.encodeColour(74, 79, 180, 255));
		this.sector.setAngle(rad + (Math.PI / 100),  Math.PI - (Math.PI / 100) + 0.015);
		this.sector.draw(37F, (res.getScaledWidth() / 2) + 36, (res.getScaledHeight() / 2) - 40F);
		
		this.drawTexturedModalRect(this.guiLeft + 86, this.guiTop + 6, 176, 26, 76, 76);
		this.drawTexturedModalRect(this.guiLeft + 111, this.guiTop + 31, 176, 0, 26, 26);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		if(this.isMouseInsideArea(par1, par2, this.guiLeft + 86, this.guiTop + 6, 76, 76)) {
			if(!this.isMouseInsideArea(par1, par2, this.guiLeft + 115, this.guiTop + 35, 17, 17)) {
				if(!this.inventory.cheaty) {
					this.drawToolTip(this.getMojoTooltip(this.inventory.getMojo(), this.inventory.getCapacity()), par1, par2);
				} else {
					ArrayList<String> list = new ArrayList<String>();
					list.add("\u00a77" + StatCollector.translateToLocal("sorcery.block.mojoStorage.info.infinite"));
					this.drawToolTip(list, par1, par2);
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id) {
			case 0:
				this.inventory.sendDataPacket((byte)1, -10);
				break;
			case 1:
				this.inventory.sendDataPacket((byte)1, 10);
				break;
			case 2:
				this.inventory.sendDataPacket((byte)2, -10);
				break;
			case 3:
				this.inventory.sendDataPacket((byte)2, 10);
				break;
			case 4:
				this.inventory.sendDataPacket((byte)0, 0);
				break;
		}
	}
	
	@Override
	public ArrayList<String> getHelpTooltip(int mouseX, int mouseY) {
		ArrayList<String> ret =  new ArrayList<String>();
		
		return ret;
	}
}
