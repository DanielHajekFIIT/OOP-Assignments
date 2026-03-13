package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Inventory<T> {
    private int capacity;
    private int size;
    private List<T> items;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>(capacity);
    }

    public void add(T item) throws InventoryFullException {
        if (isFull()) {
            throw new InventoryFullException("Inventory is full!");
        }
        items.add(item);
        size++;
    }

    public void remove(T item) {
        items.remove(item);
        size--;
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    // Querying methods
    public int size() {
        return size;
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getCapacity() {
        return capacity;
    }

    // Lambda expressions
    public List<T> findAll(ItemFilter<T> filter) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (filter.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public Optional<T> findFirst(ItemFilter<T> filter) {
        Optional<T> result = Optional.empty();
        for (T item : items) {
            if (filter.test(item)) {
                result = Optional.of(item);
                break;
            }
        }
        return result;
    }

    public void applyToAll(Consumer<T> consumer) {
        for (T item : items) {
            consumer.accept(item);
        }
    }
}
