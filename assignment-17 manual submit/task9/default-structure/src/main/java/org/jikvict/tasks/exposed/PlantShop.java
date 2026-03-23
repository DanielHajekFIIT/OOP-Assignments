package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple plant shop that manages an inventory of plants.
 * <p>
 * Use the {@code getGrowablePlants()}, {@code getTreatablePlants()}, and
 * {@code getSellablePlants()} methods to see how {@code instanceof} checks
 * work with interfaces — a single plant can appear in multiple lists if it
 * implements multiple interfaces.
 */
public class PlantShop {

    private final String shopName;
    private final List<Plant> plants = new ArrayList<>();

    public PlantShop(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    /**
     * Adds a plant to the shop inventory.
     */
    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    /**
     * Returns an unmodifiable view of all plants in the shop.
     */
    public List<Plant> getPlants() {
        return Collections.unmodifiableList(plants);
    }

    /**
     * Returns all plants that implement the Growable interface.
     * Uses reflection-free instanceof checks.
     */
    public List<Plant> getGrowablePlants() {
        List<Plant> result = new ArrayList<>();
        for (Plant p : plants) {
            try {
                Class<?> growable = Class.forName("org.jikvict.tasks.exposed.Growable");
                if (growable.isInstance(p)) {
                    result.add(p);
                }
            } catch (ClassNotFoundException e) {
                // Growable interface not yet created by student
            }
        }
        return result;
    }

    /**
     * Returns all plants that implement the Treatable interface.
     */
    public List<Plant> getTreatablePlants() {
        List<Plant> result = new ArrayList<>();
        for (Plant p : plants) {
            try {
                Class<?> treatable = Class.forName("org.jikvict.tasks.exposed.Treatable");
                if (treatable.isInstance(p)) {
                    result.add(p);
                }
            } catch (ClassNotFoundException e) {
                // Treatable interface not yet created by student
            }
        }
        return result;
    }

    /**
     * Returns all plants that implement the Sellable interface.
     */
    public List<Plant> getSellablePlants() {
        List<Plant> result = new ArrayList<>();
        for (Plant p : plants) {
            try {
                Class<?> sellable = Class.forName("org.jikvict.tasks.exposed.Sellable");
                if (sellable.isInstance(p)) {
                    result.add(p);
                }
            } catch (ClassNotFoundException e) {
                // Sellable interface not yet created by student
            }
        }
        return result;
    }

    /**
     * Calls grow() on every plant that implements Growable.
     */
    public void growAll() {
        for (Plant p : getGrowablePlants()) {
            try {
                p.getClass().getMethod("grow").invoke(p);
            } catch (Exception e) {
                System.out.println("Could not grow " + p.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Calls water() on every plant that implements Treatable.
     */
    public void waterAll() {
        for (Plant p : getTreatablePlants()) {
            try {
                p.getClass().getMethod("water").invoke(p);
            } catch (Exception e) {
                System.out.println("Could not water " + p.getName() + ": " + e.getMessage());
            }
        }
    }
}
