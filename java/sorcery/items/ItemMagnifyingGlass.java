package sorcery.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.api.SorceryAPI;
import sorcery.api.research.BlockInfo;
import sorcery.core.Sorcery;
import sorcery.lib.EventScheduler.EventObject;
import sorcery.lib.SorceryItems;
import sorcery.network.UnlockResearchPacket;

public class ItemMagnifyingGlass extends ItemArcane {

	public ItemMagnifyingGlass() {
		this.setMaxStackSize(1);
		this.setFull3D();
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		System.out.println(world.isRemote);
		if(player.inventory.hasItem(SorceryItems.researchJournal)) {
			if(world.isRemote && !Sorcery.eventScheduler.isEventScheduled("research")) {
				ItemStack item = new ItemStack(world.getBlock(x, y, z), 1, world.getBlockMetadata(x, y, z));
				String blockName = item.getDisplayName();
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("sorcery.research.inspect" + world.rand.nextInt(9), blockName)));
				
				BlockInfo info = new BlockInfo();
				Block block = world.getBlock(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);
				info.setBlockAndMeta(block, meta);
				String nodeName = "null";
				//System.out.println(SorceryAPI.fieldResearchRecipes.size());
				for(int i = 0; i < SorceryAPI.fieldResearchRecipes.size(); i++) {
					if(SorceryAPI.fieldResearchRecipes.get(i).researchObject instanceof BlockInfo) {
						BlockInfo recipeInfo = (BlockInfo)SorceryAPI.fieldResearchRecipes.get(i).researchObject;
						if(recipeInfo.block == info.block && recipeInfo.metadata == info.metadata)
							nodeName = SorceryAPI.fieldResearchRecipes.get(i).researchTitle;
					}
				}
				
				Sorcery.eventScheduler.scheduleEvent("research", new EventObject(new Object[] {player, world.rand.nextInt(7), blockName, nodeName}) {
					@Override
					public void call() {
						if(!"null".equals(this.args[3]) && SorceryAPI.canPlayerUnlockResearch((EntityPlayer)this.args[0], (String)this.args[3])) {
							UnlockResearchPacket packet = new UnlockResearchPacket((String)this.args[3]);
							SorceryAPI.unlockResearch(((EntityPlayer)this.args[0]),
									SorceryAPI.fieldResearchRecipes.get(((Integer)this.args[1])).researchTitle);
						} else {
							((EntityPlayer)args[0]).addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("sorcery.research.fail" + args[1], args[2])));
						}
					}
				}, 60);
			}
			return true;
		} else {
			if(world.getBlock(x, y, z) == Blocks.bookshelf) {
				if(!world.isRemote) {
					world.setBlockToAir(x, y, z);
					ItemStack item = ItemResearchJournal.getBeginnerBook();
					item.stackTagCompound.setString("owner", player.func_145748_c_().getFormattedText());
					EntityItem entity = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, item);
					world.spawnEntityInWorld(entity);
					entity.playSound("random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
}
