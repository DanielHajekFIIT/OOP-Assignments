package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Exposed tests for thread-based parallel task execution.
 */
class ParallelTaskRunnerTest {

    private boolean prerequisites() {
        return Util.tryLoadClass("ParallelTaskRunner") != null;
    }

    @Test
    @DisplayName("runAll should return results in submission order")
    void runAllOrder() {
        assumeTrue(prerequisites());

        Class<?> runnerCls = Util.loadClass("ParallelTaskRunner");
        Object runner = Util.createInstance(runnerCls, new Class[]{int.class}, 4);

        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(() -> 11);
        tasks.add(() -> 22);
        tasks.add(() -> 33);

        try {
            List<?> out = (List<?>) Util.call(runner, "runAll", new Class[]{List.class}, tasks);
            assertEquals(List.of(11, 22, 33), out, "Results must preserve input order");
        } finally {
            Util.call(runner, "shutdown");
        }
    }

    @Test
    @DisplayName("runAll should execute tasks in parallel (time-based sanity check)")
    void runAllParallelTiming() {
        assumeTrue(prerequisites());

        Class<?> runnerCls = Util.loadClass("ParallelTaskRunner");
        Object runner = Util.createInstance(runnerCls, new Class[]{int.class}, 4);

        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final int value = i;
            tasks.add(() -> {
                Thread.sleep(300);
                return value;
            });
        }

        long start = System.currentTimeMillis();
        try {
            List<?> out = (List<?>) Util.call(runner, "runAll", new Class[]{List.class}, tasks);
            long elapsed = System.currentTimeMillis() - start;

            assertEquals(4, out.size());
            assertTrue(elapsed < 900,
                    "Expected parallel execution. Elapsed=" + elapsed + "ms is too slow for 4x300ms tasks on 4 threads.");
        } finally {
            Util.call(runner, "shutdown");
        }
    }

    @Test
    @DisplayName("runAllWithTimeout should return only completed results")
    void runAllWithTimeout() {
        assumeTrue(prerequisites());

        Class<?> runnerCls = Util.loadClass("ParallelTaskRunner");
        Object runner = Util.createInstance(runnerCls, new Class[]{int.class}, 3);

        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(() -> {
            Thread.sleep(50);
            return 1;
        });
        tasks.add(() -> {
            Thread.sleep(500);
            return 2;
        });
        tasks.add(() -> {
            Thread.sleep(700);
            return 3;
        });

        try {
            List<?> out = (List<?>) Util.call(runner, "runAllWithTimeout", new Class[]{List.class, long.class}, tasks, 200L);
            assertTrue(out.contains(1), "Fast task result should be present");
            assertFalse(out.contains(2), "Slow task result should not be present under timeout");
            assertFalse(out.contains(3), "Slow task result should not be present under timeout");
        } finally {
            Util.call(runner, "shutdown");
        }
    }
}
