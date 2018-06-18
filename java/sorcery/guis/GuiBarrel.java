package sorcery.guis;

import java.util.ArrayList;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;

import org.lwjgl.opengl.GL11;

import sorcery.containers.ContainerBarrel;
import sorcery.lib.Properties;
import sorcery.tileentities.TileEntityBarrel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBarrel extends GuiContainerSorcery {
	private TileEntityBarrel inventory;
	
	public GuiBarrel(InventoryPlayer player, TileEntityBarrel tile, World world, int i, int j, int k) {
		super(new ContainerBarrel(player, tile, world, i, j, k), "liquidstorage");
		this.inventory = tile;
	}
	
	/** Draw the foreground layer for the GuiContainer (everything in front of
	 * the items) */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.barrel"), 8, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	/** Draw the background layer for the GuiContainer (everything behind the
	 * items) */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		
		if(this.inventory.tanks[0] != null && this.inventory.tanks[0].getFluid() != null && this.inventory.tanks[0].getFluid().amount > 0) {
			displayGauge(this.guiWidth, this.guiHeight, 17, 62, 58, inventory.tanks[0]);
		}
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		
		if(par1 > var5 + 62 && par2 > var6 + 17 && par1 < var5 + 62 + 16 && par2 < var6 + 17 + 59)
			this.drawToolTip(this.getFluidTooltip(this.inventory.tanks[0]), par1, par2);
	}
}
