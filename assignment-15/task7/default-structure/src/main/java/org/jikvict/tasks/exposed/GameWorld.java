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

    private static GameWorld instance;

    private List<GameCharacter> party;

    private CombatEventManager eventManager;

    /**
     * Private constructor — initialises an empty party list and a new
     * {@link CombatEventManager}.
     */
    private GameWorld() {
        this.party = new ArrayList<>();
        this.eventManager = new CombatEventManager();
    }

    /**
     * Returns the single GameWorld instance, creating it if it does not
     * yet exist (lazy initialisation).
     *
     * @return the singleton instance
     */
    public static GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
        }
        return instance;
    }

    /**
     * Resets the singleton instance to {@code null}.
     * <p>
     * This method exists so that unit tests can start with a fresh GameWorld.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Adds a character to the party.
     *
     * @param character the character to add
     */
    public void addCharacter(GameCharacter character) {
        this.party.add(character);
    }

    /**
     * Removes the first character whose name matches.
     *
     * @param name the name of the character to remove
     */
    public void removeCharacter(String name) {
        this.party.removeIf(character -> character.getName().equals(name));
    }

    /**
     * Finds a character in the party by name.
     *
     * @param name the name to search for
     * @return the character, or {@code null} if not found
     */
    public GameCharacter findCharacter(String name) {
        for (GameCharacter character : this.party) {
            if (character.getName().equals(name)) {return character;}
        }
        return null;
    }

    /**
     * Returns an unmodifiable view of the party.
     *
     * @return unmodifiable list of characters
     */
    public List<GameCharacter> getParty() {
        return Collections.unmodifiableList(this.party);
    }

    /**
     * Returns the global combat event manager.
     *
     * @return the event manager
     */
    public CombatEventManager getEventManager() {
        return eventManager;
    }
}
