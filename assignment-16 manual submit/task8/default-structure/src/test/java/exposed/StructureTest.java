package exposed;

import org.jikvict.tasks.exposed.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed structural tests — students can see and run these locally.
 * <p>
 * These tests use reflection and will <b>fail</b> (not skip) when a required
 * class or method is missing or misspelled, printing the expected name so you
 * can correct it.
 */
class StructureTest {

    private static final String PKG = "org.jikvict.tasks.exposed";

    // ──────────────────────── Provided interfaces ────────────────────────

    @Test
    @DisplayName("Potion should be an interface")
    void potionShouldBeInterface() {
        assertTrue(Potion.class.isInterface(), "Potion must be declared as an interface");
    }

    @Test
    @DisplayName("ItemFilter should be an interface with @FunctionalInterface")
    void itemFilterShouldBeFunctionalInterface() {
        assertTrue(ItemFilter.class.isInterface(), "ItemFilter must be an interface");
        assertNotNull(ItemFilter.class.getAnnotation(FunctionalInterface.class),
                "ItemFilter must be annotated with @FunctionalInterface");
    }

    @Test
    @DisplayName("GameException should extend Exception (checked)")
    void gameExceptionShouldExtendException() {
        assertTrue(Exception.class.isAssignableFrom(GameException.class),
                "GameException must extend Exception");
        assertFalse(RuntimeException.class.isAssignableFrom(GameException.class),
                "GameException must NOT extend RuntimeException (it should be checked)");
    }

    // ──────────────────────── Exception hierarchy ────────────────────────

    @Test
    @DisplayName("InvalidPotionException should exist and extend GameException")
    void invalidPotionExceptionHierarchy() {
        Class<?> cls = Util.tryLoadClass("InvalidPotionException");
        assertNotNull(cls,
                "Class not found: " + PKG + ".InvalidPotionException — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(GameException.class.isAssignableFrom(cls),
                "InvalidPotionException must extend GameException");
    }

    @Test
    @DisplayName("InventoryFullException should exist and extend GameException")
    void inventoryFullExceptionHierarchy() {
        Class<?> cls = Util.tryLoadClass("InventoryFullException");
        assertNotNull(cls,
                "Class not found: " + PKG + ".InventoryFullException — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(GameException.class.isAssignableFrom(cls),
                "InventoryFullException must extend GameException");
    }

    @Test
    @DisplayName("CharacterDeadException should exist and extend GameException")
    void characterDeadExceptionHierarchy() {
        Class<?> cls = Util.tryLoadClass("CharacterDeadException");
        assertNotNull(cls,
                "Class not found: " + PKG + ".CharacterDeadException — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(GameException.class.isAssignableFrom(cls),
                "CharacterDeadException must extend GameException");
    }

    @Test
    @DisplayName("All custom exceptions should have a constructor that takes String")
    void exceptionConstructors() {
        String[] names = {"InvalidPotionException", "InventoryFullException", "CharacterDeadException"};
        for (String name : names) {
            Class<?> cls = Util.tryLoadClass(name);
            assertNotNull(cls, "Class not found: " + PKG + "." + name);
            try {
                cls.getConstructor(String.class);
            } catch (NoSuchMethodException e) {
                fail(name + " must have a constructor that accepts a String message: "
                     + "public " + name + "(String message)");
            }
        }
    }

    // ──────────────────────── Decorator hierarchy ────────────────────────

    @Test
    @DisplayName("BasePotion should exist and implement Potion")
    void basePotionImplementsPotion() {
        Class<?> cls = Util.tryLoadClass("BasePotion");
        assertNotNull(cls,
                "Class not found: " + PKG + ".BasePotion — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(Potion.class.isAssignableFrom(cls),
                "BasePotion must implement the Potion interface");
    }

