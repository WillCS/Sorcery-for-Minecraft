package sorcery.api.spellcasting;

public interface ISpellEntity {
	
	/** Returns the name of what/whoever cast this spell entity */
	public String getCasterName();
	
	/** Returns the spell that produced this entity */
	public Spell getSpell();
	
	public void setSpellSize(float size);

	public void setSpellSpeed(float speed);
	
	public void setSpellPower(float power);
	
	public float getSpellSize();

	public float getSpellSpeed();
	
	public float getSpellPower();
}
