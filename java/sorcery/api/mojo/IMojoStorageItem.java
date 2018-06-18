package sorcery.api.mojo;

import net.minecraft.item.ItemStack;

/** Interface for Items that store Mojo
 * 
 * @author Vroominator */
public interface IMojoStorageItem {
	
	/** Return the amount of Mojo stored in <code>item</code> */
	public abstract int getMojo(ItemStack item);
	
	/** Add <code>amount</code> of Mojo to <code>item</code>
	 * @return the amount of Mojo now stored */
	public abstract int addMojo(ItemStack item, int amount);
	
	/** Subtract <code>amount</code> of Mojo from <code>item</code>
	 * @return the amount of Mojo now stored */
	public abstract int subtractMojo(ItemStack item, int amount);
	
	/** Set the amount of Mojo in <code>item</code> to <code>amount</code>
	 * @return the amount of Mojo now stored */
	public abstract int setMojo(ItemStack item, int amount);
	
	/** Get the capacity of the <code>item</code>
	 * @return the capacity of <code>item</code> */
	public abstract int getCapacity(ItemStack item);
}
