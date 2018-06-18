package sorcery.handlers;

import java.util.List;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import sorcery.world.ComponentVillageWizardTower;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageWizardTowerCreationHandler implements IVillageCreationHandler {
	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return(new PieceWeight(ComponentVillageWizardTower.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + i, 1 + i)));
	}
	
	@Override
	public Class<?> getComponentClass() {
		return ComponentVillageWizardTower.class;
	}

	@Override
	public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
		return ComponentVillageWizardTower.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}
}