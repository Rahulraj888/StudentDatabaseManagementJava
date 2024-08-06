package view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * Popup view to add student
 * inputs all the fields from the user and adds it to database
 */
public class AddStudentView extends JFrame {
    private final JTextField idField, firstNameField, lastNameField, emailField;
    private final JCheckBox activeCheckBox;
    private final JButton submitButton, cancelButton;

    public AddStudentView() {
        //setting up the view
        setTitle("Add Student");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        idField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        activeCheckBox = new JCheckBox("Active");
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Active:"));
        add(activeCheckBox);
        add(submitButton);
        add(cancelButton);
    }

    //methods to get value from field
    public String getId() {
        return idField.getText();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public boolean isActive() {
        return activeCheckBox.isSelected();
    }

    public void setSubmitButtonActionListener(ActionListener actionListener) {
        submitButton.addActionListener(actionListener);
    }

    public void setCancelButtonActionListener(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
