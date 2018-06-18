package sorcery.guis;

import java.util.ArrayList;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import sorcery.client.render.RenderUtils;
import sorcery.containers.ContainerForge;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import sorcery.recipes.ForgeRecipes;
import sorcery.tileentities.TileEntityForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiForge extends GuiContainerSorcery {
	// private static ResourceLocation texture =
	// Utils.getResource("textures/guis/smithing.png");
	
	private TileEntityForge inventory;
	
	public GuiForge(InventoryPlayer player, TileEntityForge tile, World world, int i, int j, int k) {
		super(new ContainerForge(player, tile, world, i, j, k), "smithing");
		this.inventory = tile;
	}
	
	/** Draw the foreground layer for the GuiContainer (everything in front of
	 * the items) */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.forge"), 26, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	/** Draw the background layer for the GuiContainer (everything behind the
	 * items) */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		
		if(this.inventory.getTankAmount(0) > 0)
			displayGauge(this.guiWidth, this.guiHeight, 19, 143, 58, this.inventory.getTanks()[0]);
		
		int var7 = this.inventory.getCookProgressScaled(42);
		this.drawTexturedModalRect(this.guiWidth + 64, this.guiHeight + 27, 176, 58, var7 + 1, 17);
		
		int heat = this.inventory.getHeatScaled(75);
		if(heat != 0) {
			this.drawTexturedModalRect(this.guiWidth + 26, this.guiHeight + 61, 0, 166, heat, 4);
			if(ForgeRecipes.getInstance().findMatchingRecipe(this.inventory) != null) {
				int tempReq = ForgeRecipes.getInstance().findMatchingRecipe(this.inventory).temperature * 75 / 100;
				this.drawTexturedModalRect(this.guiWidth + 26 + tempReq - 2, this.guiHeight + 60, 0, 170, 5, 6);
			}
		}
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		checkMousePos(par1, par2);
	}
	
	public void checkMousePos(int x, int y) {
		if(x > this.guiWidth + 143 && y > this.guiHeight + 19 && x < this.guiWidth + 143 + 16 && y < this.guiHeight + 19 + 59) {
			this.drawToolTip(this.getFluidTooltip(this.inventory.getTanks()[0]), x, y);
		}
		
		if(x > this.guiWidth + 26 && y > this.guiHeight + 61 && x < this.guiWidth + 26 + 77 && y < this.guiHeight + 61 + 6) {
			ArrayList<String> toolTip = new ArrayList<String>();
			String tip = "\u00a77" + this.inventory.temperature + Properties.DEGREES;
			
			if(ForgeRecipes.getInstance().findMatchingRecipe(this.inventory) != null) {
				int tempReq = ForgeRecipes.getInstance().findMatchingRecipe(this.inventory).temperature;
				
				tip = tip + " / " + tempReq + Properties.DEGREES;
			}
			
			toolTip.add(tip);
			this.drawToolTip(toolTip, x, y);
		}
	}
}
