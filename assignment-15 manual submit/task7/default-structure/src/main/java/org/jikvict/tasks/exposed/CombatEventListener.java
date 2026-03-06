package org.jikvict.tasks.exposed;

/**
 * Observer interface for combat events.
 * <p>
 * Part of the <b>Observer</b> design pattern. Classes implementing this
 * interface can register with a {@link CombatEventManager} to receive
 * notifications about combat events.
 *
 * @see CombatEventManager
 * @see BattleLogger
 */
public interface CombatEventListener {

    /**
     * Called when one character deals damage to another.
     *
     * @param attackerName the name of the attacking character
     * @param targetName   the name of the target character
     * @param damage       the amount of damage dealt
     */
    void onDamageDealt(String attackerName, String targetName, int damage);

    /**
     * Called when a character is defeated (health reaches 0).
     *
     * @param characterName the name of the defeated character
     */
    void onCharacterDeath(String characterName);

    /**
     * Called when a character levels up.
     *
     * @param characterName the name of the character
     * @param newLevel      the new level after levelling up
     */
    void onLevelUp(String characterName, int newLevel);
}
