package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;

public class ModelCrystal extends ModelBase {
	private ModelRenderer crystal;
	private static ResourceLocation[] textures = {
		Utils.getResource("textures/entities/crystalEnder.png"),
		Utils.getResource("textures/entities/crystalBlazing.png")
	};
	
	public ModelCrystal() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		
		this.crystal = new ModelRenderer(this, 0, 0);
		this.crystal.addBox(-1F, -1F, -1F, 2, 2, 2);
		this.crystal.setRotationPoint(0F, 0F, 0F);
	}
	
	public void render(int type) {
		RenderUtils.bindTexture(textures[type]);
		this.crystal.render(0.0625F);
	}
}
