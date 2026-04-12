# Task 11 — Streams & Data Management: The Student Records System

### Processing, filtering, and aggregating data with the Java Stream API

In this task you will build a **student records system** for a university administration office.
The office keeps track of students, the courses they take, their year of study, and their grades.
Your job is to implement a set of **query methods** that extract useful information from the raw
data — all powered by **Java Streams**.

---

## Learning goals

By completing this task, you will practice:

- Creating and using Java Streams from collections
- Filtering elements with `filter()`
- Transforming data with `map()`
- Sorting with `sorted()` and custom comparators
- Collecting results with `collect()`, `Collectors.toList()`, `Collectors.groupingBy()`, and more
- Aggregating data with `count()`, `max()`, `min()`, and averaging collectors
- Limiting results with `limit()`
- Working with `Optional` as a stream terminal operation result
- Designing clean APIs with defensive copying

---

## Important note about implementation freedom

The tests in this task **only verify the results**, not how you achieve them. You are free to
combine whichever stream operations you find most natural. There are often multiple valid ways
to produce the same output — experiment with different approaches!

For example, to compute an average you could use `Collectors.averagingDouble()`, or
`mapToDouble().average()`, or even `reduce()`. All are valid as long as the result is correct.

---

## What to implement

All classes must be in package `org.jikvict.tasks.exposed`.

### 1) `StudentRecord`

A data class representing one student's enrollment in a course.

Required class properties:

- Class name: `StudentRecord`
- Constructor:
    - `StudentRecord(String studentId, String name, String course, int year, double grade)`
    - Throws `IllegalArgumentException` on invalid input

Required behavior:

- `studentId` and `name` must be non-null and not blank
- `course` must be non-null and not blank
- `year` must be between 1 and 6 (inclusive)
- `grade` must be between 0.0 and 100.0 (inclusive)
- Methods:
    - `String getStudentId()`
    - `String getName()`
    - `String getCourse()`
    - `int getYear()`
    - `double getGrade()`

### 2) `StudentDatabase`

The main query engine. Holds a collection of student records and exposes stream-based
query methods.

Required class properties:

- Class name: `StudentDatabase`
- Constructor:
    - `StudentDatabase(java.util.List<StudentRecord> records)`
    - Must defensively copy the input list (changes to the original list must not affect the database)
    - Throws `IllegalArgumentException` if `records` is null

Required methods:

#### `List<String> getStudentNamesByCourse(String course)`

Returns the **names** of all students enrolled in the given course, sorted **alphabetically**.
Returns an empty list if no students are found.

> Useful operations: `filter`, `map`, `sorted`, `collect`

#### `Map<String, List<StudentRecord>> groupByCourse()`

Returns a map where each key is a course name and each value is the list of student records
for that course.

> Useful operations: `collect` with `Collectors.groupingBy`

#### `Map<Integer, Long> countStudentsByYear()`

Returns a map where each key is a year and each value is the number of students in that year.

> Useful operations: `collect` with `Collectors.groupingBy` and `Collectors.counting`

#### `List<StudentRecord> getTopStudents(int n)`

Returns the top `n` students by grade in **descending** order. If `n` is larger than the total
number of students, return all students sorted by grade descending. If `n` is zero or negative,
return an empty list.

> Useful operations: `sorted`, `limit`, `collect`

#### `Map<String, Double> getAverageGradePerCourse()`

Returns a map where each key is a course name and each value is the average grade for that course.

> Useful operations: `collect` with `Collectors.groupingBy` and `Collectors.averagingDouble`

#### `List<StudentRecord> filterByGradeRange(double min, double max)`

Returns all students whose grade is between `min` and `max` (inclusive).

> Useful operations: `filter`, `collect`

#### `Optional<StudentRecord> getHighestGradeStudent()`

Returns the student with the highest grade. If multiple students share the highest grade, any
one of them may be returned. Returns an empty `Optional` if the database is empty.

> Useful operations: `max`

#### `long countPassingStudents(double passingGrade)`

Returns the number of students with a grade greater than or equal to `passingGrade`.

> Useful operations: `filter`, `count`

---

## Constraints and expectations

- You are encouraged to use the Java Stream API for all query methods.
- Do not use external libraries — only the standard Java SDK.
- Avoid leaking mutable internal collections (defensive copying and unmodifiable views).

---

## Suggested implementation strategy

1. Implement `StudentRecord` first with full validation and getters.
2. Implement `StudentDatabase` with the constructor and defensive copying.
3. Start with simpler query methods (`filterByGradeRange`, `countPassingStudents`).
4. Move to grouping and aggregation methods (`groupByCourse`, `countStudentsByYear`, `getAverageGradePerCourse`).
5. Finish with `getTopStudents` and `getHighestGradeStudent`.
6. Experiment with alternative stream pipelines — try different ways to achieve the same result!
