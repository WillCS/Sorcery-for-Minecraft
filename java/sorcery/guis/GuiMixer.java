package sorcery.guis;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.containers.ContainerMixer;
import sorcery.guis.button.GuiButtonImage;
import sorcery.lib.Properties;
import sorcery.tileentities.TileEntityMixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMixer extends GuiContainerSorcery {
	private TileEntityMixer inventory;
	
	public GuiMixer(InventoryPlayer player, TileEntityMixer tile, World world, int i, int j, int k) {
		super(new ContainerMixer(player, tile, world, i, j, k), "mixer");
		this.inventory = tile;
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		// this.buttonList.add(new GuiButtonImage(0, this.guiLeft + 62,
		// this.guiTop + 19, Properties.TEXTURE_FOLDER + "misc.png", 2, 0, 2,
		// 0));
		// this.buttonList.add(new GuiButtonImage(1, this.guiLeft + 98,
		// this.guiTop + 19, Properties.TEXTURE_FOLDER + "misc.png", 2, 0, 2,
		// 0));
		this.buttonList.add(new GuiButtonImage(0, this.guiLeft + 40, this.guiTop + 37, Properties.miscTexture, 0, 0, 1, 0));
		((GuiButtonImage)this.buttonList.get(0)).showImg1 = !this.inventory.shouldFillTank1;
	}
	
	/** Draw the foreground layer for the GuiContainer (everything in front of
	 * the items) */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("sorcery.container.mixer"), 8, 6, 4210752);
		// this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"),
		// 8, this.ySize - 96 + 2, 4210752);
	}
	
	/** Draw the background layer for the GuiContainer (everything behind the
	 * items) */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int var7 = this.inventory.getCookProgressScaled(92);
		this.drawTexturedModalRect(this.guiWidth + 41, this.guiHeight + 51, 0, 166, var7, 16);
		
		if(this.inventory.tanks[0].getFluid() != null && this.inventory.tanks[0].getFluid().amount > 0) {
			displayGauge(this.guiWidth, this.guiHeight, 19, 17, 58, inventory.tanks[0]);
		}
		
		if(this.inventory.tanks[1].getFluid() != null && this.inventory.tanks[1].getFluid().amount > 0) {
			displayGauge(this.guiWidth, this.guiHeight, 19, 143, 58, inventory.tanks[1]);
		}
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		if(par1 > this.guiWidth + 143 && par2 > this.guiHeight + 19 && par1 < this.guiWidth + 143 + 16 && par2 < this.guiHeight + 19 + 59)
			this.drawToolTip(this.getFluidTooltip(this.inventory.tanks[1]), par1, par2);
		
		if(par1 > this.guiWidth + 17 && par2 > this.guiHeight + 19 && par1 < this.guiWidth + 17 + 16 && par2 < this.guiHeight + 19 + 59)
			this.drawToolTip(this.getFluidTooltip(this.inventory.tanks[0]), par1, par2);
	}
	
	protected void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			if(this.inventory.inventory[4] == null) {
				this.inventory.sendNormalPacket(2);
				
				if(this.inventory.shouldFillTank1) {
					this.inventory.shouldFillTank1 = false;
				} else {
					this.inventory.shouldFillTank1 = true;
				}
				
				if(((GuiButtonImage)this.buttonList.get(0)).showImg1) {
					((GuiButtonImage)this.buttonList.get(0)).showImg1 = false;
				} else {
					((GuiButtonImage)this.buttonList.get(0)).showImg1 = true;
				}
			}
		}
	}
}
