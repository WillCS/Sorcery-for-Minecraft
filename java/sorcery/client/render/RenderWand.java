package sorcery.client.render;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import sorcery.models.ModelWand;

public class RenderWand implements IItemRenderer {
	private ModelWand model;
	
	public RenderWand() {
		this.model = new ModelWand();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY)
			return true;
		return false;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		boolean transparency = true;
		if(type == ItemRenderType.ENTITY) {
			GL11.glRotatef(((EntityItem)data[1]).ticksExisted, 0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.15F, 0F);
		} else if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glRotatef(-2, 0F, 0F, 1.0F);
		} else if(type == ItemRenderType.EQUIPPED) {
			transparency = false;
		}

		GL11.glPushMatrix();
		model.render(item, transparency);
		GL11.glPopMatrix();
	}
	
}
