package sorcery.client.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import sorcery.models.ModelStaff;

public class RenderStaff implements IItemRenderer {
	private ModelStaff model;
	
	public RenderStaff() {
		this.model = new ModelStaff();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			return true;
		return false;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		model.render(item, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F);
	}
	
}
