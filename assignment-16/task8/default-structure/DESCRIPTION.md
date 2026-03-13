# Task 8 — Potions, Inventory & Error Handling

### Decorator Pattern, Custom Exceptions, Generics & Lambda Expressions

Your RPG world from previous tasks is growing. Characters can fight, equip weapons, and
level up — but what happens when a warrior drinks a healing potion mid-battle? What if
someone tries to stuff a 51st item into a backpack that only holds 50? And what if a player
attempts to heal a character that's already dead?

In this task you will extend the RPG system with **four new concepts** that are central to
real-world Java development. You are given the core character classes fully implemented
(from Task 6). Everything else you build **from scratch** — the description below guides you
through the problems you need to solve and the design approaches you should use.

> **Important:** Do not change method signatures or access modifiers in the provided files.

---

## 1. The Potion Brewing System — Decorator Pattern

### The problem

Your game needs potions — items that characters can drink to restore health. A basic healing
potion restores a fixed amount of HP. But in any good RPG, potions can be *enhanced*: a
healer might brew a potion that heals more than usual, or an alchemist might extend how long
its effects last.

You could create separate classes for every combination (`StrongPotion`, `LongPotion`,
`StrongLongPotion`, …), but this quickly becomes unmanageable. Every time you add a new
enhancement, you would need to create a class for every possible combination with existing
enhancements. With 3 enhancements that's 7 combinations. With 5, it's 31.

### The approach — Decorator Pattern

The **Decorator** pattern solves this by *wrapping* objects. Instead of subclassing for every
variation, you create small, focused wrappers that each add one piece of behaviour. These
wrappers can be freely composed at runtime — stack as many as you like, in any order.

The key insight: a decorated potion **is still a potion**. Every wrapper implements the same
`Potion` interface, holds a reference to the potion it wraps, and delegates to the wrapped
potion for behaviour it doesn't modify.

### What to build

You are given the `Potion` interface (see the provided file). You need to build:

**A base potion** that implements `Potion` directly. This is the simplest, undecorated potion.
It stores a name, description, healing power, and duration. When applied to a character, it
heals them by its healing power. The constructor should accept these four values
(name, description, healingPower, duration) and validate that healing power is not
negative and duration is positive — if validation fails, throw the appropriate custom
exception (see Section 2).

**An abstract decorator** that also implements `Potion`. It holds a reference to the wrapped
potion (the potion being decorated) and delegates all `Potion` methods to the wrapped potion
by default. Concrete decorators extend this class and override only the methods they need to
modify.

**Two concrete decorators:**

*Healing Boost* — wraps any potion and adds bonus healing. Its `getHealingPower()` returns
the wrapped potion's healing power **plus** the bonus. Its `getDescription()` appends
`, Healing Boost +{bonus}` to the wrapped description. When applied, it first delegates to
the wrapped potion (which does the base healing), then heals the target by the additional
bonus amount.

*Duration Extender* — wraps any potion and multiplies its duration. Its `getDuration()`
returns the wrapped potion's duration **times** the multiplier. Its `getDescription()` appends
`, Duration x{multiplier}` to the wrapped description. Applying it simply delegates to the
wrapped potion (duration is metadata — applying the potion doesn't need special behaviour
for duration).

Both decorators delegate `getName()` to the wrapped potion unchanged.

### Example of stacking

```java
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
GameCharacter warrior = new Warrior("Arthur", 100, 10, 6);
warrior.takeDamage(50); // 50 HP remaining
superPotion.apply(warrior);
System.out.println(warrior.getHealth()); // 85
```

---

## 2. When Things Go Wrong — Custom Exceptions

### The problem

What happens when a player tries to brew a potion with negative healing power? Or applies a
potion to a dead character? Or tries to add an item to a full backpack? Java's built-in
exceptions (`NullPointerException`, `IllegalArgumentException`) are too generic — they tell
you what went wrong technically, but not what went wrong in your game's logic.

### The approach — Exception hierarchy

Create a **checked exception hierarchy** rooted in a single base class. Checked exceptions
(extending `Exception` rather than `RuntimeException`) force callers to explicitly handle
error cases with `try-catch` or `throws` — this is exactly what you want when the error is
recoverable and the caller should decide how to react.

You are given `GameException` as the base class. Build three specific exceptions that extend
it:

- One for **invalid potion creation** — thrown when a potion is constructed with invalid
  parameters (negative healing power or non-positive duration)
