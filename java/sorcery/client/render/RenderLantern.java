package sorcery.client.render;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL42;

import sorcery.models.ModelLantern;

public class RenderLantern implements IItemRenderer {
	private ModelLantern model;
	
	public RenderLantern() {
		this.model = new ModelLantern();
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
