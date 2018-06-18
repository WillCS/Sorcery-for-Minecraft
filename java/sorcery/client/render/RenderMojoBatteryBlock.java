package sorcery.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import sorcery.core.Sorcery;

public class RenderMojoBatteryBlock implements IItemRenderer {
	private RenderBlocks blocks = new RenderBlocks();
	
	public RenderMojoBatteryBlock() {
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
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int dmg = item.stackTagCompound.getInteger("damage");
        RenderUtils.bindTexture(TextureMap.locationBlocksTexture);
        if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        	GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        
        if(type == ItemRenderType.ENTITY)
        	GL11.glScalef(0.5F, 0.5F, 0.5F);
        
		blocks.renderBlockAsItem(Sorcery.mojoMachine, dmg, 1.0F);
	}
}
