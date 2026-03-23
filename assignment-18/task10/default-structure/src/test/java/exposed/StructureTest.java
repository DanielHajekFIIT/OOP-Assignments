package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed structure tests: clear failures for missing classes/signatures.
 */
class StructureTest {

    @Test
    @DisplayName("InvalidRecordException should exist and extend Exception")
    void invalidRecordExceptionStructure() {
        Class<?> cls = Util.tryLoadClass("InvalidRecordException");
        assertNotNull(cls, "Create class org.jikvict.tasks.exposed.InvalidRecordException");
        assertTrue(Exception.class.isAssignableFrom(cls), "InvalidRecordException must extend Exception");
        assertDoesNotThrow(() -> cls.getConstructor(String.class),
                "InvalidRecordException must have constructor (String)");
    }

    @Test
    @DisplayName("DuplicateRecordException should exist and extend Exception")
    void duplicateRecordExceptionStructure() {
        Class<?> cls = Util.tryLoadClass("DuplicateRecordException");
        assertNotNull(cls, "Create class org.jikvict.tasks.exposed.DuplicateRecordException");
        assertTrue(Exception.class.isAssignableFrom(cls), "DuplicateRecordException must extend Exception");
        assertDoesNotThrow(() -> cls.getConstructor(String.class),
                "DuplicateRecordException must have constructor (String)");
    }

    @Test
    @DisplayName("ExperimentRecord should be serializable and have required methods")
    void experimentRecordStructure() {
        Class<?> cls = Util.tryLoadClass("ExperimentRecord");
        assertNotNull(cls, "Create class org.jikvict.tasks.exposed.ExperimentRecord");
        assertTrue(Serializable.class.isAssignableFrom(cls),
                "ExperimentRecord must implement java.io.Serializable");

        assertDoesNotThrow(() -> cls.getConstructor(String.class, String.class, java.util.List.class, long.class),
                "ExperimentRecord must have constructor (String, String, List<Integer>, long)");

        assertDoesNotThrow(() -> cls.getMethod("getRecordId"));
        assertDoesNotThrow(() -> cls.getMethod("getAuthor"));
        assertDoesNotThrow(() -> cls.getMethod("getMeasurements"));
        assertDoesNotThrow(() -> cls.getMethod("getCreatedAtEpochMilli"));
        assertDoesNotThrow(() -> cls.getMethod("average"));
        assertDoesNotThrow(() -> cls.getMethod("addMeasurement", int.class));

        Method avg = assertDoesNotThrow(() -> cls.getMethod("average"));
        assertEquals(double.class, avg.getReturnType(), "average() must return double");
    }

    @Test
    @DisplayName("ExperimentArchive should expose persistence API")
    void experimentArchiveStructure() {
        Class<?> archive = Util.tryLoadClass("ExperimentArchive");
        Class<?> record = Util.tryLoadClass("ExperimentRecord");

        assertNotNull(archive, "Create class org.jikvict.tasks.exposed.ExperimentArchive");
        assertDoesNotThrow(() -> archive.getConstructor(), "ExperimentArchive must have public no-arg constructor");

        if (record != null) {
            assertDoesNotThrow(() -> archive.getMethod("addRecord", record),
                    "addRecord(ExperimentRecord) method is required");
        }
        assertDoesNotThrow(() -> archive.getMethod("getRecords"));
        assertDoesNotThrow(() -> archive.getMethod("findById", String.class));
        assertDoesNotThrow(() -> archive.getMethod("saveToFile", String.class));
        assertDoesNotThrow(() -> archive.getMethod("loadFromFile", String.class),
                "loadFromFile(String) static method is required");
    }

    @Test
    @DisplayName("ParallelTaskRunner should expose required methods")
    void parallelTaskRunnerStructure() {
        Class<?> cls = Util.tryLoadClass("ParallelTaskRunner");
        assertNotNull(cls, "Create class org.jikvict.tasks.exposed.ParallelTaskRunner");

        assertDoesNotThrow(() -> cls.getConstructor(int.class),
                "ParallelTaskRunner must have constructor (int threadCount)");
        assertDoesNotThrow(() -> cls.getMethod("runAll", java.util.List.class),
                "runAll(List<? extends Callable<?>>) method is required");
        assertDoesNotThrow(() -> cls.getMethod("runAllWithTimeout", java.util.List.class, long.class),
                "runAllWithTimeout(List<? extends Callable<?>>, long) method is required");
        assertDoesNotThrow(() -> cls.getMethod("shutdown"),
                "shutdown() method is required");
    }
}
