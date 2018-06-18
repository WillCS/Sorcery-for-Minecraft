package sorcery.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.api.ITinkeringReclaimable;
import sorcery.api.mojo.IMojoStorageItem;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryItems;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDrill extends ItemPickaxe implements IMojoStorageItem, ITinkeringReclaimable {

	public ItemDrill() {
		super(ToolMaterial.IRON);
		this.setCreativeTab(Sorcery.tabSorceryTools);
		this.setFull3D();
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public int getMojo(ItemStack item) {
		return this.getTagCompound(item).getInteger("mojo");
	}

	@Override
	public int addMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("mojo", this.getMojo(item) + amount);
		
		if(this.getMojo(item) > this.getCapacity(item))
			this.setMojo(item, this.getCapacity(item));
		
		return this.getMojo(item);
	}

	@Override
	public int subtractMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("mojo", this.getMojo(item) - amount);
		
		if(this.getMojo(item) < 0)
			this.setMojo(item, 0);
		
		return this.getMojo(item);
	}

	@Override
	public int setMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("mojo", amount);
		
		return amount;
	}

	@Override
	public int getCapacity(ItemStack item) {
		if(this.getTagCompound(item).getInteger("capacity") == 0)
			this.getTagCompound(item).setInteger("capacity", 1000);
		
		return this.getTagCompound(item).getInteger("capacity");
	}
	
	public NBTTagCompound getTagCompound(ItemStack item) {
		if(!item.hasTagCompound())
			item.setTagCompound(new NBTTagCompound());
		
		return item.getTagCompound();
	}

	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
		if(!this.isCheaty(item))
			this.subtractMojo(item, ((int)(block.getBlockHardness(world, x, y, z) * 4.0F)) * 5);
		return true;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		if(this.getMojo(stack) == 0)
			return 1;
		
		return this.getTagCompound(stack).getInteger("harvestLevel");
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		ret.put("pickaxe", 0);
		ret.put("shovel", 0);
		return ret.keySet();
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		if(block != null && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("axe"))
			return 1.0F;
		
		if(this.getMojo(stack) == 0)
			return 2.0F;
		
		return this.getTagCompound(stack).getFloat("digSpeed");
	}
	
	public boolean isCheaty(ItemStack stack) {
		if(stack.hasTagCompound())
			return(stack.stackTagCompound.getBoolean("cheaty"));
		
		return false;
	}

	@Override
    public boolean showDurabilityBar(ItemStack stack) {
		return !this.isCheaty(stack);
    }
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("capacity", 10000);
			stack.stackTagCompound.setInteger("mojo", 0);
		}
		
		int capacity = stack.stackTagCompound.getInteger("capacity");
		int amount = stack.stackTagCompound.getInteger("mojo");
		
		double percentage = (double)amount / (double)capacity;
		return 1 - percentage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean debug) {
		if(item.hasTagCompound()) {
			int capacity = item.stackTagCompound.getInteger("capacity");
			int amount = item.stackTagCompound.getInteger("mojo");
			
			if(item.stackTagCompound.getBoolean("cheaty")) {
				list.add(StatCollector.translateToLocal("sorcery.block.mojoStorage.infinite"));
			} else {
				list.add(StatCollector.translateToLocalFormatted(
						"sorcery.block.mojoStorage.info", Utils.formatInteger(amount), Utils.formatInteger(capacity)));
			}
		} else {
			item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setInteger("capacity", 10000);
			item.stackTagCompound.setInteger("mojo", 0);
			list.add(StatCollector.translateToLocalFormatted("sorcery.block.mojoStorage.info", 0, 10000));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(this.getDrillWithData(0, 10000, 0, 0, 8.0F, 3, 1));
		list.add(this.getDrillWithData(10000, 10000, 0, 0, 8.0F, 3, 1));
		list.add(this.getDrillWithData(0, 50000, 1, 1, 10.0F, 4, 2));
		list.add(this.getDrillWithData(50000, 50000, 1, 1, 10.0F, 4, 2));
		list.add(this.getDrillWithData(0, 50000, 2, 1, 12.0F, 5, 3));
		list.add(this.getDrillWithData(50000, 50000, 2, 1, 12.0F, 5, 3));
		list.add(this.getDrillWithData(0, 50000, 2, 1, 14.0F, 6, 4));
		list.add(this.getDrillWithData(50000, 50000, 2, 1, 14.0F, 6, 4));
		list.add(this.getCheatyDrill(2, 1, 12.0F, 100, 5));
	}
	
	public ItemStack getDrillWithData(int mojo, int max, int cog, int crystal, float speed, int level, int tier) {
		ItemStack ret = new ItemStack(this);
		ret.stackTagCompound = new NBTTagCompound();
		ret.stackTagCompound.setInteger("capacity", max);
		ret.stackTagCompound.setInteger("mojo", mojo);
		ret.stackTagCompound.setInteger("cogType", cog);
		ret.stackTagCompound.setInteger("crystalType", crystal);
		ret.stackTagCompound.setFloat("digSpeed", speed);
		ret.stackTagCompound.setInteger("harvestLevel", level);
		ret.stackTagCompound.setInteger("tier", tier);
		return ret;
	}
	
	public ItemStack getCheatyDrill(int cog, int crystal, float speed, int level, int tier) {
		ItemStack ret = this.getDrillWithData(1, 1, cog, crystal, speed, level, tier);
		ret.stackTagCompound.setBoolean("cheaty", true);
		return ret;
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
    	if(!par1ItemStack.hasTagCompound())
    		return StatCollector.translateToLocal("sorcery." + this.getUnlocalizedName() + ".0.name");

        return StatCollector.translateToLocal("sorcery." + this.getUnlocalizedName() + "." +
        		par1ItemStack.stackTagCompound.getInteger("tier") + ".name");
    }

    //TODO finish reclaiming the drill properly
	@Override
	public ArrayList<ItemStack> reclaim(ItemStack item, EntityPlayer player) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int cogType = this.getTagCompound(item).getInteger("cogType");
		int crystalType = this.getTagCompound(item).getInteger("crystaLType");
		
		ret.add(new ItemStack(SorceryItems.ingotSteel, 2));
		ret.add(new ItemStack(SorceryItems.cog, 1, cogType));
		
		if(this.getTagCompound(item).getInteger("tier") > 3)
			ret.add(new ItemStack(SorceryItems.ingotInfernite, 1));
		
		return ret;
	}

	@Override
	public boolean canReclaim(ItemStack item) {
		return !this.isCheaty(item);
	}
}
