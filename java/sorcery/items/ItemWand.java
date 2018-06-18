package sorcery.items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import sorcery.api.IWrenchable;
import sorcery.api.SorceryAPI;
import sorcery.api.event.SpellCastEvent;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.Wand;
import sorcery.api.spellcasting.WandComponent.WandComponentBody;
import sorcery.api.spellcasting.WandComponent.WandComponentGenerator;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWand extends ItemArcane {
	public ItemWand() {
		setMaxStackSize(1);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	public static HashMap<String, IIcon> componentIcons = new HashMap<String, IIcon>();
	private IIcon[] icons;
	
	public int getMaxItemUseDuration(ItemStack item) {
		if(this.getWandFromItem(item) == null) return 100;
		return 100 - (this.getWandFromItem(item).body.tier * 10);
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
    public int getRenderPasses(int metadata) {
        return 4;
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
		Wand wand = this.getWandFromItem(stack);
		
		if(wand == null) {
			switch(pass) {
				case 0: return componentIcons.get("wandBodyWood");
				default: return this.icons[0];
				case 2: return componentIcons.get("wandGeneratorCrystal");
				case 3: return componentIcons.get("blank");
			}
		} else {
			switch(pass) {
				case 0: return isWrench(stack) ? this.icons[1] : this.getWandFromItem(stack).body != null ? 
						componentIcons.get(wand.body.name) : componentIcons.get("wandBodyWood");
				default: return this.icons[0];
				case 2: return this.getWandFromItem(stack).generator != null ? 
						componentIcons.get(wand.generator.name) : componentIcons.get("blank");
				case 3: return this.getWandFromItem(stack).core != null ? 
						componentIcons.get(wand.core.name) : componentIcons.get("blank");
			}
		}
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.icons = new IIcon[2];
		this.icons[0] = par1IIconRegister.registerIcon(SorceryAPI.ASSET_PREFIX + "wand/wandHilt");
		this.icons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "wandWrench");
		
		componentIcons.put("blank", par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "blankIcon"));
		
		Iterator iterator = SpellHelper.getInstance().getWandComponentsMap().entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry)iterator.next();
			componentIcons.put((String)pairs.getKey(),
					par1IIconRegister.registerIcon(SorceryAPI.ASSET_PREFIX + "wand/" + pairs.getKey()));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if(par2 != 1) {
			return 16777215;
		} else {
			Wand wand = this.getWandFromItem(par1ItemStack);
			
			if(wand == null || wand.colour == -1)
				return (158 << 16) + (73 << 8) + 42;
			int colour = wand.colour;
			return ItemDye.field_150922_c[colour];
		}
	}
	
	public static ItemStack getColouredItemStack(int data) {
		ItemStack wand = new ItemStack(SorceryItems.wand);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("colour", data + 1);
		wand.setTagCompound(tag);
		
		return wand;
	}
	
	public static boolean isWrench(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.stackTagCompound = new NBTTagCompound();
			return false;
		} else {
			return item.stackTagCompound.getBoolean("wrench");
		}
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(isWrench(stack)) {
			if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IWrenchable) {
				if(world.isRemote) {
					((IWrenchable)(world.getTileEntity(x, y, z))).onWrenched(world, player, stack, x, y, z, side);
					return false;
				} else {
					return ((IWrenchable)(world.getTileEntity(x, y, z))).onWrenched(world, player, stack, x, y, z, side);
				}
			}
		}
		return false;
	}
	
	@Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		/*if(isWrench(par1ItemStack)) {
			if(par3World.getTileEntity(par4, par5, par6) != null && par3World.getTileEntity(par4, par5, par6) instanceof IWrenchable) {
				return ((IWrenchable)(par3World.getTileEntity(par4, par5, par6))).onWrenched(par3World, par2EntityPlayer, par1ItemStack, par4, par5, par6, par7);
			}
		}*/
		return false;
    }
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(isWrench(par1ItemStack)) {
			
		} else if(SpellHelper.getInstance().getPlayerSpells(par3EntityPlayer) != null) {
			if(SpellHelper.getInstance().getPlayerSpells(par3EntityPlayer).length != 0) {
				Spell spell = SpellHelper.getInstance().getPlayerEquippedSpell(par3EntityPlayer);
				Wand wand = this.getWandFromItem(par1ItemStack);
				
				if(wand != null) {
					if(par3EntityPlayer.isSneaking()) {
						if(par2World.isRemote) {
							if(spell == null) {
								par3EntityPlayer.addChatMessage(new ChatComponentText(
										StatCollector.translateToLocal("sorcery.spellcasting.castInfoNone")));
							} else {
								par3EntityPlayer.addChatMessage(new ChatComponentText(
										StatCollector.translateToLocalFormatted("sorcery.spellcasting.castInfo", spell.name)));
							}
						}
					} else if(spell != null) {
						if(par3EntityPlayer.capabilities.isCreativeMode || SpellHelper.getInstance().hasEnergy(par3EntityPlayer, spell)) {
							if(SpellHelper.instance.doesPlayerHaveMojoForSpell(par3EntityPlayer, spell) && spell.canBeCast(par2World, par3EntityPlayer, wand)) {
								SpellCastEvent event = new SpellCastEvent(par3EntityPlayer, par1ItemStack, spell);
								MinecraftForge.EVENT_BUS.post(event);
								if(event.isCanceled()) {
									if(par2World.isRemote) {
										par3EntityPlayer.addChatMessage(new ChatComponentText(
												StatCollector.translateToLocal("sorcery.spellcasting.castBlocked")));
									}
									return par1ItemStack;
								}
								
								if(!par3EntityPlayer.capabilities.isCreativeMode) {
									par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
								} else {
									spell.castSpell(par2World, par3EntityPlayer, wand);
									if(spell.getSpellCastSound() != null && !par2World.isRemote)
										par2World.playSoundAtEntity(par3EntityPlayer, spell.getSpellCastSound(), 1.0F, 1.0F / 1.0F);
								}
							} else {
								
								par2World.playSoundAtEntity(par3EntityPlayer, "random.fizz", 0.5F, 2.6F + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.8F);
								
								Vec3 pos = par3EntityPlayer.getLookVec();
								pos.xCoord *= 1;
								pos.xCoord *= 1;
								pos.zCoord *= 1;
								for(int i = 0; i < 5; i++)
									par2World.spawnParticle("smoke", par3EntityPlayer.posX + pos.xCoord, par3EntityPlayer.posY, par3EntityPlayer.posZ + pos.zCoord, 0F, 0F, 0F);
							}
						} else {
							if(par3EntityPlayer.worldObj.isRemote) {
								par3EntityPlayer.addChatMessage(new ChatComponentText(
										StatCollector.translateToLocal("sorcery.spellcasting.noElements")));
							}
						}
					} else {
						if(par3EntityPlayer.worldObj.isRemote) {
							par3EntityPlayer.addChatMessage(new ChatComponentText(
									StatCollector.translateToLocal("sorcery.spellcasting.blocked")));
						} else {
							Utils.log(Level.WARN, StatCollector.translateToLocalFormatted(
									"sorcery.spellcasting.adminBlocked", par3EntityPlayer.func_145748_c_().getFormattedText()));
						}
					}
				} else {
					if(par3EntityPlayer.worldObj.isRemote) {
						par3EntityPlayer.addChatMessage(new ChatComponentText(
								StatCollector.translateToLocal("sorcery.spellcasting.castInfoNone")));
					}
				}
			}
		}
		return par1ItemStack;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		Spell spell = SpellHelper.getInstance().getPlayerEquippedSpell(player);
		count = stack.getMaxItemUseDuration() - count;
		int time = (int)((int)spell.getCastTime() / 4F);
		if(count < time)
			SpellHelper.getInstance().consumePlayerMojo(player, 4);
	}

	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		Spell spell = SpellHelper.getInstance().getPlayerEquippedSpell(par3EntityPlayer);
		Wand wand = this.getWandFromItem(par1ItemStack);
		int time = this.getMaxItemUseDuration(par1ItemStack) - par4;
		if(time > (spell.getCastTime() / 4)) {
			spell.castSpell(par2World, par3EntityPlayer, wand);
			if(spell.getSpellCastSound() != null && !par2World.isRemote)
				par2World.playSoundAtEntity(par3EntityPlayer, spell.getSpellCastSound(), 1.0F, 1.0F / 1.0F);
			
			/*if(par1ItemStack.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(Properties.coolDownEnchantmentID, par1ItemStack) != 0) {
				int enchLevel = EnchantmentHelper.getEnchantmentLevel(Properties.coolDownEnchantmentID, par1ItemStack);
				float newCoolTime = (spell.chargeTime - ((spell.chargeTime / 20) * enchLevel));
				SpellHelper.instance.consumePlayerMojo(par3EntityPlayer, (int)newCoolTime);
			} else {
				SpellHelper.instance.consumePlayerMojo(par3EntityPlayer, (int)spell.chargeTime);
			}*/
			
			SpellHelper.getInstance().consumeElements(par3EntityPlayer, spell, par1ItemStack);
		}
	}
	
	public int getItemEnchantability() {
		return 1;
	}
	
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}
	
	public static Wand getWandFromItem(ItemStack item) {
		if(!item.hasTagCompound())
			return null;
		
		
		return Wand.readFromNBT(item.stackTagCompound, "wand");
	}
	
	public static ItemStack getItemStackWithWand(Wand wand) {
		ItemStack item = new ItemStack(SorceryItems.wand, 1);
		NBTTagCompound tag = new NBTTagCompound();
		wand.writeToNBT(tag, "wand");
		item.setTagCompound(tag);
		return item;
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		Wand wand = new Wand();
		wand.body = (WandComponentBody)SpellHelper.getInstance().getWandComponentByName("wandBodyWood");
		wand.generator = (WandComponentGenerator)SpellHelper.getInstance().getWandComponentByName("wandGeneratorCrystal");
		wand.colour = 1;
		ItemStack item = getItemStackWithWand(wand);
		par3List.add(item);
	}
}
