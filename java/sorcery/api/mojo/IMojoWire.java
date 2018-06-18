package sorcery.api.mojo;

import java.util.HashMap;

public interface IMojoWire {
	/** @return the colour of this wire */
	public int getColour();
	
	/** @return whether or not the this wire can reach <code>receiver</code> */
	public boolean connectsToReceiver(IMojoStorage receiver);
	
	/** @return whether or not there is a Mojo distributor on the network */
	public boolean findNearestMojoDistributor();
	
	/** @return the nearest IMojoDistributor capable of sending Mojo */
	public IMojoDistributor getNearestMojoDistributor();
	
	/** @return all the connected IMojoStorage blocks */
	public IMojoStorage[] getAllConnectedStorage();
	
	/** @return a list of the capacities of all the wires connecting this wire to <code>receiver</code> */
	public int[] getWireCapacitiesToReceiver(IMojoStorage receiver);
	
	/** @return the lowest capacity wire connecting this wire <code>receiver</code> */
	public int getLowestCapacityToReceiver(IMojoStorage receiver);
}
