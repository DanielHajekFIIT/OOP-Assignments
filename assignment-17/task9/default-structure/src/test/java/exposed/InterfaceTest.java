package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed interface behaviour tests — students can see and run these locally.
 * <p>
 * Every test first checks (via reflection) whether the required classes and
 * interfaces exist. If they don't, the test is <b>skipped</b> (not failed),
 * so students can run the suite at any stage without errors.
 */
class InterfaceTest {

    // ═══════════════════════════════════════════════════════════
    //  Growable — Flower
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Flower.grow() should increase height by 2.5 per call")
    void flowerGrowIncreasesHeight() {
        Class<?> flowerCls = Util.tryLoadClass("Flower");
        Class<?> growable = Util.tryLoadClass("Growable");
        Assumptions.assumeTrue(flowerCls != null, "Flower class not yet created");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(growable.isAssignableFrom(flowerCls), "Flower does not implement Growable yet");

        Object flower = Util.createInstance(flowerCls,
                new Class[]{String.class, double.class}, "TestRose", 30.0);

        Util.callMethod(flower, "grow");
        double height = ((Number) Util.callMethod(flower, "getHeight")).doubleValue();
        assertEquals(2.5, height, 0.001, "After one grow(), height should be 2.5");

        Util.callMethod(flower, "grow");
        height = ((Number) Util.callMethod(flower, "getHeight")).doubleValue();
        assertEquals(5.0, height, 0.001, "After two grow() calls, height should be 5.0");
    }

    @Test
    @DisplayName("Flower should not grow beyond maxHeight")
    void flowerGrowCapped() {
        Class<?> flowerCls = Util.tryLoadClass("Flower");
        Class<?> growable = Util.tryLoadClass("Growable");
        Assumptions.assumeTrue(flowerCls != null, "Flower class not yet created");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(growable.isAssignableFrom(flowerCls), "Flower does not implement Growable yet");

        Object flower = Util.createInstance(flowerCls,
                new Class[]{String.class, double.class}, "TinyFlower", 3.0);

        // Grow many times — should cap at 3.0
        for (int i = 0; i < 10; i++) {
            Util.callMethod(flower, "grow");
        }
        double height = ((Number) Util.callMethod(flower, "getHeight")).doubleValue();
        assertEquals(3.0, height, 0.001, "Height should be capped at maxHeight (3.0)");
    }

    @Test
    @DisplayName("Flower.isFullyGrown() should return true when height >= maxHeight")
    void flowerIsFullyGrown() {
        Class<?> flowerCls = Util.tryLoadClass("Flower");
        Class<?> growable = Util.tryLoadClass("Growable");
        Assumptions.assumeTrue(flowerCls != null, "Flower class not yet created");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(growable.isAssignableFrom(flowerCls), "Flower does not implement Growable yet");

        Object flower = Util.createInstance(flowerCls,
                new Class[]{String.class, double.class}, "SmallFlower", 5.0);

        assertFalse((boolean) Util.callMethod(flower, "isFullyGrown"),
                "Should not be fully grown initially");

        // grow twice: 2.5 + 2.5 = 5.0 = maxHeight
        Util.callMethod(flower, "grow");
        Util.callMethod(flower, "grow");

        assertTrue((boolean) Util.callMethod(flower, "isFullyGrown"),
                "Should be fully grown at maxHeight");
    }

    // ═══════════════════════════════════════════════════════════
    //  Growable — Succulent
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Succulent.grow() should increase height by 0.5 per call")
    void succulentGrowIncreasesHeight() {
        Class<?> cls = Util.tryLoadClass("Succulent");
        Class<?> growable = Util.tryLoadClass("Growable");
        Assumptions.assumeTrue(cls != null, "Succulent class not yet created");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(growable.isAssignableFrom(cls), "Succulent does not implement Growable yet");

        Object succulent = Util.createInstance(cls,
                new Class[]{String.class, double.class}, "Aloe", 15.0);

        Util.callMethod(succulent, "grow");
        double height = ((Number) Util.callMethod(succulent, "getHeight")).doubleValue();
        assertEquals(0.5, height, 0.001, "After one grow(), height should be 0.5");
    }

    @Test
    @DisplayName("Succulent should not grow beyond maxHeight")
    void succulentGrowCapped() {
        Class<?> cls = Util.tryLoadClass("Succulent");
        Class<?> growable = Util.tryLoadClass("Growable");
        Assumptions.assumeTrue(cls != null, "Succulent class not yet created");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(growable.isAssignableFrom(cls), "Succulent does not implement Growable yet");

        Object succulent = Util.createInstance(cls,
                new Class[]{String.class, double.class}, "TinySucc", 1.0);

        for (int i = 0; i < 20; i++) {
            Util.callMethod(succulent, "grow");
        }
        double height = ((Number) Util.callMethod(succulent, "getHeight")).doubleValue();
        assertEquals(1.0, height, 0.001, "Height should be capped at maxHeight (1.0)");
    }

