package sorcery.client.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import sorcery.api.element.Element;
import sorcery.items.ItemMagicOrb;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityRuneCrafting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRunicInfuser extends TileEntitySpecialRenderer {
	public void renderRunicInfuserAt(TileEntityRuneCrafting tile, double par2, double par4, double par6, float par8) {
		if(tile.itemToRender != null && tile.itemToRender.getEntityItem().getItem() instanceof ItemMagicOrb) {
			RenderManager.instance.renderEntitySimple(tile.itemToRender, 1);
			
			float[] rgb = Element.elementsList[tile.itemToRender.getEntityItem().getItemDamage()].getFloatColour();
			if(rgb == null)
				return;
			int rand = tile.getWorldObj().rand.nextInt(8);
			if(rand == 0)
				tile.getWorldObj().spawnParticle("mobSpell", tile.itemToRender.posX, tile.itemToRender.posY + 1.25, tile.itemToRender.posZ, rgb[0], rgb[1], rgb[2]);
		}
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderRunicInfuserAt((TileEntityRuneCrafting)par1TileEntity, par2, par4, par6, par8);
	}
}
