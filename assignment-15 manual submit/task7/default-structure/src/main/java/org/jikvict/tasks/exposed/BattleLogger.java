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
    private List<String> log;
    /**
     * Creates a new BattleLogger with an empty log.
     */
    public BattleLogger() {
        log = new ArrayList<>();
    }

    @Override
    public void onDamageDealt(String attackerName, String targetName, int damage) {
        log.add("DAMAGE: " + attackerName + " dealt " + damage + " damage to " + targetName);
    }

    @Override
    public void onCharacterDeath(String characterName) {
        log.add("DEATH: " + characterName + " has been defeated");
    }

    @Override
    public void onLevelUp(String characterName, int newLevel) {
        log.add("LEVEL UP: " + characterName + " reached level " + newLevel);
    }

    /**
     * Returns an unmodifiable view of the log entries.
     *
     * @return unmodifiable list of log messages
     */
    public List<String> getLog() {
        return Collections.unmodifiableList(log);
    }

    /**
     * Clears all log entries.
     */
    public void clearLog() {
        log.clear();
    }
}
