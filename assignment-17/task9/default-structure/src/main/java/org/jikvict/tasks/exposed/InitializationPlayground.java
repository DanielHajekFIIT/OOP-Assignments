package org.jikvict.tasks.exposed;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║             INITIALIZATION ORDER PLAYGROUND                         ║
 * ║                                                                     ║
 * ║  Run this class and read the output carefully.                      ║
 * ║  Then answer the questions at the bottom of this file.              ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 * <p>
 * This class demonstrates the order in which Java initializes classes
 * and objects. It uses the Plant hierarchy you are building.
 * <p>
 * Before running: make sure you have created at least the Flower and
 * Succulent classes with their static/instance initializer blocks.
 */
public class InitializationPlayground {

    // ─── A helper class to illustrate field initialization ───

    static class FieldTracker {
        private final String label;

        FieldTracker(String label) {
            System.out.println("  >> FieldTracker created: " + label);
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // ─── A parent class with initialization instrumentation ───

    static class Parent {
        static FieldTracker staticField = new FieldTracker("Parent.staticField");

        static {
            System.out.println("  [Parent] Static initializer block");
        }

        FieldTracker instanceField = new FieldTracker("Parent.instanceField");

        {
            System.out.println("  [Parent] Instance initializer block");
        }

        Parent() {
            System.out.println("  [Parent] Constructor body");
        }
    }

    // ─── A child class with its own initialization ───

    static class Child extends Parent {
        static FieldTracker staticField = new FieldTracker("Child.staticField");

        static {
            System.out.println("  [Child] Static initializer block");
        }

        FieldTracker instanceField = new FieldTracker("Child.instanceField");

        {
            System.out.println("  [Child] Instance initializer block");
        }

        Child() {
            System.out.println("  [Child] Constructor body");
        }
    }

    // ─── Main ───

    public static void main(String[] args) {

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 1: Creating first Child instance");
        System.out.println("═══════════════════════════════════════════════════");
        Child c1 = new Child();

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 2: Creating second Child instance");
        System.out.println("═══════════════════════════════════════════════════");
        Child c2 = new Child();

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 3: Creating a Plant subclass");
        System.out.println("  (Requires Flower class to be implemented)");
        System.out.println("═══════════════════════════════════════════════════");
        try {
            Class<?> flowerClass = Class.forName("org.jikvict.tasks.exposed.Flower");
            Object flower = flowerClass
                    .getConstructor(String.class, double.class)
                    .newInstance("Rose", 30.0);
            System.out.println("  Created: " + flower);
        } catch (ClassNotFoundException e) {
            System.out.println("  [!] Flower class not found — implement it first!");
        } catch (Exception e) {
            System.out.println("  [!] Error creating Flower: " + e.getMessage());
        }

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 4: Creating another Plant subclass");
        System.out.println("  (Requires Succulent class to be implemented)");
        System.out.println("═══════════════════════════════════════════════════");
        try {
            Class<?> succulentClass = Class.forName("org.jikvict.tasks.exposed.Succulent");
            Object succulent = succulentClass
                    .getConstructor(String.class, double.class)
                    .newInstance("Aloe", 15.0);
            System.out.println("  Created: " + succulent);
        } catch (ClassNotFoundException e) {
            System.out.println("  [!] Succulent class not found — implement it first!");
        } catch (Exception e) {
            System.out.println("  [!] Error creating Succulent: " + e.getMessage());
        }

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 5: Creating a second Flower");
        System.out.println("  Watch — which static blocks run again?");
        System.out.println("═══════════════════════════════════════════════════");
        try {
            Class<?> flowerClass = Class.forName("org.jikvict.tasks.exposed.Flower");
            Object flower2 = flowerClass
                    .getConstructor(String.class, double.class)
                    .newInstance("Tulip", 25.0);
            System.out.println("  Created: " + flower2);
        } catch (ClassNotFoundException e) {
            System.out.println("  [!] Flower class not found — implement it first!");
        } catch (Exception e) {
            System.out.println("  [!] Error creating Flower: " + e.getMessage());
        }

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  QUESTIONS — answer these in comments or on paper");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println();
        System.out.println("  Q1: In Experiment 1, what ran first — Parent's or");
        System.out.println("      Child's static initializer?");
        System.out.println();
        // A1: Parent's ran first
        System.out.println("  Q2: In Experiment 2, did the static blocks run");
        System.out.println("      again? Why or why not?");
        System.out.println();
        // A2: No, a static block is allocated only once when the class is loaded.
        System.out.println("  Q3: When creating a Flower (Experiment 3), what");
        System.out.println("      was the order: Plant static → Flower static");
        System.out.println("      → Plant instance → Plant constructor →");
        System.out.println("      Flower instance → Flower constructor?");
        System.out.println();
        // A3: Exactly as printed? (I did not understand this question)
        System.out.println("  Q4: In Experiment 5, did Plant's static block run");
        System.out.println("      again when creating the second Flower? Why?");
        System.out.println();
        // A4: No, because of the same reasoning as A2 (Plant and Flower static blocks ran during Experiment 3)
        System.out.println("  Q5: What would happen if Flower's constructor did");
        System.out.println("      NOT call super(name, \"Flower\")?");
        // A5: Flower constructor could not be called, because Flower extends Plant and by definition
        // has to call parent constructor
    }
}
