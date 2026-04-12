package org.jikvict.tasks.exposed;

public class StudentRecord {
    private final String studentId, name, course;
    private final int year;
    private final double grade;

    public StudentRecord(String studentId, String name, String course, int year, double grade) {
        if (studentId == null || studentId.isBlank() ||
            name == null || name.isBlank() ||
            course == null || course.isBlank() ||
            year < 1 || year > 6 ||
            grade < 0.0 || grade > 100.0
        ) {
            throw new IllegalArgumentException();
        }
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getYear() {
        return year;
    }

    public double getGrade() {
        return grade;
    }
}
