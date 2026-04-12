package org.jikvict.tasks.exposed;

import java.util.*;
import java.util.stream.Collectors;

public class StudentDatabase {
    private List<StudentRecord> records;

    public StudentDatabase(List<StudentRecord> records) {
        if (records == null) {
            throw new IllegalArgumentException();
        }
        // Defensive copying
        this.records = new ArrayList<>(records);
    }

    public List<String> getStudentNamesByCourse(String course) {
        return records.stream()
                      .filter(s -> s.getCourse().equals(course))
                      .map(StudentRecord::getName)
                      .sorted()
                      .collect(Collectors.toList());
    }

    public Map<String, List<StudentRecord>> groupByCourse() {
        return records.stream()
                      .collect(Collectors.groupingBy(StudentRecord::getCourse));
    }

    public Map<Integer, Long> countStudentsByYear() {
        return records.stream()
                      .collect(Collectors.groupingBy(
                              StudentRecord::getYear,
                              Collectors.counting()
                              )
                      );
    }

    public List<StudentRecord> getTopStudents(int n) {
        return records.stream()
                      .sorted(Comparator.comparing(StudentRecord::getGrade)
                              .reversed())
                      .limit(n)
                      .collect(Collectors.toList());
    }

    public Map<String, Double> getAverageGradePerCourse() {
        return records.stream()
                .collect(Collectors.groupingBy(
                                StudentRecord::getCourse,
                                Collectors.averagingDouble(StudentRecord::getGrade)
                        )
                );
    }

    public List<StudentRecord> filterByGradeRange(double min, double max) {
        return records.stream()
                      .filter(s -> s.getGrade() >= min && s.getGrade() <= max)
                      .collect(Collectors.toList());
    }

    public Optional<StudentRecord> getHighestGradeStudent() {
        return records.stream()
                      .max(Comparator.comparingDouble(StudentRecord::getGrade));
    }

    public long countPassingStudents(double passingGrade) {
        return records.stream()
                      .filter(s -> s.getGrade() >= passingGrade)
                      .count();
    }
}
