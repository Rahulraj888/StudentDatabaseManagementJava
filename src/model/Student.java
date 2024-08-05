package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Stores information related to student
 */
@Setter
@Getter
@ToString
public class Student {

    private int id;
    private String firstName, lastName, email;
    private boolean active;

    public Student(int id, String firstName, String lastName, String email, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
    }

}
