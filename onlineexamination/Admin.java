package onlineexamination;

import java.util.ArrayList;
import java.util.Iterator;

public class Admin extends Person {
   static ArrayList<Admin> Admins = new ArrayList();
   static ArrayList<Student> students = new ArrayList();
   static ArrayList<Instructor> instructors = new ArrayList();
   static ArrayList<Exam> exams = new ArrayList();
   static ArrayList<Result> results = new ArrayList();

   public Admin(String name, int id, String username, String password) {
      super(name, id, username, password);
      Admins.add(this);
   }

   public void addExam(Exam exam) {
      exams.add(exam);
      System.out.println("Exam added successfully!");
   }

   public void removeExam(int examId) {
      exams.removeIf((exam) -> {
         return exam.getExamId() == examId;
      });
      System.out.println("Exam removed successfully!");
   }

   public void listExams() {
      if (exams.isEmpty()) {
         System.out.println("No exams available.");
      } else {
         Iterator var1 = exams.iterator();

         while (var1.hasNext()) {
            Exam exam = (Exam) var1.next();
            exam.display();
         }
      }

   }

   public void addStudent(Student student) {
      students.add(student);
      System.out.println("Student added successfully!");
   }

   public void removeStudent(int studentId) {
      students.removeIf((student) -> {
         return student.getId() == studentId;
      });
      System.out.println("Student removed successfully!");
   }

   public void listStudents() {
      if (students.isEmpty()) {
         System.out.println("No students available.");
      } else {
         Iterator var1 = students.iterator();

         while (var1.hasNext()) {
            Student student = (Student) var1.next();
            student.display();
         }
      }

   }

   public void addInstructor(Instructor instructor) {
      instructors.add(instructor);
      System.out.println("Instructor added successfully!");
   }

   public void removeInstructor(int instructorId) {
      instructors.removeIf((instructor) -> {
         return instructor.getId() == instructorId;
      });
      System.out.println("Instructor removed successfully!");
   }

   public void listInstructors() {
      if (instructors.isEmpty()) {
         System.out.println("No instructors available.");
      } else {
         Iterator var1 = instructors.iterator();

         while (var1.hasNext()) {
            Instructor instructor = (Instructor) var1.next();
            instructor.display();
         }
      }

   }

   public void listResults() {
      if (results.isEmpty()) {
         System.out.println("No results available.");
      } else {
         Iterator var1 = results.iterator();

         while (var1.hasNext()) {
            Result result = (Result) var1.next();
            result.display();
         }
      }

   }

   public void display() {
      System.out.println("Admin Name: " + this.name);
      System.out.println("Admin ID: " + this.id);
   }

   public void createAndAddStudent(String name, int id, String username, String password) {
      Student newStudent = new Student(name, id, username, password);
      this.addStudent(newStudent);
   }

   public void createAndAddInstructor(String name, int id, String username, String password) {
      Instructor newInstructor = new Instructor(name, id, username, password);
      this.addInstructor(newInstructor);
   }
}
