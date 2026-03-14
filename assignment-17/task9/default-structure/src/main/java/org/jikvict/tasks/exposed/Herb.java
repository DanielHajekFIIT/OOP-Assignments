package org.jikvict.tasks.exposed;

public class Herb extends Plant implements Growable, Treatable, Sellable {

    private static final int MAX_HEALTH = 100;
    // ─── Static initializer ─── runs once when the class is first loaded
    static {
        System.out.println("[Herb] Static initializer block");
    }

    private double height;
    private double maxHeight;
    private int health = 60;
    private double price;

    // ─── Instance initializer ─── runs every time a new Plant is created,
    //     BEFORE the constructor body
    {
        System.out.println("[Herb] Instance initializer block");
    }

    public Herb(String name, double maxHeight, double price) {
        super(name, "Herb");
        this.maxHeight = maxHeight;
        this.price = price;
    }

    @Override
    public void grow() {
        height = Math.min(height  + 1.8, maxHeight);
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
        health = Math.min(health + 15, MAX_HEALTH);
    }

    @Override
    public void fertilize() {
        height = Math.min(height + 2.0, maxHeight);
    }

    @Override
    public int getHealthLevel() {
        return health;
    }

    public double getPrice() {
        return price;
    }

    public String getLabel() {
        return super.getName() + " — Fresh herb, " + String.format("%.2f", price) + " €";
    }
}
