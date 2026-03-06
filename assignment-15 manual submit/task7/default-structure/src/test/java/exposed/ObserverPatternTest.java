package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed Observer pattern tests — students can see and run these locally.
 */
class ObserverPatternTest {

    @Test
    @DisplayName("CombatEventManager should allow adding listeners")
    void addListener() {
        CombatEventManager manager = new CombatEventManager();
        BattleLogger logger = new BattleLogger();
        manager.addListener(logger);
        assertEquals(1, manager.getListeners().size());
    }

    @Test
    @DisplayName("BattleLogger should record damage events")
    void loggerRecordsDamage() {
        CombatEventManager manager = new CombatEventManager();
        BattleLogger logger = new BattleLogger();
        manager.addListener(logger);

        manager.notifyDamageDealt("Arthur", "Goblin", 15);

        assertEquals(1, logger.getLog().size());
        assertEquals("DAMAGE: Arthur dealt 15 damage to Goblin", logger.getLog().get(0));
    }

    @Test
    @DisplayName("BattleLogger should record death events")
    void loggerRecordsDeath() {
        CombatEventManager manager = new CombatEventManager();
        BattleLogger logger = new BattleLogger();
        manager.addListener(logger);

        manager.notifyCharacterDeath("Goblin");

        assertEquals(1, logger.getLog().size());
        assertEquals("DEATH: Goblin has been defeated", logger.getLog().get(0));
    }

    @Test
    @DisplayName("BattleLogger should record level-up events")
    void loggerRecordsLevelUp() {
        CombatEventManager manager = new CombatEventManager();
        BattleLogger logger = new BattleLogger();
        manager.addListener(logger);

        manager.notifyLevelUp("Arthur", 2);

        assertEquals(1, logger.getLog().size());
        assertEquals("LEVEL UP: Arthur reached level 2", logger.getLog().get(0));
    }
}
