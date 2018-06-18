package sorcery.models.wand;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sorcery.api.spellcasting.ModelWandComponent;
import sorcery.client.render.RenderUtils;
import sorcery.lib.WandComponentRegistry;

public class ModelCrystalGeneratorComponent extends ModelWandComponent {
	    ModelRenderer Crystal;
	    
	    ResourceLocation texture = WandComponentRegistry.getWandComponentTexture("crystalGenerator");
	    
	    public ModelCrystalGeneratorComponent() {
			Crystal = new ModelRenderer(this, 0, 0);
			Crystal.addBox(-0.5F, -6F, -0.5F, 1, 1, 1);
			Crystal.setRotationPoint(0F, 0F, 0F);
			Crystal.setTextureSize(64, 32);
	    }
	    
		@Override
		public void render(ItemStack wand) {
			RenderUtils.bindTexture(this.texture);
			Crystal.render(0.63F);
		}
		
	}