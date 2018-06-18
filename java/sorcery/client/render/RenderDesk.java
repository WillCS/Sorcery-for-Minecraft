package sorcery.client.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;

import sorcery.models.ModelDesk;
import sorcery.tileentities.TileEntityDesk;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDesk extends TileEntitySpecialRenderer implements IItemRenderer {
	private ModelDesk model;	
	private EntityItem item;
	
	public RenderDesk() {
		this.model = new ModelDesk();
	}
	
	public void renderDeskAt(TileEntityDesk tile, double x, double y, double z, float partialTickTime) {
		if(tile.isDummy)
			return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.model.renderTileEntity(tile);
		GL11.glPopMatrix();
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderDeskAt((TileEntityDesk)par1TileEntity, par2, par4, par6, par8);
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON || 
				type == ItemRenderType.ENTITY || type == ItemRenderType.INVENTORY)
			return true;
		return false;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		switch(type) {
			case ENTITY:
				model.renderItemEntity(((EntityItem)data[1]));
				break;
			case EQUIPPED_FIRST_PERSON:
				model.renderItemFirstPerson(item);
				break;
			case EQUIPPED:
				model.renderItemInHand(item);
				break;
			case INVENTORY:
				model.renderItemInventory(item);
				break;
			default:
				break;
		}
		GL11.glPopMatrix();
	}
}
