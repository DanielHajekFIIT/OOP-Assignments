package org.jikvict.tasks.exposed;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws GameException {
        Potion base = new BasePotion("Elixir", "Restores health", 20, 30);

        // Wrap with healing boost (+15)
        Potion boosted = new HealingBoostDecorator(base, 15);
        System.out.println(boosted.getHealingPower()); // 35
        System.out.println(boosted.getDescription());  // "Restores health, Healing Boost +15"

        // Stack duration extender on top (x3)
        Potion superPotion = new DurationExtenderDecorator(boosted, 3);
        System.out.println(superPotion.getHealingPower()); // 35
        System.out.println(superPotion.getDuration());      // 90
        System.out.println(superPotion.getDescription());
        // "Restores health, Healing Boost +15, Duration x3"


        // Apply to a character (heals 20 from base + 15 from boost = 35 total)
        GameCharacter warrior = new Warrior("Arthur", 100, 10, 45);
        warrior.takeDamage(50); // 50 HP remaining
        System.out.println(superPotion.getHealingPower());
        superPotion.apply(warrior);
        System.out.println(warrior.getHealth()); // 85

        // This should throw InvalidPotionException
        try {
            Potion bad = new BasePotion("Poison", "Oops", -5, 30);
        } catch (GameException e) {
            System.out.println(e.getMessage()); // something about negative healing
        }

        // Applying to a dead character should throw CharacterDeadException
        GameCharacter ghost = new Warrior("Ghost", 100, 10, 5);
        ghost.takeDamage(100); // dead
        try {
            base.apply(ghost);
        } catch (GameException e) {
            System.out.println("Cannot heal the dead!");
        }

        Inventory<Potion> potionBag = new Inventory<>(3);
        potionBag.add(superPotion);
        potionBag.add(boosted);
        System.out.println(potionBag.size());    // 2
        System.out.println(potionBag.isFull());  // false

        Inventory<Weapon> weaponRack = new Inventory<>(1);
        Weapon sword = new Weapon("Sword", 67, 100);
        Weapon axe = new Weapon("Axe", 420, 100);
        weaponRack.add(sword);
        try {
            weaponRack.add(axe);
        } catch (GameException e) {
            System.out.println(e.getMessage());// throws InventoryFullException!
        }

        Inventory<Potion> bag = new Inventory<>(10);
        Potion weakPotion = new BasePotion("Weak Potion", "", 10, 67);
        Potion strongPotion = new BasePotion("Strong Potion", "", 50, 67);
        Potion megaPotion = new BasePotion("Mega Elixir", "", 100, 67);
        bag.add(weakPotion);    // healing 10
        bag.add(strongPotion);  // healing 50
        bag.add(megaPotion);    // healing 100

        // Find all potions stronger than 30 HP
        List<Potion> strong = bag.findAll(p -> p.getHealingPower() > 30);
        System.out.println(strong.size()); // 2

        // Find the first potion named "Mega Elixir"
        Optional<Potion> mega = bag.findFirst(p -> p.getName().equals("Mega Elixir"));
        mega.ifPresent(p -> System.out.println("Found: " + p.getName()));

        // Print all potion descriptions
        bag.applyToAll(p -> System.out.println(p.getDescription()));
    }
}
