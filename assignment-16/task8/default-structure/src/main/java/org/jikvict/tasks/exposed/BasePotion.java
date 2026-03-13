package org.jikvict.tasks.exposed;

public class BasePotion implements Potion {
    private String name;
    private String description;
    private int healingPower;
    private int duration;


    public BasePotion(String name, String description, int healingPower, int duration) throws InvalidPotionException {
        this.name = name;
        this.description = description;
        if (healingPower < 0 || duration <= 0) {
            throw new InvalidPotionException("Potion has negative healing power or non-positive duration!");
        }
        this.healingPower = healingPower;
        this.duration = duration;
    }

    // ──────────────────────── getters & setters ──────────────────────────────
    /** Returns the potion's name. */
    @Override
    public String getName() {
        return this.name;
    };

    /** Returns a human-readable description (decorators append to it). */
    @Override
    public String getDescription() {
        return this.description;
    };

    /** Returns the total healing power (decorators may increase it). */
    public int getHealingPower() {
        return this.healingPower;
    }

    /** Returns the duration in seconds (decorators may extend it). */
    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public void apply(GameCharacter target) throws CharacterDeadException {
        if (!target.isAlive()) {
            throw new CharacterDeadException("Cannot heal the dead!");
        }
        target.heal(healingPower);
    };
}
