package sorcery.guis.button;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonFixed extends GuiButton {

	public int width;
	public int height;
	
	public GuiButtonFixed(int ID, int X, int Y, int width, int height, String string) {
		super(ID, X, Y, width, height, string);
		this.width = width;
		this.height = height;
	}
}
