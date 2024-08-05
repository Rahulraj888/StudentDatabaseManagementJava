package view;

import model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentListRenderer extends JPanel implements ListCellRenderer<Student> {
    private final JLabel idLabel, nameLabel, emailLabel, activeLabel;

    public StudentListRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        idLabel = new JLabel();
        nameLabel = new JLabel();
        emailLabel = new JLabel();
        activeLabel = new JLabel();

        add(idLabel);
        add(nameLabel);
        add(emailLabel);
        add(activeLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Student> list, Student student, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        idLabel.setText("ID: " + student.getId());
        nameLabel.setText("Name: " + student.getFirstName() + " " + student.getLastName());
        emailLabel.setText("Email: " + student.getEmail());
        activeLabel.setText("Active: " + (student.isActive() ? "Yes" : "No"));

//        if (isSelected) {
//            setBackground(list.getSelectionBackground());
//            setForeground(list.getSelectionForeground());
//        } else {
//            setBackground(list.getBackground());
//            setForeground(list.getForeground());
//        }

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));

        return this;
    }
}
