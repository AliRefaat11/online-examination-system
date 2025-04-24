package onlineexamination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Exam extends Courses {

    private String course;
    private int examId;
    private String date;
    private int timeLimit;
    private Map<String, List<String>> questions;
    private Map<String, String> correctAnswers;

    public Exam(String courseName, int examId, String date, int timeLimit) {
        super(courseName);
        this.course = courseName;
        this.examId = examId;
        this.date = date;
        this.timeLimit = timeLimit;
        this.questions = new HashMap<>();
        this.correctAnswers = new HashMap<>();
    }

    public void addQuestion(String question, List<String> options, String correctAnswer) {
        questions.put(question, options);
        correctAnswers.put(question, correctAnswer);
    }

    public Map<String, List<String>> getQuestions() {
        return questions;
    }

    public String getCorrectAnswer(String question) {
        return correctAnswers.get(question);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "course='" + course + '\'' +
                ", examId=" + examId +
                ", date='" + date + '\'' +
                ", timeLimit=" + timeLimit +
                ", questions=" + questions +
                ", correctAnswers=" + correctAnswers +
                '}';
    }

    public String getDate() {
        return date;
    }

    @Override
    public List<Exam> getExams() {
        return super.getExams();
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getExamId() {
        return examId;
    }

    public void addQuestions() {
        try (Scanner scanner = new Scanner(System.in)) {
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
                addQuestion(question, options, correctAnswer);
            }
        }
    }

    public void startExam() {
        try (Scanner scanner = new Scanner(System.in)) {
            int score = 0;
            System.out.println("Starting exam: " + course);
            for (String question : questions.keySet()) {
                System.out.println("Q: " + question);
                List<String> options = questions.get(question);
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((char) ('A' + i) + ": " + options.get(i));
                }
                System.out.print("Your answer: ");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase(correctAnswers.get(question))) {
                    score++;
                }
            }
            int totalQuestions = questions.size();
            double percentage = ((double) score / totalQuestions) * 100;
            System.out.println("Exam finished.");
            System.out.println("Your score: " + score + "/" + totalQuestions);
            System.out.println("Percentage: " + percentage + "%");
        }
    }

    public void display() {
        System.out.println("Exam ID: " + examId);
        System.out.println("Course Name: " + course);
        System.out.println("Date: " + date);
        System.out.println("Time Limit: " + timeLimit + " minutes");
        System.out.println("Questions:");
        for (Map.Entry<String, List<String>> entry : questions.entrySet()) {
            System.out.println("Q: " + entry.getKey());
            List<String> options = entry.getValue();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((char) ('A' + i) + ": " + options.get(i));
            }
        }
    }
}