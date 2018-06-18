package sorcery.entities.spells;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sorcery.api.spellcasting.ISpellEntity;
import sorcery.api.spellcasting.RenderInfo;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellCasterInfo;

public class EntitySpellBase extends Entity implements ISpellEntity {
	public SpellCasterInfo casterInfo;
	public Spell spell;
	public int magnitude = 1;
	public float size = 1.0F;
	public float speed = 1.0F;
	public float power = 1.0F;
	
	public EntitySpellBase(World par1World) {
		super(par1World);
	}
	
	public EntitySpellBase(World par1World, Spell spell) {
		super(par1World);
		this.spell = spell;
	}
	
	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, 1.0F);
		this.dataWatcher.addObject(17, 1.0F);
		this.dataWatcher.addObject(18, 1.0F);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {

	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {

	}

	@Override
	public String getCasterName() {
		return this.casterInfo.casterName;
	}

	@Override
	public Spell getSpell() {
		return this.spell;
	}
	
	@Override
	public void setSpellSize(float size) {
		this.size = size;
		this.dataWatcher.updateObject(16, Float.valueOf(size));
		this.setSize(size, size);
	}
	
	@Override
	public void setSpellSpeed(float speed) {
		this.speed = speed;
		this.dataWatcher.updateObject(17, Float.valueOf(speed));
	}
	
	@Override
	public void setSpellPower(float power) {
		this.power = power;
		this.dataWatcher.updateObject(18, Float.valueOf(power));
	}

	@Override
	public float getSpellSize() {
		return this.dataWatcher.getWatchableObjectFloat(16);
	}

	@Override
	public float getSpellSpeed() {
		return this.dataWatcher.getWatchableObjectFloat(17);
	}

	@Override
	public float getSpellPower() {
		return this.dataWatcher.getWatchableObjectFloat(18);
	}
}
