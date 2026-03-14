package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed structural tests — students can see and run these locally.
 * <p>
 * All tests use reflection and will not crash if classes are missing.
 * Instead they produce clear assertion messages telling the student
 * what needs to be created.
 */
class StructureTest {

    private static final String PKG = "org.jikvict.tasks.exposed";

    // ═══════════════════════════════════════════════════════════
    //  Interface existence
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Growable should exist and be an interface")
    void growableShouldBeInterface() {
        Class<?> cls = Util.tryLoadClass("Growable");
        assertNotNull(cls,
                "Interface not found: " + PKG + ".Growable — "
                + "create a file Growable.java in the exposed package and declare it as an interface.");
        assertTrue(cls.isInterface(),
                "Growable must be declared as an interface, not a class.");
    }

    @Test
    @DisplayName("Treatable should exist and be an interface")
    void treatableShouldBeInterface() {
        Class<?> cls = Util.tryLoadClass("Treatable");
        assertNotNull(cls,
                "Interface not found: " + PKG + ".Treatable — "
                + "create a file Treatable.java in the exposed package and declare it as an interface.");
        assertTrue(cls.isInterface(),
                "Treatable must be declared as an interface, not a class.");
    }

    @Test
    @DisplayName("Sellable should exist and be an interface")
    void sellableShouldBeInterface() {
        Class<?> cls = Util.tryLoadClass("Sellable");
        assertNotNull(cls,
                "Interface not found: " + PKG + ".Sellable — "
                + "create a file Sellable.java in the exposed package and declare it as an interface.");
        assertTrue(cls.isInterface(),
                "Sellable must be declared as an interface, not a class.");
    }

    // ═══════════════════════════════════════════════════════════
    //  Interface methods
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Growable should declare grow(), getHeight(), isFullyGrown()")
    void growableMethods() {
        Class<?> cls = Util.tryLoadClass("Growable");
        if (cls == null) {
            fail("Growable interface not found — create it first.");
            return;
        }

        assertDoesNotThrow(() -> cls.getMethod("grow"),
                "Growable must declare: void grow()");
        assertDoesNotThrow(() -> cls.getMethod("getHeight"),
                "Growable must declare: double getHeight()");
        assertDoesNotThrow(() -> cls.getMethod("isFullyGrown"),
                "Growable must declare: boolean isFullyGrown()");
    }

    @Test
    @DisplayName("Treatable should declare water(), fertilize(), getHealthLevel()")
    void treatableMethods() {
        Class<?> cls = Util.tryLoadClass("Treatable");
        if (cls == null) {
            fail("Treatable interface not found — create it first.");
            return;
        }

        assertDoesNotThrow(() -> cls.getMethod("water"),
                "Treatable must declare: void water()");
        assertDoesNotThrow(() -> cls.getMethod("fertilize"),
                "Treatable must declare: void fertilize()");
        assertDoesNotThrow(() -> cls.getMethod("getHealthLevel"),
                "Treatable must declare: int getHealthLevel()");
    }

    @Test
    @DisplayName("Sellable should declare getPrice(), getLabel()")
    void sellableMethods() {
        Class<?> cls = Util.tryLoadClass("Sellable");
        if (cls == null) {
            fail("Sellable interface not found — create it first.");
            return;
        }

        assertDoesNotThrow(() -> cls.getMethod("getPrice"),
                "Sellable must declare: double getPrice()");
        assertDoesNotThrow(() -> cls.getMethod("getLabel"),
                "Sellable must declare: String getLabel()");
    }

    // ═══════════════════════════════════════════════════════════
    //  Concrete class existence and hierarchy
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Flower should exist and extend Plant")
    void flowerExtendsPlant() {
        Class<?> cls = Util.tryLoadClass("Flower");
        assertNotNull(cls,
                "Class not found: " + PKG + ".Flower — "
                + "create Flower.java extending Plant.");
        Class<?> plant = Util.loadClass("Plant");
        assertTrue(plant.isAssignableFrom(cls),
                "Flower must extend Plant.");
    }

