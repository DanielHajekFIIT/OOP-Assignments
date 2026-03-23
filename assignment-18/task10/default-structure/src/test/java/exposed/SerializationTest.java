package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Exposed behavioral tests for serialization and archive features.
 * Skipped when required classes do not exist yet.
 */
class SerializationTest {

    private boolean prerequisites() {
        return Util.tryLoadClass("ExperimentRecord") != null
                && Util.tryLoadClass("ExperimentArchive") != null
                && Util.tryLoadClass("InvalidRecordException") != null
                && Util.tryLoadClass("DuplicateRecordException") != null;
    }

    @Test
    @DisplayName("A valid record should compute average correctly")
    void averageWorks() {
        assumeTrue(prerequisites());

        Class<?> recordCls = Util.loadClass("ExperimentRecord");
        List<Integer> values = new ArrayList<>(List.of(10, 20, 30, 40));

        Object record = Util.createInstance(recordCls,
                new Class[]{String.class, String.class, List.class, long.class},
                "R-001", "Alice", values, 1700000000000L);

        double avg = ((Number) Util.call(record, "average")).doubleValue();
        assertEquals(25.0, avg, 0.0001);
    }

    @Test
    @DisplayName("Invalid record data should throw InvalidRecordException")
    void invalidRecordThrows() {
        assumeTrue(prerequisites());

        Class<?> recordCls = Util.loadClass("ExperimentRecord");
        Class<?> exCls = Util.loadClass("InvalidRecordException");

        Util.InvokeResult result = Util.tryCreateInstance(recordCls,
                new Class[]{String.class, String.class, List.class, long.class},
                " ", "Bob", List.of(1, 2), 1700000000000L);

        assertTrue(result.threw(), "Constructor should throw for blank record id");
        assertTrue(exCls.isInstance(result.exception()), "Expected InvalidRecordException");
    }

    @Test
    @DisplayName("Archive should save to file and load back")
    void archivePersistenceRoundTrip() throws Exception {
        assumeTrue(prerequisites());

        Class<?> recordCls = Util.loadClass("ExperimentRecord");
        Class<?> archiveCls = Util.loadClass("ExperimentArchive");

        Object archive = Util.createInstance(archiveCls, new Class[]{});

        Object r1 = Util.createInstance(recordCls,
                new Class[]{String.class, String.class, List.class, long.class},
                "R-100", "Dr. Quinn", List.of(5, 10, 15), 1700000000010L);

        Object r2 = Util.createInstance(recordCls,
                new Class[]{String.class, String.class, List.class, long.class},
                "R-200", "Dr. Singh", List.of(3, 9, 12, 24), 1700000000020L);

        Util.call(archive, "addRecord", new Class[]{recordCls}, r1);
        Util.call(archive, "addRecord", new Class[]{recordCls}, r2);

        Path tmp = Files.createTempFile("task10-archive-", ".bin");
        try {
            Util.call(archive, "saveToFile", new Class[]{String.class}, tmp.toString());

            Object loaded = Util.callStatic(archiveCls, "loadFromFile", new Class[]{String.class}, tmp.toString());
            List<?> loadedRecords = (List<?>) Util.call(loaded, "getRecords");
            assertEquals(2, loadedRecords.size(), "Loaded archive should contain two records");

            Optional<?> found = (Optional<?>) Util.call(loaded, "findById", new Class[]{String.class}, "R-200");
            assertTrue(found.isPresent(), "Record R-200 should exist after loading");

            double avg = ((Number) Util.call(found.get(), "average")).doubleValue();
            assertEquals(12.0, avg, 0.0001, "Average should remain consistent after serialization");
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}
