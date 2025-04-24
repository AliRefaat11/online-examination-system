package onlineexamination;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instructor extends Person {
    public Instructor(String name, int id, String username, String password) {
        super(name, id, username, password);
    }

    public int getId() {
        return this.id;
    }

    public void createExam() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Exam ID: ");
        int examId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String course = scanner.nextLine();
        System.out.print("Enter Exam Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Time Limit (minutes): ");
        int timeLimit = scanner.nextInt();
        scanner.nextLine();
        Exam newExam = new Exam(course, examId, date, timeLimit);
        System.out.print("Do you want to add questions now? (yes/no): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the number of questions: ");
            int numberOfQuestions = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numberOfQuestions; ++i) {
                System.out.print("Enter question " + (i + 1) + ": ");
                String question = scanner.nextLine();
                System.out.print("Enter the number of options for question " + (i + 1) + ": ");
                int numberOfOptions = scanner.nextInt();
                scanner.nextLine();
                List<String> options = new ArrayList();

                for (int j = 0; j < numberOfOptions; ++j) {
                    System.out.print("Enter option " + (char) (65 + j) + ": ");
                    String option = scanner.nextLine();
                    options.add(option);
                }

                System.out.print("Enter the correct answer for question " + (i + 1) + ": ");
                String correctAnswer = scanner.nextLine();
                newExam.addQuestion(question, options, correctAnswer);
            }
        }

        Admin.exams.add(newExam);
        Files.saveExams(Admin.exams);
        System.out.println("Exam created and added successfully!");
    }

    public void display() {
        System.out.println("Instructor Name: " + this.name);
        System.out.println("Instructor ID: " + this.id);
    }
}
