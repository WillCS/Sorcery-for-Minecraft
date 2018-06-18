package sorcery.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import sorcery.models.ModelWizardHat;

public class RenderWizardHatItem implements IItemRenderer {
	private ModelWizardHat model;
	
	public RenderWizardHatItem() {
		this.model = new ModelWizardHat();
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
		if(type == ItemRenderType.ENTITY)
			GL11.glRotatef(((EntityItem)data[1]).ticksExisted, 0F, 1.0F, 0.0F);
		else GL11.glTranslatef(0.5F, 0.2F, 0F);
				
		this.model.render(item, 0.0D, 0.0D, 0.0D, 0.06F);
	}
	
}
