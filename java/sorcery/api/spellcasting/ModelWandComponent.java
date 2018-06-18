package sorcery.api.spellcasting;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public abstract class ModelWandComponent extends ModelBase {

	public abstract void render(ItemStack wand);
}
