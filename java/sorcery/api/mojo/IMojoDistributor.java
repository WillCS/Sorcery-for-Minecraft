package sorcery.api.mojo;

import net.minecraftforge.common.util.ForgeDirection;

/** Interface for Mojo Storage tile entities specifically designed
 * 	to distribute Mojo.
 *  @author Vroominator */
public interface IMojoDistributor extends IMojoStorage {
	
	/** @return a list of all the directions that this container is able to send to */
	public ForgeDirection[] getDirectionsToSendTo();
}
