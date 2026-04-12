package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Exposed behavioral tests for stream-based query methods.
 * Skipped when required classes do not exist yet.
 */
class StreamOperationsTest {

    private boolean prerequisites() {
        return Util.tryLoadClass("StudentRecord") != null
                && Util.tryLoadClass("StudentDatabase") != null;
    }

    private Object makeRecord(Class<?> cls, String id, String name, String course, int year, double grade) {
        return Util.createInstance(cls,
                new Class[]{String.class, String.class, String.class, int.class, double.class},
                id, name, course, year, grade);
    }

    private Object buildDatabase(Class<?> dbCls, Class<?> recCls) {
        List<Object> records = new ArrayList<>();
        records.add(makeRecord(recCls, "S001", "Alice", "Math", 2, 85.0));
        records.add(makeRecord(recCls, "S002", "Bob", "Math", 1, 72.5));
        records.add(makeRecord(recCls, "S003", "Charlie", "Physics", 2, 91.0));
        records.add(makeRecord(recCls, "S004", "Diana", "Math", 3, 68.0));
        records.add(makeRecord(recCls, "S005", "Eve", "Physics", 1, 95.5));
        records.add(makeRecord(recCls, "S006", "Frank", "Chemistry", 2, 55.0));
        return Util.createInstance(dbCls, new Class[]{List.class}, records);
    }

    @Test
    @DisplayName("getStudentNamesByCourse should return sorted names for a given course")
    void studentNamesByCourse() {
        assumeTrue(prerequisites());

        Class<?> recCls = Util.loadClass("StudentRecord");
        Class<?> dbCls = Util.loadClass("StudentDatabase");
        Object db = buildDatabase(dbCls, recCls);

        List<?> names = (List<?>) Util.call(db, "getStudentNamesByCourse",
                new Class[]{String.class}, "Math");

        assertEquals(List.of("Alice", "Bob", "Diana"), names,
                "Names should be alphabetically sorted");
    }

    @Test
    @DisplayName("getTopStudents should return top n students by grade descending")
    void topStudents() {
        assumeTrue(prerequisites());

        Class<?> recCls = Util.loadClass("StudentRecord");
        Class<?> dbCls = Util.loadClass("StudentDatabase");
        Object db = buildDatabase(dbCls, recCls);

        List<?> top3 = (List<?>) Util.call(db, "getTopStudents",
                new Class[]{int.class}, 3);

        assertEquals(3, top3.size(), "Should return exactly 3 students");

        double first = ((Number) Util.call(top3.get(0), "getGrade")).doubleValue();
        double second = ((Number) Util.call(top3.get(1), "getGrade")).doubleValue();
        double third = ((Number) Util.call(top3.get(2), "getGrade")).doubleValue();

        assertEquals(95.5, first, 0.0001);
        assertEquals(91.0, second, 0.0001);
        assertEquals(85.0, third, 0.0001);
    }

    @Test
    @DisplayName("countPassingStudents should count students at or above passing grade")
    void countPassing() {
        assumeTrue(prerequisites());

        Class<?> recCls = Util.loadClass("StudentRecord");
        Class<?> dbCls = Util.loadClass("StudentDatabase");
        Object db = buildDatabase(dbCls, recCls);

        long count = ((Number) Util.call(db, "countPassingStudents",
                new Class[]{double.class}, 70.0)).longValue();

        assertEquals(4, count, "4 students have grade >= 70.0");
    }

    @Test
    @DisplayName("filterByGradeRange should return students within range")
    void filterByRange() {
        assumeTrue(prerequisites());

        Class<?> recCls = Util.loadClass("StudentRecord");
        Class<?> dbCls = Util.loadClass("StudentDatabase");
        Object db = buildDatabase(dbCls, recCls);

        List<?> filtered = (List<?>) Util.call(db, "filterByGradeRange",
                new Class[]{double.class, double.class}, 70.0, 90.0);

        assertEquals(2, filtered.size(),
                "2 students have grade in [70.0, 90.0]: Alice (85.0) and Bob (72.5)");
    }

    @Test
    @DisplayName("getHighestGradeStudent should return the student with maximum grade")
    void highestGrade() {
        assumeTrue(prerequisites());

        Class<?> recCls = Util.loadClass("StudentRecord");
        Class<?> dbCls = Util.loadClass("StudentDatabase");
        Object db = buildDatabase(dbCls, recCls);

        Optional<?> highest = (Optional<?>) Util.call(db, "getHighestGradeStudent");

        assertTrue(highest.isPresent(), "Should return a student");
        double grade = ((Number) Util.call(highest.get(), "getGrade")).doubleValue();
        assertEquals(95.5, grade, 0.0001, "Eve has the highest grade (95.5)");
    }
}
