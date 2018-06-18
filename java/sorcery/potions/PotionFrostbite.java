package sorcery.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import sorcery.client.render.RenderUtils;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionFrostbite extends Potion {
	public PotionFrostbite(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
		this.setEffectiveness(0.25D);
		this.setIconIndex(0, 0);
		this.setPotionName("sorcery.potion.frost");
	}
	
	@SideOnly(Side.CLIENT)
	public int getStatusIIconIndex() {
		RenderUtils.bindTexture(Properties.potionTexture);
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() {
		return true;
	}
	
	@Override
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2) {
		if(par1EntityLivingBase.worldObj.rand.nextInt(25) == 0 && par1EntityLivingBase.prevHealth > 1)
			par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 1);
	}
	
	@Override
	public boolean isReady(int par1, int par2) {
		return true;
	}
}
