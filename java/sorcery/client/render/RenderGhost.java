package sorcery.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import sorcery.entities.EntityGhost;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhost extends RenderLiving {
	private static final ResourceLocation texture = Utils.getResource(Properties.TEXTURE_FOLDER + "mobs/Ghost.png");
	protected ModelBiped modelBipedMain;
	protected float field_77070_b;
	
	public RenderGhost(ModelBiped par1ModelBiped, float par2) {
		this(par1ModelBiped, par2, 1.0F);
		this.modelBipedMain = par1ModelBiped;
	}
	
	public RenderGhost(ModelBiped par1ModelBiped, float par2, float par3) {
		super(par1ModelBiped, par2);
		this.modelBipedMain = par1ModelBiped;
		this.field_77070_b = par3;
	}
	
	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {
		super.renderEquippedItems(par1EntityLiving, par2);
		ItemStack var3 = par1EntityLiving.getHeldItem();
		
		if(var3 != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			float var4;
			
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(var3, ItemRenderType.EQUIPPED);
			boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, var3, ItemRendererHelper.BLOCK_3D));
			
			if(var3.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(((ItemBlock)var3.getItem()).field_150939_a.getRenderType()))) {
				var4 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var4 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
			} else if(var3.getItem() == Items.bow) {
				var4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if(var3.getItem().isFull3D()) {
				var4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				var4 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var4, var4, var4);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}
			
			this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 0);
			
			if(var3.getItem().requiresMultipleRenderPasses()) {
				for(int x = 1; x < var3.getItem().getRenderPasses(var3.getItemDamage()); x++) {
					this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, x);
				}
			}
			
			GL11.glPopMatrix();
		}
	}
	
	protected int shouldAlphaRenderPass(EntityGhost par1Entity, int par2, float par3) {
		if(par2 == 0) {
			this.setRenderPassModel(this.modelBipedMain);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return 1;
		} else {
			if(par2 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			
			return -1;
		}
	}
	
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		this.setRenderPassModel(this.modelBipedMain);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
		return this.shouldAlphaRenderPass((EntityGhost)par1EntityLiving, par2, par3);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
