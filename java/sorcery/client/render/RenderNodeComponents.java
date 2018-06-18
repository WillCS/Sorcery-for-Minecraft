package sorcery.client.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import sorcery.models.ModelNodeBase;

public class RenderNodeComponents implements IItemRenderer {
	private ModelNodeBase model;
	
	public RenderNodeComponents() {
		this.model = new ModelNodeBase();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(item.getItemDamage() <= 2)
			if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.ENTITY)
				return true;
		
		return false;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		model.renderComponents(item.getItemDamage(), 0.0625F, type);
	}
}
