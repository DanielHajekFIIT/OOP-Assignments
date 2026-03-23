package exposed;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection utilities for exposed tests.
 * Tests stay runnable even when students implement classes incrementally.
 */
public final class Util {

    private static final String PACKAGE = "org.jikvict.tasks.exposed";

    private Util() {
    }

    public static Class<?> tryLoadClass(String simpleName) {
        try {
            return Class.forName(PACKAGE + "." + simpleName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> loadClass(String simpleName) {
        try {
            return Class.forName(PACKAGE + "." + simpleName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + simpleName, e);
        }
    }

    public static Object createInstance(Class<?> type, Class<?>[] argTypes, Object... args) {
        try {
            Constructor<?> ctor = type.getConstructor(argTypes);
            return ctor.newInstance(args);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate " + type.getSimpleName(), e);
        }
    }

    public static InvokeResult tryCreateInstance(Class<?> type, Class<?>[] argTypes, Object... args) {
        try {
            Constructor<?> ctor = type.getConstructor(argTypes);
            Object value = ctor.newInstance(args);
            return new InvokeResult(value, null);
        } catch (InvocationTargetException e) {
            return new InvokeResult(null, e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate " + type.getSimpleName(), e);
        }
    }

    public static Object call(Object target, String methodName) {
        try {
            Method m = target.getClass().getMethod(methodName);
            return m.invoke(target);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Cannot call " + methodName, e);
        }
    }

    public static Object call(Object target, String methodName, Class<?>[] argTypes, Object... args) {
        try {
            Method m = target.getClass().getMethod(methodName, argTypes);
            return m.invoke(target, args);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Cannot call " + methodName, e);
        }
    }

    public static InvokeResult tryCall(Object target, String methodName, Class<?>[] argTypes, Object... args) {
        try {
            Method m = target.getClass().getMethod(methodName, argTypes);
            Object value = m.invoke(target, args);
            return new InvokeResult(value, null);
        } catch (InvocationTargetException e) {
            return new InvokeResult(null, e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Cannot call " + methodName, e);
        }
    }

    public static Object callStatic(Class<?> type, String methodName, Class<?>[] argTypes, Object... args) {
        try {
            Method m = type.getMethod(methodName, argTypes);
            return m.invoke(null, args);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Cannot call static " + methodName, e);
        }
    }

    public record InvokeResult(Object value, Throwable exception) {
        public boolean threw() {
            return exception != null;
        }
    }
}
