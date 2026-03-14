package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Exposed potion tests — basic decorator pattern behaviour.
 */
class PotionTest {

    private boolean potionClassesExist() {
        return Util.tryLoadClass("BasePotion") != null;
    }

    private boolean decoratorClassesExist() {
        return potionClassesExist()
                && Util.tryLoadClass("PotionDecorator") != null
                && Util.tryLoadClass("HealingBoostDecorator") != null
                && Util.tryLoadClass("DurationExtenderDecorator") != null;
    }

    // ──────────────────────── BasePotion basics ────────────────────────

    @Test
    @DisplayName("BasePotion should return correct name and healing power")
    void basePotionBasics() {
        assumeTrue(potionClassesExist(), "BasePotion not yet implemented");

        Object obj = Util.createBasePotion("Health Potion", "Restores health", 25, 30);
        assertTrue(obj instanceof Potion, "BasePotion should implement Potion");

        Potion potion = (Potion) obj;
        assertEquals("Health Potion", potion.getName());
        assertEquals(25, potion.getHealingPower());
        assertEquals(30, potion.getDuration());
        assertEquals("Restores health", potion.getDescription());
    }

    @Test
    @DisplayName("BasePotion.apply() should heal the target character")
    void basePotionApply() throws GameException {
        assumeTrue(potionClassesExist(), "BasePotion not yet implemented");

        Potion potion = (Potion) Util.createBasePotion("HP Pot", "Heals", 20, 30);
        GameCharacter warrior = Util.createWarrior("Test", 100, 10, 6);
        warrior.takeDamage(50); // 50 HP remaining

        potion.apply(warrior);
        assertEquals(70, warrior.getHealth(), "Potion should heal by its healing power");
    }

    // ──────────────────────── HealingBoostDecorator ────────────────────────

    @Test
    @DisplayName("HealingBoostDecorator should increase healing power")
    void healingBoostIncreasesHealing() {
        assumeTrue(decoratorClassesExist(), "Decorator classes not yet implemented");

        Potion base = (Potion) Util.createBasePotion("Elixir", "Heals", 20, 30);
        Potion boosted = (Potion) Util.createHealingBoostDecorator(base, 15);

        assertEquals(35, boosted.getHealingPower(),
                "HealingBoostDecorator should add bonus to healing power");
        assertEquals("Elixir", boosted.getName(),
                "Name should delegate to wrapped potion");
    }

    // ──────────────────────── DurationExtenderDecorator ────────────────────────

    @Test
    @DisplayName("DurationExtenderDecorator should multiply duration")
    void durationExtenderMultipliesDuration() {
        assumeTrue(decoratorClassesExist(), "Decorator classes not yet implemented");

        Potion base = (Potion) Util.createBasePotion("Elixir", "Heals", 20, 30);
        Potion extended = (Potion) Util.createDurationExtenderDecorator(base, 3);

        assertEquals(90, extended.getDuration(),
                "DurationExtenderDecorator should multiply duration");
        assertEquals(20, extended.getHealingPower(),
                "Duration extender should not change healing power");
    }

    // ──────────────────────── Stacking ────────────────────────

    @Test
    @DisplayName("Decorators should stack: healing boost + duration extender")
    void decoratorStacking() {
        assumeTrue(decoratorClassesExist(), "Decorator classes not yet implemented");

        Potion base = (Potion) Util.createBasePotion("Elixir", "Restores health", 20, 30);
        Potion boosted = (Potion) Util.createHealingBoostDecorator(base, 15);
        Potion superPotion = (Potion) Util.createDurationExtenderDecorator(boosted, 3);

        assertEquals(35, superPotion.getHealingPower());
        assertEquals(90, superPotion.getDuration());
        assertEquals("Restores health, Healing Boost +15, Duration x3",
                superPotion.getDescription());
    }
}
