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
    private List<CombatEventListener> listeners;
    /**
     * Creates a new CombatEventManager with an empty listener list.
     */
    public CombatEventManager() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Registers a new listener to receive combat event notifications.
     *
     * @param listener the listener to add
     */
    public void addListener(CombatEventListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes a previously registered listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(CombatEventListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Returns an unmodifiable view of the currently registered listeners.
     *
     * @return unmodifiable list of listeners
     */
    public List<CombatEventListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    /**
     * Notifies all registered listeners that damage was dealt.
     *
     * @param attackerName the name of the attacker
     * @param targetName   the name of the target
     * @param damage       the amount of damage
     */
    public void notifyDamageDealt(String attackerName, String targetName, int damage) {
        for  (CombatEventListener listener : listeners) {
            listener.onDamageDealt(attackerName, targetName, damage);
        }
    }

    /**
     * Notifies all registered listeners that a character has been defeated.
     *
     * @param characterName the name of the defeated character
     */
    public void notifyCharacterDeath(String characterName) {
        for  (CombatEventListener listener : listeners) {
            listener.onCharacterDeath(characterName);
        }
    }

    /**
     * Notifies all registered listeners that a character levelled up.
     *
     * @param characterName the name of the character
     * @param newLevel      the new level
     */
    public void notifyLevelUp(String characterName, int newLevel) {
        for (CombatEventListener listener : listeners) {
            listener.onLevelUp(characterName, newLevel);
        }
    }
}
