package sorcery.guis;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.containers.ContainerNode;
import sorcery.core.Sorcery;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;
import sorcery.lib.Properties;
import sorcery.network.TileEntityPacket;
import sorcery.tileentities.nodes.TileEntityNodeBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNodeBase extends GuiContainerSorcery {
	private TileEntityNodeBase node;
	
	public static int offset = -6;
	
	private boolean redStoneOn;
	private boolean redStoneOff;
	private boolean redStonePulse;
	
	public static IIcon[] IIcons = new IIcon[50];
	
	public GuiNodeBase(InventoryPlayer player, TileEntityNodeBase node, World world, int i, int j, int k) {
		super(new ContainerNode(player, node, world, i, j, k), "itemtransport");
		this.node = node;
		this.xSize = 176;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		// this.fontRendererObj.drawString(StatCollector.translateToLocal(node.getInvName()),
		// 8, 6, 4210752);
		// this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"),
		// 8, this.ySize - 96 + 2, 4210752);
		if(this.node.isInput)
			this.fontRendererObj.drawString(this.node.getPriority() + "", this.node.guiDetails.priorityX + 6, this.node.guiDetails.priorityY + 6, (255 << 16) + (255 << 8) + 255);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(this.guiTexture);
		this.guiWidth = (this.width - this.xSize) / 2;
		this.guiHeight = (this.height - this.ySize) / 2;
		
		this.redStoneOn = (this.node.getRedstoneStatus() == 0);
		this.redStoneOff = (this.node.getRedstoneStatus() == 1);
		this.redStonePulse = (this.node.getRedstoneStatus() == 2);
		this.xSize = 176;
		// this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		
		if(this.node.hasBuffer())
			this.drawBuffer();
		
		if(!this.node.isInput && this.node.isOutput && this.node.getFilter().length != 0)
			this.drawFilter();
		
		if(this.node.isInput)
			this.drawPriorityButton();
		
		this.drawRedstoneButtons();
		this.drawInventory();
		this.drawColorSlots();
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		if(this.node.isInput) {
			if(par1 > xOffset + this.node.guiDetails.priorityX && par1 < xOffset + this.node.guiDetails.priorityX + 16) {
				if(par2 > yOffset + this.node.guiDetails.priorityY && par2 < yOffset + this.node.guiDetails.priorityY + 16) {
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.click"), 1.0F));
					
					int mod = (par3 == 0 ? 1 : -1);
					this.sendPacket((byte)0, mod);
					this.node.setPriority(mod);
				}
			}
		}
		
		if(par1 > xOffset + this.node.guiDetails.redControlX + 1 && par1 < xOffset + this.node.guiDetails.redControlX + 1 + 16) {
			if(par2 > yOffset + this.node.guiDetails.redControlY + 1 && par2 < yOffset + this.node.guiDetails.redControlY + 1 + 16) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.click"), 1.0F));
				
				this.sendPacket((byte)1, 0);
				this.node.setRedstoneStatus(0);
			}
		}
		
		if(par1 > xOffset + this.node.guiDetails.redControlX + 1 + 19 && par1 < xOffset + this.node.guiDetails.redControlX + 1 + 19 + 16) {
			if(par2 > yOffset + this.node.guiDetails.redControlY + 1 && par2 < yOffset + this.node.guiDetails.redControlY + 1 + 16) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.click"), 1.0F));
				
				this.sendPacket((byte)1, 1);
				this.node.setRedstoneStatus(1);
			}
		}
		
		/*
		 * if(this.node.isOutput && !this.node.isInput) { if(par1 > xOffset +
		 * this.node.guiDetails.redControlX + 1 + 38 && par1 < xOffset +
		 * this.node.guiDetails.redControlX + 1 + 38 + 16) { if(par2 > yOffset +
		 * this.node.guiDetails.redControlY + 1 && par2 < yOffset +
		 * this.node.guiDetails.redControlY + 1 + 16) {
		 * this.mc.getSoundHandler().playSoundFX("random.click", 1.0F, 1.0F);
		 * this.sendPacket((byte)1, 2); this.node.setRedstoneStatus(2); } } }
		 */
		
		if(this.node.isOutput && !this.node.isInput && node.getFilter().length != 0) {
			if(par1 > xOffset + this.node.guiDetails.filterX - 30 && par1 < xOffset + this.node.guiDetails.filterX - 30 + 16) {
				if(par2 > yOffset + this.node.guiDetails.filterY + offset + 7 && par2 < yOffset + this.node.guiDetails.filterY + offset + 7 + 16) {
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.click"), 1.0F));
					
					if(this.node.getOutputMode() == 0) {
						this.sendPacket((byte)2, 1);
						this.node.setOutputMode(1);
					} else {
						this.sendPacket((byte)2, 0);
						this.node.setOutputMode(0);
					}
				}
			}
		}
		
		super.mouseClicked(par1, par2, par3);
	}
	
	public void drawPriorityButton() {
		NodeGUIDetails dtls = this.node.guiDetails;
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(xOffset + dtls.priorityX + offset + 1, yOffset + dtls.priorityY + offset + 1, 200, 47, 28, 28);
		this.drawTexturedModalRect(xOffset + dtls.priorityX, yOffset + dtls.priorityY, 182, 0, 18, 18);
	}
	
	public void drawRedstoneButtons() {
		NodeGUIDetails dtls = this.node.guiDetails;
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		int buttons = /* this.node.isOutput && !this.node.isInput ? 3 : */2;
		boolean vertical = dtls.redControlVertical;
		int x = dtls.redControlX;
		int y = dtls.redControlY;
		int width = buttons == 3 ? 66 : 47;
		int height = 28;
		int bX = buttons == 3 ? 190 : 209;
		int bY = buttons == 3 ? 164 : 136;
		if(vertical) {
			bY = 0;
			bX = buttons == 3 ? 228 : 200;
			height = buttons == 3 ? 66 : 47;
			width = 28;
		}
		
		this.drawTexturedModalRect(xOffset + x + offset, yOffset + y + offset, bX, bY, width, height);
		
		if(this.redStoneOn)
			this.drawTexturedModalRect(xOffset + dtls.redControlX - 1, yOffset + dtls.redControlY - 1, 182, 18, 18, 18);
		else
			this.drawTexturedModalRect(xOffset + dtls.redControlX - 1, yOffset + dtls.redControlY - 1, 182, 0, 18, 18);
		
		if(this.redStoneOff)
			this.drawTexturedModalRect(xOffset + dtls.redControlX - 1 + 19, yOffset + dtls.redControlY - 1, 182, 18, 18, 18);
		else
			this.drawTexturedModalRect(xOffset + dtls.redControlX - 1 + 19, yOffset + dtls.redControlY - 1, 182, 0, 18, 18);
		
		/*
		 * if(this.node.isOutput && !this.node.isInput) { if(this.redStonePulse)
		 * this.drawTexturedModalRect(xOffset + dtls.redControlX - 1 + 38,
		 * yOffset + dtls.redControlY - 1, 182, 18, 18, 18); else
		 * this.drawTexturedModalRect(xOffset + dtls.redControlX - 1 + 38,
		 * yOffset + dtls.redControlY - 1, 182, 0, 18, 18); }
		 */
		
		RenderUtils.bindTexture(Properties.itemTexture);
		this.drawTexturedModelRectFromIcon(xOffset + dtls.redControlX, yOffset + dtls.redControlY, this.IIcons[0], 16, 16);
		this.drawTexturedModelRectFromIcon(xOffset + dtls.redControlX + 19, yOffset + dtls.redControlY, this.IIcons[1], 16, 16);
		/*
		 * if(this.node.isOutput && !this.node.isInput)
		 * this.drawTexturedModelRectFromIIcon(xOffset + dtls.redControlX + 38,
		 * yOffset + dtls.redControlY, this.IIcons[2], 16, 16);
		 */
		
		RenderUtils.bindTexture(this.guiTexture);
	}
	
	public void drawBuffer() {
		NodeGUIDetails dtls = this.node.guiDetails;
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		int x = 0;
		int y = 0;
		int width = 64;
		int height = 64;
		if(this.node.getBufferSize() != 3) {
			x = 192;
			y = 192;
		} else {
			if(dtls.bufferVertical) {
				x = 146;
				width = 28;
			} else {
				y = 138;
				height = 28;
			}
		}
		
		this.drawTexturedModalRect(xOffset + dtls.bufferX + offset, yOffset + dtls.bufferY + offset, x, y, width, height);
	}
	
	public void drawFilter() {
		NodeGUIDetails dtls = this.node.guiDetails;
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		int x = 192;
		int y = 192;
		int width = 64;
		int height = 64;
		
		this.drawTexturedModalRect(xOffset + dtls.filterX + offset, yOffset + dtls.filterY + offset, x, y, width, height);
		
		this.drawTexturedModalRect(xOffset + dtls.filterX - 30 - 6, yOffset + dtls.filterY + offset, 200, 47, 28, 28);
		this.drawTexturedModalRect(xOffset + dtls.filterX - 30 - 1, yOffset + dtls.filterY + offset + 5, 182, 0, 18, 18);
		switch(this.node.getOutputMode()) {
			case 0:
				this.drawTexturedModalRect(xOffset + dtls.filterX - 30, yOffset + dtls.filterY + offset + 6, 183, 36, 16, 16);
				break;
			case 1:
				this.drawTexturedModalRect(xOffset + dtls.filterX - 30, yOffset + dtls.filterY + offset + 6, 183, 52, 16, 16);
				break;
		}
	}
	
	public void drawInventory() {
		NodeGUIDetails dtls = this.node.guiDetails;
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		int x = dtls.inventoryX;
		int y = dtls.inventoryY;
		
		this.drawTexturedModalRect(xOffset + x - 8, yOffset + y - 8, 0, 166, 176, 90);
	}
	
	public void drawColorSlots() {
		NodeGUIDetails dtls = this.node.guiDetails;
		int xOffset = (this.width - this.xSize) / 2;
		int yOffset = (this.height - this.ySize) / 2;
		int xi = dtls.codeX;
		int yi = dtls.codeY;
		
		int xo = dtls.targetX;
		int yo = dtls.targetY;
		
		if(this.node.isInput) {
			this.drawTexturedModalRect(xOffset + xi + offset, yOffset + yi + offset, 64, 138, 47, 28);
			if(this.node.colourCode != -1) {
				int colour = ItemDye.field_150922_c[this.node.colourCode];
				float r = (float)(colour >> 16 & 255) / 255.0F;
				float g = (float)(colour >> 8 & 255) / 255.0F;
				float b = (float)(colour & 255) / 255.0F;
				GL11.glPushMatrix();
				GL11.glColor3f(r, g, b);
				this.drawTexturedModalRect(xOffset + xi + 20, yOffset + yi + 1, 149, 120, 16, 13);
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glPopMatrix();
			}
		}
		
		if(this.node.isOutput) {
			this.drawTexturedModalRect(xOffset + xo + offset, yOffset + yo + offset, 111, 138, 47, 28);
			
			if(this.node.colourTarget != -1) {
				int colour = ItemDye.field_150922_c[this.node.colourTarget];
				float r = (float)(colour >> 16 & 255) / 255.0F;
				float g = (float)(colour >> 8 & 255) / 255.0F;
				float b = (float)(colour & 255) / 255.0F;
				GL11.glPushMatrix();
				GL11.glColor3f(r, g, b);
				this.drawTexturedModalRect(xOffset + xo + 20, yOffset + yo + 1, 133, 120, 16, 13);
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glPopMatrix();
			}
		}
	}
	
	public void sendPacket(byte type, int data) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("action", type);
		tag.setInteger("data", data);
		TileEntityPacket packet = new TileEntityPacket(tag, this.node.xCoord, this.node.yCoord, this.node.zCoord);

		if(this.node.getWorldObj().isRemote)
			Sorcery.packetPipeline.sendToServer(packet);
		else
			Sorcery.packetPipeline.sendToAll(packet);
	}
}
