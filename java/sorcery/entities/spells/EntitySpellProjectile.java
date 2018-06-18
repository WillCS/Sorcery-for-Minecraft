package sorcery.entities.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sorcery.api.spellcasting.Spell;

public class EntitySpellProjectile extends EntitySpellBase implements IProjectile {
	public EntitySpellProjectile(World par1World) {
		super(par1World);
	}
	
	public EntitySpellProjectile(World par1World, double x, double y, double z, Spell spell) {
		super(par1World, spell);
		this.setPosition(x, y, z);
	}
	
	public EntitySpellProjectile(World par1World, EntityLivingBase caster, Spell spell) {
		super(par1World, spell);
		this.setPosition(caster.posX, caster.posY, caster.posZ);
		this.motionX = (double)(-MathHelper.sin(caster.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(caster.rotationPitch / 180.0F * (float)Math.PI) * 0.4F);
        this.motionZ = (double)(MathHelper.cos(caster.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(caster.rotationPitch / 180.0F * (float)Math.PI) * 0.4F);
        this.motionY = (double)(-MathHelper.sin((caster.rotationPitch / 180.0F * (float)Math.PI) * 0.4F));
        //this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	@Override
	public void setThrowableHeading(double var1, double var3, double var5, float var7, float var8) {
        float f2 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
        var1 /= (double)f2;
        var3 /= (double)f2;
        var5 /= (double)f2;
        var1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var1 *= (double)var7;
        var3 *= (double)var7;
        var5 *= (double)var7;
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;
        float f3 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)f3) * 180.0D / Math.PI);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
	}
	
	public float getPower() {
		return this.dataWatcher.getWatchableObjectFloat(16);
	}
	
	public float getSpeed() {
		return this.dataWatcher.getWatchableObjectFloat(17);
	}
	
	public float getSize() {
		return this.dataWatcher.getWatchableObjectFloat(18);
	}
	
	@Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        
        this.posX += (this.motionX * this.getSpeed());
        this.posY += (this.motionY * this.getSpeed());
        this.posZ += (this.motionZ * this.getSpeed());
        
        if(this.ticksExisted > 50)
        	this.kill();
    }
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {

	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {

	}
}
