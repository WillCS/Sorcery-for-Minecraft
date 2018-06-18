package sorcery.guis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.api.mojo.MojoBattery;
import sorcery.client.render.RenderUtils;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import sorcery.network.TileEntityPacket;

public class GuiContainerSorcery extends GuiContainer {
	protected ResourceLocation guiTexture;
	protected int guiWidth;
	protected int guiHeight;
	protected boolean helpMode = false;
	
	public GuiContainerSorcery(Container par1Container, String texture) {
		super(par1Container);
		this.guiTexture = Utils.getResource("textures/guis/" + texture + ".png");
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawHelpButton();
		RenderUtils.bindTexture(this.guiTexture);
		this.guiWidth = (this.width - this.xSize) / 2;
		this.guiHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(this.guiWidth, this.guiHeight, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		if(this.helpMode) {
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			RenderHelper.disableStandardItemLighting();
            RenderUtils.bindTexture(Properties.hudTexture);
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(par1 + 7, par2 + 10, 247, 36, 9, 15);
			GL11.glPopMatrix();
			
			ArrayList tip = this.getHelpTooltip(par1, par2);
			if(!tip.isEmpty())
				this.drawToolTip(tip, par1, par2);
		}
	}
	
	@Override
    protected void keyTyped(char par1, int par2) {
		if(par2 == 1 && this.helpMode) {
			this.helpMode = false;
			return;
		}
		
		super.keyTyped(par1, par2);
		if(par1 == 'i')
			this.helpMode = !this.helpMode;
	}
	
	protected void drawHelpButton() {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		RenderHelper.disableStandardItemLighting();
        RenderUtils.bindTexture(Properties.hudTexture);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(this.guiLeft + this.xSize - 28, this.guiTop - 30, 210, 0, 28, 28);
		if(!this.helpMode)
			this.drawTexturedModalRect(this.guiLeft + this.xSize - 23, this.guiTop - 25, 238, 0, 18, 18);
		else
			this.drawTexturedModalRect(this.guiLeft + this.xSize - 23, this.guiTop - 25, 238, 18, 18, 18);
		
		this.drawTexturedModalRect(this.guiLeft + this.xSize - 19, this.guiTop - 24, 247, 36, 9, 15);
		GL11.glPopMatrix();
	}

	protected void drawToolTip(List tooltip, int par2, int par3) { //TODO Make tooltips switch sides when they won't fit onscreen
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		if(!tooltip.isEmpty()) {
			int var5 = 0;
			Iterator var6 = tooltip.iterator();
			
			while(var6.hasNext()) {
				String var7 = (String)var6.next();
				int var8 = this.fontRendererObj.getStringWidth(var7);
				
				if(var8 > var5) {
					var5 = var8;
				}
			}
			
			if(par2 + var5 + 18 >= this.width) {
				par2 -= var5 + 24;
			}
			
			int var15 = par2 + 12;
			int var16 = par3 - 12;
			int var9 = 8;
			
			if(tooltip.size() > 1) {
				var9 += 2 + (tooltip.size() - 1) * 10;
			}
			
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
				
				this.fontRendererObj.drawStringWithShadow(var14, var15, var16, -1);
				
				if(var13 == 0) {
					var16 += 2;
				}
				
				var16 += 10;
			}
		}
	}
	
	protected void displayGauge(int x, int y, int row, int column, int tankHeight, FluidTank tank) {
		int fluidAmount = tank.getFluid().amount * tankHeight / tank.getCapacity();
		this.drawTank(x, y, row, column, fluidAmount, tank.getFluid());
	}
	
	protected void displayGauge(int x, int y, int row, int column, int tankHeight, int tankCapacity, FluidStack fluid) {
		int fluidAmount = fluid.amount * tankHeight / tankCapacity;
		this.drawTank(x, y, row, column, fluidAmount, fluid);
	}
	
