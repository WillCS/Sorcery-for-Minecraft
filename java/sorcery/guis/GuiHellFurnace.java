package sorcery.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import sorcery.containers.ContainerHellFurnace;
import sorcery.lib.Properties;
import sorcery.tileentities.TileEntityHellFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHellFurnace extends GuiContainerSorcery {
	private TileEntityHellFurnace furnaceInventory;
	
	public GuiHellFurnace(InventoryPlayer player, TileEntityHellFurnace tile, World world, int i, int j, int k) {
		super(new ContainerHellFurnace(player, tile, world, i, j, k), "hellfurnace");
		this.furnaceInventory = tile;
	}
	
	/** Draw the foreground layer for the GuiContainer (everything in front of
	 * the items) */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.hellFurnace"), 60, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	/** Draw the background layer for the GuiContainer (everything behind the
	 * items) */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int var7;
		
		if(this.furnaceInventory.isBurning()) {
			var7 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(this.guiWidth + 56, this.guiHeight + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
		}
		
		var7 = this.furnaceInventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiWidth + 79, this.guiHeight + 34, 176, 14, var7 + 1, 16);
	}
}
