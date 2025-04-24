
package onlineexamination;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineExamination {

    public static void main(String[] args) {

        Admin ali = new Admin("Ali Refaat", 3933, "ali2303933", "ali123");
        Admin hassan = new Admin("Hassan Mohamed", 1155, "hassan2301155", "hassan123");
        Admin youssef = new Admin("Youssef Ahmed", 2688, "youssef2302688", "youssef123");
        Admin adel = new Admin("Mohamed Adel", 133, "mohamed2300133", "mohamed123");
        Admin omar = new Admin("Omar Khaled", 816, "omar230816", "omar123");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            Admin.students = (ArrayList<Student>) Files.loadStudents();
            Admin.instructors = (ArrayList<Instructor>) Files.loadInstructors();
            Admin.Admins = (ArrayList<Admin>) Files.loadAdmins();
            Admin.exams = (ArrayList<Exam>) Files.loadExams();
            Admin.results = (ArrayList<Result>) Files.loadResults();

            Admin.Admins.add(ali);
            Admin.Admins.add(hassan);
            Admin.Admins.add(youssef);
            Admin.Admins.add(adel);
            Admin.Admins.add(omar);

            System.out.println("=== Online Examination System ===");
            System.out.println("1. Admin");
            System.out.println("2. Instructor");
            System.out.println("3. Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> loginAdmin(Admin.Admins, scanner);
                case 2 -> {
                    System.out.println("1. Login\n2. Sign Up");
                    int c = scanner.nextInt();
                    scanner.nextLine();
                    switch (c) {
                        case 1 -> loginInstructor(Admin.instructors, scanner);
                        case 2 -> signUpInstructor(Admin.instructors, scanner);
                        default -> System.out.println("Wrong input!!");
                    }
                }
                case 3 -> {
                    System.out.println("1. Login\n2. Sign Up");
                    int c = scanner.nextInt();
                    scanner.nextLine();
                    switch (c) {
                        case 1 -> loginStudent(Admin.students, scanner);
                        case 2 -> signUpStudent(Admin.students, scanner);
                        default -> System.out.println("Wrong input!!");
                    }
                }
                case 4 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
            Files.saveStudents(Admin.students);
            Files.saveInstructors(Admin.instructors);
            Files.saveExams(Admin.exams);
            Files.saveResults(Admin.results);
            Files.saveAdmins(Admin.Admins);
        }
    }

    protected static void signUpInstructor(ArrayList<Instructor> instructors, Scanner scanner) {
        System.out.print("Enter Instructor ID: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Instructor Name: ");
        String instructorName = scanner.nextLine();
        System.out.print("Enter Username: ");
        String instructorUsername = scanner.nextLine();
        System.out.print("Enter Password: ");
        String instructorPassword = scanner.nextLine();
        Instructor newInstructor = new Instructor(instructorName, instructorId, instructorUsername, instructorPassword);
        instructors.add(newInstructor);
        System.out.println("Instructor signed up successfully!");
    }

    protected static void signUpStudent(ArrayList<Student> students, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter Username: ");
        String studentUsername = scanner.nextLine();
        System.out.print("Enter Password: ");
        String studentPassword = scanner.nextLine();
        Student newStudent = new Student(studentName, studentId, studentUsername, studentPassword);
        students.add(newStudent);
        System.out.println("Student signed up successfully!");
    }

    protected static void loginAdmin(ArrayList<Admin> admins, Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        for (Admin admin : admins) {
            if (admin.login(username, password)) {
                System.out.println("Admin Login Successful!");
                adminMenu(admin, scanner);
                return;
            }
        }
        System.out.println("Invalid Admin Credentials!");
    }

    protected static void adminMenu(Admin admin, Scanner scanner) {
        while (true) {
            System.out.println("=== Admin Menu ===");
            System.out.println("1. Add Exam");
            System.out.println("2. List Exams");
            System.out.println("3. Add Instructor");
            System.out.println("4. Delete Instructor");
            System.out.println("5. Add Student");
            System.out.println("6. Delete Student");
            System.out.println("7. List Students");
            System.out.println("8. List Instructors");
            System.out.println("9. List Results");
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Exam ID: ");
                    int examId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.nextLine();
                    System.out.print("Enter Exam Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Time Limit (minutes): ");
                    int timeLimit = scanner.nextInt();
                    scanner.nextLine();

                    Exam newExam = new Exam(courseName, examId, date, timeLimit);

                    System.out.print("Enter the number of questions: ");
                    int numberOfQuestions = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfQuestions; i++) {
                        System.out.print("Enter question " + (i + 1) + ": ");
                        String question = scanner.nextLine();
                        System.out.print("Enter the number of options for question " + (i + 1) + ": ");
                        int numberOfOptions = scanner.nextInt();
                        scanner.nextLine();
                        List<String> options = new ArrayList<>();
                        for (int j = 0; j < numberOfOptions; j++) {
                            System.out.print("Enter option " + (char) ('A' + j) + ": ");
                            String option = scanner.nextLine();
                            options.add(option);
                        }
                        System.out.print("Enter the correct answer for question " + (i + 1) + ": ");
                        String correctAnswer = scanner.nextLine();
                        newExam.addQuestion(question, options, correctAnswer);
                    }

                    admin.addExam(newExam);
                    Files.saveExams(Admin.exams); // Save the exams to file
                }
                case 2 -> admin.listExams();
                case 3 -> {
                    System.out.print("Enter Instructor ID: ");
                    int instructorId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Instructor Name: ");
                    String instructorName = scanner.nextLine();
                    System.out.print("Enter Username: ");
                    String instructorUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String instructorPassword = scanner.nextLine();
                    admin.createAndAddInstructor(instructorName, instructorId, instructorUsername, instructorPassword);
                }
                case 4 -> {
                    System.out.print("Enter Instructor ID to delete: ");
                    int instructorId = scanner.nextInt();
                    scanner.nextLine();
                    admin.removeInstructor(instructorId);
                }
                case 5 -> {
                    System.out.print("Enter Student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Enter Username: ");
                    String studentUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String studentPassword = scanner.nextLine();
                    admin.createAndAddStudent(studentName, studentId, studentUsername, studentPassword);
                }
                case 6 -> {
                    System.out.print("Enter Student ID to delete: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();
                    admin.removeStudent(studentId);
                }
                case 7 -> admin.listStudents();
                case 8 -> admin.listInstructors();
                case 9 -> admin.listResults();
                case 10 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    protected static void loginInstructor(ArrayList<Instructor> instructors, Scanner scanner) {
        System.out.print("Enter Instructor Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Instructor Password: ");
        String password = scanner.nextLine();
        for (Instructor instructor : instructors) {
            if (instructor.login(username, password)) {
                System.out.println("Instructor Login Successful!");
                instructorMenu(instructor, scanner);
                return;
            }
        }
        System.out.println("Invalid Instructor Credentials!");
    }

    protected static void instructorMenu(Instructor instructor, Scanner scanner) {
        while (true) {
            System.out.println("=== Instructor Menu ===");
            System.out.println("1. Add Exam");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> instructor.createExam();
                case 2 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    protected static void loginStudent(ArrayList<Student> students, Scanner scanner) {
        System.out.print("Enter Student Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Student Password: ");
        String password = scanner.nextLine();
        for (Student student : students) {
            if (student.login(username, password)) {
                System.out.println("Student Login Successful!");
                studentMenu(student, scanner);
                return;
            }
        }
        System.out.println("Invalid Student Credentials!");
    }

    protected static void studentMenu(Student student, Scanner scanner) {
        while (true) {
            System.out.println("=== Student Menu ===");
            System.out.println("1. Take Exam");
            System.out.println("2. View Results");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> student.getAvailableExams(scanner);
                case 2 -> student.viewResults();
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}