    // ═══════════════════════════════════════════════════════════
    //  Treatable — Flower
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Flower.water() should increase health by 10, capped at 100")
    void flowerWater() {
        Class<?> flowerCls = Util.tryLoadClass("Flower");
        Class<?> treatable = Util.tryLoadClass("Treatable");
        Assumptions.assumeTrue(flowerCls != null, "Flower class not yet created");
        Assumptions.assumeTrue(treatable != null, "Treatable interface not yet created");
        Assumptions.assumeTrue(treatable.isAssignableFrom(flowerCls), "Flower does not implement Treatable yet");

        Object flower = Util.createInstance(flowerCls,
                new Class[]{String.class, double.class}, "Rose", 30.0);

        int initialHealth = ((Number) Util.callMethod(flower, "getHealthLevel")).intValue();
        assertEquals(50, initialHealth, "Flower health should start at 50");

        Util.callMethod(flower, "water");
        int afterWater = ((Number) Util.callMethod(flower, "getHealthLevel")).intValue();
        assertEquals(60, afterWater, "After one water(), health should be 60");

        // Water many times — should cap at 100
        for (int i = 0; i < 20; i++) {
            Util.callMethod(flower, "water");
        }
        int capped = ((Number) Util.callMethod(flower, "getHealthLevel")).intValue();
        assertEquals(100, capped, "Health should be capped at 100");
    }

    @Test
    @DisplayName("Flower.fertilize() should increase height by 1.5")
    void flowerFertilize() {
        Class<?> flowerCls = Util.tryLoadClass("Flower");
        Class<?> treatable = Util.tryLoadClass("Treatable");
        Assumptions.assumeTrue(flowerCls != null, "Flower class not yet created");
        Assumptions.assumeTrue(treatable != null, "Treatable interface not yet created");
        Assumptions.assumeTrue(treatable.isAssignableFrom(flowerCls), "Flower does not implement Treatable yet");

        Object flower = Util.createInstance(flowerCls,
                new Class[]{String.class, double.class}, "Rose", 30.0);

        Util.callMethod(flower, "fertilize");
        double height = ((Number) Util.callMethod(flower, "getHeight")).doubleValue();
        assertEquals(1.5, height, 0.001, "After fertilize(), height should be 1.5");
    }

    // ═══════════════════════════════════════════════════════════
    //  Growable + Treatable — Herb
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Herb.grow() should increase height by 1.8 per call")
    void herbGrowIncreasesHeight() {
        Class<?> cls = Util.tryLoadClass("Herb");
        Class<?> growable = Util.tryLoadClass("Growable");
        Assumptions.assumeTrue(cls != null, "Herb class not yet created");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(growable.isAssignableFrom(cls), "Herb does not implement Growable yet");

        Object herb = Util.createInstance(cls,
                new Class[]{String.class, double.class, double.class}, "Basil", 20.0, 2.50);

        Util.callMethod(herb, "grow");
        double height = ((Number) Util.callMethod(herb, "getHeight")).doubleValue();
        assertEquals(1.8, height, 0.001, "After one grow(), height should be 1.8");
    }

    @Test
    @DisplayName("Herb.water() should increase health by 15, starting from 60")
    void herbWater() {
        Class<?> cls = Util.tryLoadClass("Herb");
        Class<?> treatable = Util.tryLoadClass("Treatable");
        Assumptions.assumeTrue(cls != null, "Herb class not yet created");
        Assumptions.assumeTrue(treatable != null, "Treatable interface not yet created");
        Assumptions.assumeTrue(treatable.isAssignableFrom(cls), "Herb does not implement Treatable yet");

        Object herb = Util.createInstance(cls,
                new Class[]{String.class, double.class, double.class}, "Mint", 20.0, 1.50);

        int initialHealth = ((Number) Util.callMethod(herb, "getHealthLevel")).intValue();
        assertEquals(60, initialHealth, "Herb health should start at 60");

        Util.callMethod(herb, "water");
        int afterWater = ((Number) Util.callMethod(herb, "getHealthLevel")).intValue();
        assertEquals(75, afterWater, "After one water(), health should be 75");
    }

