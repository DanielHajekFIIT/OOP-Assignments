package exposed;

import org.jikvict.tasks.exposed.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility helpers shared by exposed tests.
 * Uses reflection to work even when student classes are not yet implemented.
 */
public class Util {

    private static final String PACKAGE = "org.jikvict.tasks.exposed";

    // ──────────────────── Class loading ────────────────────

    /**
     * Tries to load a class from the exposed package. Returns null if not found.
     */
    public static Class<?> tryLoadClass(String className) {
        try {
            return Class.forName(PACKAGE + "." + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Loads a class from the exposed package. Throws RuntimeException if not found.
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(PACKAGE + "." + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }
    }

    // ──────────────────── Character creation ────────────────────

    public static Warrior createWarrior(String name, int health, int attack, int armor) {
        return new Warrior(name, health, attack, armor);
    }

    public static Mage createMage(String name, int health, int attack, int mana) {
        return new Mage(name, health, attack, mana);
    }

    public static Archer createArcher(String name, int health, int attack, int arrows) {
        return new Archer(name, health, attack, arrows);
    }

    // ──────────────────── Reflection helpers ────────────────────

    /**
     * Creates an instance via a constructor with the given parameter types and arguments.
     * Unwraps InvocationTargetException for checked exceptions.
     */
    public static Object createInstance(Class<?> clazz, Class<?>[] paramTypes, Object... args) {
        try {
            Constructor<?> ctor = clazz.getConstructor(paramTypes);
            return ctor.newInstance(args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            throw new RuntimeException(cause);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Calls a no-arg method on an instance via reflection.
     */
    public static Object callMethod(Object instance, String methodName) {
        try {
            Method method = instance.getClass().getMethod(methodName);
            return method.invoke(instance);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            throw new RuntimeException(cause);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call " + methodName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Calls a method with specified parameter types on an instance via reflection.
     */
    public static Object callMethod(Object instance, String methodName,
                                    Class<?>[] paramTypes, Object... args) {
        try {
            Method method = instance.getClass().getMethod(methodName, paramTypes);
            return method.invoke(instance, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            throw new RuntimeException(cause);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call " + methodName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Creates a BasePotion via reflection. Assumes valid parameters.
     */
    public static Object createBasePotion(String name, String desc, int healingPower, int duration) {
        Class<?> cls = loadClass("BasePotion");
        return createInstance(cls,
                new Class[]{String.class, String.class, int.class, int.class},
                name, desc, healingPower, duration);
    }

    /**
     * Creates a HealingBoostDecorator via reflection.
     */
    public static Object createHealingBoostDecorator(Object potion, int bonusHealing) {
        Class<?> cls = loadClass("HealingBoostDecorator");
        return createInstance(cls, new Class[]{Potion.class, int.class}, potion, bonusHealing);
    }

    /**
     * Creates a DurationExtenderDecorator via reflection.
     */
    public static Object createDurationExtenderDecorator(Object potion, int multiplier) {
        Class<?> cls = loadClass("DurationExtenderDecorator");
        return createInstance(cls, new Class[]{Potion.class, int.class}, potion, multiplier);
    }

    /**
     * Creates an Inventory via reflection.
     */
    public static Object createInventory(int capacity) {
        Class<?> cls = loadClass("Inventory");
        return createInstance(cls, new Class[]{int.class}, capacity);
    }
}