    @Test
    @DisplayName("BasePotion should have constructor (String, String, int, int)")
    void basePotionConstructor() {
        Class<?> cls = Util.tryLoadClass("BasePotion");
        assertNotNull(cls, "Class not found: " + PKG + ".BasePotion");
        try {
            cls.getConstructor(String.class, String.class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("BasePotion must have a constructor: "
                 + "public BasePotion(String name, String description, int healingPower, int duration)");
        }
    }

    @Test
    @DisplayName("PotionDecorator should exist, implement Potion, and be abstract")
    void potionDecoratorStructure() {
        Class<?> cls = Util.tryLoadClass("PotionDecorator");
        assertNotNull(cls,
                "Class not found: " + PKG + ".PotionDecorator — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(Potion.class.isAssignableFrom(cls),
                "PotionDecorator must implement the Potion interface");
        assertTrue(Modifier.isAbstract(cls.getModifiers()),
                "PotionDecorator must be declared abstract");
    }

    @Test
    @DisplayName("PotionDecorator should have constructor (Potion)")
    void potionDecoratorConstructor() {
        Class<?> cls = Util.tryLoadClass("PotionDecorator");
        assertNotNull(cls, "Class not found: " + PKG + ".PotionDecorator");
        try {
            cls.getConstructor(Potion.class);
        } catch (NoSuchMethodException e) {
            fail("PotionDecorator must have a constructor: "
                 + "public PotionDecorator(Potion wrappedPotion)");
        }
    }

    @Test
    @DisplayName("HealingBoostDecorator should exist and extend PotionDecorator")
    void healingBoostDecoratorHierarchy() {
        Class<?> decorator = Util.tryLoadClass("PotionDecorator");
        assertNotNull(decorator, "Class not found: " + PKG + ".PotionDecorator");

        Class<?> cls = Util.tryLoadClass("HealingBoostDecorator");
        assertNotNull(cls,
                "Class not found: " + PKG + ".HealingBoostDecorator — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(decorator.isAssignableFrom(cls),
                "HealingBoostDecorator must extend PotionDecorator");
    }

    @Test
    @DisplayName("HealingBoostDecorator should have constructor (Potion, int)")
    void healingBoostDecoratorConstructor() {
        Class<?> cls = Util.tryLoadClass("HealingBoostDecorator");
        assertNotNull(cls, "Class not found: " + PKG + ".HealingBoostDecorator");
        try {
            cls.getConstructor(Potion.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("HealingBoostDecorator must have a constructor: "
                 + "public HealingBoostDecorator(Potion wrappedPotion, int bonusHealing)");
        }
    }

    @Test
    @DisplayName("DurationExtenderDecorator should exist and extend PotionDecorator")
    void durationExtenderDecoratorHierarchy() {
        Class<?> decorator = Util.tryLoadClass("PotionDecorator");
        assertNotNull(decorator, "Class not found: " + PKG + ".PotionDecorator");

        Class<?> cls = Util.tryLoadClass("DurationExtenderDecorator");
        assertNotNull(cls,
                "Class not found: " + PKG + ".DurationExtenderDecorator — "
                + "make sure the class exists with exactly this name in the correct package.");
        assertTrue(decorator.isAssignableFrom(cls),
                "DurationExtenderDecorator must extend PotionDecorator");
    }

    @Test
    @DisplayName("DurationExtenderDecorator should have constructor (Potion, int)")
    void durationExtenderDecoratorConstructor() {
        Class<?> cls = Util.tryLoadClass("DurationExtenderDecorator");
        assertNotNull(cls, "Class not found: " + PKG + ".DurationExtenderDecorator");
        try {
            cls.getConstructor(Potion.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("DurationExtenderDecorator must have a constructor: "
                 + "public DurationExtenderDecorator(Potion wrappedPotion, int multiplier)");
        }
    }

    // ──────────────────────── Inventory ────────────────────────

    @Test
    @DisplayName("Inventory class should exist")
    void inventoryClassExists() {
        Class<?> cls = Util.tryLoadClass("Inventory");
        assertNotNull(cls,
                "Class not found: " + PKG + ".Inventory — "
                + "make sure the class exists with exactly this name in the correct package.");
    }

    @Test
    @DisplayName("Inventory should have a constructor that takes int (capacity)")
    void inventoryConstructor() {
        Class<?> cls = Util.tryLoadClass("Inventory");
        assertNotNull(cls, "Class not found: " + PKG + ".Inventory");
        try {
            cls.getConstructor(int.class);
        } catch (NoSuchMethodException e) {
            fail("Inventory must have a constructor: public Inventory(int capacity)");
        }
    }

    @Test
    @DisplayName("Inventory should have required methods: add, remove, contains, size, getItems, isFull, isEmpty, getCapacity")
    void inventoryMethods() {
        Class<?> cls = Util.tryLoadClass("Inventory");
        assertNotNull(cls, "Class not found: " + PKG + ".Inventory");

        assertMethodExists(cls, "add", "void add(T item)", Object.class);
        assertMethodExists(cls, "remove", "boolean remove(T item)", Object.class);
        assertMethodExists(cls, "contains", "boolean contains(T item)", Object.class);
        assertMethodExists(cls, "size", "int size()");
        assertMethodExists(cls, "getItems", "List<T> getItems()");
        assertMethodExists(cls, "isFull", "boolean isFull()");
        assertMethodExists(cls, "isEmpty", "boolean isEmpty()");
        assertMethodExists(cls, "getCapacity", "int getCapacity()");
    }

    @Test
    @DisplayName("Inventory should have lambda methods: findAll(ItemFilter), findFirst(ItemFilter), applyToAll(Consumer)")
    void inventoryLambdaMethods() {
        Class<?> cls = Util.tryLoadClass("Inventory");
        assertNotNull(cls, "Class not found: " + PKG + ".Inventory");

        assertMethodExists(cls, "findAll", "List<T> findAll(ItemFilter<T> filter)", ItemFilter.class);
        assertMethodExists(cls, "findFirst", "Optional<T> findFirst(ItemFilter<T> filter)", ItemFilter.class);
        assertMethodExists(cls, "applyToAll", "void applyToAll(Consumer<T> action)", java.util.function.Consumer.class);
    }

    // ──────────────────────── Helper ────────────────────────

    private void assertMethodExists(Class<?> cls, String methodName,
                                    String expectedSignature, Class<?>... paramTypes) {
        try {
            cls.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            fail("Method not found in " + cls.getSimpleName() + ": " + expectedSignature
                 + " — check the method name and parameter types.");
        }
    }
}
