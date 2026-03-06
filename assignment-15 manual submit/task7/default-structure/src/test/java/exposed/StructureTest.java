package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed structural tests — students can see and run these locally.
 */
class StructureTest {

    // ──────────────────────── Interfaces ────────────────────────

    @Test
    @DisplayName("AttackStrategy should be an interface")
    void attackStrategyShouldBeInterface() {
        assertTrue(AttackStrategy.class.isInterface(),
                "AttackStrategy must be declared as an interface");
    }

    @Test
    @DisplayName("CombatEventListener should be an interface")
    void combatEventListenerShouldBeInterface() {
        assertTrue(CombatEventListener.class.isInterface(),
                "CombatEventListener must be declared as an interface");
    }

    // ──────────────────────── Strategy implementations ────────────────────────

    @Test
    @DisplayName("MeleeAttackStrategy should implement AttackStrategy")
    void meleeImplementsStrategy() {
        assertTrue(AttackStrategy.class.isAssignableFrom(MeleeAttackStrategy.class),
                "MeleeAttackStrategy must implement AttackStrategy");
    }

    @Test
    @DisplayName("RangedAttackStrategy should implement AttackStrategy")
    void rangedImplementsStrategy() {
        assertTrue(AttackStrategy.class.isAssignableFrom(RangedAttackStrategy.class),
                "RangedAttackStrategy must implement AttackStrategy");
    }

    @Test
    @DisplayName("MagicAttackStrategy should implement AttackStrategy")
    void magicImplementsStrategy() {
        assertTrue(AttackStrategy.class.isAssignableFrom(MagicAttackStrategy.class),
                "MagicAttackStrategy must implement AttackStrategy");
    }

    // ──────────────────────── Observer implementation ────────────────────────

    @Test
    @DisplayName("BattleLogger should implement CombatEventListener")
    void battleLoggerImplementsListener() {
        assertTrue(CombatEventListener.class.isAssignableFrom(BattleLogger.class),
                "BattleLogger must implement CombatEventListener");
    }

    // ──────────────────────── Singleton ────────────────────────

    @Test
    @DisplayName("GameWorld should have a private constructor")
    void gameWorldPrivateConstructor() {
        var constructors = GameWorld.class.getDeclaredConstructors();
        assertTrue(constructors.length > 0, "GameWorld should have at least one constructor");
        for (var c : constructors) {
            assertTrue(Modifier.isPrivate(c.getModifiers()),
                    "All GameWorld constructors must be private");
        }
    }

    @Test
    @DisplayName("GameCharacter should have setAttackStrategy, getAttackStrategy, and executeStrategy methods")
    void gameCharacterStrategyMethods() throws NoSuchMethodException {
        assertNotNull(GameCharacter.class.getMethod("setAttackStrategy", AttackStrategy.class));
        assertNotNull(GameCharacter.class.getMethod("getAttackStrategy"));
        assertNotNull(GameCharacter.class.getMethod("executeStrategy"));
    }
}
