package org.jikvict.tasks.exposed;

/**
 * Strategy interface for calculating attack damage.
 * <p>
 * Part of the <b>Strategy</b> design pattern. Each concrete implementation
 * defines a different damage-calculation algorithm that can be swapped at
 * runtime via {@link GameCharacter#setAttackStrategy(AttackStrategy)}.
 *
 * @see MeleeAttackStrategy
 * @see RangedAttackStrategy
 * @see MagicAttackStrategy
 */
@FunctionalInterface
public interface AttackStrategy {

    /**
     * Calculates damage based on the character's base attack power.
     *
     * @param baseAttackPower the character's base attack power
     * @return the calculated damage
     */
    int execute(int baseAttackPower);
}
