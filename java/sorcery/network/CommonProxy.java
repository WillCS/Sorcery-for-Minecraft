package sorcery.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import sorcery.containers.ContainerBarrel;
import sorcery.containers.ContainerDesk;
import sorcery.containers.ContainerForge;
import sorcery.containers.ContainerHellFurnace;
import sorcery.containers.ContainerMixer;
import sorcery.containers.ContainerMojoStorage;
import sorcery.containers.ContainerNode;
import sorcery.containers.ContainerRepairTable;
import sorcery.containers.ContainerResearchJournal;
import sorcery.containers.ContainerRuneCrafting;
import sorcery.containers.ContainerSpellbook;
import sorcery.containers.ContainerTinkering;
import sorcery.handlers.CraftingHandler;
import sorcery.handlers.MojoTickHandler;
import sorcery.handlers.PlayerTracker;
import sorcery.handlers.ServerEventHandler;
import sorcery.handlers.ServerTickHandler;
import sorcery.handlers.VillageWizardTowerCreationHandler;
import sorcery.handlers.VillagerTradingHandlerWizard;
import sorcery.lib.Properties;
import sorcery.lib.WandComponentRegistry;
import sorcery.tileentities.TileEntityBarrel;
import sorcery.tileentities.TileEntityDesk;
import sorcery.tileentities.TileEntityElementalInfuser;
import sorcery.tileentities.TileEntityForge;
import sorcery.tileentities.TileEntityHellFurnace;
import sorcery.tileentities.TileEntityIncubator;
import sorcery.tileentities.TileEntityInfusionStand;
import sorcery.tileentities.TileEntityLantern;
import sorcery.tileentities.TileEntityMixer;
import sorcery.tileentities.TileEntityMojoStorage;
import sorcery.tileentities.TileEntityMojoWire;
import sorcery.tileentities.TileEntityPipe;
import sorcery.tileentities.TileEntityRepairTable;
import sorcery.tileentities.TileEntityRuneCrafting;
import sorcery.tileentities.TileEntityTinkering;
import sorcery.tileentities.nodes.TileEntityNode;
import sorcery.tileentities.nodes.TileEntityNodeBase;
import sorcery.tileentities.nodes.TileEntityNodeBuffer;
import sorcery.tileentities.nodes.TileEntityNodeCollector;
import sorcery.tileentities.nodes.TileEntityNodeExtractor;
import sorcery.tileentities.nodes.TileEntityNodeInserter;
import sorcery.tileentities.nodes.TileEntityNodeVoid;
import sorcery.world.ComponentVillageWizardTower;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class CommonProxy implements IGuiHandler {
	public void registerRenderInformation() {
	}
	
	public void registerSounds() {
	}
	
	public void registerKeys() {
	}
	
	public String getLanguage() {
		return null;
	}
	
	public int addArmor(String armor) {
		return 0;
	}
	
	public int getRenderID() {
		return 0;
	}
	
	public ResourceLocation getTerrainTexture() {
		return null;
	}
	
	public ResourceLocation getItemTexture() {
		return null;
	}
	
	public void handleMojoPacket(int mojo, int maxMojo, int burnout, boolean regen) {
		
	}
	
	public void handleAdvancedMojoSetup(NBTTagCompound tag) {
		
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(ID == Properties.GUI_TINKERING)
			return new ContainerTinkering(player.inventory, (TileEntityTinkering)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_HELLFURNACE)
			return new ContainerHellFurnace(player.inventory, (TileEntityHellFurnace)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_FORGE)
			return new ContainerForge(player.inventory, (TileEntityForge)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_RUNECRAFTING)
			return new ContainerRuneCrafting(player.inventory, (TileEntityRuneCrafting)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_SPELLBOOK)
			return new ContainerSpellbook(player.inventory, world, x, y, z);
		if(ID == Properties.GUI_MIXER)
			return new ContainerMixer(player.inventory, (TileEntityMixer)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_BARREL)
			return new ContainerBarrel(player.inventory, (TileEntityBarrel)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_NODE)
			return new ContainerNode(player.inventory, (TileEntityNodeBase)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_MOJOSTORAGE)
			return new ContainerMojoStorage(player.inventory, (TileEntityMojoStorage)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_RESEARCH)
			return new ContainerResearchJournal(player.inventory, world, x, y, z);
		if(ID == Properties.GUI_DESK)
			return new ContainerDesk(player.inventory, (TileEntityDesk)tileEntity, world, x, y, z);
		if(ID == Properties.GUI_REPAIR)
			return new ContainerRepairTable(player.inventory, (TileEntityRepairTable)tileEntity, world, x, y, z);
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public World getClientWorld() {
		return null;
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTinkering.class, "TileEntitySorceryTinkering");
		GameRegistry.registerTileEntity(TileEntityHellFurnace.class, "TileEntitySorceryHellFurnace");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "TileEntitySorceryIncubator");
		GameRegistry.registerTileEntity(TileEntityForge.class, "TileEntitySorceryForge");
		GameRegistry.registerTileEntity(TileEntityInfusionStand.class, "TileEntitySorceryInfusionStand");
		GameRegistry.registerTileEntity(TileEntityMixer.class, "TileEntitySorceryMixer");
		GameRegistry.registerTileEntity(TileEntityBarrel.class, "TileEntitySorceryBarrel");
		GameRegistry.registerTileEntity(TileEntityMojoStorage.class, "TileEntitySorceryMojoStorage");
		GameRegistry.registerTileEntity(TileEntityMojoWire.class, "TileEntitySorceryMojoWire");
		GameRegistry.registerTileEntity(TileEntityPipe.class, "TileEntitySorceryPipe");
		GameRegistry.registerTileEntity(TileEntityLantern.class, "TileEntitySorceryLantern");
		GameRegistry.registerTileEntity(TileEntityDesk.class, "TileEntitySorceryDesk");
		GameRegistry.registerTileEntity(TileEntityElementalInfuser.class, "TileEntitySorceryInfuser");
		GameRegistry.registerTileEntity(TileEntityRepairTable.class, "TileEntitySorceryRepairTable");
		
		GameRegistry.registerTileEntity(TileEntityNode.class, "TileEntitySorceryNode");
		GameRegistry.registerTileEntity(TileEntityNodeExtractor.class, "TileEntitySorceryNodeExtractor");
		GameRegistry.registerTileEntity(TileEntityNodeInserter.class, "TileEntitySorceryNodeInserter");
		GameRegistry.registerTileEntity(TileEntityNodeCollector.class, "TileEntitySorceryNodeCollector");
		GameRegistry.registerTileEntity(TileEntityNodeVoid.class, "TileEntitySorceryNodeVoid");
		GameRegistry.registerTileEntity(TileEntityNodeBuffer.class, "TileEntitySorceryNodeBuffer");
	}
	
	public void registerVillageComponents() {
		VillagerRegistry.instance().registerVillagerId(Properties.villagerWizardID);
		VillagerRegistry.instance().registerVillageTradeHandler(Properties.villagerWizardID, new VillagerTradingHandlerWizard());
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageWizardTowerCreationHandler());
		MapGenStructureIO.func_143031_a(ComponentVillageWizardTower.class, Properties.ASSET_PREFIX + "ViWT");
	}
	
	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
		
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		
		FMLCommonHandler.instance().bus().register(new PlayerTracker());
		FMLCommonHandler.instance().bus().register(new MojoTickHandler());
	}
	
	public void registerWandComponents() {
		WandComponentRegistry.registerComponents();
	}
	
	public void spawnParticleEffect(String effect, Object... args) {
		
	}
	
}