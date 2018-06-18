package sorcery.api.mojo;

import net.minecraftforge.common.util.ForgeDirection;

/** Interface for any sort of container for storing Mojo
 * 
 * @author Vroominator */
public interface IMojoStorage {
	
	/** Return the amount of Mojo stored in the container */
	public abstract int getMojo();
	
	/** Add <code>amount</code> of Mojo to the container
	 * @return the amount of Mojo now stored */
	public abstract int addMojo(int amount);
	
	/** Fill the container with <code>amount</code> of Mojo
	 * @return any Mojo leftover, if the container is filled.
	 * Return -1 if filling could not occur for any reason besides the container being full */
	public abstract int fill(int amount);
	
	/** Subtract <code>amount</code> of Mojo from the container 
	 * @return the amount of Mojo now stored */
	public abstract int subtractMojo(int amount);
	
	/** Set the amount of Mojo in the container to <code>amount</code>
	 * @return the amount of Mojo now stored */
	public abstract int setMojo(int amount);
	
	/** Get the capacity of the container
	 * @return the capacity of the container */
	public abstract int getCapacity();
	
	/** @return whether or not this container is full */
	public abstract boolean isFull();
	
	/** @return whether or not this container can receive Mojo */
	public abstract boolean canReceiveMojo();
	
	/** @return whether or not this container can send Mojo */
	public abstract boolean canSendMojo();
	
	/** @return a list of all the directions that this container should be able to receive from */
	public ForgeDirection[] getDirectionsToReceiveFrom();
}
