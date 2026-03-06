package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed Singleton pattern tests — students can see and run these locally.
 */
class SingletonTest {

    @AfterEach
    void tearDown() {
        GameWorld.resetInstance();
    }

    @Test
    @DisplayName("GameWorld.getInstance() should return the same instance every time")
    void sameInstance() {
        GameWorld a = GameWorld.getInstance();
        GameWorld b = GameWorld.getInstance();
        assertNotNull(a, "getInstance() must not return null");
        assertSame(a, b, "getInstance() must return the same object");
    }

    @Test
    @DisplayName("resetInstance should allow a new instance to be created")
    void resetCreatesNewInstance() {
        GameWorld first = GameWorld.getInstance();
        GameWorld.resetInstance();
        GameWorld second = GameWorld.getInstance();

        assertNotNull(second);
        assertNotSame(first, second, "After reset, a new instance should be created");
    }

    @Test
    @DisplayName("GameWorld should allow adding characters to the party")
    void addCharacterToParty() {
        GameWorld world = GameWorld.getInstance();
        world.addCharacter(new Warrior("Arthur", 100, 10, 6));
        assertEquals(1, world.getParty().size());
    }
}
