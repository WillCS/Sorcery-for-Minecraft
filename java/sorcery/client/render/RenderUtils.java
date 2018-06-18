package sorcery.client.render;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import sorcery.lib.utils.Utils;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderUtils {
	public static float rightAngle = (float)(Math.PI / 2);
	
	public static TextureManager renderEngine = FMLClientHandler.instance().getClient().renderEngine;
	public static RenderItem itemRenderer = new RenderItem();
	public static FontRenderer fontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
	
	public static void activate2D() {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
	}
	
	public static void deactivate2D() {
		GL11.glPopAttrib();
	}
	
	public static void activateBlending() {
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
		if(Minecraft.isFancyGraphicsEnabled()) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}
	
	public static void deactivateBlending() {
		GL11.glPopAttrib();
	}
	
	public static DoubleBuffer getArcPoints(double start, double end, double radius, double xO, double yO, double zO) {
		int vertices = (int)Math.ceil(Math.abs((end - start) * 16 * Math.PI));
		double d = (end - start) / vertices;
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(vertices * 3);
		
		double x = radius * Math.sin(start);
		double y = radius * Math.cos(start);
		
		double tanF = Math.tan(d);
		double radF = Math.cos(d);
		
		double tanX;
		double tanY;
		
		for(int i = 0; i < vertices; i++) {
			buffer.put(x + xO);
			buffer.put(y + yO);
			buffer.put(zO);
			
			tanX = y;
			tanY = -x;
			
			x += tanX * tanF;
			y += tanY * tanF;
			
			x *= radF;
			y *= radF;
		}
		buffer.flip();
		return buffer;
	}
	
	private static float square(float x, float y, float z) {
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public static void facePlayer() {
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, fb);
		float scaleX = square(fb.get(0), fb.get(1), fb.get(2));
		float scaleY = square(fb.get(4), fb.get(5), fb.get(6));
		float scaleZ = square(fb.get(8), fb.get(9), fb.get(10));
		for(int i = 0; i < 12; i++)
			fb.put(i, 0);
		
		fb.put(0, scaleX);
		fb.put(5, scaleY);
		fb.put(10, scaleZ);
		GL11.glLoadMatrix(fb);
	}
	
	public static float[] slighlyModifyColour(float[] colour) {
		Random rand = Minecraft.getMinecraft().theWorld.rand;
		float mod = rand.nextFloat() / 10.0F;
		boolean neg = rand.nextBoolean();
		colour[0] = neg ? colour[0] + mod : colour[0] - mod;
		
		mod = rand.nextFloat() / 10.0F;
		neg = rand.nextBoolean();
		colour[1] = neg ? colour[0] + mod : colour[0] - mod;
		
		mod = rand.nextFloat() / 10.0F;
		neg = rand.nextBoolean();
		colour[2] = neg ? colour[0] + mod : colour[0] - mod;
		
		return colour;
	}
	
	public static DoubleBuffer gradient(int colour1, int colour2, int points) {
		DoubleBuffer dbc = BufferUtils.createDoubleBuffer(points * 4);
		
		for(int i = 0; i < points; i++) {
			double[] c1 = Utils.decodeColourD(colour1);
			double[] c2 = Utils.decodeColourD(colour2);
			double[] c3 = {c1[0] * (1 - (i / points) + c2[0] * (i / points)), c1[1] * (1 - (i / points) + c2[1] * (i / points)), c1[2] * (1 - (i / points) + c2[2] * (i / points)), c1[3] * (1 - (i / points) + c2[3] * (i / points))};
			
			dbc.put(c3[0]);
			dbc.put(c3[1]);
			dbc.put(c3[2]);
			dbc.put(c3[3]);
		}
		
		dbc.flip();
		
		return dbc;
	}
	
	public static void bindTexture(String texture) {
		bindTexture(new ResourceLocation(texture));
	}
	
	public static void bindTexture(ResourceLocation texture) {
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
	}
	
	public static void drawTexturedQuad(int x, int y, int i, int j, int width, int height, int zLevel, float scale) {
		scale = scale / 2;
		i = (int)(i * scale);
		j = (int)(j * scale);
		width = (int)(width * scale);
		height = (int)(height * scale);
		float var7 = 0.00390625F / scale;
		float var8 = 0.00390625F / scale;
		Tessellator tesselator = Tessellator.instance;
		tesselator.startDrawingQuads();
		tesselator.addVertexWithUV(x + 0, y + height, zLevel, (i + 0) * var7, (j + height) * var8);
		tesselator.addVertexWithUV(x + width, y + height, zLevel, (i + width) * var7, (j + height) * var8);
		tesselator.addVertexWithUV(x + width, y + 0, zLevel, (i + width) * var7, (j + 0) * var8);
		tesselator.addVertexWithUV(x + 0, y + 0, zLevel, (i + 0) * var7, (j + 0) * var8);
		tesselator.draw();
	}
}
