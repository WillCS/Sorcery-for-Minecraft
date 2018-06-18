package sorcery.client.render.geometry;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;

public class Sector {
	protected DoubleBuffer points;
	protected DoubleBuffer colour;
	
	public Sector() {
		DoubleBuffer arcPoints = RenderUtils.getArcPoints(Math.PI + (Math.PI / 100), Math.PI - 2 * Math.PI + (Math.PI / 100), 4, 0, 0, 0);
		this.points = BufferUtils.createDoubleBuffer(arcPoints.limit() + 6);
		this.points.put(new double[]{0, 0, 0});
		arcPoints.put(new double[]{0, 0, 0});
		this.points.put(arcPoints);
		arcPoints.rewind();
		this.points.put(arcPoints.get());
		this.points.put(arcPoints.get());
		this.points.put(arcPoints.get());
		this.points.flip();
	}
	
	public void setAngle(double angle, double start) {
		DoubleBuffer arcPoints = RenderUtils.getArcPoints(start + 0.00001F, start + angle, 4, 0, 0, 0);
		this.points = BufferUtils.createDoubleBuffer(arcPoints.limit() + 6);
		this.points.put(new double[]{0, 0, 0});
		arcPoints.put(new double[]{0, 0, 0});
		this.points.put(arcPoints);
		arcPoints.rewind();
		this.points.put(arcPoints.get());
		this.points.put(arcPoints.get());
		this.points.put(arcPoints.get());
		this.points.flip();
	}
	
	public void setColour(int colour1, int colour2) {
		DoubleBuffer dbc = RenderUtils.gradient(colour1, colour2, this.points.limit() / 3);
		
		this.colour = BufferUtils.createDoubleBuffer(dbc.limit() + 4);
		this.colour.put(Utils.decodeColourD(colour2));
		this.colour.put(dbc);
		this.colour.flip();
	}
	
	public void draw(float radius, float x, float y) {
		this.points.rewind();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(radius / 4, radius / 4, 0);
		RenderUtils.activate2D();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		RenderUtils.activateBlending();
		GL11.glColorPointer(4, 0, this.colour);
		GL11.glVertexPointer(3, 0, this.points);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, this.points.limit() / 3);
		RenderUtils.deactivateBlending();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		RenderUtils.deactivate2D();
		GL11.glPopMatrix();
	}
}
