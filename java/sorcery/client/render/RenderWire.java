package sorcery.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import sorcery.lib.Properties;
import sorcery.models.ModelElementalInfuser;
import sorcery.models.ModelWire;
import sorcery.tileentities.TileEntityMojoWire;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWire extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	private ModelWire model;
	
	public RenderWire() {
		this.model = new ModelWire();
	}
	
	public void renderPipeAt(TileEntityMojoWire wire, double x, double y, double z, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		this.model.render(wire, 0.0625F);
		GL11.glPopMatrix();
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderPipeAt((TileEntityMojoWire)par1TileEntity, par2, par4, par6, par8);
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		this.model.render(metadata, 0.0625F);
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
		return Properties.wireRenderID;
	}
}