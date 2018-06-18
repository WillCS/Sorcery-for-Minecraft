package sorcery.guis;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import sorcery.client.render.RenderUtils;
import sorcery.client.render.geometry.Sector;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityMojo;

public class GuiContainerMojo extends GuiContainerSorcery {
	public TileEntityMojo tile;
	public Sector sector = new Sector();
	private ResourceLocation meter = new ResourceLocation(Properties.ASSET_PREFIX + Properties.HUD_TEXTURE_FILE);
	
	public GuiContainerMojo(Container par1Container, TileEntityMojo mojo, String texture) {
		super(par1Container, texture);
		this.tile = mojo;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.mojoStorage"), 8, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		
		RenderUtils.bindTexture(this.meter);
		this.drawTexturedModalRect(this.guiLeft - 43, this.guiTop + 5, 41, 180, 41, 76);
		
		float rad = (float)(((float)this.tile.getMojo() / (float)this.tile.getCapacity()) * Math.PI);
		ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		
		this.sector.setColour(Utils.encodeColour(0, 10, 207, 255), Utils.encodeColour(74, 79, 180, 255));
		this.sector.setAngle(rad + (Math.PI / 100), 2 * Math.PI - 0.015 - rad);
		this.sector.draw(37F, (res.getScaledWidth() / 2) - 94F, (res.getScaledHeight() / 2) - 40F);
		
		this.drawTexturedModalRect(this.guiLeft - 45, this.guiTop + 5, 0, 180, 41, 76);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		if(this.isMouseInsideArea(par1, par2, this.guiLeft - 45, this.guiTop + 5, 41, 76))
			this.drawToolTip(this.getMojoTooltip(this.tile.getMojo(), this.tile.getCapacity()), par1, par2);
	}
}
