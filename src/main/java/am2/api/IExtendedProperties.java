package am2.api;

import am2.api.math.AMVector3;

/**
 Extended properties on EntityLiving used in Ars Magica.

 @author Mithion */
public interface IExtendedProperties {
	float getCurrentMana();

	float getMaxMana();

	int getMarkDimension();

	int getMagicLevel();

	int getNumSummons();

	AMVector3 getMarkLocation();

	boolean getHasUnlockedAugmented();

	boolean getMarkSet();

	boolean setMagicLevelWithMana(int magicLevel);

	void setCurrentMana(float currentMana);
}
