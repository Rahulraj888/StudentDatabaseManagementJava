package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static constants.DatabaseConstants.ACTIVE;
import static constants.DatabaseConstants.ID;
import static constants.DatabaseConstants.EMAIL;
import static constants.DatabaseConstants.FIRST_NAME;
import static constants.DatabaseConstants.LAST_NAME;
import static constants.DatabaseConstants.TABLE_NAME;

/***
 * Student Data Access Object
 * Handles inserting, updating and deleting data in student table
 */
public class StudentDAO {
    private Connection connection;
    private static final int MAX_RETRIES = 3; // Maximum retries to get DB connection
    private static final long RETRY_DELAY_MS = 2000; // 2 seconds delay

    /**
     * Retry mechanism used here to create database object. Will retry 3 times to connect
     */
    public StudentDAO() {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                connection = DatabaseConnection.getInstance().getConnection();
                System.out.println("Connected to the database successfully.");
                break;
            } catch (SQLException e) {
                attempts++;
                System.out.println("Attempt " + attempts + " failed: " + e.getMessage());
                if (attempts >= MAX_RETRIES) {
                    System.out.println("Maximum retries reached. Could not connect to the database.");
                } else {
                    System.out.println("Retrying connection in " + (RETRY_DELAY_MS / 1000) + " seconds...");
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.out.println("Retry interrupted.");
                    }
                }
            }
        }
    }

    /**
     * Adds student details to database
     *
     * @return boolean
     */
    public boolean addStudent(Student student) {
        String query = "INSERT INTO " + TABLE_NAME + " (" + ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + EMAIL + ", " + ACTIVE + ") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getEmail());
            statement.setBoolean(5, student.isActive());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Exception occurred while adding student details to database " + e);
        }
        return false;
    }

    /**
     * Delete data of student with given id
     *
     * @return boolean
     */
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Exception occurred while deleting student details " + e);
        }
        return false;
    }

    /**
     * Fetches list of all students
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + FIRST_NAME + " ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt(ID),
                        resultSet.getString(FIRST_NAME),
                        resultSet.getString(LAST_NAME),
                        resultSet.getString(EMAIL),
                        resultSet.getBoolean(ACTIVE)
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while fetching all student details " + e);
        }
        return students;
    }

    /**
     * Fetch details of student with given id
     */
    public Student findStudentById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getInt(ID),
                        resultSet.getString(FIRST_NAME),
                        resultSet.getString(LAST_NAME),
                        resultSet.getString(EMAIL),
                        resultSet.getBoolean(ACTIVE)
                );
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while fetching student details: " + e.getMessage());
        }
        return null;
    }

    /**
     * Update active status of student
     *
     * @return boolean
     */
    public boolean updateStudentStatus(int id, boolean isActive) {
        String query = "UPDATE " + TABLE_NAME + " SET " + ACTIVE + " = ? WHERE " + ID + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, isActive);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Exception occurred while updating student details " + e);
        }
        return false;
    }
}