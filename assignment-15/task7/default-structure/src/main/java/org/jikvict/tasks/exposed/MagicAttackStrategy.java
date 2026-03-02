package org.jikvict.tasks.exposed;

/**
 * A magic-based attack strategy — devastating arcane power.
 * <p>
 * Damage formula: {@code baseAttackPower * 3}
 */
public class MagicAttackStrategy implements AttackStrategy {

    /**
     * Calculates magic damage.
     *
     * @param baseAttackPower the character's base attack power
     * @return {@code baseAttackPower * 3}
     */
    @Override
    public int execute(int baseAttackPower) {
        // TODO: Implement — return baseAttackPower * 3
        return 0;
    }
}
