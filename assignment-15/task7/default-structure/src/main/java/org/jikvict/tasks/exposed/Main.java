package org.jikvict.tasks.exposed;

/**
 * Demonstration of the three design patterns applied to the RPG system.
 * <p>
 * Once you have implemented all the TODO classes, uncomment the code below
 * and run it to see the patterns in action.
 */
public class Main {

    public static void main(String[] args) {
        // TODO: After implementing all patterns, uncomment the code below.


        // ═══════════════════════ Strategy Pattern ═══════════════════════

        GameCharacter warrior = new Warrior("Arthur", 100, 10, 6);
        System.out.println("=== Strategy Pattern ===");

        // No strategy — falls back to calculateDamage()
        System.out.println("Default damage:     " + warrior.executeStrategy());

        // Switch to melee strategy
        warrior.setAttackStrategy(new MeleeAttackStrategy());
        System.out.println("Melee strategy:     " + warrior.executeStrategy());

        // Switch to ranged strategy at runtime
        warrior.setAttackStrategy(new RangedAttackStrategy());
        System.out.println("Ranged strategy:    " + warrior.executeStrategy());

        // Switch to magic strategy
        warrior.setAttackStrategy(new MagicAttackStrategy());
        System.out.println("Magic strategy:     " + warrior.executeStrategy());

        // ═══════════════════════ Observer Pattern ═══════════════════════

        System.out.println("\n=== Observer Pattern ===");
        CombatEventManager eventManager = new CombatEventManager();
        BattleLogger logger = new BattleLogger();
        eventManager.addListener(logger);

        eventManager.notifyDamageDealt("Arthur", "Goblin", 15);
        eventManager.notifyCharacterDeath("Goblin");
        eventManager.notifyLevelUp("Arthur", 2);

        System.out.println("Battle log:");
        for (String entry : logger.getLog()) {
            System.out.println("  " + entry);
        }

        // ═══════════════════════ Singleton Pattern ═══════════════════════

        System.out.println("\n=== Singleton Pattern ===");
        GameWorld world = GameWorld.getInstance();
        world.addCharacter(warrior);
        world.addCharacter(new Mage("Gandalf", 80, 15, 50));
        world.addCharacter(new Archer("Legolas", 90, 12, 20));

        System.out.println("Party size: " + world.getParty().size());
        System.out.println("Same instance? " + (GameWorld.getInstance() == world));

        GameCharacter found = world.findCharacter("Gandalf");
        if (found != null) {
            System.out.println("Found: " + found);
        }
    }
}
