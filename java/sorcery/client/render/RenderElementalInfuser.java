package sorcery.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import sorcery.fluid.PipeHelper;
import sorcery.lib.Properties;
import sorcery.models.ModelElementalInfuser;
import sorcery.tileentities.TileEntityElementalInfuser;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderElementalInfuser extends TileEntitySpecialRenderer {
	private ModelElementalInfuser model;
	
	public RenderElementalInfuser() {
		this.model = new ModelElementalInfuser();
	}
	
	public void renderTileAt(TileEntityElementalInfuser tile, double x, double y, double z, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		this.model.render(tile);
		GL11.glPopMatrix();
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderTileAt((TileEntityElementalInfuser)par1TileEntity, par2, par4, par6, par8);
	}
}