package sorcery.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.client.render.RenderUtils;
import sorcery.client.render.geometry.Circle;
import sorcery.lib.utils.Utils;

public class EntityMiniSpellFX extends EntityFX {
	float[] colours;
	private Circle circle;
	float scale;
	
	public EntityMiniSpellFX(World par1World, double par2, double par4, double par6, float scale, float[] colours) {
		super(par1World, par2, par4, par6);
		this.colours = colours;
		this.circle = new Circle();
		this.particleMaxAge = 40;
		this.noClip = true;
		this.particleGravity = 0F;
		this.scale = scale;
	}
	
	public EntityMiniSpellFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		this.circle = new Circle();
		this.particleMaxAge = 20;
		this.noClip = true;
		this.particleGravity = 0F;
		this.scale = 0.25F;
	}
	
	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
		float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
		float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
		
		if(this.colours != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			RenderUtils.facePlayer();
			float fade = this.particleAge / 40F;
			float scale = this.scale - (this.scale / fade);
			GL11.glScalef(scale, scale, scale);
			
			this.circle.setColour(Utils.encodeColour(this.colours[0], this.colours[1], this.colours[2], 0.5F - (fade / 2)), Utils.encodeColour(this.colours[0], this.colours[1], this.colours[2], 1F - fade));
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
			this.circle.draw(0.04F, 0, 0);
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}
	
}
