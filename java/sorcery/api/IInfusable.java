package sorcery.api;

import sorcery.api.element.ElementStack;

/** Tile Entities implementing this interface have access to
 *  elemental infusers.
 *  @author Vroominator */
public interface IInfusable {
	
	/** @return whether or not this tile entity can accept <code>stack</code> */
	boolean canInfuse(ElementStack stack);
	
	/** Perform whatever it is your tile entity does when it receives charge.
	 *  @return the amount left over, if any */
	int infuse(ElementStack stack);
	
	/** @return the height of the point that beams will collide, relative to the center of the block */
	float getInfusionCenter();
}
