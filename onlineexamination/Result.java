package onlineexamination;

import java.io.Serializable;

public class Result extends Courses implements Serializable {

    private int studentId;
    private int examId;
    private int score;

    public Result(String courseID) {
        super(courseID);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "studentId=" + studentId +
                ", examId=" + examId +
                ", score=" + score +
                '}';
    }

    public void display() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Exam ID: " + examId);
        System.out.println("Score: " + score + "%");
    }
}