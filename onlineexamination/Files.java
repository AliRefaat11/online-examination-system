package onlineexamination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Files {
    private static final String ADMIN_FILE = "admin.dat";
    private static final String STUDENT_FILE = "students.dat";
    private static final String INSTRUCTOR_FILE = "instructors.dat";
    private static final String EXAMS_FILE = "exams.dat";
    private static final String RESULTS_FILE = "results.dat";

    public static void saveCourseToFile(Courses course) {
        String filename = course.getCourseName() + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (Exam exam : course.exams) {
                out.writeObject(exam);
            }
            System.out.println("Course '" + course.getCourseName() + "' saved successfully in file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving course '" + course.getCourseName() + "': " + e.getMessage());
        }
    }

    public static Courses loadCourseFromFile(String courseName) {
        String filename = courseName + ".dat";
        Courses courses = new Courses(courseName);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                courses.exams.add((Exam) in.readObject());
            }
        } catch (EOFException e) {
            System.out.println("Course '" + courseName + "' loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading course '" + courseName + "': " + e.getMessage());
        }
        return courses;
    }

    public static List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {
            return (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Instructor> loadInstructors() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INSTRUCTOR_FILE))) {
            return (List<Instructor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading instructors: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Admin> loadAdmins() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ADMIN_FILE))) {
            return (List<Admin>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading admins: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Exam> loadExams() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXAMS_FILE))) {
            return (List<Exam>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading exams: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Result> loadResults() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RESULTS_FILE))) {
            return (List<Result>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading results: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public static void saveInstructors(List<Instructor> instructors) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INSTRUCTOR_FILE))) {
            oos.writeObject(instructors);
        } catch (IOException e) {
            System.out.println("Error saving instructors: " + e.getMessage());
        }
    }

    public static void saveAdmins(List<Admin> admins) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADMIN_FILE))) {
            oos.writeObject(admins);
        } catch (IOException e) {
            System.out.println("Error saving admins: " + e.getMessage());
        }
    }

    public static void saveExams(List<Exam> exams) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXAMS_FILE))) {
            oos.writeObject(exams);
        } catch (IOException e) {
            System.out.println("Error saving exams: " + e.getMessage());
        }
    }

    public static void saveResults(List<Result> results) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RESULTS_FILE))) {
            oos.writeObject(results);
        } catch (IOException e) {
            System.out.println("Error saving results: " + e.getMessage());
        }
    }
}