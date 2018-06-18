package sorcery.guis;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.containers.ContainerRepairTable;
import sorcery.tileentities.TileEntityRepairTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRepairTable extends GuiContainerMojo {
	private TileEntityRepairTable tile;
	
	public GuiRepairTable(InventoryPlayer player, TileEntityRepairTable tile, World world, int i, int j, int k) {
		super(new ContainerRepairTable(player, tile, world, i, j, k), tile, "repair");
		this.tile = tile;
	}
	
	protected void drawGuiContainerForegroundLayer(int j, int k) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.repair"), 8, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
}
