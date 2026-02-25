package org.jikvict.tasks.exposed;

/**
 * Abstract base class for all game characters.
 * <p>
 * Subclasses must:
 * <ul>
 *   <li>Call {@code super(name, health, baseAttackPower)} in their constructor</li>
 *   <li>Implement {@link #getCharacterType()}</li>
 *   <li>Implement {@link #calculateDamage()}</li>
 * </ul>
 * <p>
 * Fields to declare (all private unless specified otherwise):
 * <ul>
 *   <li>{@code name}            — String,  private</li>
 *   <li>{@code health}          — int,     private</li>
 *   <li>{@code maxHealth}       — int,     private</li>
 *   <li>{@code baseAttackPower} — int,     <b>protected</b> (subclasses need direct access)</li>
 *   <li>{@code level}           — int,     private</li>
 *   <li>{@code weapon}          — Weapon,  private</li>
 *   <li>{@code alive}           — boolean, private</li>
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
    /**
     * Creates a new GameCharacter.
     * <p>
     * Initialisation rules:
     * <ul>
     *   <li>maxHealth = health</li>
     *   <li>level = 1</li>
     *   <li>alive = true</li>
     *   <li>weapon = null</li>
     * </ul>
     *
     * @param name            the character's name
     * @param health          starting (and maximum) health points
     * @param baseAttackPower base attack power
     */
    public GameCharacter(String name, int health, int baseAttackPower) {
        this.name = name;
        this.health = health;
        this.baseAttackPower = baseAttackPower;
        this.maxHealth = health;
        this.level = 1;
        this.weapon = null;
        this.alive = true;
    }

    // ──────────────────────────── abstract methods ────────────────────────────

    /**
     * Returns the type of this character, for example {@code "Warrior"}.
     *
     * @return character type
     */
    public abstract String getCharacterType();

    /**
     * Calculates the raw damage this character deals (before weapon bonus).
     * Each subclass defines its own formula.
     *
     * @return calculated damage
     */
    public abstract int calculateDamage();

    // ──────────────────────────── concrete methods ────────────────────────────

    /**
     * Returns a formatted information string.
     * <p>
     * Format: {@code "Name: {name} | Type: {type} | HP: {health}/{maxHealth} | Level: {level}"}
     * <p>
     * Subclasses should override this and <b>call super</b> to append extra info.
     *
     * @return info string
     */
    public String getInfo() {
        return "Name: " + name + " | Type: " + getCharacterType()
                + " | HP: " + health + "/" + maxHealth + " | Level: " + level;
    }

    /**
     * Attacks a target character.
     * <ol>
     *   <li>Calculate damage via {@link #calculateDamage()}</li>
     *   <li>If a weapon is equipped and not broken, add its damage and call {@code weapon.use()}</li>
     *   <li>Call {@code target.takeDamage(totalDamage)}</li>
     * </ol>
     *
     * @param target the character to attack
     */
    public void attack(GameCharacter target) {
        int totalDamage = calculateDamage();
        if (weapon != null && !weapon.isBroken()) {
            totalDamage += weapon.getDamage();
            weapon.use();
        }
        target.takeDamage(totalDamage);
    }

    /**
     * Returns the total damage this character <i>would</i> deal — without
     * actually attacking anyone or consuming weapon durability.
     * <p>
     * This is a <b>method overload</b> of {@link #attack(GameCharacter)}.
     *
     * @return potential total damage
     */
    public int attack() {
        int totalDamage = calculateDamage();
        if (weapon != null && !weapon.isBroken()) {
            totalDamage += weapon.getDamage();
        }
        return totalDamage;
    }

    /**
     * Reduces health by the given damage amount.
     * <ul>
     *   <li>Health must not drop below 0.</li>
     *   <li>If health reaches 0, set alive to false.</li>
     * </ul>
     *
     * @param damage incoming damage
     */
    public void takeDamage(int damage) {
        if (damage >= health) {
            health = 0;
            alive = false;
        }
        else {
            health -= damage;
        }
    }

    /**
     * Heals the character by the given amount.
     * <ul>
     *   <li>Health must not exceed maxHealth.</li>
     *   <li>Does nothing if the character is not alive.</li>
     * </ul>
     *
     * @param amount hit points to restore
     */
    public void heal(int amount) {
        if (alive) {
            if (health + amount > maxHealth) {
                health = maxHealth;
            } else {
                health += amount;
            }
        }
    }


    /**
     * Levels up: {@code level += 1}, {@code maxHealth += 10}, {@code baseAttackPower += 2}.
     */
    public void levelUp() {
        level += 1;
        maxHealth += 10;
        baseAttackPower += 2;
    }

    // ──────────────────────── getters & setters ──────────────────────────────

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getBaseAttackPower() {
        return baseAttackPower;
    }

    public int getLevel() {
        return level;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    // ──────────────────────────── toString ────────────────────────────────────

    /**
     * Format: {@code "{type}: {name} (Level {level})"}
     */
    @Override
    public String toString() {
        return getCharacterType() + ": " + name + " (Level" + level + ")";
    }
}
