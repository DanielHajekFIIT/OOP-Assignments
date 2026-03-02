package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages combat event listeners — the <b>Subject</b> in the Observer pattern.
 * <p>
 * Maintains a list of {@link CombatEventListener} instances and notifies
 * all registered listeners when combat events occur.
 */
public class CombatEventManager {

    // TODO: Declare a private List<CombatEventListener> field called "listeners"

    /**
     * Creates a new CombatEventManager with an empty listener list.
     */
    public CombatEventManager() {
        // TODO: Initialize the listeners list (use ArrayList)
    }

    /**
     * Registers a new listener to receive combat event notifications.
     *
     * @param listener the listener to add
     */
    public void addListener(CombatEventListener listener) {
        // TODO: Add the listener to the list
    }

    /**
     * Removes a previously registered listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(CombatEventListener listener) {
        // TODO: Remove the listener from the list
    }

    /**
     * Returns an unmodifiable view of the currently registered listeners.
     *
     * @return unmodifiable list of listeners
     */
    public List<CombatEventListener> getListeners() {
        // TODO: Return Collections.unmodifiableList(...) wrapping the listeners
        return Collections.emptyList();
    }

    /**
     * Notifies all registered listeners that damage was dealt.
     *
     * @param attackerName the name of the attacker
     * @param targetName   the name of the target
     * @param damage       the amount of damage
     */
    public void notifyDamageDealt(String attackerName, String targetName, int damage) {
        // TODO: Iterate over all listeners and call onDamageDealt(...)
    }

    /**
     * Notifies all registered listeners that a character has been defeated.
     *
     * @param characterName the name of the defeated character
     */
    public void notifyCharacterDeath(String characterName) {
        // TODO: Iterate over all listeners and call onCharacterDeath(...)
    }

    /**
     * Notifies all registered listeners that a character levelled up.
     *
     * @param characterName the name of the character
     * @param newLevel      the new level
     */
    public void notifyLevelUp(String characterName, int newLevel) {
        // TODO: Iterate over all listeners and call onLevelUp(...)
    }
}