    @Test
    @DisplayName("Herb.fertilize() should increase height by 2.0")
    void herbFertilize() {
        Class<?> cls = Util.tryLoadClass("Herb");
        Class<?> treatable = Util.tryLoadClass("Treatable");
        Assumptions.assumeTrue(cls != null, "Herb class not yet created");
        Assumptions.assumeTrue(treatable != null, "Treatable interface not yet created");
        Assumptions.assumeTrue(treatable.isAssignableFrom(cls), "Herb does not implement Treatable yet");

        Object herb = Util.createInstance(cls,
                new Class[]{String.class, double.class, double.class}, "Basil", 20.0, 2.50);

        Util.callMethod(herb, "fertilize");
        double height = ((Number) Util.callMethod(herb, "getHeight")).doubleValue();
        assertEquals(2.0, height, 0.001, "After fertilize(), height should be 2.0");
    }

    // ═══════════════════════════════════════════════════════════
    //  Sellable — Herb
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("Herb.getPrice() should return the price passed to constructor")
    void herbGetPrice() {
        Class<?> cls = Util.tryLoadClass("Herb");
        Class<?> sellable = Util.tryLoadClass("Sellable");
        Assumptions.assumeTrue(cls != null, "Herb class not yet created");
        Assumptions.assumeTrue(sellable != null, "Sellable interface not yet created");
        Assumptions.assumeTrue(sellable.isAssignableFrom(cls), "Herb does not implement Sellable yet");

        Object herb = Util.createInstance(cls,
                new Class[]{String.class, double.class, double.class}, "Basil", 20.0, 2.50);

        double price = ((Number) Util.callMethod(herb, "getPrice")).doubleValue();
        assertEquals(2.50, price, 0.001, "getPrice() should return 2.50");
    }

    @Test
    @DisplayName("Herb.getLabel() should return formatted label")
    void herbGetLabel() {
        Class<?> cls = Util.tryLoadClass("Herb");
        Class<?> sellable = Util.tryLoadClass("Sellable");
        Assumptions.assumeTrue(cls != null, "Herb class not yet created");
        Assumptions.assumeTrue(sellable != null, "Sellable interface not yet created");
        Assumptions.assumeTrue(sellable.isAssignableFrom(cls), "Herb does not implement Sellable yet");

        Object herb = Util.createInstance(cls,
                new Class[]{String.class, double.class, double.class}, "Basil", 20.0, 2.50);

        String label = (String) Util.callMethod(herb, "getLabel");
        assertEquals("Basil — Fresh herb, 2.50 €", label,
                "getLabel() should return: Basil — Fresh herb, 2.50 €");
    }

    // ═══════════════════════════════════════════════════════════
    //  PlantShop integration
    // ═══════════════════════════════════════════════════════════

    @Test
    @DisplayName("PlantShop should correctly categorize plants by interface")
    void plantShopCategorization() {
        Class<?> flowerCls = Util.tryLoadClass("Flower");
        Class<?> succulentCls = Util.tryLoadClass("Succulent");
        Class<?> herbCls = Util.tryLoadClass("Herb");
        Assumptions.assumeTrue(flowerCls != null, "Flower class not yet created");
        Assumptions.assumeTrue(succulentCls != null, "Succulent class not yet created");
        Assumptions.assumeTrue(herbCls != null, "Herb class not yet created");

        // Verify all interfaces exist
        Class<?> growable = Util.tryLoadClass("Growable");
        Class<?> treatable = Util.tryLoadClass("Treatable");
        Class<?> sellable = Util.tryLoadClass("Sellable");
        Assumptions.assumeTrue(growable != null, "Growable interface not yet created");
        Assumptions.assumeTrue(treatable != null, "Treatable interface not yet created");
        Assumptions.assumeTrue(sellable != null, "Sellable interface not yet created");

        org.jikvict.tasks.exposed.PlantShop shop = new org.jikvict.tasks.exposed.PlantShop("Test Shop");

        org.jikvict.tasks.exposed.Plant flower = (org.jikvict.tasks.exposed.Plant)
                Util.createInstance(flowerCls, new Class[]{String.class, double.class}, "Rose", 30.0);
        org.jikvict.tasks.exposed.Plant succulent = (org.jikvict.tasks.exposed.Plant)
                Util.createInstance(succulentCls, new Class[]{String.class, double.class}, "Aloe", 15.0);
        org.jikvict.tasks.exposed.Plant herb = (org.jikvict.tasks.exposed.Plant)
                Util.createInstance(herbCls, new Class[]{String.class, double.class, double.class}, "Basil", 20.0, 2.50);

        shop.addPlant(flower);
        shop.addPlant(succulent);
        shop.addPlant(herb);

        assertEquals(3, shop.getPlants().size(), "Shop should have 3 plants");
        assertEquals(3, shop.getGrowablePlants().size(), "All 3 plants are Growable");
        assertEquals(2, shop.getTreatablePlants().size(), "Flower and Herb are Treatable");
        assertEquals(1, shop.getSellablePlants().size(), "Only Herb is Sellable");
    }
}
