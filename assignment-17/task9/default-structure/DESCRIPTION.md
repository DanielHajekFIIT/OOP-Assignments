# Task 9 — Interfaces & Java Initialization Order: The Plant Shop

### Understanding Interfaces, Inheritance, and How Java Brings Your Code to Life

In this task you will build a **plant shop** system where different plants (flowers, succulents,
herbs) each have unique growing and treatment behaviours defined through **interfaces you design
yourself**. Along the way you will explore **how Java actually initializes classes and objects** —
the exact order in which static blocks, instance blocks, field initializers, and constructors
execute, including across an inheritance hierarchy.

This task has two parts:

| Part | What you will learn |
|---|---|
| **Part 1 — Interfaces & the Plant Shop** | Design and implement your own interfaces; have classes implement multiple interfaces; understand interface contracts |
| **Part 2 — Initialization Order Playground** | Discover — by running code and observing output — the precise order Java uses to load classes, initialize fields, execute initializer blocks, and call constructors |

---

## Provided files

All files live in package `org.jikvict.tasks.exposed`:

| File | Status | Description |
|---|---|---|
| `Plant.java` | **Provided** | Abstract base class for all plants — contains static/instance initializer blocks and prints initialization messages |
| `PlantShop.java` | **Provided** | A simple shop that holds plants — students add plants and interact with them |
| `InitializationPlayground.java` | **Provided** | A playground class for experimenting with initialization order — run it, read the output, answer the questions |
| `Main.java` | **Provided** | Entry point — uncomment sections as you implement |

> **Do not** change method signatures or access modifiers in provided files.
> You **may** add private helper methods if you need them.

---

## Part 1 — Interfaces & the Plant Shop

You will **create three interfaces from scratch** and then build concrete plant classes that
implement them. The interfaces are **not** provided — you must create the `.java` files yourself
based on the descriptions below.

### Interface 1: `Growable`

Any plant that can grow must implement this interface.

| Method | Return type | Description |
|---|---|---|
| `grow()` | `void` | Advances the plant's growth by one step. How much it grows depends on the concrete class. |
| `getHeight()` | `double` | Returns the plant's current height in centimetres. |
| `isFullyGrown()` | `boolean` | Returns `true` if the plant has reached its maximum height. |

### Interface 2: `Treatable`

Some plants need special care (watering, fertilizing). Not every plant needs treatment —
succulents, for example, are low-maintenance.

| Method | Return type | Description |
|---|---|---|
| `water()` | `void` | Waters the plant, improving its health. |
| `fertilize()` | `void` | Fertilizes the plant, boosting its growth rate. |
| `getHealthLevel()` | `int` | Returns the plant's health as a value from 0 to 100. |

### Interface 3: `Sellable`

Items that can be sold in the shop.

| Method | Return type | Description |
|---|---|---|
| `getPrice()` | `double` | Returns the selling price. |
| `getLabel()` | `String` | Returns a display label for the shop (e.g. `"Basil — Fresh herb, 2.50 €"`). |

---

### Concrete classes to implement

You must create the following classes, all extending `Plant` and implementing the appropriate
interfaces. Each class should have **its own static initializer block** and **instance initializer
block** that print messages (just like `Plant` does) — this will help you see the full
initialization chain when you create instances.

#### `Flower` extends `Plant` implements `Growable`, `Treatable`

A decorative plant that grows and needs regular care.

| Constructor | `Flower(String name, double maxHeight)` |
|---|---|
| `grow()` | Increases height by `2.5` cm per call, up to `maxHeight` |
| `getHeight()` | Returns current height |
| `isFullyGrown()` | `true` when height ≥ maxHeight |
| `water()` | Increases health by `10`, capped at `100` |
| `fertilize()` | Increases height by an extra `1.5` cm (respecting maxHeight) |
| `getHealthLevel()` | Returns current health (starts at `50`) |

> The `Flower` constructor should call `super(name, "Flower")` and print:
> `[Flower] Constructor called for: {name}`

