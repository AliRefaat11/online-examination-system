package onlineexamination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Student extends Person {

    protected ArrayList<Result> results;

    public Student(String name, int id, String username, String password) {
        super(name, id, username, password);
        this.results = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void takeExam(Exam exam) {
        System.out.println("Taking exam: " + exam.getCourseName());
        System.out.println("You have " + exam.getTimeLimit() + " minutes to complete this exam.");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + exam.getTimeLimit() * 60 * 1000;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long timeRemaining = endTime - currentTime;
                if (timeRemaining <= 0) {
                    System.out.println("Time is up!");
                    timer.cancel();
                } else {
                    long minutes = (timeRemaining / 1000) / 60;
                    long seconds = (timeRemaining / 1000) % 60;
                    System.out.println("Time remaining: " + minutes + " minutes " + seconds + " seconds");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10000); // Update every 10 seconds

        try (Scanner scanner = new Scanner(System.in)) {
            int score = 0;
            for (Map.Entry<String, List<String>> entry : exam.getQuestions().entrySet()) {
                if (System.currentTimeMillis() > endTime) {
                    System.out.println("Time is up!");
                    break;
                }
                long currentTime = System.currentTimeMillis();
                long timeRemaining = endTime - currentTime;
                long minutes = (timeRemaining / 1000) / 60;
                long seconds = (timeRemaining / 1000) % 60;
                System.out.println("Time remaining: " + minutes + " minutes " + seconds + " seconds");

                String question = entry.getKey();
                List<String> options = entry.getValue();
                System.out.println("Q: " + question);
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((char) ('A' + i) + ": " + options.get(i));
                }
                System.out.print("Your answer: ");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase(exam.getCorrectAnswer(question))) {
                    score++;
                }
            }
            timer.cancel();
            int totalQuestions = exam.getQuestions().size();
            double percentage = ((double) score / totalQuestions) * 100;
            System.out.println("Exam finished.");
            System.out.println("Your score: " + score + "/" + totalQuestions);
            System.out.println("Percentage: " + percentage + "%");

            // Save the result
            Result result = new Result(exam.getCourseName());
            result.setStudentId(this.id);
            result.setExamId(exam.getExamId());
            result.setScore((int) percentage);
            results.add(result);
            Admin.results.add(result);
        }
        Files.saveResults(Admin.results);
    }

    public void viewResults() {
        if (results.isEmpty()) {
            System.out.println("No results available.");
        } else {
            for (Result result : results) {
                result.display();
            }
        }
    }

    @Override
    public void display() {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + id);
    }

    public void getAvailableExams(Scanner scanner) {
        System.out.println("Available Exams:");
        if (Admin.exams.isEmpty()) {
            System.out.println("No exams are available at the moment.");
            return;
        }
        for (int i = 0; i < Admin.exams.size(); i++) {
            Exam exam = Admin.exams.get(i);
            System.out.println((i + 1) + ". " + exam.getCourseName() + " (ID: " + exam.getExamId() + ") Date: "
                    + exam.getDate() + " Time Limit: " + exam.getTimeLimit() + " minutes");
        }
        System.out.print("Enter the exam number to start the exam: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > Admin.exams.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        Exam selectedExam = Admin.exams.get(choice - 1);
        takeExam(selectedExam);
    }
}