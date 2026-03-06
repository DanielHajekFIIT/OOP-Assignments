package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed Strategy pattern tests — students can see and run these locally.
 */
class StrategyPatternTest {

    @Test
    @DisplayName("MeleeAttackStrategy should calculate (int)(baseAttackPower * 1.5)")
    void meleeStrategyBasic() {
        AttackStrategy strategy = new MeleeAttackStrategy();
        // (int)(10 * 1.5) = 15
        assertEquals(15, strategy.execute(10));
    }

    @Test
    @DisplayName("RangedAttackStrategy should calculate baseAttackPower + 15")
    void rangedStrategyBasic() {
        AttackStrategy strategy = new RangedAttackStrategy();
        assertEquals(25, strategy.execute(10));
    }

    @Test
    @DisplayName("MagicAttackStrategy should calculate baseAttackPower * 3")
    void magicStrategyBasic() {
        AttackStrategy strategy = new MagicAttackStrategy();
        assertEquals(30, strategy.execute(10));
    }

    @Test
    @DisplayName("A character with no strategy should fall back to calculateDamage()")
    void executeStrategyFallback() {
        GameCharacter warrior = Util.createWarrior("W", 100, 10, 6);
        assertNull(warrior.getAttackStrategy());
        // Should fall back to calculateDamage() = 10 + 6/2 = 13
        assertEquals(13, warrior.executeStrategy());
    }

    @Test
    @DisplayName("A character with a strategy set should use the strategy")
    void executeStrategyWithStrategy() {
        GameCharacter warrior = Util.createWarrior("W", 100, 10, 6);
        warrior.setAttackStrategy(new MagicAttackStrategy());
        // Magic: 10 * 3 = 30
        assertEquals(30, warrior.executeStrategy());
    }
}
