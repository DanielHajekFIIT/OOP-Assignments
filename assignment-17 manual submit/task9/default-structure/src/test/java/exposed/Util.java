package exposed;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utility helpers shared by exposed tests.
 * Uses reflection so tests work even when student classes are not yet created.
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

    // ──────────────────── Instance creation ────────────────────

    /**
     * Creates an instance via a constructor with the given parameter types and arguments.
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

    // ──────────────────── Reflection helpers ────────────────────

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
     * Checks if a class has a method with the given name and parameter types.
     */
    public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            clazz.getMethod(methodName, paramTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