    @Test
    @DisplayName("Flower should implement Growable and Treatable")
    void flowerImplementsInterfaces() {
        Class<?> flower = Util.tryLoadClass("Flower");
        if (flower == null) {
            fail("Flower class not found.");
            return;
        }

        Class<?> growable = Util.tryLoadClass("Growable");
        Class<?> treatable = Util.tryLoadClass("Treatable");

        if (growable != null) {
            assertTrue(growable.isAssignableFrom(flower),
                    "Flower must implement Growable.");
        }
        if (treatable != null) {
            assertTrue(treatable.isAssignableFrom(flower),
                    "Flower must implement Treatable.");
        }
    }

    @Test
    @DisplayName("Flower should have constructor (String, double)")
    void flowerConstructor() {
        Class<?> cls = Util.tryLoadClass("Flower");
        if (cls == null) {
            fail("Flower class not found.");
            return;
        }
        assertDoesNotThrow(() -> cls.getConstructor(String.class, double.class),
                "Flower must have constructor: public Flower(String name, double maxHeight)");
    }

    @Test
    @DisplayName("Succulent should exist and extend Plant")
    void succulentExtendsPlant() {
        Class<?> cls = Util.tryLoadClass("Succulent");
        assertNotNull(cls,
                "Class not found: " + PKG + ".Succulent — "
                + "create Succulent.java extending Plant.");
        Class<?> plant = Util.loadClass("Plant");
        assertTrue(plant.isAssignableFrom(cls),
                "Succulent must extend Plant.");
    }

    @Test
    @DisplayName("Succulent should implement Growable (but NOT Treatable)")
    void succulentImplementsInterfaces() {
        Class<?> succulent = Util.tryLoadClass("Succulent");
        if (succulent == null) {
            fail("Succulent class not found.");
            return;
        }

        Class<?> growable = Util.tryLoadClass("Growable");
        Class<?> treatable = Util.tryLoadClass("Treatable");

        if (growable != null) {
            assertTrue(growable.isAssignableFrom(succulent),
                    "Succulent must implement Growable.");
        }
        if (treatable != null) {
            assertFalse(treatable.isAssignableFrom(succulent),
                    "Succulent should NOT implement Treatable — it is low-maintenance.");
        }
    }

    @Test
    @DisplayName("Succulent should have constructor (String, double)")
    void succulentConstructor() {
        Class<?> cls = Util.tryLoadClass("Succulent");
        if (cls == null) {
            fail("Succulent class not found.");
            return;
        }
        assertDoesNotThrow(() -> cls.getConstructor(String.class, double.class),
                "Succulent must have constructor: public Succulent(String name, double maxHeight)");
    }

    @Test
    @DisplayName("Herb should exist and extend Plant")
    void herbExtendsPlant() {
        Class<?> cls = Util.tryLoadClass("Herb");
        assertNotNull(cls,
                "Class not found: " + PKG + ".Herb — "
                + "create Herb.java extending Plant.");
        Class<?> plant = Util.loadClass("Plant");
        assertTrue(plant.isAssignableFrom(cls),
                "Herb must extend Plant.");
    }

    @Test
    @DisplayName("Herb should implement Growable, Treatable, and Sellable")
    void herbImplementsInterfaces() {
        Class<?> herb = Util.tryLoadClass("Herb");
        if (herb == null) {
            fail("Herb class not found.");
            return;
        }

        Class<?> growable = Util.tryLoadClass("Growable");
        Class<?> treatable = Util.tryLoadClass("Treatable");
        Class<?> sellable = Util.tryLoadClass("Sellable");

        if (growable != null) {
            assertTrue(growable.isAssignableFrom(herb),
                    "Herb must implement Growable.");
        }
        if (treatable != null) {
            assertTrue(treatable.isAssignableFrom(herb),
                    "Herb must implement Treatable.");
        }
        if (sellable != null) {
            assertTrue(sellable.isAssignableFrom(herb),
                    "Herb must implement Sellable.");
        }
    }

    @Test
    @DisplayName("Herb should have constructor (String, double, double)")
    void herbConstructor() {
        Class<?> cls = Util.tryLoadClass("Herb");
        if (cls == null) {
            fail("Herb class not found.");
            return;
        }
        assertDoesNotThrow(() -> cls.getConstructor(String.class, double.class, double.class),
                "Herb must have constructor: public Herb(String name, double maxHeight, double price)");
    }
}
