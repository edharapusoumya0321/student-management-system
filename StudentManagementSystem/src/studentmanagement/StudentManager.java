
package studentmanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();
    private final String fileName = "students.dat";

    public StudentManager() {
        loadFromFile();
    }

    private Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private int readInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readGrade(Scanner sc) {
        while (true) {
            System.out.print("Enter grade (0-100): ");
            try {
                double grade =
                    Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(grade)
                        && grade >= 0 && grade <= 100) {
                    return grade;
                }

                System.out.println("Grade must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid grade.");
            }
        }
    }

    // Add a student
    public void addStudent(Scanner sc) {
        int id = readInt(sc, "Enter student ID: ");

        if (id <= 0) {
            System.out.println("ID must be positive.");
            return;
        }

        if (findById(id) != null) {
            System.out.println("This ID already exists.");
            return;
        }

        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        int age = readInt(sc, "Enter age: ");

        if (age < 1 || age > 120) {
            System.out.println("Age must be between 1 and 120.");
            return;
        }

        double grade = readGrade(sc);

        System.out.print("Enter subjects separated by commas: ");
        String subjects = sc.nextLine().trim();

        if (subjects.isEmpty()) {
            System.out.println("Subjects cannot be empty.");
            return;
        }

        Student student =
            new Student(id, name, age, grade, subjects);

        students.add(student);
        saveToFile();

        System.out.println("Student added successfully!");
    }

    // Display all students
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n---------------- STUDENT RECORDS ----------------");
        System.out.printf("%-8s %-20s %-8s %-10s %-25s%n",
                "ID", "NAME", "AGE", "GRADE", "SUBJECTS");

        for (Student s : students) {
            System.out.printf("%-8d %-20s %-8d %-10.2f %-25s%n",
                    s.getId(), s.getName(), s.getAge(),
                    s.getGrade(), s.getSubjects());
        }
    }

    // Search by ID or name
    public void searchStudent(Scanner sc) {
        System.out.println("1. Search by ID");
        System.out.println("2. Search by name");

        int choice = readInt(sc, "Choose option: ");

        if (choice == 1) {
            int id = readInt(sc, "Enter student ID: ");
            Student s = findById(id);

            if (s != null) {
                System.out.println(s);
            } else {
                System.out.println("Student not found.");
            }

        } else if (choice == 2) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine().trim();
            boolean found = false;

            for (Student s : students) {
                if (s.getName().equalsIgnoreCase(name)) {
                    System.out.println(s);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Student not found.");
            }
        } else {
            System.out.println("Invalid search option.");
        }
    }

    // Update grade and/or subjects
    public void updateStudent(Scanner sc) {
        int id = readInt(sc, "Enter student ID to update: ");
        Student s = findById(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("1. Update grade");
        System.out.println("2. Update subjects");
        System.out.println("3. Update both");

        int choice = readInt(sc, "Choose option: ");

        if (choice < 1 || choice > 3) {
            System.out.println("Invalid option.");
            return;
        }

        double newGrade = s.getGrade();
        String newSubjects = s.getSubjects();

        if (choice == 1 || choice == 3) {
            newGrade = readGrade(sc);
        }

        if (choice == 2 || choice == 3) {
            System.out.print("Enter new subjects: ");
            newSubjects = sc.nextLine().trim();

            if (newSubjects.isEmpty()) {
                System.out.println("Subjects cannot be empty.");
                return;
            }
        }

        s.setGrade(newGrade);
        s.setSubjects(newSubjects);
        saveToFile();

        System.out.println("Student updated successfully!");
    }

    // Delete a student after confirmation
    public void deleteStudent(Scanner sc) {
        int id = readInt(sc, "Enter student ID to delete: ");
        Student s = findById(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println(s);
        System.out.print("Confirm deletion (yes/no): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            students.remove(s);
            saveToFile();
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // Calculate average grade
    public void calculateAverage() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        double total = 0;

        for (Student s : students) {
            total += s.getGrade();
        }

        System.out.printf("Class average grade: %.2f%n",
                total / students.size());
    }

    // Save records to a file
    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {

            out.writeObject(students);

        } catch (IOException e) {
            System.out.println("Error saving records: "
                    + e.getMessage());
        }
    }

    // Load records when the application starts
    private void loadFromFile() {
        File file = new File(fileName);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {

            Object data = in.readObject();

            if (data instanceof ArrayList<?>) {
                for (Object item : (ArrayList<?>) data) {
                    if (item instanceof Student) {
                        students.add((Student) item);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading records: "
                    + e.getMessage());
        }
    }
}
