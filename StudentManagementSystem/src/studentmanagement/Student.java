
package studentmanagement;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;
    private double grade;
    private String subjects;

    public Student(int id, String name, int age,
                   double grade, String subjects) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGrade() {
        return grade;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Name: %s | Age: %d | Grade: %.2f | Subjects: %s",
            id, name, age, grade, subjects
        );
    }
}
