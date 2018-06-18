package sorcery.network;

import java.util.HashMap;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import sorcery.client.render.RenderBlazeArrow;
import sorcery.client.render.RenderBoneArrow;
import sorcery.client.render.RenderDesk;
import sorcery.client.render.RenderDrill;
import sorcery.client.render.RenderElementalInfuser;
import sorcery.client.render.RenderGhost;
import sorcery.client.render.RenderGoldenChicken;
import sorcery.client.render.RenderIncubator;
import sorcery.client.render.RenderIncubatorEgg;
import sorcery.client.render.RenderLantern;
import sorcery.client.render.RenderLanternEntity;
import sorcery.client.render.RenderLanternTile;
import sorcery.client.render.RenderMachineHull;
import sorcery.client.render.RenderMojoBatteryBlock;
import sorcery.client.render.RenderNodeComponents;
import sorcery.client.render.RenderNodeItem;
import sorcery.client.render.RenderNodes;
import sorcery.client.render.RenderPhoenix;
import sorcery.client.render.RenderPipes;
import sorcery.client.render.RenderRunicInfuser;
import sorcery.client.render.RenderStaff;
import sorcery.client.render.RenderUnicorn;
import sorcery.client.render.RenderUnicow;
import sorcery.client.render.RenderWand;
import sorcery.client.render.RenderWire;
import sorcery.client.render.RenderWizardHatItem;
import sorcery.entities.EntityBlazeArrow;
import sorcery.entities.EntityBoneArrow;
import sorcery.entities.EntityGhost;
import sorcery.entities.EntityGoldenChicken;
import sorcery.entities.EntityIncubatorEgg;
import sorcery.entities.EntityLantern;
import sorcery.entities.EntityNodeItem;
import sorcery.entities.EntityPhoenix;
import sorcery.entities.EntityUnicorn;
import sorcery.entities.EntityUnicow;
import sorcery.guis.GuiBarrel;
import sorcery.guis.GuiDesk;
import sorcery.guis.GuiForge;
import sorcery.guis.GuiHellFurnace;
import sorcery.guis.GuiMixer;
import sorcery.guis.GuiMojoStorage;
import sorcery.guis.GuiNodeBase;
import sorcery.guis.GuiRepairTable;
import sorcery.guis.GuiResearch;
import sorcery.guis.GuiRuneCrafting;
import sorcery.guis.GuiSpellbook;
import sorcery.guis.GuiTinkering;
import sorcery.handlers.ClientEventHandler;
import sorcery.handlers.ClientTickHandler;
import sorcery.handlers.KeyStrokeHandler;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import sorcery.lib.WandComponentRegistry;
import sorcery.lib.utils.Utils;
import sorcery.models.ModelIncubatorEgg;
import sorcery.models.ModelPhoenix;
import sorcery.models.ModelUnicorn;
import sorcery.models.ModelUnicow;
import sorcery.tileentities.TileEntityBarrel;
import sorcery.tileentities.TileEntityDesk;
import sorcery.tileentities.TileEntityElementalInfuser;
import sorcery.tileentities.TileEntityForge;
import sorcery.tileentities.TileEntityHellFurnace;
import sorcery.tileentities.TileEntityIncubator;
import sorcery.tileentities.TileEntityLantern;
import sorcery.tileentities.TileEntityMixer;
import sorcery.tileentities.TileEntityMojoStorage;
import sorcery.tileentities.TileEntityMojoWire;
import sorcery.tileentities.TileEntityPipe;
import sorcery.tileentities.TileEntityRepairTable;
import sorcery.tileentities.TileEntityRuneCrafting;
import sorcery.tileentities.TileEntityTinkering;
import sorcery.tileentities.nodes.TileEntityNodeBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.registerItemRenderer(SorceryItems.staff, new RenderStaff());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.wand, new RenderWand());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.wizardHat, new RenderWizardHatItem());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.nodeComponents, new RenderNodeComponents());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.lantern, new RenderLantern());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.desk, new RenderDesk());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.mojoStorage, new RenderMojoBatteryBlock());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.powerGenerator, new RenderMachineHull());
		MinecraftForgeClient.registerItemRenderer(SorceryItems.drillGlove, new RenderDrill());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityUnicorn.class, new RenderUnicorn(new ModelUnicorn(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPhoenix.class, new RenderPhoenix(new ModelPhoenix(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGoldenChicken.class, new RenderGoldenChicken(new ModelChicken(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnicow.class, new RenderUnicow(new ModelUnicow(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, new RenderGhost(new ModelZombie(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoneArrow.class, new RenderBoneArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlazeArrow.class, new RenderBlazeArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityIncubatorEgg.class, new RenderIncubatorEgg(new ModelIncubatorEgg(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNodeItem.class, new RenderNodeItem());
		RenderingRegistry.registerEntityRenderingHandler(EntityLantern.class, new RenderLanternEntity());
	}
	
	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		FMLCommonHandler.instance().bus().register(new KeyStrokeHandler());
	}
	
	public void registerWandComponents() {
		super.registerWandComponents();
		WandComponentRegistry.registerModels();
	}
	
	@Override
	public String getLanguage() {
		return FMLClientHandler.instance().getClient().getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}
	
	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
	
	@Override
	public int getRenderID() {
		return RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(ID == Properties.GUI_TINKERING)
			return new GuiTinkering(player.inventory, (TileEntityTinkering)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_HELLFURNACE)
			return new GuiHellFurnace(player.inventory, (TileEntityHellFurnace)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_FORGE)
			return new GuiForge(player.inventory, (TileEntityForge)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_RUNECRAFTING)
			return new GuiRuneCrafting(player.inventory, (TileEntityRuneCrafting)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_SPELLBOOK)
			return new GuiSpellbook(player, world, x, y, z);
		if(ID == Properties.GUI_MIXER)
			return new GuiMixer(player.inventory, (TileEntityMixer)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_BARREL)
			return new GuiBarrel(player.inventory, (TileEntityBarrel)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_NODE)
			return new GuiNodeBase(player.inventory, (TileEntityNodeBase)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_MOJOSTORAGE)
			return new GuiMojoStorage(player.inventory, (TileEntityMojoStorage)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_RESEARCH)
			return new GuiResearch(player, world, x, y, z);
		if(ID == Properties.GUI_DESK)
			return new GuiDesk(player.inventory, (TileEntityDesk)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_REPAIR)
			return new GuiRepairTable(player.inventory, (TileEntityRepairTable)tileEntity, world, x, y, z);
		return null;
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIncubator.class, new RenderIncubator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRuneCrafting.class, new RenderRunicInfuser());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNodeBase.class, new RenderNodes());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe.class, new RenderPipes());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMojoWire.class, new RenderWire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLantern.class, new RenderLanternTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDesk.class, new RenderDesk());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityElementalInfuser.class, new RenderElementalInfuser());
		RenderingRegistry.registerBlockHandler(new RenderNodes());
		RenderingRegistry.registerBlockHandler(new RenderPipes());
		RenderingRegistry.registerBlockHandler(new RenderWire());
	}
	
	@Override
	public void registerVillageComponents() {
		super.registerVillageComponents();
		VillagerRegistry.instance().registerVillagerSkin(Properties.villagerWizardID, Utils.getResource("textures/mobs/wizard.png"));
	}
	
	@Override
	public ResourceLocation getTerrainTexture() {
		return TextureMap.locationBlocksTexture;
	}
	
	@Override
	public ResourceLocation getItemTexture() {
		return TextureMap.locationItemsTexture;
	}

	@Override
	public void handleMojoPacket(int mojo, int maxMojo, int burnout, boolean regen) {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		
		SpellHelper.getInstance().setPlayerMaxMojo(player, maxMojo);
		SpellHelper.getInstance().setPlayerMojo(player, mojo);
		SpellHelper.getInstance().setPlayerCanRegenMojo(player, regen);
		SpellHelper.getInstance().setPlayerBurnOutTimer(player, burnout);
	}
	
	@Override
	public void handleAdvancedMojoSetup(NBTTagCompound tag) {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		
		NBTTagList spellsList = tag.getTagList("spells", 10);
		sorcery.api.spellcasting.Spell[] spells = new sorcery.api.spellcasting.Spell[spellsList.tagCount()];
		for(int i = 0; i < spellsList.tagCount(); i++) {
			spells[i] = sorcery.api.spellcasting.Spell.readFromNBT((NBTTagCompound)spellsList.getCompoundTagAt(i));
		}
		
		HashMap<String, Integer> restricted = new HashMap();
		NBTTagList list = tag.getTagList("restrictedMojoSectors", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tempTag = (NBTTagCompound)list.getCompoundTagAt(i);
			String name = tempTag.getString("name");
			int amount = tempTag.getInteger("amount");
			restricted.put(name, amount);
		}
		
		SpellHelper.getInstance().setPlayerRestrictedMojoSectors(player, restricted);
		SpellHelper.getInstance().setPlayerSpells(player, spells);
		
	}
	
	@Override
	public void spawnParticleEffect(String effect, Object... args) {
		World world = (World)args[0];
		double x = (Double)args[1];
		double y = (Double)args[2];
		double z = (Double)args[3];
		
		if(effect.equals("sparkle")) {
			float scale = (Float)args[4];
			float[] colours = new float[]{(Float)args[5], (Float)args[6], (Float)args[7]};
			//FMLClientHandler.instance().getClient().effectRenderer.addEffect(new EntitySparkleFX(world, x, y, z, scale, colours));
		}
		
		if(effect.equals("spell")) {
			float scale = (Float)args[4];
			float[] colours = new float[]{(Float)args[5], (Float)args[6], (Float)args[7]};
			//FMLClientHandler.instance().getClient().effectRenderer.addEffect(new EntityMiniSpellFX(world, x, y, z, scale, colours));
		}
	}
}