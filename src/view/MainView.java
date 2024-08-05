package view;

import model.Student;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame {
    private final JButton addStudentButton, deleteStudentButton, activateStudentButton,
            deactivateStudentButton, displayStudentsButton, searchStudentButton;
    private final JList<Student> studentList;
    private final DefaultListModel<Student> studentListModel;

    public MainView() {
        setTitle("Student Management System");
        setSize(800, 600); // Increased size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 5, 5)); // Add gaps between buttons
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around buttons

        addStudentButton = new JButton("Add Student");
        deleteStudentButton = new JButton("Delete Student");
        activateStudentButton = new JButton("Activate Student");
        deactivateStudentButton = new JButton("Deactivate Student");
        displayStudentsButton = new JButton("Display All Students");
        searchStudentButton = new JButton("Search Student");

        buttonPanel.add(addStudentButton);
        buttonPanel.add(deleteStudentButton);
        buttonPanel.add(activateStudentButton);
        buttonPanel.add(deactivateStudentButton);
        buttonPanel.add(displayStudentsButton);
        buttonPanel.add(searchStudentButton);

        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentList.setCellRenderer(new StudentListRenderer());
        JScrollPane scrollPane = new JScrollPane(studentList);

        // Center the list view
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around list view

        add(buttonPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void setAddStudentButtonActionListener(ActionListener actionListener) {
        addStudentButton.addActionListener(actionListener);
    }

    public void setDeleteStudentButtonActionListener(ActionListener actionListener) {
        deleteStudentButton.addActionListener(actionListener);
    }

    public void setActivateStudentButtonActionListener(ActionListener actionListener) {
        activateStudentButton.addActionListener(actionListener);
    }

    public void setDeactivateStudentButtonActionListener(ActionListener actionListener) {
        deactivateStudentButton.addActionListener(actionListener);
    }

    public void setDisplayStudentsButtonActionListener(ActionListener actionListener) {
        displayStudentsButton.addActionListener(actionListener);
    }

    public void setSearchStudentButtonActionListener(ActionListener actionListener) {
        searchStudentButton.addActionListener(actionListener);
    }

    public void displayStudents(List<Student> students) {
        studentListModel.clear();
        students.forEach(studentListModel::addElement);
    }


    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public String getStudentIdFromUser(String message) {
        return JOptionPane.showInputDialog(this, message);
    }
}
