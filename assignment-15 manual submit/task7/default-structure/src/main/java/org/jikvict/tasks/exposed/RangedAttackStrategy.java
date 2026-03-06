package org.jikvict.tasks.exposed;

/**
 * A ranged attack strategy — consistent bonus from distance.
 * <p>
 * Damage formula: {@code baseAttackPower + 15}
 */
public class RangedAttackStrategy implements AttackStrategy {

    /**
     * Calculates ranged damage.
     *
     * @param baseAttackPower the character's base attack power
     * @return {@code baseAttackPower + 15}
     */
    @Override
    public int execute(int baseAttackPower) {
        return baseAttackPower + 15;
    }
}
