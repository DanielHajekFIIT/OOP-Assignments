package org.jikvict.tasks.exposed;

/**
 * A functional interface for filtering items in an {@code Inventory}.
 * <p>
 * Because this interface has exactly one abstract method, it can be
 * implemented using a <b>lambda expression</b>.
 *
 * @param <T> the type of item to test
 */
@FunctionalInterface
public interface ItemFilter<T> {

    /**
     * Tests whether the given item matches the filter condition.
     *
     * @param item the item to test
     * @return {@code true} if the item matches, {@code false} otherwise
     */
    boolean test(T item);
}