- One for a **full inventory** — thrown when trying to add an item to an inventory that has
  reached its capacity
- One for acting on a **dead character** — thrown when trying to apply a potion (or perform
  any action) on a character that is no longer alive

Each exception should accept a message `String` in its constructor and pass it to the parent.

### Where exceptions are thrown

The base potion constructor validates its parameters and throws the invalid-potion exception
if they are out of range. The potion's `apply` method checks whether the target is alive and
throws the dead-character exception if not. The inventory's `add` method checks capacity and
throws the full-inventory exception if the container is at its limit.

### Example

```java
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
    potion.apply(ghost);
} catch (GameException e) {
    System.out.println("Cannot heal the dead!");
}
```

---

## 3. The Inventory — Generics

### The problem

Characters need a place to store their items — potions, weapons, and potentially other types
of objects in the future. You could create `PotionInventory`, `WeaponInventory`, etc., but
that means duplicating the same storage logic for every item type.

### The approach — Generic classes

A **generic class** lets you write the storage logic once and parameterise it with a type.
`Inventory<Potion>` stores potions, `Inventory<Weapon>` stores weapons — same code, full
type safety at compile time.

### What to build

A generic `Inventory<T>` class with a fixed capacity. It should support:

- **Adding** items — but throw the full-inventory exception if at capacity
- **Removing** and **checking** for items
- **Querying** the size, whether it's full, whether it's empty
- **Getting all items** as an unmodifiable list (preventing external code from bypassing the
  capacity limit)

The constructor takes the capacity as an `int`.

### Example

```java
Inventory<Potion> potionBag = new Inventory<>(3);
potionBag.add(healthPotion);
potionBag.add(boostedPotion);
System.out.println(potionBag.size());    // 2
System.out.println(potionBag.isFull());  // false

Inventory<Weapon> weaponRack = new Inventory<>(1);
weaponRack.add(sword);
// weaponRack.add(axe); // throws InventoryFullException!
```

---

## 4. Searching and Processing Items — Lambda Expressions

### The problem

A character's inventory might hold many potions. How do you find all potions with healing
power above 30? Or find the first potion whose name contains "Elixir"? You could write a
separate method for each query, but that creates rigid, single-purpose code.

### The approach — Functional interfaces and lambdas

You are given `ItemFilter<T>` — a **functional interface** with a single `boolean test(T item)`
method. Because it has exactly one abstract method, it can be implemented concisely using
a **lambda expression**.

Add three methods to your inventory that leverage functional programming:

**findAll** — accepts an `ItemFilter<T>` and returns a new list containing only the items
that match the filter.

**findFirst** — accepts an `ItemFilter<T>` and returns an `Optional<T>` — the first matching
item, or empty if nothing matches.

**applyToAll** — accepts a `java.util.function.Consumer<T>` and executes the given action
on every item in the inventory.

### Example

```java
Inventory<Potion> bag = new Inventory<>(10);
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
```

---

## Naming conventions

All your classes should be in package `org.jikvict.tasks.exposed`. Choose class names that
clearly describe their purpose — the tests will look for names that follow standard Java
conventions and match the concepts described above (e.g., a class implementing the decorator
for healing boost should be named `HealingBoostDecorator`).

The exception classes should be named after the condition they represent: `InvalidPotionException`,
`InventoryFullException`, `CharacterDeadException`.

The base potion class should be named `BasePotion`. The abstract decorator should be named
`PotionDecorator`. The duration-extending decorator should be named `DurationExtenderDecorator`.
The generic inventory class should be named `Inventory`.

---

## Design hints

- The abstract decorator should have a **protected** field for the wrapped potion, so concrete
  decorators can access it directly.
- Think carefully about how `apply()` works in decorators — the healing boost decorator needs
  to both delegate to the wrapped potion **and** add its own extra healing.
- The `Inventory` class has nothing potion-specific in it. It works with any type `T`.
- `findAll` and `findFirst` should use the `ItemFilter<T>` interface, not `java.util.function.Predicate`.
  This is intentional — you should understand how functional interfaces work, not just use
  the standard library ones.
- Your exceptions are **checked** — this means methods that throw them must declare it in
  their signature, and callers must handle them.

---

## Submission

Take your whole project, remove unnecessary files (like `.idea` folder or build folder) and zip it.

The final zip file should contain the `default-structure` folder at the root.
