package sorcery.api.element;

import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.SorceryAPI;

/** The Elements used in spells, like Void and Frost
 * 
 * @author Vroominator */
public class Element {
	public static Element[] elementsList = new Element[256];
	
	public static Element energy;
	public static Element stone;
	public static Element water;
	public static Element wind;
	public static Element frost;
	public static Element life;
	public static Element fire;
	public static Element voodoo;
	public static Element ender;
	public static Element arcane;
	
	/** @param name
	 *            The name of the element.
	 * @param id
	 *            The ID of the element.
	 * @param colour
	 *            The colour to use, in Minecraft's integer format.
	 * @see SorceryAPI#encodeColour Colour converter */
	public Element(String name, int ID, int colour) {
		this.name = name;
		this.ID = ID;
		this.colour = colour;
		
		if(elementsList[ID] != null)
			System.out.println("Element id " + ID + " already occupied by " + elementsList[ID] + " while adding " + this);
		else
			elementsList[ID] = this;
	}
	
	/** @param name
	 *            The name of the element.
	 * @param id
	 *            The ID of the element.
	 * @param colour
	 *            The colour to use in float rgb format. */
	public Element(String name, int ID, float[] colour) {
		this.name = name;
		this.ID = ID;
		this.colour = SorceryAPI.encodeColour(colour[0], colour[1], colour[2], 0F);
		
		if(elementsList[ID] != null)
			System.out.println("Element id " + ID + " already occupied by " + elementsList[ID] + " while adding " + this);
		else
			elementsList[ID] = this;
	}
	
	/** ID */
	public final int ID;
	
	/** Name, in lower case */
	private final String name;
	
	/** Colour, in Minecraft's integer format */
	private final int colour;
	
	/** Icons are registered automagically */
	public IIcon Icon;
	
	public String getLocalizedName() {
		return StatCollector.translateToLocal(this.getUnlocalizedName());
	}
	
	public String getUnlocalizedName() {
		return "sorceryapi.element." + this.name + ".name";
	}
	
	public String getName() {
		return this.name;
	}
	
	public IIcon getIcon() {
		return this.Icon;
	}
	
	public int getIntColour() {
		return this.colour;
	}
	
	public float[] getFloatColour() {
		return SorceryAPI.decodeColour(this.colour);
	}
	
	/** Retrieve the element using <code>name</code> */
	public static Element getElementFromName(String name) {
		for(int i = 0; i < elementsList.length; i++) {
			if(elementsList[i] != null && elementsList[i].getName().equals(name))
				return elementsList[i];
		}
		
		return null;
	}
	
	/** Retrieve the next free Element ID */
	public static int getNextElementID() {
		for(int i = 0; i < elementsList.length; i++) {
			if(elementsList[i] == null)
				return i;
		}
		
		return -1;
	}
}
