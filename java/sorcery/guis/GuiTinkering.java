package sorcery.guis;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.containers.ContainerTinkering;
import sorcery.tileentities.TileEntityTinkering;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTinkering extends GuiContainerSorcery {
	public ContainerTinkering container;
	public TileEntityTinkering tileEntity;
	
	public GuiTinkering(InventoryPlayer player, TileEntityTinkering tile, World world, int i, int j, int k) {
		super(new ContainerTinkering(player, tile, world, i, j, k), "tinkering");
		this.tileEntity = tile;
	}
	
	protected void drawGuiContainerForegroundLayer(int j, int k) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.tinkering"), 28, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
}
