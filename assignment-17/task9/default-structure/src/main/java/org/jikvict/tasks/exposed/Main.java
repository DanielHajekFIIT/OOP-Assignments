package org.jikvict.tasks.exposed;

/**
 * Entry point for the Plant Shop application.
 * <p>
 * Uncomment sections as you implement each part of the task.
 */
public class Main {

    public static void main(String[] args) {

        // ═══════════════════════ Part 1: Interfaces & Plant Shop ═══════════════════════

        // TODO: Uncomment the code below after implementing
        //       Growable, Treatable, Sellable, Flower, Succulent, and Herb.


        System.out.println("=== Creating Plants ===");
        System.out.println("Watch the initialization order in the output!\n");

        Flower rose = new Flower("Rose", 30.0);
        System.out.println();
        Succulent aloe = new Succulent("Aloe Vera", 15.0);
        System.out.println();
        Herb basil = new Herb("Basil", 20.0, 2.50);
        System.out.println();

        // ─── PlantShop usage ───
        PlantShop shop = new PlantShop("Green Thumb");
        shop.addPlant(rose);
        shop.addPlant(aloe);
        shop.addPlant(basil);

        System.out.println("=== Shop Inventory ===");
        for (Plant p : shop.getPlants()) {
            System.out.println("  " + p.describe());
        }

        System.out.println("\n=== Growable Plants ===");
        for (Plant p : shop.getGrowablePlants()) {
            System.out.println("  " + p.getName());
        }

        System.out.println("\n=== Treatable Plants ===");
        for (Plant p : shop.getTreatablePlants()) {
            System.out.println("  " + p.getName());
        }

        System.out.println("\n=== Sellable Plants ===");
        for (Plant p : shop.getSellablePlants()) {
            System.out.println("  " + p.getName());
        }

        // ─── Growing ───
        System.out.println("\n=== Growing all plants ===");
        shop.growAll();
        shop.growAll();
        shop.growAll();
        System.out.println("Rose height:  " + rose.getHeight() + " cm");
        System.out.println("Aloe height:  " + aloe.getHeight() + " cm");
        System.out.println("Basil height: " + basil.getHeight() + " cm");

        // ─── Watering ───
        System.out.println("\n=== Watering all plants ===");
        shop.waterAll();
        System.out.println("Rose health:  " + rose.getHealthLevel());
        System.out.println("Basil health: " + basil.getHealthLevel());

        // ─── Selling herbs ───
        System.out.println("\n=== Herb for sale ===");
        System.out.println(basil.getLabel());

        // ═══════════════════════ Part 2: Initialization Playground ═══════════════════════

        System.out.println("\nTo explore initialization order, run InitializationPlayground.java directly.");
    }
}
