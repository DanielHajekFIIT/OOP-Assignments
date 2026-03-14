package org.jikvict.tasks.exposed;

/**
 * Base checked exception for all game-related errors.
 * <p>
 * Extend this class to create specific exception types for different
 * error conditions in the game (invalid potions, full inventories,
 * dead characters, etc.).
 */
public class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }
}
