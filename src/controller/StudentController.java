package controller;

import model.Student;
import model.StudentDAO;
import view.AddStudentView;
import view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentController {
    private final MainView mainView;
    private final AddStudentView addStudentView;
    private final StudentDAO studentDAO;

    public StudentController(MainView mainView, AddStudentView addStudentView, StudentDAO studentDAO) {
        this.mainView = mainView;
        this.addStudentView = addStudentView;
        this.studentDAO = studentDAO;

        this.mainView.setAddStudentButtonActionListener(new AddStudentButtonListener());
        this.mainView.setDeleteStudentButtonActionListener(new DeleteStudentButtonListener());
        this.mainView.setActivateStudentButtonActionListener(new ActivateStudentButtonListener());
        this.mainView.setDeactivateStudentButtonActionListener(new DeactivateStudentButtonListener());
        this.mainView.setDisplayStudentsButtonActionListener(new DisplayStudentsButtonListener());
        this.mainView.setSearchStudentButtonActionListener(new SearchStudentButtonListener());

        this.addStudentView.setSubmitButtonActionListener(new SubmitButtonListener());
        this.addStudentView.setCancelButtonActionListener(e -> addStudentView.dispose());
    }

    private class AddStudentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addStudentView.setVisible(true);
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
    }

    private class DeleteStudentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idStr = mainView.getStudentIdFromUser("Enter the student ID to delete:");
            try {
                int id = Integer.parseInt(idStr);
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
    }

    private class ActivateStudentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idStr = mainView.getStudentIdFromUser("Enter the student ID to activate:");
            try {
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
    }

    private class DeactivateStudentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idStr = mainView.getStudentIdFromUser("Enter the student ID to deactivate:");
            try {
                int id = Integer.parseInt(idStr);
                boolean isUpdated = studentDAO.updateStudentStatus(id, false);

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
    }

    private class DisplayStudentsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshStudentList();
        }
    }

    private class SearchStudentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idStr = mainView.getStudentIdFromUser("Enter the student ID to search:");
            try {
                int id = Integer.parseInt(idStr);
                Student student = studentDAO.findStudentById(id);

                if (student != null) {
                    JOptionPane.showMessageDialog(mainView, student.toString());
                } else {
                    mainView.showMessage("Student not found.");
                }
            } catch (NumberFormatException ex) {
                mainView.showMessage("Please enter a valid ID.");
            }
        }
    }

    private void refreshStudentList() {
        List<Student> students = studentDAO.getAllStudents();
        mainView.displayStudents(students);
    }
}
