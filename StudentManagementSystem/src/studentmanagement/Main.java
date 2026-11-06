
package studentmanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        boolean running = true;

        while (running) {

            System.out.println("\n==================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM");
            System.out.println("   WeIntern - Week 2 Assignment");
            System.out.println("==================================");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Calculate Class Average");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from 1 to 7.");
                continue;
            }

            switch (choice) {

                case 1:
                    manager.addStudent(sc);
                    break;

                case 2:
                    manager.displayAll();
                    break;

                case 3:
                    manager.searchStudent(sc);
                    break;

                case 4:
                    manager.updateStudent(sc);
                    break;

                case 5:
                    manager.deleteStudent(sc);
                    break;

                case 6:
                    manager.calculateAverage();
                    break;

                case 7:
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1 to 7.");
            }
        }

        sc.close();
    }
}
