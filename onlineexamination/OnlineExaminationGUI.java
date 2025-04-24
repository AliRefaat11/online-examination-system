package onlineexamination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OnlineExaminationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        Admin.students = (ArrayList<Student>) Files.loadStudents();
        Admin.instructors = (ArrayList<Instructor>) Files.loadInstructors();
        Admin.Admins = (ArrayList<Admin>) Files.loadAdmins();
        Admin.exams = (ArrayList<Exam>) Files.loadExams();
        Admin.results = (ArrayList<Result>) Files.loadResults();

        Admin yasmin = new Admin("Yasmin hosny", 7757, "yasmin2307757", "yasmin123");
        Admin ali = new Admin("Ali Refaat", 3933, "ali2303933", "ali123");
        Admin hassan = new Admin("Hassan Mohamed", 1155, "hassan2301155", "hassan123");
        Admin youssef = new Admin("Youssef Ahmed", 2688, "youssef2302688", "youssef123");
        Admin adel = new Admin("Mohamed Adel", 133, "mohamed2300133", "mohamed123");
        Admin omar = new Admin("Omar Khaled", 816, "omar230816", "omar123");

        Admin.Admins.add(yasmin);
        Admin.Admins.add(ali);
        Admin.Admins.add(hassan);
        Admin.Admins.add(youssef);
        Admin.Admins.add(adel);
        Admin.Admins.add(omar);

        primaryStage.setTitle("Online Examination System");

        GridPane homePane = createHomePane();
        Scene homeScene = new Scene(homePane, 500, 500);

        primaryStage.setScene(homeScene);
        primaryStage.show();

        Files.saveStudents(Admin.students);
        Files.saveInstructors(Admin.instructors);
        Files.saveExams(Admin.exams);
        Files.saveResults(Admin.results);
        Files.saveAdmins(Admin.Admins);
    }

    private GridPane createHomePane() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button adminBT = new Button("Admin");
        Button instructorBT = new Button("Instructor");
        Button studentBT = new Button("Student");
        Button exitBT = new Button("Exit");

        exitBT.setOnAction(_ -> System.exit(0));
        adminBT.setOnAction(_ -> showAdminPane());
        instructorBT.setOnAction(_ -> showInstructorPane());
        studentBT.setOnAction(_ -> showStudentPane());

        pane.add(adminBT, 0, 0);
        pane.add(instructorBT, 1, 0);
        pane.add(studentBT, 2, 0);
        pane.add(exitBT, 3, 0);

        return pane;
    }

    private void showAdminPane() {
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin");
        GridPane adminPane = new GridPane();
        adminPane.setAlignment(Pos.CENTER);
        adminPane.setHgap(10);
        adminPane.setVgap(10);
        adminPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label usernameLabel = new Label("Username: ");
        TextField usernameTF = new TextField();
        Label passLabel = new Label("Password: ");
        PasswordField passTF = new PasswordField();
        Button loginBT = new Button("Login");
        Button backBT = new Button("Back");

        backBT.setOnAction(_ -> adminStage.close());
        loginBT.setOnAction(_ -> {
            String username = usernameTF.getText();
            String password = passTF.getText();
            boolean loginsuccessful = false;
            for (Admin admin : Admin.Admins) {
                if (admin.login(username, password)) {
                    loginsuccessful = true;
                    showAdminMenu(admin);
                    adminStage.close();
                    break;
                }
            }
            if (!loginsuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Admin Credentials!");
                alert.showAndWait();
            }
        });

        adminPane.add(usernameLabel, 0, 0);
        adminPane.add(usernameTF, 1, 0);
        adminPane.add(passLabel, 0, 1);
        adminPane.add(passTF, 1, 1);
        adminPane.add(loginBT, 0, 2);
        adminPane.add(backBT, 1, 2);

        Scene adminScene = new Scene(adminPane, 600, 600);
        adminStage.setScene(adminScene);
        adminStage.show();
    }

    private void showAdminMenu(Admin admin) {
        TabPane tabPane = new TabPane();

        Tab addExamTab = new Tab("Add Exam");
        addExamTab.setContent(createAddExamPane(admin));

        Tab addInstructorTab = new Tab("Add Instructor");
        addInstructorTab.setContent(createAddInstructorPane(admin));

        Tab addStudentTab = new Tab("Add Student");
        addStudentTab.setContent(createAddStudentPane(admin));

        Tab deleteExamTab = new Tab("Delete Exam");
        deleteExamTab.setContent(createDeleteExamPane(admin));

        Tab deleteInstructorTab = new Tab("Delete Instructor");
        deleteInstructorTab.setContent(createDeleteInstructorPane(admin));

        Tab deleteStudentTab = new Tab("Delete Student");
        deleteStudentTab.setContent(createDeleteStudentPane(admin));

        Tab listExamsTab = new Tab("List Exams");
        listExamsTab.setContent(createListExamsPane());

        Tab listInstructorsTab = new Tab("List Instructors");
        listInstructorsTab.setContent(createListInstructorsPane());

        Tab listStudentsTab = new Tab("List Students");
        listStudentsTab.setContent(createListStudentsPane());

        Tab listResultsTab = new Tab("List Results");
        listResultsTab.setContent(createListResultsPane());

        tabPane.getTabs().addAll(addExamTab, addInstructorTab, addStudentTab, deleteExamTab, deleteInstructorTab,
                deleteStudentTab,
                listExamsTab, listInstructorsTab, listStudentsTab, listResultsTab);

        Scene adminMenuScene = new Scene(tabPane, 600, 600);
        Stage adminMenuStage = new Stage();

        adminMenuStage.setScene(adminMenuScene);
        adminMenuStage.setTitle("Admin Menu");
        adminMenuStage.show();
    }

    private GridPane createAddExamPane(Admin admin) {
        GridPane addExamPane = new GridPane();
        addExamPane.setAlignment(Pos.CENTER);
        addExamPane.setHgap(10);
        addExamPane.setVgap(10);
        addExamPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label examIdLabel = new Label("Exam ID:");
        TextField examIdField = new TextField();
        Label courseNameLabel = new Label("Course Name:");
        TextField courseNameField = new TextField();
        Label dateLabel = new Label("Exam Date (YYYY-MM-DD):");
        TextField dateField = new TextField();
        Label timeLimitLabel = new Label("Time Limit (minutes):");
        TextField timeLimitField = new TextField();
        Button addButton = new Button("Add Exam");

        VBox questionsBox = new VBox(10);
        questionsBox.setAlignment(Pos.CENTER);

        Button addQuestionButton = new Button("Add Question");
        addQuestionButton.setOnAction(e -> {
            VBox questionBox = new VBox(10);
            questionBox.setAlignment(Pos.CENTER);

            Label questionLabel = new Label("Question:");
            TextField questionField = new TextField();
            Button addChoiceButton = new Button("Add Choice");

            VBox choicesBox = new VBox(10);
            choicesBox.setAlignment(Pos.CENTER);

            ToggleGroup correctAnswerGroup = new ToggleGroup();

            addChoiceButton.setOnAction(event -> {
                HBox choiceBox = new HBox(10);
                choiceBox.setAlignment(Pos.CENTER);

                TextField choiceField = new TextField();
                Label correctLabel = new Label("Correct:");
                RadioButton correctRadioButton = new RadioButton();
                correctRadioButton.setToggleGroup(correctAnswerGroup);

                choiceBox.getChildren().addAll(new Label("Choice:"), choiceField, correctLabel, correctRadioButton);
                choicesBox.getChildren().add(choiceBox);
            });

            questionBox.getChildren().addAll(questionLabel, questionField, addChoiceButton, choicesBox);
            questionsBox.getChildren().add(questionBox);
        });

        addButton.setOnAction(e -> {
            int examId = Integer.parseInt(examIdField.getText());
            String courseName = courseNameField.getText();
            String date = dateField.getText();
            int timeLimit = Integer.parseInt(timeLimitField.getText());

            Exam newExam = new Exam(courseName, examId, date, timeLimit);

            for (int i = 0; i < questionsBox.getChildren().size(); i++) {
                VBox questionBox = (VBox) questionsBox.getChildren().get(i);
                TextField questionField = (TextField) questionBox.getChildren().get(1);
                String question = questionField.getText();

                VBox choicesBox = (VBox) questionBox.getChildren().get(3);
                List<String> options = new ArrayList<>();
                String correctAnswer = "";

                for (int j = 0; j < choicesBox.getChildren().size(); j++) {
                    HBox choiceBox = (HBox) choicesBox.getChildren().get(j);
                    TextField choiceField = (TextField) choiceBox.getChildren().get(1);
                    RadioButton correctRadioButton = (RadioButton) choiceBox.getChildren().get(3);

                    options.add(choiceField.getText());
                    if (correctRadioButton.isSelected()) {
                        correctAnswer = choiceField.getText();
                    }
                }

                newExam.addQuestion(question, options, correctAnswer);
            }

            admin.addExam(newExam);
            Files.saveExams(Admin.exams);
        });

        addExamPane.add(examIdLabel, 0, 0);
        addExamPane.add(examIdField, 1, 0);
        addExamPane.add(courseNameLabel, 0, 1);
        addExamPane.add(courseNameField, 1, 1);
        addExamPane.add(dateLabel, 0, 2);
        addExamPane.add(dateField, 1, 2);
        addExamPane.add(timeLimitLabel, 0, 3);
        addExamPane.add(timeLimitField, 1, 3);
        addExamPane.add(addQuestionButton, 0, 4);
        addExamPane.add(questionsBox, 0, 5, 2, 1);
        addExamPane.add(addButton, 0, 6);

        return addExamPane;
    }

    private VBox createListExamsPane() {
        VBox listExamsPane = new VBox(10);
        listExamsPane.setAlignment(Pos.CENTER);
        listExamsPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button listExamsButton = new Button("List Exams");
        listExamsPane.getChildren().addAll(listExamsButton);
        listExamsButton.setOnAction(_ -> {
            ObservableList<String> examList = FXCollections.observableArrayList();
            for (int i = 0; i < Admin.exams.size(); i++) {
                Exam exam = Admin.exams.get(i);
                examList.add((i + 1) + ". " + exam.getCourseName() + " (ID: " + exam.getExamId() + ") Date: "
                        + exam.getDate() + " Time Limit: " + exam.getTimeLimit() + " minutes");
            }
            ListView<String> listView = new ListView<>(examList);
            listView.setPrefHeight(150);
            listExamsPane.getChildren().addAll(listView);
        });

        return listExamsPane;
    }

    private GridPane createDeleteExamPane(Admin admin) {
        GridPane deleteStudentPane = new GridPane();
        deleteStudentPane.setAlignment(Pos.CENTER);
        deleteStudentPane.setHgap(10);
        deleteStudentPane.setVgap(10);
        deleteStudentPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label examIdLabel = new Label("Exam ID:");
        TextField examIdField = new TextField();
        Button deleteButton = new Button("Delete");

        deleteButton.setOnAction(_ -> {
            int examId = Integer.parseInt(examIdField.getText());
            admin.removeExam(examId);
            Files.saveStudents(Admin.students);
        });

        deleteStudentPane.add(examIdLabel, 0, 0);
        deleteStudentPane.add(examIdField, 1, 0);
        deleteStudentPane.add(deleteButton, 0, 1);

        return deleteStudentPane;
    }

    private GridPane createAddInstructorPane(Admin admin) {
        GridPane addInstructorPane = new GridPane();
        addInstructorPane.setAlignment(Pos.CENTER);
        addInstructorPane.setHgap(10);
        addInstructorPane.setVgap(10);
        addInstructorPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label instructorIdLabel = new Label("Instructor ID:");
        TextField instructorIdField = new TextField();
        Label instructorNameLabel = new Label("Instructor Name:");
        TextField instructorNameField = new TextField();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button addButton = new Button("Add");

        addButton.setOnAction(_ -> {
            int instructorId = Integer.parseInt(instructorIdField.getText());
            String instructorName = instructorNameField.getText();
            String instructorUsername = usernameField.getText();
            String instructorPassword = passwordField.getText();
            admin.createAndAddInstructor(instructorName, instructorId, instructorUsername, instructorPassword);
            Files.saveInstructors(Admin.instructors);
        });

        addInstructorPane.add(instructorIdLabel, 0, 0);
        addInstructorPane.add(instructorIdField, 1, 0);
        addInstructorPane.add(instructorNameLabel, 0, 1);
        addInstructorPane.add(instructorNameField, 1, 1);
        addInstructorPane.add(usernameLabel, 0, 2);
        addInstructorPane.add(usernameField, 1, 2);
        addInstructorPane.add(passwordLabel, 0, 3);
        addInstructorPane.add(passwordField, 1, 3);
        addInstructorPane.add(addButton, 0, 4);

        return addInstructorPane;
    }

    private GridPane createDeleteInstructorPane(Admin admin) {
        GridPane deleteInstructorPane = new GridPane();
        deleteInstructorPane.setAlignment(Pos.CENTER);
        deleteInstructorPane.setHgap(10);
        deleteInstructorPane.setVgap(10);
        deleteInstructorPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label instructorIdLabel = new Label("Instructor ID:");
        TextField instructorIdField = new TextField();
        Button deleteButton = new Button("Delete");

        deleteButton.setOnAction(_ -> {
            int instructorId = Integer.parseInt(instructorIdField.getText());
            admin.removeInstructor(instructorId);
            Files.saveInstructors(Admin.instructors);
        });

        deleteInstructorPane.add(instructorIdLabel, 0, 0);
        deleteInstructorPane.add(instructorIdField, 1, 0);
        deleteInstructorPane.add(deleteButton, 0, 1);

        return deleteInstructorPane;
    }

    private GridPane createAddStudentPane(Admin admin) {
        GridPane addStudentPane = new GridPane();
        addStudentPane.setAlignment(Pos.CENTER);
        addStudentPane.setHgap(10);
        addStudentPane.setVgap(10);
        addStudentPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label studentIdLabel = new Label("Student ID:");
        TextField studentIdField = new TextField();
        Label studentNameLabel = new Label("Student Name:");
        TextField studentNameField = new TextField();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button addButton = new Button("Add");

        addButton.setOnAction(_ -> {
            int studentId = Integer.parseInt(studentIdField.getText());
            String studentName = studentNameField.getText();
            String studentUsername = usernameField.getText();
            String studentPassword = passwordField.getText();
            admin.createAndAddStudent(studentName, studentId, studentUsername, studentPassword);
            Files.saveStudents(Admin.students);
        });

        addStudentPane.add(studentIdLabel, 0, 0);
        addStudentPane.add(studentIdField, 1, 0);
        addStudentPane.add(studentNameLabel, 0, 1);
        addStudentPane.add(studentNameField, 1, 1);
        addStudentPane.add(usernameLabel, 0, 2);
        addStudentPane.add(usernameField, 1, 2);
        addStudentPane.add(passwordLabel, 0, 3);
        addStudentPane.add(passwordField, 1, 3);
        addStudentPane.add(addButton, 0, 4);

        return addStudentPane;
    }

    private GridPane createDeleteStudentPane(Admin admin) {
        GridPane deleteStudentPane = new GridPane();
        deleteStudentPane.setAlignment(Pos.CENTER);
        deleteStudentPane.setHgap(10);
        deleteStudentPane.setVgap(10);
        deleteStudentPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label studentIdLabel = new Label("Student ID:");
        TextField studentIdField = new TextField();
        Button deleteButton = new Button("Delete");

        deleteButton.setOnAction(_ -> {
            int studentId = Integer.parseInt(studentIdField.getText());
            admin.removeStudent(studentId);
            Files.saveStudents(Admin.students);
        });

        deleteStudentPane.add(studentIdLabel, 0, 0);
        deleteStudentPane.add(studentIdField, 1, 0);
        deleteStudentPane.add(deleteButton, 0, 1);

        return deleteStudentPane;
    }

    private VBox createListStudentsPane() {
        VBox listStudentsPane = new VBox(10);
        listStudentsPane.setAlignment(Pos.CENTER);
        listStudentsPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button listStudentsButton = new Button("List Students");
        listStudentsButton.setOnAction(_ -> {
            ObservableList<String> studentList = FXCollections.observableArrayList();
            for (int i = 0; i < Admin.students.size(); i++) {
                Student student = Admin.students.get(i);
                studentList.add(
                        (i + 1) + "Name:" + student.name + " (ID: " + student.id + ") UserName: " + student.username);
            }
            ListView<String> listView = new ListView<>(studentList);
            listView.setPrefHeight(150);
            listStudentsPane.getChildren().addAll(listView);
        });

        listStudentsPane.getChildren().add(listStudentsButton);

        return listStudentsPane;
    }

    private VBox createListInstructorsPane() {
        VBox listInstructorsPane = new VBox(10);
        listInstructorsPane.setAlignment(Pos.CENTER);
        listInstructorsPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button listInstructorsButton = new Button("List Instructors");
        listInstructorsButton.setOnAction(_ -> {
            ObservableList<String> instructorList = FXCollections.observableArrayList();
            for (int i = 0; i < Admin.instructors.size(); i++) {
                Instructor instructor = Admin.instructors.get(i);
                instructorList.add((i + 1) + ". Name:" + instructor.name + " ID: " + instructor.id + " UserName: "
                        + instructor.username);
            }
            ListView<String> listView = new ListView<>(instructorList);
            listView.setPrefHeight(150);
            listInstructorsPane.getChildren().addAll(listView);
        });

        listInstructorsPane.getChildren().add(listInstructorsButton);

        return listInstructorsPane;
    }

    private VBox createListResultsPane() {
        VBox listResultsPane = new VBox(10);
        listResultsPane.setAlignment(Pos.CENTER);
        listResultsPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button listResultsButton = new Button("List Results");
        listResultsButton.setOnAction(_ -> {
            ObservableList<String> ResultList = FXCollections.observableArrayList();
            for (int i = 0; i < Admin.results.size(); i++) {
                Result result = Admin.results.get(i);
                ResultList.add((i + 1) + ". StudenTID: " + result.getStudentId() + " ExamID: " + result.getExamId()
                        + " Score: " + result.getScore());
            }
            ListView<String> listView = new ListView<>(ResultList);
            listView.setPrefHeight(150);
            listResultsPane.getChildren().addAll(listView);
        });

        listResultsPane.getChildren().add(listResultsButton);

        return listResultsPane;
    }

    private void showInstructorPane() {
        Stage instructorStage = new Stage();
        instructorStage.setTitle("Instructor");
        GridPane instructorPane = new GridPane();
        instructorPane.setAlignment(Pos.CENTER);
        instructorPane.setHgap(10);
        instructorPane.setVgap(10);
        instructorPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label usernameLabel = new Label("Username: ");
        TextField usernameTF = new TextField();
        Label passLabel = new Label("Password: ");
        PasswordField passTF = new PasswordField();
        Button loginBT = new Button("Login");
        Button signUpBT = new Button("Sign Up");

        loginBT.setOnAction(_ -> {
            String username = usernameTF.getText();
            String password = passTF.getText();
            boolean loginsuccessful = false;
            for (Instructor instrictor : Admin.instructors) {
                if (instrictor.login(username, password)) {
                    loginsuccessful = true;
                    showInstructorMenu();
                    instructorStage.close();
                    break;
                }
            }
            if (!loginsuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Instructor Credentials!");
                alert.showAndWait();
            }
        });
        signUpBT.setOnAction(_ -> showInstructorSignUp());

        instructorPane.add(usernameLabel, 0, 0);
        instructorPane.add(usernameTF, 1, 0);
        instructorPane.add(passLabel, 0, 1);
        instructorPane.add(passTF, 1, 1);
        instructorPane.add(loginBT, 0, 2);
        instructorPane.add(signUpBT, 1, 2);

        Scene instructorScene = new Scene(instructorPane, 600, 600);
        instructorStage.setScene(instructorScene);
        instructorStage.show();
    }

    private void showInstructorSignUp() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Instructor");
        GridPane signUpPane = new GridPane();
        signUpPane.setAlignment(Pos.CENTER);
        signUpPane.setHgap(10);
        signUpPane.setVgap(10);
        signUpPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label nameLabel = new Label("Name: ");
        TextField nameTF = new TextField();
        Label idLabel = new Label("ID: ");
        TextField IDTF = new TextField();
        Label usernameLabel = new Label("Username: ");
        TextField usernameTF = new TextField();
        Label passLabel = new Label("Password: ");
        PasswordField passTF = new PasswordField();

        Button signUpBT = new Button("Sign Up");
        Button backBT = new Button("Back");

        signUpBT.setOnAction(_ -> {
            String name = nameTF.getText();
            int ID = Integer.parseInt(IDTF.getText());
            String username = usernameTF.getText();
            String password = passTF.getText();
            Instructor instructor = new Instructor(name, ID, username, password);
            Admin.instructors.add(instructor);
            Files.saveInstructors(Admin.instructors);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText(null);
            alert.setContentText("Instructor signed up successfully!");
            alert.showAndWait();
            signUpStage.close();
        });
        backBT.setOnAction(_ -> signUpStage.close());

        signUpPane.add(nameLabel, 0, 0);
        signUpPane.add(nameTF, 1, 0);
        signUpPane.add(idLabel, 0, 1);
        signUpPane.add(IDTF, 1, 1);
        signUpPane.add(usernameLabel, 0, 2);
        signUpPane.add(usernameTF, 1, 2);
        signUpPane.add(passLabel, 0, 3);
        signUpPane.add(passTF, 1, 3);
        signUpPane.add(backBT, 0, 4);
        signUpPane.add(signUpBT, 1, 4);

        Scene signUpScene = new Scene(signUpPane, 500, 400);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }

    private void showInstructorMenu() {
        TabPane tabPane = new TabPane();

        Tab addExamTab = new Tab("Add Exam");
        addExamTab.setContent(createAddExamPane());

        Tab logoutTab = new Tab("Logout");
        logoutTab.setContent(createLogoutPane());

        tabPane.getTabs().addAll(addExamTab, logoutTab);

        Scene instructorMenuScene = new Scene(tabPane, 500, 400);
        Stage instructorMenuStage = new Stage();

        instructorMenuStage.setScene(instructorMenuScene);
        instructorMenuStage.setTitle("Instructor Menu");
        instructorMenuStage.show();
    }

    private GridPane createAddExamPane() {
        GridPane addExamPane = new GridPane();
        addExamPane.setAlignment(Pos.CENTER);
        addExamPane.setHgap(10);
        addExamPane.setVgap(10);
        addExamPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label examIdLabel = new Label("Exam ID:");
        TextField examIdField = new TextField();
        Label courseNameLabel = new Label("Course Name:");
        TextField courseNameField = new TextField();
        Label dateLabel = new Label("Exam Date (YYYY-MM-DD):");
        TextField dateField = new TextField();
        Label timeLimitLabel = new Label("Time Limit (minutes):");
        TextField timeLimitField = new TextField();
        Button addButton = new Button("Add");

        addButton.setOnAction(_ -> {
            int examId = Integer.parseInt(examIdField.getText());
            String courseName = courseNameField.getText();
            String date = dateField.getText();
            int timeLimit = Integer.parseInt(timeLimitField.getText());

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Exam");
            dialog.setHeaderText("Enter the number of questions:");
            int numberOfQuestions = Integer.parseInt(dialog.showAndWait().orElse("0"));

            Exam newExam = new Exam(courseName, examId, date, timeLimit);
            for (int i = 0; i < numberOfQuestions; i++) {
                dialog.setContentText("Enter question " + (i + 1) + ":");
                String question = dialog.showAndWait().orElse("");

                dialog.setContentText("Enter the number of options for question " + (i + 1) + ":");
                int numberOfOptions = Integer.parseInt(dialog.showAndWait().orElse("0"));

                List<String> options = new ArrayList<>();
                for (int j = 0; j < numberOfOptions; j++) {
                    dialog.setContentText("Enter option " + (char) ('A' + j) + ":");
                    String option = dialog.showAndWait().orElse("");
                    options.add(option);
                }

                dialog.setContentText("Enter the correct answer for question " + (i + 1) + ":");
                String correctAnswer = dialog.showAndWait().orElse("");

                newExam.addQuestion(question, options, correctAnswer);
            }

            Admin.exams.add(newExam);
            Files.saveExams(Admin.exams);
        });

        addExamPane.add(examIdLabel, 0, 0);
        addExamPane.add(examIdField, 1, 0);
        addExamPane.add(courseNameLabel, 0, 1);
        addExamPane.add(courseNameField, 1, 1);
        addExamPane.add(dateLabel, 0, 2);
        addExamPane.add(dateField, 1, 2);
        addExamPane.add(timeLimitLabel, 0, 3);
        addExamPane.add(timeLimitField, 1, 3);
        addExamPane.add(addButton, 0, 4);

        return addExamPane;
    }

    private VBox createLogoutPane() {
        VBox logoutPane = new VBox(10);
        logoutPane.setAlignment(Pos.CENTER);
        logoutPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(_ -> logoutPane.getScene().getWindow().hide());

        logoutPane.getChildren().add(logoutButton);

        return logoutPane;
    }

    private void showStudentPane() {
        Stage StudentStage = new Stage();
        StudentStage.setTitle("Student");
        GridPane StudentPane = new GridPane();
        StudentPane.setAlignment(Pos.CENTER);
        StudentPane.setHgap(10);
        StudentPane.setVgap(10);
        StudentPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label usernameLabel = new Label("Username: ");
        TextField usernameTF = new TextField();
        Label passLabel = new Label("Password: ");
        PasswordField passTF = new PasswordField();
        Button loginBT = new Button("Login");
        Button signUpBT = new Button("Sign Up");

        loginBT.setOnAction(_ -> {
            String username = usernameTF.getText();
            String password = passTF.getText();
            boolean loginsuccessful = false;
            for (Student student : Admin.students) {
                if (student.login(username, password)) {
                    loginsuccessful = true;
                    showStudentMenu(student);
                    StudentStage.close();
                    break;
                }
            }
            if (!loginsuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Instructor Credentials!");
                alert.showAndWait();
            }
        });
        signUpBT.setOnAction(_ -> showStudentSignUp());

        StudentPane.add(usernameLabel, 0, 0);
        StudentPane.add(usernameTF, 1, 0);
        StudentPane.add(passLabel, 0, 1);
        StudentPane.add(passTF, 1, 1);
        StudentPane.add(loginBT, 0, 2);
        StudentPane.add(signUpBT, 1, 2);

        Scene StudentScene = new Scene(StudentPane, 600, 600);
        StudentStage.setScene(StudentScene);
        StudentStage.show();
    }

    private void showStudentSignUp() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Student");
        GridPane signUpPane = new GridPane();
        signUpPane.setAlignment(Pos.CENTER);
        signUpPane.setHgap(10);
        signUpPane.setVgap(10);
        signUpPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label nameLabel = new Label("Name: ");
        TextField nameTF = new TextField();
        Label idLabel = new Label("ID: ");
        TextField IDTF = new TextField();
        Label usernameLabel = new Label("Username: ");
        TextField usernameTF = new TextField();
        Label passLabel = new Label("Password: ");
        PasswordField passTF = new PasswordField();

        Button signUpBT = new Button("Sign Up");
        Button backBT = new Button("Back");

        signUpBT.setOnAction(_ -> {
            String name = nameTF.getText();
            int ID = Integer.parseInt(IDTF.getText());
            String username = usernameTF.getText();
            String password = passTF.getText();
            Student student = new Student(name, ID, username, password);
            Admin.students.add(student);
            Files.saveStudents(Admin.students);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText(null);
            alert.setContentText("Student signed up successfully!");
            alert.showAndWait();
            signUpStage.close();
        });
        backBT.setOnAction(_ -> signUpStage.close());

        signUpPane.add(nameLabel, 0, 0);
        signUpPane.add(nameTF, 1, 0);
        signUpPane.add(idLabel, 0, 1);
        signUpPane.add(IDTF, 1, 1);
        signUpPane.add(usernameLabel, 0, 2);
        signUpPane.add(usernameTF, 1, 2);
        signUpPane.add(passLabel, 0, 3);
        signUpPane.add(passTF, 1, 3);
        signUpPane.add(backBT, 0, 4);
        signUpPane.add(signUpBT, 1, 4);

        Scene signUpScene = new Scene(signUpPane, 500, 400);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }

    private void showStudentMenu(Student student) {
        TabPane tabPane = new TabPane();

        Tab takeExamTab = new Tab("Take Exam");
        takeExamTab.setContent(createAvailableExamsScene(student));

        Tab viewResultsTab = new Tab("View Results");
        viewResultsTab.setContent(createViewResultsPane(student));

        Tab logoutTab = new Tab("Logout");
        logoutTab.setContent(createLogoutPane());

        tabPane.getTabs().addAll(takeExamTab, viewResultsTab, logoutTab);

        Scene studentMenuScene = new Scene(tabPane, 500, 400);
        Stage studentMenuStage = new Stage();

        studentMenuStage.setScene(studentMenuScene);
        studentMenuStage.setTitle("Student Menu");
        studentMenuStage.show();
    }

    public VBox createAvailableExamsScene(Student student) {
        VBox layout = new VBox(10);
        layout.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label header = new Label("Available Exams:");
        layout.getChildren().add(header);
        ObservableList<String> examList = FXCollections.observableArrayList();
        for (int i = 0; i < Admin.exams.size(); i++) {
            Exam exam = Admin.exams.get(i);
            examList.add((i + 1) + ". " + exam.getCourseName() + " (ID: " + exam.getExamId() + ") Date: "
                    + exam.getDate() + " Time Limit: " + exam.getTimeLimit() + " minutes");
        }
        ListView<String> listView = new ListView<>(examList);
        listView.setPrefHeight(150);
        Button startExamButton = new Button("Start Exam");
        startExamButton.setOnAction(_ -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Exam selectedExam = Admin.exams.get(selectedIndex);
                showExamScene(student, selectedExam);
            } else {
                System.out.println("Please select an exam to start.");
            }
        });
        layout.getChildren().addAll(listView, startExamButton);
        return layout;
    }

    private void showExamScene(Student student, Exam exam) {
        Stage examStage = new Stage();
        examStage.setTitle("Exam: " + exam.getCourseName());

        VBox examLayout = new VBox(10);
        examLayout.setPadding(new Insets(20));
        examLayout.setAlignment(Pos.CENTER);
        examLayout.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Label timerLabel = new Label();
        examLayout.getChildren().add(timerLabel);

        int[] remainingTime = { exam.getTimeLimit() * 60 };

        final Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            if (remainingTime[0] > 0) {
                remainingTime[0]--;
                int minutes = remainingTime[0] / 60;
                int seconds = remainingTime[0] % 60;
                timerLabel.setText(String.format("Time left: %02d:%02d", minutes, seconds));
            } else {
                // tl.stop();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Time's up");
                alert.setHeaderText(null);
                alert.setContentText("Time's up! The exam is over.");
                alert.showAndWait();
                examStage.close();
            }
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        List<ToggleGroup> toggleGroups = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : exam.getQuestions().entrySet()) {
            VBox questionBox = new VBox(10);
            questionBox.setAlignment(Pos.CENTER);

            Label questionLabel = new Label(entry.getKey());
            questionBox.getChildren().add(questionLabel);

            ToggleGroup choicesGroup = new ToggleGroup();
            toggleGroups.add(choicesGroup);
            for (String choice : entry.getValue()) {
                RadioButton choiceButton = new RadioButton(choice);
                choiceButton.setToggleGroup(choicesGroup);
                questionBox.getChildren().add(choiceButton);
            }

            examLayout.getChildren().add(questionBox);
        }

        Button submitButton = new Button("Submit Exam");
        submitButton.setOnAction(e -> {
            tl.stop();

            int score = 0;
            for (int i = 0; i < exam.getQuestions().size(); i++) {
                Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) exam.getQuestions().entrySet()
                        .toArray()[i];
                String correctAnswer = entry.getValue().get(0); // Assuming the first choice is the correct answer
                ToggleGroup choicesGroup = toggleGroups.get(i);
                RadioButton selectedChoice = (RadioButton) choicesGroup.getSelectedToggle();
                if (selectedChoice != null && selectedChoice.getText().equals(correctAnswer)) {
                    score++;
                }
            }
            Result result = new Result(exam.getCourseName());
            result.setStudentId(student.getId());
            result.setExamId(exam.getExamId());
            result.setScore(score);
            student.results.add(result);
            Files.saveResults(Admin.results);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exam Submitted");
            alert.setHeaderText(null);
            alert.setContentText("Your exam has been submitted. Your score is: " + score);
            alert.showAndWait();
            examStage.close();
        });

        examLayout.getChildren().add(submitButton);

        Scene examScene = new Scene(examLayout, 600, 400);
        examStage.setScene(examScene);
        examStage.show();
    }

    private VBox createViewResultsPane(Student student) {
        VBox viewResultsPane = new VBox(10);
        viewResultsPane.setAlignment(Pos.CENTER);
        viewResultsPane.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                CornerRadii.EMPTY,
                null)));

        Button viewResultsButton = new Button("View Results");
        viewResultsButton.setOnAction(_ -> {
            ObservableList<String> examList = FXCollections.observableArrayList();
            for (int i = 0; i < student.results.size(); i++) {
                Result result = student.results.get(i);
                examList.add((i + 1) + ". ExamID: " + result.getExamId() + " Score: " + result.getScore());
            }
            ListView<String> results = new ListView<>(examList);
            results.setPrefHeight(150);
            viewResultsPane.getChildren().addAll(results);
        });

        viewResultsPane.getChildren().add(viewResultsButton);

        return viewResultsPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}