package org.jikvict.tasks.exposed;

public class Flower extends Plant implements Growable, Treatable {

    private static final int MAX_HEALTH = 100;
    // ─── Static initializer ─── runs once when the class is first loaded
    static {
        System.out.println("[Flower] Static initializer block");
    }

    private double height;
    private double maxHeight;
    private int health = 50;

    // ─── Instance initializer ─── runs every time a new Plant is created,
    //     BEFORE the constructor body
    {
        System.out.println("[Flower] Instance initializer block");
    }

    public Flower(String name, double maxHeight) {
        super(name, "Flower");
        System.out.println("[Flower] Constructor called for: " + name);
        this.maxHeight = maxHeight;
    }

    @Override
    public void grow() {
        height = Math.min(height  + 2.5, maxHeight);
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isFullyGrown() {
        return height >= maxHeight;
    }

    @Override
    public void water() {
        health = Math.min(health + 10, MAX_HEALTH);
    }

    @Override
    public void fertilize() {
        height = Math.min(height  + 1.5, maxHeight);
    }

    @Override
    public int  getHealthLevel() {
        return health;
    }
}