	private void drawTank(int x, int y, int row, int column, int scaledAmount, FluidStack fluid) {
		if(fluid == null)
			return;
		
		IIcon FluidIIcon;
		FluidIIcon = fluid.getFluid().getStillIcon();
		
		int start = 0;
		
		RenderUtils.bindTexture(Properties.terrainTexture);
		while(true && FluidIIcon != null) {
			int displacement = 0;
			
			if(scaledAmount > 16) {
				displacement = 16;
				scaledAmount -= 16;
			} else {
				displacement = scaledAmount;
				scaledAmount = 0;
			}
			
			this.drawTexturedModelRectFromIcon(x + column, y + row + 58 - displacement - start, FluidIIcon, 16, 16 - (16 - displacement));
			start = start + 16;
			
			if(displacement == 0 || scaledAmount == 0)
				break;
		}
		RenderUtils.bindTexture(this.guiTexture);
		drawTexturedModalRect(x + column, y + row, 176, 0, 16, 58);
	}
	
	public String formatFluidString(FluidTank tank) {
		if(tank.getFluidAmount() == 0)
			return "0" + Properties.mL + "/ " + Utils.formatInteger(tank.getCapacity()) + Properties.mL;
		
		return Utils.formatInteger(tank.getFluidAmount()) + Properties.mL + " / " + Utils.formatInteger(tank.getCapacity()) + Properties.mL;
	}
	
	public String formatFluidString(int amount, int capacity) {
		if(amount == 0)
			return "0" + Properties.mL + " / " + Utils.formatInteger(capacity) + Properties.mL;
		
		return Utils.formatInteger(amount) + Properties.mL + " / " + Utils.formatInteger(capacity) + Properties.mL;
	}
	
	public ArrayList<String> getFluidTooltip(FluidTank tank) {
		return getFluidTooltip(tank.getFluid(), tank.getCapacity());
	}
	
	public ArrayList<String> getFluidTooltip(FluidStack fluid, int capacity) {
		ArrayList<String> toolTip = new ArrayList<String>();
		if(fluid == null || fluid.amount == 0) {
			toolTip.add("Empty");
			toolTip.add("\u00a77" + formatFluidString(0, capacity));
		} else {
			toolTip.add(StatCollector.translateToLocal(fluid.getFluid().getLocalizedName() + ".name"));
			toolTip.add("\u00a77" + formatFluidString(fluid.amount, capacity));
		}
		
		return toolTip;
	}
	
	public String formatMojoString(MojoBattery battery) {
		
		return Utils.formatInteger(battery.getMojo()) + " / " + Utils.formatInteger(battery.getCapacity()) + " Mojo";
	}
	
	public String formatMojoString(int mojo, int capacity) {
		
		return Utils.formatInteger(mojo) + " / " + Utils.formatInteger(capacity) + " Mojo";
	}
	
	public ArrayList<String> getMojoTooltip(MojoBattery battery) {
		ArrayList<String> toolTip = new ArrayList<String>();
		
		toolTip.add("\u00a77" + formatMojoString(battery));
		
		return toolTip;
	}
	
	public ArrayList<String> getMojoTooltip(int mojo, int capacity) {
		ArrayList<String> toolTip = new ArrayList<String>();
		
		toolTip.add("\u00a77" + formatMojoString(mojo, capacity));
		
		return toolTip;
	}
	
	public ArrayList<String> getHelpTooltip(int mouseX, int mouseY) {
		return new ArrayList<String>();
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		
		if(this.isMouseInsideArea(par1, par2, this.guiLeft + this.xSize - 22, this.guiTop - 25, 18, 18)) {
			this.helpMode = !this.helpMode;
		} else if(this.helpMode){
			this.helpMode = false;
		}
	}
	
	public boolean isMouseInsideArea(int mouseX, int mouseY, int areaLeft, int areaTop, int width, int height) {
		if(mouseX >= areaLeft && mouseX <= areaLeft + width)
			if(mouseY >= areaTop && mouseY <= areaTop + height)
				return true;
		
		return false;
	}
	
	public void requestUpdate(TileEntity tile) { 
		TileEntityPacket packet = new TileEntityPacket(null, tile.xCoord, tile.yCoord, tile.zCoord);
		packet.setSpecialID(TileEntityPacket.REQUEST_ID);
		Sorcery.packetPipeline.sendToServer(packet);
	}
}
