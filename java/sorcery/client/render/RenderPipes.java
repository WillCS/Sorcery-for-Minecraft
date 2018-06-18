package sorcery.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import sorcery.fluid.PipeHelper;
import sorcery.lib.Properties;
import sorcery.models.ModelPipe;
import sorcery.tileentities.TileEntityPipe;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPipes extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	private ModelPipe model;
	
	public RenderPipes() {
		this.model = new ModelPipe();
	}
	
	public void renderPipeAt(TileEntityPipe pipe, double x, double y, double z, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		this.model.render(pipe, 0.0625F);
		GL11.glPopMatrix();
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderPipeAt((TileEntityPipe)par1TileEntity, par2, par4, par6, par8);
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		this.model.render(PipeHelper.getPipeFromID(metadata), 0.0625F);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelid) {
		return true;
	}
	
	@Override
	public int getRenderId() {
		return Properties.pipeRenderID;
	}
}