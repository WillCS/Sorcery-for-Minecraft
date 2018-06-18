package sorcery.guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import sorcery.containers.ContainerRuneCrafting;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryFluids;
import sorcery.tileentities.TileEntityRuneCrafting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRuneCrafting extends GuiContainerSorcery {
	private TileEntityRuneCrafting inventory;
	private EntityPlayer player;
	
	public GuiRuneCrafting(InventoryPlayer player, TileEntityRuneCrafting tile, World world, int i, int j, int k) {
		super(new ContainerRuneCrafting(player, tile, world, i, j, k), "runecrafting");
		this.inventory = tile;
		this.player = player.player;
	}
	
	/** Draw the foreground layer for the GuiContainer (everything in front of
	 * the items) */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.runecrafting"), 26, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	/** Draw the background layer for the GuiContainer (everything behind the
	 * items) */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int var7 = this.inventory.getCookProgressScaled(43);
		this.drawTexturedModalRect(this.guiWidth + 63, this.guiHeight + 29, 176, 61, var7 + 1, 16);
		
		if(this.inventory.tankQuantity > 0) {
			displayGauge(this.guiWidth, this.guiHeight, 19, 143, 58, TileEntityRuneCrafting.tankMaxCapacity, new FluidStack(FluidRegistry.getFluid("mojo"), this.inventory.tankQuantity));
		}
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		if(par1 > var5 + 143 && par2 > var6 + 19 && par1 < var5 + 143 + 16 && par2 < var6 + 19 + 59)
			this.drawToolTip(this.getFluidTooltip(new FluidStack(FluidRegistry.getFluid("mojo"), this.inventory.tankQuantity), TileEntityRuneCrafting.tankMaxCapacity), par1, par2);
	}
}
