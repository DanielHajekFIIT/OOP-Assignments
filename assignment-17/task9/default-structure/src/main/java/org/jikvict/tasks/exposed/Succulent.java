package org.jikvict.tasks.exposed;

public class Succulent extends Plant implements Growable {

    // ─── Static initializer ─── runs once when the class is first loaded
    static {
        System.out.println("[Succulent] Static initializer block");
    }

    private double height;
    private double maxHeight;

    // ─── Instance initializer ─── runs every time a new Plant is created,
    //     BEFORE the constructor body
    {
        System.out.println("[Succulent] Instance initializer block");
    }

    public Succulent(String name, double maxHeight) {
        super(name, "Succulent");
        System.out.println("[Succulent] Constructor called for: " + name);
        this.maxHeight = maxHeight;
    }

    @Override
    public void grow() {
        height = Math.min(height  + 0.5, maxHeight);
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isFullyGrown() {
        return height >= maxHeight;
    }
}
