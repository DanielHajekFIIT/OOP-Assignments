package org.jikvict.tasks.exposed;

/**
 * Represents a potion that can be applied to a {@link GameCharacter}.
 * <p>
 * This is the component interface for the <b>Decorator</b> pattern.
 * Both the base potion and all decorators implement this interface.
 */
public interface Potion {

    /** Returns the potion's name. */
    String getName();

    /** Returns a human-readable description (decorators append to it). */
    String getDescription();

    /** Returns the total healing power (decorators may increase it). */
    int getHealingPower();

    /** Returns the duration in seconds (decorators may extend it). */
    int getDuration();

    /**
     * Applies this potion to the given character.
     *
     * @param target the character to heal
     * @throws GameException if the potion cannot be applied (e.g. target is dead)
     */
    void apply(GameCharacter target) throws GameException;
}