#### `Succulent` extends `Plant` implements `Growable`

A hardy, slow-growing plant that does **not** need special treatment.

| Constructor | `Succulent(String name, double maxHeight)` |
|---|---|
| `grow()` | Increases height by `0.5` cm per call, up to `maxHeight` |
| `getHeight()` | Returns current height |
| `isFullyGrown()` | `true` when height ≥ maxHeight |

> The `Succulent` constructor should call `super(name, "Succulent")` and print:
> `[Succulent] Constructor called for: {name}`

#### `Herb` extends `Plant` implements `Growable`, `Treatable`, `Sellable`

An edible plant that grows, needs care, **and** can be sold.

| Constructor | `Herb(String name, double maxHeight, double price)` |
|---|---|
| `grow()` | Increases height by `1.8` cm per call, up to `maxHeight` |
| `getHeight()` | Returns current height |
| `isFullyGrown()` | `true` when height ≥ maxHeight |
| `water()` | Increases health by `15`, capped at `100` |
| `fertilize()` | Increases height by an extra `2.0` cm (respecting maxHeight) |
| `getHealthLevel()` | Returns current health (starts at `60`) |
| `getPrice()` | Returns the price passed to the constructor |
| `getLabel()` | Returns `"{name} — Fresh herb, {price} €"` (e.g. `"Basil — Fresh herb, 2.50 €"`) |

> The `Herb` constructor should call `super(name, "Herb")` and print:
> `[Herb] Constructor called for: {name}`

> **Tip for `getLabel()`:** use `String.format("%.2f", price)` to format the price to 2 decimal places.

---

### Using `PlantShop`

The provided `PlantShop` class has these methods:

| Method | Description |
|---|---|
| `addPlant(Plant plant)` | Adds a plant to the shop's inventory |
| `getPlants()` | Returns an unmodifiable list of all plants |
| `getGrowablePlants()` | Returns a list of all plants that implement `Growable` |
| `getTreatablePlants()` | Returns a list of all plants that implement `Treatable` |
| `getSellablePlants()` | Returns a list of all plants that implement `Sellable` |
| `growAll()` | Calls `grow()` on every `Growable` plant |
| `waterAll()` | Calls `water()` on every `Treatable` plant |

---

## Part 2 — Initialization Order Playground

Open `InitializationPlayground.java` and **run it**. Read the printed output carefully and
answer the questions in the comments. This section has no "code to implement" — it is a
**learning exercise** where you observe and reason about the output.

### What you will discover

When you create an instance of a subclass, Java executes initialization in this order:

1. **Static initializer blocks and static field assignments** of the **parent** class (only once,
   when the class is first loaded)
2. **Static initializer blocks and static field assignments** of the **child** class (only once)
3. **Instance field initializers** and **instance initializer blocks** of the **parent** class
   (in the order they appear in the source)
4. **Parent constructor** body
5. **Instance field initializers** and **instance initializer blocks** of the **child** class
6. **Child constructor** body

Creating a **second** instance of the same class skips steps 1–2 (static initialization
already happened).

### Experiments to try

After running the playground, try these experiments in `Main.java` or directly in the
playground:

1. Create a `Flower`, then a `Succulent`, then another `Flower`. Which static blocks run
   the second time?
2. What happens if a child class has a field that calls a method before the constructor runs?
3. Does the order change if you move the instance initializer block above or below a field
   declaration?
4. What do you observe about interface default methods (if you add one to your interfaces)?

---

## Summary

| What to create | Type |
|---|---|
| `Growable.java` | Interface (you write it) |
| `Treatable.java` | Interface (you write it) |
| `Sellable.java` | Interface (you write it) |
| `Flower.java` | Class extending `Plant`, implementing `Growable`, `Treatable` |
| `Succulent.java` | Class extending `Plant`, implementing `Growable` |
| `Herb.java` | Class extending `Plant`, implementing `Growable`, `Treatable`, `Sellable` |
