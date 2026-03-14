package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for all plants in the shop.
 * <p>
 * Pay attention to the print statements in the static initializer block,
 * instance initializer block, and constructor. When you create subclasses,
 * add similar blocks and observe the order in which they execute.
 */
public abstract class Plant {

    // ─── Static initializer ─── runs once when the class is first loaded
    static {
        System.out.println("[Plant] Static initializer block");
    }

    private String name;
    private String species;
    private int age = 0;

    // ─── Instance initializer ─── runs every time a new Plant is created,
    //     BEFORE the constructor body
    {
        System.out.println("[Plant] Instance initializer block");
    }

    /**
     * Creates a new Plant.
     *
     * @param name    the name of this plant (e.g. "Rose")
     * @param species the species category (e.g. "Flower")
     */
    public Plant(String name, String species) {
        System.out.println("[Plant] Constructor called for: " + name);
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    /**
     * Advances the plant's age by one unit.
     */
    public void ageOneYear() {
        age++;
    }

    /**
     * Returns a short textual description of this plant.
     * Subclasses should override for more detail.
     */
    public String describe() {
        return name + " (" + species + "), age " + age;
    }

    @Override
    public String toString() {
        return describe();
    }
}
