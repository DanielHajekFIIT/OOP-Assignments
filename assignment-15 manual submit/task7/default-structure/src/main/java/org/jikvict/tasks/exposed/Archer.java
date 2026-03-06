package org.jikvict.tasks.exposed;

/**
 * An archer character that uses arrows.
 * <p>
 * <b>Fully implemented from Task 6 — no changes needed.</b>
 */
public class Archer extends GameCharacter {

    private int arrowCount;

    public Archer(String name, int health, int baseAttackPower, int arrowCount) {
        super(name, health, baseAttackPower);
        this.arrowCount = arrowCount;
    }

    @Override
    public String getCharacterType() {
        return "Archer";
    }

    /** Damage = baseAttackPower + 5. */
    @Override
    public int calculateDamage() {
        return baseAttackPower + 5;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " | Arrows: " + arrowCount;
    }

    /**
     * Shoots one arrow.
     *
     * @return {@code true} if an arrow was available, {@code false} if out of arrows
     */
    public boolean shootArrow() {
        if (arrowCount > 0) {
            arrowCount--;
            return true;
        }
        return false;
    }

    public int getArrowCount() { return arrowCount; }
    public void setArrowCount(int arrowCount) { this.arrowCount = arrowCount; }
}
