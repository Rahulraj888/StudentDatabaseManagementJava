package controller;

import model.Student;
import model.StudentDAO;
import view.AddStudentView;
import view.MainView;

import javax.swing.JOptionPane;
import java.util.List;

public class StudentController {
    private final MainView mainView;
    private final AddStudentView addStudentView;
    private final StudentDAO studentDAO;

    public StudentController(MainView mainView, AddStudentView addStudentView, StudentDAO studentDAO) {
        this.mainView = mainView;
        this.addStudentView = addStudentView;
        this.studentDAO = studentDAO;

        this.mainView.setAddStudentButtonActionListener(e -> addStudentView.setVisible(true));
        this.mainView.setDeleteStudentButtonActionListener(e -> deleteStudent());
        this.mainView.setActivateStudentButtonActionListener(e -> activateStudent());
        this.mainView.setDeactivateStudentButtonActionListener(e -> deactivateStudent());
        this.mainView.setDisplayStudentsButtonActionListener(e -> refreshStudentList());
        this.mainView.setSearchStudentButtonActionListener(e -> searchStudent());

        this.addStudentView.setSubmitButtonActionListener(e -> addStudent());
        this.addStudentView.setCancelButtonActionListener(e -> addStudentView.dispose());
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(addStudentView.getId());
            String firstName = addStudentView.getFirstName();
            String lastName = addStudentView.getLastName();
            String email = addStudentView.getEmail();
            boolean active = addStudentView.isActive();
            Student student = new Student(id, firstName, lastName, email, active);
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

    private void deleteStudent() {
        Integer id = getStudentIdFromUser("Enter the student ID to delete:");
        if (id == null) return;
        if (studentDAO.findStudentById(id) == null) {
            mainView.showMessage("Student ID not found.");
            return;
        }
        if (studentDAO.deleteStudent(id)) {
            mainView.showMessage("Student deleted successfully!");
            refreshStudentList();
        } else {
            mainView.showMessage("Failed to delete student.");
        }
    }

    private void activateStudent() {
        Integer id = getStudentIdFromUser("Enter the student ID to activate:");
        if (id == null) return;
        if (studentDAO.findStudentById(id) == null) {
            mainView.showMessage("Student ID not found.");
            return;
        }
        if (studentDAO.updateStudentStatus(id, true)) {
            mainView.showMessage("Student activated successfully!");
            refreshStudentList();
        } else {
            mainView.showMessage("Failed to activate student.");
        }
    }

    private void deactivateStudent() {
        Integer id = getStudentIdFromUser("Enter the student ID to deactivate:");
        if (id == null) return;
        if (studentDAO.findStudentById(id) == null) {
            mainView.showMessage("Student ID not found.");
            return;
        }
        if (studentDAO.updateStudentStatus(id, false)) {
            mainView.showMessage("Student deactivated successfully!");
            refreshStudentList();
        } else {
            mainView.showMessage("Failed to deactivate student.");
        }
    }

    private Integer getStudentIdFromUser(String message) {
        String idStr = mainView.getStudentIdFromUser(message);
        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            mainView.showMessage("Please enter a valid ID.");
            return null;
        }
    }

    private void refreshStudentList() {
        List<Student> students = studentDAO.getAllStudents();
        mainView.displayStudents(students);
    }

    private void searchStudent() {
        Integer id = getStudentIdFromUser("Enter the student ID to search:");
        if (id == null) return;
        Student student = studentDAO.findStudentById(id);
        if (student != null) {
            mainView.displayStudents(List.of(student));
        } else {
            mainView.showMessage("Student not found.");
        }
    }
}
