package org.jikvict.tasks.exposed;

/**
 * A melee-focused attack strategy — hits hard at close range.
 * <p>
 * Damage formula: {@code (int)(baseAttackPower * 1.5)}
 */
public class MeleeAttackStrategy implements AttackStrategy {

    /**
     * Calculates melee damage.
     *
     * @param baseAttackPower the character's base attack power
     * @return {@code (int)(baseAttackPower * 1.5)}
     */
    @Override
    public int execute(int baseAttackPower) {
        // TODO: Implement — return (int)(baseAttackPower * 1.5)
        return 0;
    }
}
