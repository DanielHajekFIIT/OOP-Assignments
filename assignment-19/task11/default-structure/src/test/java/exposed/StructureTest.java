package exposed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exposed structure tests: clear failures for missing classes/signatures.
 */
class StructureTest {

    @Test
    @DisplayName("StudentRecord should exist and have required constructor")
    void studentRecordStructure() {
        Class<?> cls = Util.tryLoadClass("StudentRecord");
        assertNotNull(cls, "Create class org.jikvict.tasks.exposed.StudentRecord");

        assertDoesNotThrow(() -> cls.getConstructor(String.class, String.class, String.class, int.class, double.class),
                "StudentRecord must have constructor (String, String, String, int, double)");

        assertDoesNotThrow(() -> cls.getMethod("getStudentId"));
        assertDoesNotThrow(() -> cls.getMethod("getName"));
        assertDoesNotThrow(() -> cls.getMethod("getCourse"));
        assertDoesNotThrow(() -> cls.getMethod("getYear"));
        assertDoesNotThrow(() -> cls.getMethod("getGrade"));

        Method grade = assertDoesNotThrow(() -> cls.getMethod("getGrade"));
        assertEquals(double.class, grade.getReturnType(), "getGrade() must return double");

        Method year = assertDoesNotThrow(() -> cls.getMethod("getYear"));
        assertEquals(int.class, year.getReturnType(), "getYear() must return int");
    }

    @Test
    @DisplayName("StudentDatabase should exist and have required constructor")
    void studentDatabaseStructure() {
        Class<?> dbCls = Util.tryLoadClass("StudentDatabase");
        assertNotNull(dbCls, "Create class org.jikvict.tasks.exposed.StudentDatabase");

        assertDoesNotThrow(() -> dbCls.getConstructor(java.util.List.class),
                "StudentDatabase must have constructor (List<StudentRecord>)");
    }

    @Test
    @DisplayName("StudentDatabase should expose all required query methods")
    void studentDatabaseMethods() {
        Class<?> dbCls = Util.tryLoadClass("StudentDatabase");
        assertNotNull(dbCls, "Create class org.jikvict.tasks.exposed.StudentDatabase");

        assertDoesNotThrow(() -> dbCls.getMethod("getStudentNamesByCourse", String.class),
                "getStudentNamesByCourse(String) method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("groupByCourse"),
                "groupByCourse() method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("countStudentsByYear"),
                "countStudentsByYear() method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("getTopStudents", int.class),
                "getTopStudents(int) method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("getAverageGradePerCourse"),
                "getAverageGradePerCourse() method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("filterByGradeRange", double.class, double.class),
                "filterByGradeRange(double, double) method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("getHighestGradeStudent"),
                "getHighestGradeStudent() method is required");
        assertDoesNotThrow(() -> dbCls.getMethod("countPassingStudents", double.class),
                "countPassingStudents(double) method is required");
    }

    @Test
    @DisplayName("Query methods should have correct return types")
    void queryMethodReturnTypes() throws Exception {
        Class<?> dbCls = Util.tryLoadClass("StudentDatabase");
        assertNotNull(dbCls, "Create class org.jikvict.tasks.exposed.StudentDatabase");

        assertEquals(java.util.List.class,
                dbCls.getMethod("getStudentNamesByCourse", String.class).getReturnType(),
                "getStudentNamesByCourse must return List");
        assertEquals(java.util.Map.class,
                dbCls.getMethod("groupByCourse").getReturnType(),
                "groupByCourse must return Map");
        assertEquals(java.util.Map.class,
                dbCls.getMethod("countStudentsByYear").getReturnType(),
                "countStudentsByYear must return Map");
        assertEquals(java.util.List.class,
                dbCls.getMethod("getTopStudents", int.class).getReturnType(),
                "getTopStudents must return List");
        assertEquals(java.util.Map.class,
                dbCls.getMethod("getAverageGradePerCourse").getReturnType(),
                "getAverageGradePerCourse must return Map");
        assertEquals(java.util.List.class,
                dbCls.getMethod("filterByGradeRange", double.class, double.class).getReturnType(),
                "filterByGradeRange must return List");
        assertEquals(java.util.Optional.class,
                dbCls.getMethod("getHighestGradeStudent").getReturnType(),
                "getHighestGradeStudent must return Optional");
        assertEquals(long.class,
                dbCls.getMethod("countPassingStudents", double.class).getReturnType(),
                "countPassingStudents must return long");
    }
}
