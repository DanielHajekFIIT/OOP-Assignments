package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the game world — a <b>Singleton</b>.
 * <p>
 * There is exactly one GameWorld instance in the application. It holds
 * the party of characters and the global {@link CombatEventManager}.
 * <p>
 * Implementation requirements:
 * <ul>
 *   <li>Private constructor</li>
 *   <li>Private static field {@code instance}</li>
 *   <li>{@link #getInstance()} creates the instance on first call (lazy initialisation)</li>
 *   <li>{@link #resetInstance()} sets {@code instance} to {@code null} (for testing)</li>
 * </ul>
 */
public class GameWorld {

    // TODO: Declare a private static GameWorld field called "instance"

    // TODO: Declare a private List<GameCharacter> field called "party"

    // TODO: Declare a private CombatEventManager field called "eventManager"

    /**
     * Private constructor — initialises an empty party list and a new
     * {@link CombatEventManager}.
     */
    private GameWorld() {
        // TODO: Initialize party (ArrayList) and eventManager (new CombatEventManager)
    }

    /**
     * Returns the single GameWorld instance, creating it if it does not
     * yet exist (lazy initialisation).
     *
     * @return the singleton instance
     */
    public static GameWorld getInstance() {
        // TODO: If instance is null, create a new GameWorld. Return instance.
        return null;
    }

    /**
     * Resets the singleton instance to {@code null}.
     * <p>
     * This method exists so that unit tests can start with a fresh GameWorld.
     */
    public static void resetInstance() {
        // TODO: Set the static instance field to null
    }

    /**
     * Adds a character to the party.
     *
     * @param character the character to add
     */
    public void addCharacter(GameCharacter character) {
        // TODO: Add the character to the party list
    }

    /**
     * Removes the first character whose name matches.
     *
     * @param name the name of the character to remove
     */
    public void removeCharacter(String name) {
        // TODO: Remove the character with the matching name from the party
    }

    /**
     * Finds a character in the party by name.
     *
     * @param name the name to search for
     * @return the character, or {@code null} if not found
     */
    public GameCharacter findCharacter(String name) {
        // TODO: Iterate over the party and return the character whose name matches
        return null;
    }

    /**
     * Returns an unmodifiable view of the party.
     *
     * @return unmodifiable list of characters
     */
    public List<GameCharacter> getParty() {
        // TODO: Return Collections.unmodifiableList(...) wrapping the party
        return Collections.emptyList();
    }

    /**
     * Returns the global combat event manager.
     *
     * @return the event manager
     */
    public CombatEventManager getEventManager() {
        // TODO: Return the eventManager field
        return null;
    }
}
