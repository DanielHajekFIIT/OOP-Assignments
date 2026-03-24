package org.jikvict.tasks.exposed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Took me some
public class ParallelTaskRunner {
    private final ExecutorService executor;

    public ParallelTaskRunner (int threadCount) {
        // Thread count must be positive for the thread pool
        this.executor = Executors.newFixedThreadPool(Math.max(1, threadCount));
    }

    public List<?> runAll(List<? extends Callable<?>> tasks) {
        // This is allowed since IllegalArgumentException is unchecked
        if (tasks == null) throw new IllegalArgumentException("Task list cannot be null");

        // Submit phase - store futures in order
        List<Future<?>> futures = new ArrayList<>();
        for (Callable<?> task : tasks) {
            // submit() accepts a callable and returns a future
            futures.add(executor.submit(task));
        }

        // Collect phase - wait for each result in order
        List<Object> results = new ArrayList<>();
        // Checked exceptions are allowed to be thrown without declaration as they just rethrow an unchecked one
        for (Future<?> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return results;
    }

    public List<?> runAllWithTimeout(List<? extends Callable<?>> tasks, long timeoutMillis) {
        if (tasks == null) throw new IllegalArgumentException("Task list cannot be null");
        if (timeoutMillis <= 0) throw new IllegalArgumentException("Timeout must be positive");

        // Pretty much the same code as runAll()
        // Submit phase - store futures in order
        List<Future<?>> futures = new ArrayList<>();
        for (Callable<?> task : tasks) {
            futures.add(executor.submit(task));
        }

        // Collect phase - only collect results that completed within timeout
        List<Object> results = new ArrayList<>();
        // Added deadline
        long deadline = System.currentTimeMillis() + timeoutMillis;
        for (Future<?> future : futures) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) break;
            try {
                results.add(future.get(remaining, TimeUnit.MILLISECONDS));
            } catch (TimeoutException e) {
                future.cancel(true); // cancel the timed out task
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return results;
    }

    // Ensure proper executor shutdown
    public void shutdown() {
        executor.shutdown();
    }
}
