package view;

import model.Student;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main View that contains button on left side and displays all the students on the left side
 */
public class MainView extends JFrame {
    private JButton addStudentButton, deleteStudentButton, activateStudentButton, deactivateStudentButton, displayStudentsButton, searchStudentButton;
    private DefaultListModel<Student> studentListModel; //to store and manage data for JList

    public MainView() {
        setTitle("Student Management System");
        setSize(1250, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(setButtonPanel(), BorderLayout.WEST); //button panel
        add(studentsListPanel(), BorderLayout.CENTER); //list panel
    }

    //creates panel to display the list of students
    JPanel studentsListPanel() {
        studentListModel = new DefaultListModel<>();
        JList<Student> studentList = new JList<>(studentListModel);
        studentList.setCellRenderer(new StudentListRenderer()); //customizing each cell

        // Center the list view
        JPanel studentListPanel = new JPanel(new BorderLayout());
        studentListPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);
        studentListPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around list view
        return studentListPanel;
    }

    //creates panel for all the buttons
    JPanel setButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 5, 5)); // gap added between buttons
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); //padding for buttons

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
        return buttonPanel;
    }

    //methods to set up action listeners for all buttons
    //this methods will be called from student controllers where the implementation is there
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

    //input dialogue to get id from user
    public String getStudentIdFromUser(String message) {
        return JOptionPane.showInputDialog(this, message);
    }
}
