package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Exposed inventory tests — basic generics and lambda behaviour.
 */
class InventoryTest {

    private boolean inventoryExists() {
        return Util.tryLoadClass("Inventory") != null;
    }

    private boolean allClassesExist() {
        return inventoryExists()
                && Util.tryLoadClass("BasePotion") != null
                && Util.tryLoadClass("InventoryFullException") != null;
    }

    // ──────────────────────── Basic operations ────────────────────────

    @Test
    @DisplayName("Inventory should track size after adding items")
    void inventorySize() throws Exception {
        assumeTrue(allClassesExist(), "Required classes not yet implemented");

        Object inv = Util.createInventory(5);
        Object potion = Util.createBasePotion("HP", "Heals", 10, 30);

        // add
        Method add = inv.getClass().getMethod("add", Object.class);
        add.invoke(inv, potion);

        // size
        Method size = inv.getClass().getMethod("size");
        assertEquals(1, size.invoke(inv));
    }

    @Test
    @DisplayName("Inventory.isFull() should return true at capacity")
    void inventoryIsFull() throws Exception {
        assumeTrue(allClassesExist(), "Required classes not yet implemented");

        Object inv = Util.createInventory(1);
        Object potion = Util.createBasePotion("HP", "Heals", 10, 30);

        Method add = inv.getClass().getMethod("add", Object.class);
        Method isFull = inv.getClass().getMethod("isFull");

        assertFalse((boolean) isFull.invoke(inv));
        add.invoke(inv, potion);
        assertTrue((boolean) isFull.invoke(inv));
    }

    @Test
    @DisplayName("Inventory should throw InventoryFullException when adding past capacity")
    void inventoryFullException() throws Exception {
        assumeTrue(allClassesExist(), "Required classes not yet implemented");

        Class<?> exCls = Util.loadClass("InventoryFullException");
        Object inv = Util.createInventory(1);
        Object potion1 = Util.createBasePotion("HP1", "Heals", 10, 30);
        Object potion2 = Util.createBasePotion("HP2", "Heals", 20, 30);

        Method add = inv.getClass().getMethod("add", Object.class);
        add.invoke(inv, potion1);

        try {
            add.invoke(inv, potion2);
            fail("Expected InventoryFullException to be thrown");
        } catch (InvocationTargetException e) {
            assertTrue(exCls.isInstance(e.getCause()),
                    "Expected InventoryFullException but got " + e.getCause().getClass().getSimpleName());
        }
    }

    // ──────────────────────── Lambda filtering ────────────────────────

    @Test
    @DisplayName("findAll with lambda should filter items correctly")
    void findAllWithLambda() throws Exception {
        assumeTrue(allClassesExist(), "Required classes not yet implemented");

        Object inv = Util.createInventory(10);
        Object weak = Util.createBasePotion("Weak", "Weak heal", 10, 30);
        Object strong = Util.createBasePotion("Strong", "Strong heal", 50, 30);
        Object mega = Util.createBasePotion("Mega", "Mega heal", 100, 30);

        Method add = inv.getClass().getMethod("add", Object.class);
        add.invoke(inv, weak);
        add.invoke(inv, strong);
        add.invoke(inv, mega);

        // Use ItemFilter lambda to find potions with healing > 30
        ItemFilter<Object> filter = item -> {
            if (item instanceof Potion p) {
                return p.getHealingPower() > 30;
            }
            return false;
        };

        Method findAll = inv.getClass().getMethod("findAll", ItemFilter.class);
        List<?> result = (List<?>) findAll.invoke(inv, filter);

        assertEquals(2, result.size(), "Should find 2 potions with healing > 30");
    }

    @Test
    @DisplayName("getItems() should return an unmodifiable list")
    void getItemsUnmodifiable() throws Exception {
        assumeTrue(inventoryExists(), "Inventory not yet implemented");

        Object inv = Util.createInventory(5);
        Method getItems = inv.getClass().getMethod("getItems");
        List<?> items = (List<?>) getItems.invoke(inv);

        assertThrows(UnsupportedOperationException.class,
                () -> items.add(null),
                "getItems() must return an unmodifiable list");
    }
}
