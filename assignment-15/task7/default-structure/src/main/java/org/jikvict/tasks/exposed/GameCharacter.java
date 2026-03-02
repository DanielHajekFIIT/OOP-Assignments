package org.jikvict.tasks.exposed;

/**
 * Abstract base class for all game characters.
 * <p>
 * <b>Fully implemented from Task 6.</b> In Task 7 you must add support for
 * the <b>Strategy</b> pattern — see the TODO section at the bottom of this file.
 * <p>
 * New elements to add:
 * <ul>
 *   <li>{@code attackStrategy} — private field of type {@link AttackStrategy}, initially {@code null}</li>
 *   <li>{@link #setAttackStrategy(AttackStrategy)} — stores the strategy</li>
 *   <li>{@link #getAttackStrategy()} — returns the current strategy (may be {@code null})</li>
 *   <li>{@link #executeStrategy()} — delegates to the strategy or falls back to {@link #calculateDamage()}</li>
 * </ul>
 */
public abstract class GameCharacter {

    private String name;
    private int health;
    private int maxHealth;
    protected int baseAttackPower;
    private int level;
    private Weapon weapon;
    private boolean alive;

    private AttackStrategy attackStrategy = null;

    /**
     * Creates a new GameCharacter.
     *
     * @param name            the character's name
     * @param health          starting (and maximum) health points
     * @param baseAttackPower base attack power
     */
    public GameCharacter(String name, int health, int baseAttackPower) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.baseAttackPower = baseAttackPower;
        this.level = 1;
        this.alive = true;
        this.weapon = null;
    }

    // ──────────────────────────── abstract methods ────────────────────────────

    /** Returns the type of this character, e.g. {@code "Warrior"}. */
    public abstract String getCharacterType();

    /** Calculates the raw damage (before weapon bonus). */
    public abstract int calculateDamage();

    // ──────────────────────────── concrete methods ────────────────────────────

    /**
     * Format: {@code "Name: {name} | Type: {type} | HP: {health}/{maxHealth} | Level: {level}"}
     */
    public String getInfo() {
        return "Name: " + name + " | Type: " + getCharacterType()
                + " | HP: " + health + "/" + maxHealth + " | Level: " + level;
    }

    /**
     * Attacks a target character — calculates damage, adds weapon bonus, calls
     * {@code target.takeDamage(...)}.
     */
    public void attack(GameCharacter target) {
        int damage = calculateDamage();
        if (weapon != null && !weapon.isBroken()) {
            damage += weapon.getDamage();
            weapon.use();
        }
        target.takeDamage(damage);
    }

    /**
     * Returns the total damage this character <i>would</i> deal without
     * actually attacking anyone or consuming weapon durability.
     */
    public int attack() {
        int damage = calculateDamage();
        if (weapon != null && !weapon.isBroken()) {
            damage += weapon.getDamage();
        }
        return damage;
    }

    /** Reduces health by {@code damage}. Health cannot drop below 0. */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    /** Heals by {@code amount} up to maxHealth. Does nothing if dead. */
    public void heal(int amount) {
        if (!alive) return;
        health = Math.min(health + amount, maxHealth);
    }

    /** Levels up: level +1, maxHealth +10, baseAttackPower +2. */
    public void levelUp() {
        level += 1;
        maxHealth += 10;
        baseAttackPower += 2;
    }

    // ──────────────────────── getters & setters ──────────────────────────────

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getBaseAttackPower() { return baseAttackPower; }
    public int getLevel() { return level; }
    public Weapon getWeapon() { return weapon; }
    public boolean isAlive() { return alive; }

    public void setName(String name) { this.name = name; }
    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    @Override
    public String toString() {
        return getCharacterType() + ": " + name + " (Level " + level + ")";
    }

    // ======================== TASK 7 — Strategy Pattern ========================

    /**
     * Sets the attack strategy for this character.
     *
     * @param strategy the strategy to use (may be {@code null} to clear it)
     */
    public void setAttackStrategy(AttackStrategy strategy) {
        this.attackStrategy = strategy;
    }

    /**
     * Returns the currently set attack strategy.
     *
     * @return the attack strategy, or {@code null} if none is set
     */
    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    /**
     * Executes the current attack strategy.
     * <ul>
     *   <li>If a strategy is set → return {@code attackStrategy.execute(baseAttackPower)}</li>
     *   <li>If no strategy is set → fall back to {@link #calculateDamage()}</li>
     * </ul>
     *
     * @return the calculated damage
     */
    public int executeStrategy() {
        if (attackStrategy != null) {
           attackStrategy.execute(baseAttackPower);
        }
        else {
            calculateDamage();
        }
        return 0;
    }
}
