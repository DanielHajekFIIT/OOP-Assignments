package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A concrete observer that logs all combat events to an internal list.
 * <p>
 * Log message formats:
 * <ul>
 *   <li>Damage: {@code "DAMAGE: {attacker} dealt {damage} damage to {target}"}</li>
 *   <li>Death:  {@code "DEATH: {name} has been defeated"}</li>
 *   <li>Level up: {@code "LEVEL UP: {name} reached level {level}"}</li>
 * </ul>
 */
public class BattleLogger implements CombatEventListener {

    // TODO: Declare a private List<String> field called "log"

    /**
     * Creates a new BattleLogger with an empty log.
     */
    public BattleLogger() {
        // TODO: Initialize the log list (use ArrayList)
    }

    @Override
    public void onDamageDealt(String attackerName, String targetName, int damage) {
        // TODO: Add "DAMAGE: {attackerName} dealt {damage} damage to {targetName}" to the log
    }

    @Override
    public void onCharacterDeath(String characterName) {
        // TODO: Add "DEATH: {characterName} has been defeated" to the log
    }

    @Override
    public void onLevelUp(String characterName, int newLevel) {
        // TODO: Add "LEVEL UP: {characterName} reached level {newLevel}" to the log
    }

    /**
     * Returns an unmodifiable view of the log entries.
     *
     * @return unmodifiable list of log messages
     */
    public List<String> getLog() {
        // TODO: Return Collections.unmodifiableList(...) wrapping the log
        return Collections.emptyList();
    }

    /**
     * Clears all log entries.
     */
    public void clearLog() {
        // TODO: Clear the log list
    }
}
