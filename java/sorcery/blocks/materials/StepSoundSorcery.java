package sorcery.blocks.materials;

import net.minecraft.block.Block;
import sorcery.lib.Properties;

public class StepSoundSorcery extends Block.SoundType {
	public StepSoundSorcery(String par1Str, float par2, float par3) {
		super(par1Str, par2, par3);
	}
	
	public String getBreakSound() {
		return Properties.ASSET_PREFIX + "dig." + this.soundName;
	}
	
	public String getStepSound() {
		return Properties.ASSET_PREFIX + "step." + this.soundName;
	}
}
