
package onlineexamination;

import java.io.Serializable;
import java.util.*;

public class Courses implements Serializable {

    String courseName;

    protected ArrayList<Exam> exams = new ArrayList<Exam>();

    public Courses(String courseName) {
        this.courseName = courseName;

    }

    public String getCourseName() {
        return courseName;
    }

    public List<Exam> getExams() {
        return exams;
    }

    @Override
    public String toString() {
        return "Course Name: " + courseName + ", Exams: " + exams;
    }

}