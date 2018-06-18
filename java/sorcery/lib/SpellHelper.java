package sorcery.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import sorcery.api.ISpellBook;
import sorcery.api.ISpellHelper;
import sorcery.api.SorceryAPI;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.element.IElementProvider;
import sorcery.api.research.Research;
import sorcery.api.research.ResearchNode;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.WandComponent;
import sorcery.core.Sorcery;
import sorcery.items.ItemSpellbook;
import sorcery.items.ItemStaff;
import sorcery.items.ItemWand;
import sorcery.mojo.PlayerMojoInfo;
import sorcery.network.PlayerMojoInfoPacket;
import sorcery.network.PlayerResearchPacket;

public class SpellHelper implements ISpellHelper {
	public static SpellHelper instance = new SpellHelper();
	
	public static SpellHelper getInstance() {
		return instance;
	}
	
	public ItemStack getSpellBook(EntityPlayer player) {
		if(player.getCurrentEquippedItem().getItem() instanceof ItemSpellbook) {
			return player.getCurrentEquippedItem();
		} else {
			for(int i = 0; i < player.inventory.mainInventory.length; i++) {
				if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ISpellBook) {
					return player.inventory.mainInventory[i];
				}
			}
		}
		return null;
	}
	
	public ItemStack getSpellBookItem(EntityPlayer player) {
		if(player.getCurrentEquippedItem().getItem() instanceof ItemSpellbook) {
			return player.getCurrentEquippedItem();
		} else {
			for(int i = 0; i < player.inventory.mainInventory.length; i++) {
				if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ISpellBook) {
					return player.inventory.mainInventory[i];
				}
			}
		}
		return null;
	}
	
	public boolean hasEnergy(EntityPlayer player, Spell spell) {
		if(player.inventory.hasItem(SorceryItems.energyInfinite))
			return true;
		
		int[] foundElements = new int[spell.getCastInfo().elements.length];
		
		for(int i = 0; i < spell.getCastInfo().elements.length; i++) {
			for(int j = 0; j < player.inventory.armorInventory.length; j++) {
				if(player.inventory.armorInventory[j] != null && player.inventory.armorInventory[j].getItem() instanceof IElementProvider) {
					IElementProvider item = (IElementProvider)player.inventory.armorInventory[j].getItem();
					ElementStack[] elements = item.getElements(player.inventory.armorInventory[j]);
					
					for(int k = 0; k < elements.length; k++) {
						if(elements[k].getID() == spell.getCastInfo().elements[i].getID()) {
							foundElements[i] += elements[k].amount;
						}
					}
				}
			}
			
			for(int j = 0; j < player.inventory.mainInventory.length; j++) {
				if(player.inventory.mainInventory[j] != null && player.inventory.mainInventory[j].getItem() instanceof IElementProvider) {
					IElementProvider item = (IElementProvider)player.inventory.mainInventory[j].getItem();
					ElementStack[] elements = item.getElements(player.inventory.mainInventory[j]);
					
					for(int k = 0; k < elements.length; k++) {
						if(elements[k].getID() == spell.getCastInfo().elements[i].getID()) {
							foundElements[i] += elements[k].amount;
						}
					}
				}
			}
			
			if(player.getCurrentEquippedItem().getItem() instanceof ItemStaff) {
				if(player.getCurrentEquippedItem().getItemDamage() == spell.getCastInfo().elements[i].getID())
					foundElements[i] = spell.getCastInfo().elements[i].amount;
			}
		}
		
		for(int i = 0; i < spell.getCastInfo().elements.length; i++) {
			if(foundElements[i] < spell.getCastInfo().elements[i].amount)
				return false;
		}
		
		return true;
	}
	
	public void consumeElements(EntityPlayer player, Spell spell, ItemStack wand) {
		if(player.worldObj.isRemote)
			return;

		ElementStack[] elements = spell.getCastInfo().elements.clone();
		
		int[] amount = new int[elements.length];
		for(int i = 0; i < amount.length; i++)
			amount[i] = elements[i].amount;
		
		for(int i = 0; i < elements.length; i++) {
			if(player.getCurrentEquippedItem().getItem() instanceof ItemStaff) {
				if(player.getCurrentEquippedItem().getItemDamage() == spell.getCastInfo().elements[i].getID()) {
					amount[i] = 0;
				}
			}
			
			if(amount[i] != 0)
				amount[i] = this.consumeElement(player.inventory.armorInventory, new ElementStack(elements[i].element, amount[i]));
			
			if(amount[i] != 0)
				amount[i] = this.consumeElement(player.inventory.mainInventory, new ElementStack(elements[i].element, amount[i]));
		}
	}
	
	public int consumeElement(ItemStack[] inv, ElementStack element) {
		int consumed = element.amount;
		
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null && inv[i].getItem() instanceof IElementProvider) {
				ElementStack[] elements = ((IElementProvider)inv[i].getItem()).getElements(inv[i]).clone();
				
				for(int j = 0; j < elements.length; j++) {
					if(elements[j].getID() == element.getID()) {
						ElementStack e = elements[j];
						
						for(int k = 0; k < e.amount; k++) {
							if(consumed > 0) {
								inv[i] = ((IElementProvider)inv[i].getItem()).consume(inv[i], new ElementStack(element.element, 1));
								consumed--;
							}
							
							if(inv[i] != null && inv[i].stackSize == 0)
								inv[i] = null;
							
							if(consumed <= 0)
								return 0;
						}
					}
					
					if(consumed <= 0)
						return 0;
				}
			}
			
			if(consumed <= 0)
				return 0;
		}
		
		if(consumed <= 0)
			return 0;
		
		return consumed;
	}
	
	public int getElementAmountInPlayerInv(EntityPlayer player, Element element) {
		if(player.inventory.hasItem(SorceryItems.energyInfinite))
			return -1;
		
		int found = 0;
		
		for(int j = 0; j < player.inventory.armorInventory.length; j++) {
			if(player.inventory.armorInventory[j] != null && player.inventory.armorInventory[j].getItem() instanceof IElementProvider) {
				IElementProvider item = (IElementProvider)player.inventory.armorInventory[j].getItem();
				ElementStack[] elements = item.getElements(player.inventory.armorInventory[j]);
				
				for(int k = 0; k < elements.length; k++) {
					if(elements[k].getID() == element.ID) {
						found += elements[k].amount;
					}
				}
			}
		}
		
		for(int j = 0; j < player.inventory.mainInventory.length; j++) {
			if(player.inventory.mainInventory[j] != null && player.inventory.mainInventory[j].getItem() instanceof IElementProvider) {
				IElementProvider item = (IElementProvider)player.inventory.mainInventory[j].getItem();
				ElementStack[] elements = item.getElements(player.inventory.mainInventory[j]);
				
				for(int k = 0; k < elements.length; k++) {
					if(elements[k].getID() == element.ID) {
						found += elements[k].amount;
					}
				}
			}
		}
		
		if(player.getCurrentEquippedItem().getItem() instanceof ItemStaff) {
			if(player.getCurrentEquippedItem().getItemDamage() == element.ID)
				found = -1;
		}
		
		return found;
	}
	
	public NBTTagCompound getPlayerSorceryCustomData(EntityPlayer player) {
		if(!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		
		if(!player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey("SorceryData")) {
			player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag("SorceryData", new NBTTagCompound());
		}
		
		return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag("SorceryData");
	}
	
	public NBTTagList getPlayerResearchTag(EntityPlayer player) {
		return getPlayerSorceryCustomData(player).getTagList("research", 10);
	}
	
	public boolean hasPlayerUnlockedNode(EntityPlayer player, ResearchNode node) {
		Research research = Research.readFromNBT(player);
		return research.nodes.contains(node);
	}
	
	public boolean doesPlayerHaveMojoForSpell(EntityPlayer player, Spell spell) {
		int mojo = getPlayerMojo(player);
		
		if(mojo - spell.getCastInfo().mojoCost >= 0)
			return true;
		return true;
	}
	
	public int getPlayerMojo(EntityPlayer player) {
		getPlayerSorceryCustomData(player);
		
		return getPlayerSorceryCustomData(player).getInteger("mojo");
	}
	
	public void setPlayerMojo(EntityPlayer player, int mp) {
		getPlayerSorceryCustomData(player).setInteger("mojo", mp);
	}
	
	public void consumePlayerMojo(EntityPlayer player, int mojo) {
		int playerMojo = getPlayerMojo(player);
		
		setPlayerMojo(player, playerMojo - mojo);
		if(getPlayerMojo(player) <= 0)
			setPlayerMojo(player, 0);
	}
	
	public void setPlayerMaxMojo(EntityPlayer player, int mojo) {
		getPlayerSorceryCustomData(player).setInteger("maxMojo", mojo);
	}
	
	public int getPlayerMaxMojoWithoutRestrictedSectors(EntityPlayer player) {
		if(getPlayerSorceryCustomData(player).getInteger("maxMojo") == 0)
			return 200;
		
		return getPlayerSorceryCustomData(player).getInteger("maxMojo");
	}
	
	public int getPlayerMaxMojo(EntityPlayer player) {
		if(getPlayerSorceryCustomData(player).getInteger("maxMojo") == 0)
				return 200;
		
		int restricted = getPlayerTotalRestrictedMojo(player);
		
		return getPlayerSorceryCustomData(player).getInteger("maxMojo") - restricted;
	}
	
	public void setPlayerCanRegenMojo(EntityPlayer player, boolean regen) {
		getPlayerSorceryCustomData(player).setBoolean("mojoRegen", !regen);
	}
	
	public boolean getPlayerCanRegenMojo(EntityPlayer player) {
		return !getPlayerSorceryCustomData(player).getBoolean("mojoRegen");
	}
	
	public boolean getPlayerCanUseMojo(EntityPlayer player) {
		if(getIsPlayerBurntOut(player))
			return false;
		
		if(getPlayerMojo(player) <= 0)
			return false;
		
		if(getPlayerMaxMojo(player) <= 0)
			return false;
		
		return true;
	}
	
	public HashMap<String, Integer> getPlayerRestrictedMojoSectors(EntityPlayer player) {
		HashMap<String, Integer> ret = new HashMap();
		NBTTagList list = getPlayerSorceryCustomData(player).getTagList("restrictedMojoSectors", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)list.getCompoundTagAt(i);
			ret.put(tag.getString("name"), tag.getInteger("amount"));
		}
		
		return ret;
	}
	
	public void setPlayerRestrictedMojoSectors(EntityPlayer player, HashMap<String, Integer> sectors) {	
		NBTTagList list = new NBTTagList();
		
		if(sectors != null) {
			Iterator iterator = sectors.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)iterator.next();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("name", (String)pairs.getKey());
				tag.setInteger("amount", (Integer)pairs.getValue());
				list.appendTag(tag);
			}
			
			getPlayerSorceryCustomData(player).setTag("restrictedMojoSectors", list);
		}
	}
	
	public void addPlayerRestrictedMojoSector(EntityPlayer player, String name, int sector) {
		NBTTagList list = getPlayerSorceryCustomData(player).getTagList("restrictedMojoSectors", 10);
		
		for(int i = 0; i < list.tagCount(); i++) {
			if(((NBTTagCompound)list.getCompoundTagAt(i)).getString("name").equals(name)) {
				changePlayerRestrictedMojoSector(player, name, sector);
				return;
			}
		}
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("amount", sector);
		tag.setString("name", name);
		list.appendTag(tag);
		getPlayerSorceryCustomData(player).setTag("restrictedMojoSectors", list);
	}
	
	public void removePlayerRestrictedMojoSector(EntityPlayer player, String name) {
		NBTTagList list = getPlayerSorceryCustomData(player).getTagList("restrictedMojoSectors", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			if(((NBTTagCompound)list.getCompoundTagAt(i)).getString("name").equals(name))
				list.removeTag(i);
		}
		getPlayerSorceryCustomData(player).setTag("restrictedMojoSectors", list);
	}
	
	public void changePlayerRestrictedMojoSector(EntityPlayer player, String name, int sector) {
		NBTTagList list = getPlayerSorceryCustomData(player).getTagList("restrictedMojoSectors", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			if(((NBTTagCompound)list.getCompoundTagAt(i)).getString("name").equals(name))
				((NBTTagCompound)list.getCompoundTagAt(i)).setInteger("amount", sector);
		}
		getPlayerSorceryCustomData(player).setTag("restrictedMojoSectors", list);
	}
	
	public int getPlayerTotalRestrictedMojo(EntityPlayer player) {
		NBTTagList list = getPlayerSorceryCustomData(player).getTagList("restrictedMojoSectors", 10);
		int amount = 0;
		
		for(int i = 0; i < list.tagCount(); i++) {
			amount += ((NBTTagCompound)list.getCompoundTagAt(i)).getInteger("amount");
		}
		
		return amount;
	}
	
	public NBTTagList getPlayerRestrictedMojoSectorNBTList(EntityPlayer player) {
		return getPlayerSorceryCustomData(player).getTagList("restrictedMojoSectors", 10);
	}
	
	public int getPlayerDarkMojo(EntityPlayer player) {
		getPlayerSorceryCustomData(player);
		
		return getPlayerSorceryCustomData(player).getInteger("darkMojo");
	}
	
	public void setPlayerDarkMojo(EntityPlayer player, int mp) {
		getPlayerSorceryCustomData(player).setInteger("darkMojo", mp);
	}
	
	public void consumePlayerDarkMojo(EntityPlayer player, int mojo) {
		int playerMojo = getPlayerDarkMojo(player);
		
		setPlayerDarkMojo(player, playerMojo - mojo);
		if(getPlayerDarkMojo(player) <= 0)
			setPlayerDarkMojo(player, 0);
	}
	
	public void setPlayerMaxDarkMojo(EntityPlayer player, int mojo) {
		getPlayerSorceryCustomData(player).setInteger("maxDarkMojo", mojo);
	}
	
	public int getPlayerMaxDarkMojo(EntityPlayer player) {
		return getPlayerSorceryCustomData(player).getInteger("maxDarkMojo");
	}
	
	public boolean getPlayerHasDarkMojo(EntityPlayer player) {
		return getPlayerSorceryCustomData(player).getBoolean("hasDarkMojo");
	}
	
	public Spell[] getPlayerSpells(EntityPlayer player) {
		NBTTagList spells = getPlayerSorceryCustomData(player).getTagList("sorcerySpells", 10);
		
		Spell[] ret = new Spell[9];
		for(int i = 0; i < spells.tagCount(); i++) {
			ret[i] = Spell.readFromNBT((NBTTagCompound)spells.getCompoundTagAt(i));
		}
		
		return ret;
	}
	
	public Spell getPlayerEquippedSpell(EntityPlayer player) {
		NBTTagList spells = getPlayerSorceryCustomData(player).getTagList("sorcerySpells", 10);
		
		if(spells.tagCount() != 0 && spells.getCompoundTagAt(0) != null && !spells.getCompoundTagAt(0).hasNoTags())
		return Spell.readFromNBT((NBTTagCompound)spells.getCompoundTagAt(0));
		
		return null;
	}
	
	public void setPlayerSpells(EntityPlayer player, Spell[] spells) {
		NBTTagList spellsList = new NBTTagList();
		
		for(int i = 0; i < spells.length; i++) {
			if(spells[i] != null)
				spellsList.appendTag(spells[i].writeToNBT());
		}
		
		getPlayerSorceryCustomData(player).setTag("sorcerySpells", spellsList);
	}
	
	public void setPlayerSpell(EntityPlayer player, Spell spell, int spellIndex) {
		NBTTagList spellsList = getPlayerSorceryCustomData(player).getTagList("sorcerySpells", 10);
		
		Spell[] counter = new Spell[9];
		for(int i = 0; i < spellsList.tagCount(); i++) {
			counter[i] = Spell.readFromNBT((NBTTagCompound)spellsList.getCompoundTagAt(i));
		}
		
		counter[spellIndex] = spell;
		
		setPlayerSpells(player, counter);
	}
	
	public void toggleWrenchMode(EntityPlayer player) {
		ItemStack item = player.getHeldItem();
		if(item.getItem() instanceof ItemWand) {
			if(!item.hasTagCompound())
				item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setBoolean("wrench", !ItemWand.isWrench(item));
		}
	}
	
	public boolean getIsPlayerBurntOut(EntityPlayer player) {
		return getPlayerSorceryCustomData(player).getInteger("burnout") != 0;
	}

	public int getPlayerBurnOutTimer(EntityPlayer player) {
		return getPlayerSorceryCustomData(player).getInteger("burnout");
	}

	public void addToPlayerBurnOutTimer(EntityPlayer player, int amount) {
		int burnout = getPlayerSorceryCustomData(player).getInteger("burnout");
		burnout += amount;
		setPlayerBurnOutTimer(player, burnout);
	}

	public void setPlayerBurnOutTimer(EntityPlayer player, int amount) {
		getPlayerSorceryCustomData(player).setInteger("burnout", amount);
	}
	
	public void sendMojoPacketToPlayer(EntityPlayer player, int mojo, int maxMojo, int burnout, boolean regen) {
		PlayerMojoInfo info = new PlayerMojoInfo();
		info.mojo = mojo;
		info.maxMojo = maxMojo;
		info.burnout = burnout;
		info.canRegen = regen;
		PlayerMojoInfoPacket packet = new PlayerMojoInfoPacket(info);
		packet.setBasic();
		Sorcery.packetPipeline.sendTo(packet, (EntityPlayerMP)player);
		
	}
	
	public void sendAdvancedMojoPacketToPlayer(EntityPlayer player, PlayerMojoInfo info) {
		PlayerMojoInfoPacket packet = new PlayerMojoInfoPacket(info);
		Sorcery.packetPipeline.sendTo(packet, (EntityPlayerMP)player);
	}
	
	public PlayerResearchPacket getPlayerResearchPacket(EntityPlayer player) {
		return new PlayerResearchPacket(player);
	}
	
	public void sendPlayerResearchPacketToServer(EntityPlayer player) {
		Sorcery.packetPipeline.sendToServer(getPlayerResearchPacket(player));
	}
	
	public void sendPlayerResearchPacketToPlayer(EntityPlayer player) {
		Sorcery.packetPipeline.sendTo(getPlayerResearchPacket(player), (EntityPlayerMP)player);
	}
	
	public HashMap<String, WandComponent> getWandComponentsMap() {
		return WandComponentRegistry.components;
	}
	
	public WandComponent getWandComponentByName(String name) {
		return getWandComponentsMap().get(name);
	}
	
	
	public ArrayList<SpellComponent> getSpellComponentsFromResearch(ItemStack journal, World world) {
		if(journal.hasTagCompound()) {
			if(journal.stackTagCompound.getString("owner").equals("cheaty book"))
				return SorceryAPI.research.getSpellComponents();

			EntityPlayer player = world.getPlayerEntityByName(journal.stackTagCompound.getString("owner"));
			if(player != null)
				return Research.readFromNBT(player).getSpellComponents();
		}
		
		return new ArrayList<SpellComponent>();
	}
	
	public ItemStack getPageFromSpellIDs(int... ids) {
		ItemStack stack = new ItemStack(SorceryItems.spellPage, 1);
		NBTTagCompound tags = new NBTTagCompound();
		tags.setIntArray("spells", ids);
		stack.setTagCompound(tags);
		return stack;
	}
	
	public int[] getSpellIDsFromPage(ItemStack page) {
		if(!page.hasTagCompound())
			return new int[0];
		
		int[] spells = page.stackTagCompound.getIntArray("spells");
		return spells;
	}
	
	public void setPageName(ItemStack page, String name) {
		page.stackTagCompound.setString("specialName", name);
	}
	
	public ItemStack getSpellbookFromSpellIDs(int... ids) {
		ItemStack stack = new ItemStack(SorceryItems.spellbook, 1);
		NBTTagCompound tags = new NBTTagCompound();
		tags.setIntArray("spells", ids);
		stack.setTagCompound(tags);
		return stack;
	}
	
	public int[] getSpellIDsFromBook(ItemStack book) {
		if(!book.hasTagCompound())
			return new int[0];
		
		int[] spells = book.stackTagCompound.getIntArray("spells");
		return spells;
	}
	
	public void addPageToBook(ItemStack page, ItemStack book) {
		if(!page.hasTagCompound())
			return;
		if(!book.hasTagCompound()) {
			NBTTagCompound tags = new NBTTagCompound();
			book.setTagCompound(tags);
		}
		
		if(book.getItem() == Items.book) {
			book = new ItemStack(SorceryItems.spellbook, 1);
			
			NBTTagCompound tags = new NBTTagCompound();
			book.setTagCompound(tags);
		}
		
		int[] pageSpells = page.stackTagCompound.getIntArray("spells");
		int[] bookSpells = book.stackTagCompound.getIntArray("spells");
		
		ArrayList<Integer> newPageSpells = new ArrayList<Integer>();
		ArrayList<Integer> newBookSpells = new ArrayList<Integer>();
		
		for(int i = 0; i < bookSpells.length; i++) {
			newBookSpells.add(bookSpells[i]);
		}
		
		for(int i = 0; i < pageSpells.length; i++) {
			boolean isDupe = false;
			
			for(int k = 0; k < bookSpells.length; k++) {
				if(pageSpells[i] == bookSpells[k]) {
					isDupe = true;
					break;
				}
			}
			
			if(!isDupe) {
				newBookSpells.add(pageSpells[i]);
			} else {
				newPageSpells.add(pageSpells[i]);
			}
		}
		
		int[] pageSpellsNew = new int[newPageSpells.size()];
		int[] bookSpellsNew = new int[newBookSpells.size()];
		
		for(int i = 0; i < newPageSpells.size(); i++) {
			pageSpellsNew[i] = newPageSpells.get(i);
		}
		
		for(int i = 0; i < newBookSpells.size(); i++) {
			bookSpellsNew[i] = newBookSpells.get(i);
		}
		
		// page.stackTagCompound.getCompoundTag("spells").setIntArray("spells",
		// pageSpellsNew);
		NBTTagCompound tags = new NBTTagCompound();
		tags.setIntArray("spells", pageSpellsNew);
		page.setTagCompound(tags);
		
		NBTTagCompound tags2 = new NBTTagCompound();
		tags2.setIntArray("spells", bookSpellsNew);
		book.setTagCompound(tags2);
		// book.stackTagCompound.getCompoundTag("spells").setIntArray("spells",
		// bookSpellsNew);
	}
}
