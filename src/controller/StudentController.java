package controller;

import model.Student;
import model.StudentDAO;
import view.AddStudentView;
import view.MainView;

import java.util.List;

/**
 * handles communication between model and view part
 * sets callback functions for events, fetched data from database and  inflates UI
 */
public class StudentController {
    private final MainView mainView;
    private final AddStudentView addStudentView;
    private final StudentDAO studentDAO;

    public StudentController(MainView mainView, AddStudentView addStudentView, StudentDAO studentDAO) {
        this.mainView = mainView;
        this.addStudentView = addStudentView;
        this.studentDAO = studentDAO;

        //add callback functionality for all button clicks
        this.mainView.setAddStudentButtonActionListener(e -> addStudentView.setVisible(true));
        this.mainView.setDeleteStudentButtonActionListener(e -> deleteStudent());
        this.mainView.setActivateStudentButtonActionListener(e -> activateStudent());
        this.mainView.setDeactivateStudentButtonActionListener(e -> deactivateStudent());
        this.mainView.setDisplayStudentsButtonActionListener(e -> refreshStudentList());
        this.mainView.setSearchStudentButtonActionListener(e -> searchStudent());

        //callback for button click events in add student view
        this.addStudentView.setSubmitButtonActionListener(e -> addStudent());
        this.addStudentView.setCancelButtonActionListener(e -> addStudentView.dispose());
    }

    //callback function to add a student
    private void addStudent() {
        try {
            Student student = validateAndFetchDetails();
            if (student == null) return;
            boolean isAdded = studentDAO.addStudent(student);
            if (isAdded) {
                addStudentView.showMessage("Student added successfully!");
                addStudentView.dispose();
                refreshStudentList();
            } else {
                addStudentView.showMessage("Failed to add student.");
            }
        } catch (NumberFormatException ex) {
            addStudentView.showMessage("Please enter a valid ID.");
        }
    }

    //validate the entered data to add student
    private Student validateAndFetchDetails() {
        String idString = addStudentView.getId();
        String firstName = addStudentView.getFirstName();
        String lastName = addStudentView.getLastName();
        String email = addStudentView.getEmail();
        boolean active = addStudentView.isActive();
        if (idString.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            addStudentView.showMessage("All fields are required.");
            return null;
        }
        return new Student(Integer.parseInt(addStudentView.getId()), firstName, lastName, email, active);
    }

    //gets id and checks whether we have student with that ID.
    private Integer getAndValidateID() {
        Integer id = getStudentIdFromUser("Enter the student ID");
        if (id == null || studentDAO.findStudentById(id) == null) {
            mainView.showMessage("Student ID not found.");
            return null;
        }
        return id;
    }

    //delete student with given ID
    private void deleteStudent() {
        Integer id = getAndValidateID();
        if (id == null) return;
        if (studentDAO.deleteStudent(id)) {
            mainView.showMessage("Student deleted successfully!");
            refreshStudentList();
        } else {
            mainView.showMessage("Failed to delete student.");
        }
    }

    //activate student with given ID
    private void activateStudent() {
        Integer id = getAndValidateID();
        if (id == null) return;
        if (studentDAO.updateStudentStatus(id, true)) {
            mainView.showMessage("Student activated successfully!");
            refreshStudentList();
        } else {
            mainView.showMessage("Failed to activate student.");
        }
    }

    //deactivate student with given ID
    private void deactivateStudent() {
        Integer id = getAndValidateID();
        if (id == null) return;
        if (studentDAO.updateStudentStatus(id, false)) {
            mainView.showMessage("Student deactivated successfully!");
            refreshStudentList();
        } else {
            mainView.showMessage("Failed to deactivate student.");
        }
    }

    //tries to parse input entered by user validates it
    private Integer getStudentIdFromUser(String message) {
        String idStr = mainView.getStudentIdFromUser(message);
        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            mainView.showMessage("Please enter a valid ID.");
            return null;
        }
    }

    //method to refresh student list
    public void refreshStudentList() {
        List<Student> students = studentDAO.getAllStudents();
        mainView.displayStudents(students);
    }

    //search for student with given ID
    private void searchStudent() {
        Integer id = getStudentIdFromUser("Enter the student ID to search:");
        if (id == null) return;
        Student student = studentDAO.findStudentById(id);
        if (student != null) {
            mainView.displayStudents(List.of(student));
        } else {
            mainView.showMessage("Student not found for given ID.");
        }
    }
}
