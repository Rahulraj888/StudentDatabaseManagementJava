package controller;

import model.Student;
import model.StudentDAO;
import view.AddStudentView;
import view.MainView;

import javax.swing.JOptionPane;
import java.util.List;

/**
 * Manages communication between UI and the database(model part)
 * sets action listener for all the buttons. Once the button are clicked, adds of fetches from database
 */
public class StudentController {
    private final MainView mainView;
    private final AddStudentView addStudentView;
    private final StudentDAO studentDAO;

    public StudentController(MainView mainView, AddStudentView addStudentView, StudentDAO studentDAO) {
        //initialize main view, view to add student and StudentDAO
        this.mainView = mainView;
        this.addStudentView = addStudentView;
        this.studentDAO = studentDAO;

        //setup action listener for all button on UI
        this.mainView.setAddStudentButtonActionListener(e -> addStudentView.setVisible(true));
        this.mainView.setDeleteStudentButtonActionListener(e -> deleteStudent());
        this.mainView.setActivateStudentButtonActionListener(e -> activateStudent());
        this.mainView.setDeactivateStudentButtonActionListener(e -> deactivateStudent());
        this.mainView.setDisplayStudentsButtonActionListener(e -> refreshStudentList());
        this.mainView.setSearchStudentButtonActionListener(e -> searchStudent());

        this.addStudentView.setSubmitButtonActionListener(e -> addStudent());
        this.addStudentView.setCancelButtonActionListener(e -> addStudentView.dispose());
    }

    /**
     * Takes input from UI and adds a record to database
     */
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

    /**
     * Takes id as input from user and deleted record from database
     */
    private void deleteStudent() {
        String idStr = mainView.getStudentIdFromUser("Enter the student ID to delete:");
        try {
            int id = Integer.parseInt(idStr);
            //TODO add validation for ID
            boolean isDeleted = studentDAO.deleteStudent(id);

            if (isDeleted) {
                mainView.showMessage("Student deleted successfully!");
                refreshStudentList();
            } else {
                mainView.showMessage("Failed to delete student.");
            }
        } catch (NumberFormatException ex) {
            mainView.showMessage("Please enter a valid ID.");
        }
    }

    /**
     * Takes id as input and activates the student
     */
    private void activateStudent() {
        String idStr = mainView.getStudentIdFromUser("Enter the student ID to activate:");
        try {
            //TODO add validation for ID
            int id = Integer.parseInt(idStr);
            boolean isUpdated = studentDAO.updateStudentStatus(id, true);

            if (isUpdated) {
                mainView.showMessage("Student activated successfully!");
                refreshStudentList();
            } else {
                mainView.showMessage("Failed to activate student.");
            }
        } catch (NumberFormatException ex) {
            mainView.showMessage("Please enter a valid ID.");
        }
    }

    /**
     * takes id as input from the user and deactivates the student
     */
    private void deactivateStudent() {
        String idStr = mainView.getStudentIdFromUser("Enter the student ID to deactivate:");
        try {
            int id = Integer.parseInt(idStr);
            boolean isUpdated = studentDAO.updateStudentStatus(id, false);
            //TODO add validation for id
            if (isUpdated) {
                mainView.showMessage("Student deactivated successfully!");
                refreshStudentList();
            } else {
                mainView.showMessage("Failed to deactivate student.");
            }
        } catch (NumberFormatException ex) {
            mainView.showMessage("Please enter a valid ID.");
        }
    }

    /**
     * method to update the UI as soon the data is changed
     */
    private void refreshStudentList() {
        List<Student> students = studentDAO.getAllStudents();
        mainView.displayStudents(students);
    }

    /**
     * Takes id as input from user and searches for student with that ID.
     */
    private void searchStudent() {
        String idStr = mainView.getStudentIdFromUser("Enter the student ID to search:");
        try {
            int id = Integer.parseInt(idStr);
            Student student = studentDAO.findStudentById(id);
            if (student != null) {
                JOptionPane.showMessageDialog(mainView, student); //Display student details as a string
            } else {
                mainView.showMessage("Student not found.");
            }
        } catch (NumberFormatException ex) {
            mainView.showMessage("Please enter a valid ID.");
        }
    }
}
