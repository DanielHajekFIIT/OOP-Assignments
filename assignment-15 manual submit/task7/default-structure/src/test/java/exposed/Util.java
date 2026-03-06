package exposed;

import org.jikvict.tasks.exposed.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Utility helpers shared by exposed tests.
 */
public class Util {

    private static final String PACKAGE = "org.jikvict.tasks.exposed";

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
     * Calls a no-arg method on an instance via reflection and returns the result.
     */
    public static Object callMethod(Object instance, String methodName) {
        try {
            Method method = instance.getClass().getMethod(methodName);
            return method.invoke(instance);
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to call " + methodName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Loads a class from the exposed package.
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(PACKAGE + "." + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }
    }
}
