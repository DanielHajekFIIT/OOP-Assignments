package org.jikvict.tasks.exposed;

/**
 * Represents a weapon that can be equipped by a {@link GameCharacter}.
 * <p>
 * <b>Fully implemented from Task 6 — no changes needed.</b>
 */
public class Weapon {

    private String name;
    private int damage;
    private int durability;

    public Weapon(String name, int damage, int durability) {
        this.name = name;
        this.damage = damage;
        this.durability = durability;
    }

    // Getters & setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public int getDurability() { return durability; }
    public void setDurability(int durability) { this.durability = durability; }

    /**
     * Uses the weapon once, reducing durability by 1.
     *
     * @return {@code true} if the weapon had remaining durability, {@code false} otherwise
     */
    public boolean use() {
        if (durability > 0) {
            durability--;
            return true;
        }
        return false;
    }

    /** @return {@code true} if durability is 0 */
    public boolean isBroken() {
        return durability == 0;
    }

    /** Format: {@code "Weapon: {name} (Damage: {damage}, Durability: {durability})"} */
    @Override
    public String toString() {
        return "Weapon: " + name + " (Damage: " + damage + ", Durability: " + durability + ")";
    }
}
