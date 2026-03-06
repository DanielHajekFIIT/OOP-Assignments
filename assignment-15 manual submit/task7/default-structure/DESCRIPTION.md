# Task 7 — Design Patterns: Strategy, Observer & Singleton

### Extending the RPG Character System from Task 6

In this task you will extend the RPG character system with three fundamental **design patterns**.
The base classes (`GameCharacter`, `Weapon`, `Warrior`, `Mage`, `Archer`) are provided **fully
implemented** — your job is to add the patterns on top.

| Pattern | What you will learn |
|---|---|
| **Strategy** | Encapsulate interchangeable algorithms behind a common interface; switch behaviour at runtime without modifying the client code |
| **Observer** | Establish a one-to-many dependency so that when an event occurs, all interested parties are notified automatically |
| **Singleton** | Guarantee a class has exactly one instance and provide a global access point to it |

---

## Provided files

All files live in package `org.jikvict.tasks.exposed`:

| File | Status | Description |
|---|---|---|
| `GameCharacter.java` | Implemented + **TODOs** | Base class — strategy integration methods marked TODO |
| `Weapon.java` | Fully implemented | Weapon class from Task 6 |
| `Warrior.java` | Fully implemented | Subclass from Task 6 |
| `Mage.java` | Fully implemented | Subclass from Task 6 |
| `Archer.java` | Fully implemented | Subclass from Task 6 |
| `AttackStrategy.java` | **Complete interface** | Strategy pattern — no changes needed |
| `MeleeAttackStrategy.java` | Skeleton — **TODO** | Concrete strategy |
| `RangedAttackStrategy.java` | Skeleton — **TODO** | Concrete strategy |
| `MagicAttackStrategy.java` | Skeleton — **TODO** | Concrete strategy |
| `CombatEventListener.java` | **Complete interface** | Observer pattern — no changes needed |
| `CombatEventManager.java` | Skeleton — **TODO** | Subject (manages observers) |
| `BattleLogger.java` | Skeleton — **TODO** | Concrete observer |
| `GameWorld.java` | Skeleton — **TODO** | Singleton |

> **Do not** change method signatures or access modifiers that are already present.
> You **may** add private helper methods if you need them.

---

## What you must implement

### 1. Strategy Pattern — Attack Strategies

The **Strategy** pattern lets you define a family of algorithms, put each of them into a
separate class, and make their objects interchangeable.

#### `AttackStrategy` (interface — already provided)

A functional interface with a single method:

```java
int execute(int baseAttackPower);
```

#### `MeleeAttackStrategy` implements `AttackStrategy`

| Method | Behaviour |
|---|---|
| `execute(int baseAttackPower)` | Returns `(int)(baseAttackPower * 1.5)` |

#### `RangedAttackStrategy` implements `AttackStrategy`

| Method | Behaviour |
|---|---|
| `execute(int baseAttackPower)` | Returns `baseAttackPower + 15` |

#### `MagicAttackStrategy` implements `AttackStrategy`

| Method | Behaviour |
|---|---|
| `execute(int baseAttackPower)` | Returns `baseAttackPower * 3` |

#### Changes to `GameCharacter`

| New element | Details |
|---|---|
| Field `attackStrategy` | `private AttackStrategy`, initially `null` |
| `setAttackStrategy(AttackStrategy)` | Stores the strategy |
| `getAttackStrategy()` | Returns the current strategy (may be `null`) |
| `executeStrategy()` | If a strategy is set → `attackStrategy.execute(baseAttackPower)`. Otherwise → `calculateDamage()` |

---

### 2. Observer Pattern — Combat Events

The **Observer** pattern defines a subscription mechanism that lets multiple objects listen for
and react to events happening in another object.

#### `CombatEventListener` (interface — already provided)

| Method | Description |
|---|---|
| `void onDamageDealt(String attackerName, String targetName, int damage)` | Called when damage is dealt |
| `void onCharacterDeath(String characterName)` | Called when a character dies |
| `void onLevelUp(String characterName, int newLevel)` | Called when a character levels up |

#### `CombatEventManager` (the Subject)

| Field (private) | Type | Notes |
|---|---|---|
| `listeners` | `List<CombatEventListener>` | starts as empty `ArrayList` |

| Method | Behaviour |
|---|---|
| `addListener(CombatEventListener)` | Adds a listener to the list |
| `removeListener(CombatEventListener)` | Removes the listener from the list |
| `getListeners()` | Returns an **unmodifiable** view of the listeners (`Collections.unmodifiableList(...)`) |
| `notifyDamageDealt(String, String, int)` | Calls `onDamageDealt` on every registered listener |
| `notifyCharacterDeath(String)` | Calls `onCharacterDeath` on every registered listener |
| `notifyLevelUp(String, int)` | Calls `onLevelUp` on every registered listener |

#### `BattleLogger` implements `CombatEventListener`

| Field (private) | Type | Notes |
|---|---|---|
| `log` | `List<String>` | starts as empty `ArrayList` |

| Method | Behaviour |
|---|---|
| `onDamageDealt(attacker, target, damage)` | Appends `"DAMAGE: {attacker} dealt {damage} damage to {target}"` |
| `onCharacterDeath(name)` | Appends `"DEATH: {name} has been defeated"` |
| `onLevelUp(name, level)` | Appends `"LEVEL UP: {name} reached level {level}"` |
| `getLog()` | Returns an **unmodifiable** view of the log |
| `clearLog()` | Clears all entries |

---

### 3. Singleton Pattern — GameWorld

The **Singleton** pattern ensures that a class has only one instance, while providing a global
access point to it.

#### `GameWorld`

| Field (private) | Type | Notes |
|---|---|---|
| `instance` | `static GameWorld` | the single instance |
| `party` | `List<GameCharacter>` | characters in the world |
| `eventManager` | `CombatEventManager` | global event manager |

| Element | Behaviour |
|---|---|
| **Constructor** | `private` — initialises `party` (empty `ArrayList`) and `eventManager` (new instance) |
| `static getInstance()` | Returns the singleton. Creates it on first call (lazy initialisation) |
| `static resetInstance()` | Sets `instance` to `null` (used by tests to ensure a clean state) |
| `addCharacter(GameCharacter)` | Adds the character to the party |
| `removeCharacter(String name)` | Removes the character whose name matches |
| `findCharacter(String name)` | Returns the character with the given name, or `null` if not found |
| `getParty()` | Returns an **unmodifiable** view of the party |
| `getEventManager()` | Returns the event manager |

---

## Quick example

```java
// ── Strategy ──
GameCharacter warrior = new Warrior("Arthur", 100, 10, 6);
warrior.setAttackStrategy(new MeleeAttackStrategy());
System.out.println(warrior.executeStrategy()); // 15  ((int)(10 * 1.5))

warrior.setAttackStrategy(new MagicAttackStrategy());
System.out.println(warrior.executeStrategy()); // 30  (10 * 3)

// ── Observer ──
CombatEventManager manager = new CombatEventManager();
BattleLogger logger = new BattleLogger();
manager.addListener(logger);

manager.notifyDamageDealt("Arthur", "Goblin", 15);
System.out.println(logger.getLog().get(0));
// "DAMAGE: Arthur dealt 15 damage to Goblin"

// ── Singleton ──
GameWorld world = GameWorld.getInstance();
world.addCharacter(warrior);
System.out.println(world.getParty().size()); // 1
System.out.println(GameWorld.getInstance() == world); // true
```

---

## Submission

Take your whole project, remove unnecessary files (like `.idea` folder or build folder) and zip it.

The final zip file should contain the `default-structure` folder at the root.
