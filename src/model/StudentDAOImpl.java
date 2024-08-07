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
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

import java.util.logging.Logger;

/**
 * Student Data Access Object Implementation
 * Handles inserting, updating, and deleting data in student table
 */
public class StudentDAOImpl implements StudentDAO {
    private final Connection connection;
    private static final int MAX_RETRIES = 3; //maximum number of retries
    private static final long RETRY_DELAY_MS = 2000; //delay for retrying
    private static final Logger logger = Logger.getLogger(StudentDAOImpl.class.getName());


    public StudentDAOImpl() {
        int attempts = 0;
        Connection tempConnection = null;
        // retry mechanism added to connect to database
        while (attempts < MAX_RETRIES) {
            try {
                tempConnection = DatabaseConnection.getInstance().getConnection();
                logger.log(INFO, "Connected to the database successfully.");
                break;
            } catch (SQLException e) {
                attempts++;
                logger.log(INFO, "Attempt " + attempts + " failed: " + e.getMessage());
                if (attempts >= MAX_RETRIES) {
                    logger.log(INFO, "Maximum retries reached. Could not connect to the database.");
                } else {
                    logger.log(INFO, "Retrying connection in " + (RETRY_DELAY_MS / 1000) + " seconds...");
                    try {
                        Thread.sleep(RETRY_DELAY_MS); //make thread sleep for 2 seconds
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        logger.log(INFO, "Retry interrupted.");
                    }
                }
            }
        }
        this.connection = tempConnection;
    }

    //add student to database
    @Override
    public boolean addStudent(Student student) {
        String query = "INSERT INTO " + TABLE_NAME + " (" + ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", "
                + EMAIL + ", " + ACTIVE + ") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) { //connection will close automatically
            setParametersToStatement(statement, student);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(SEVERE, "Exception occurred while adding student details to database ", e);
        }
        return false;
    }

    private void setParametersToStatement(PreparedStatement statement, Student student) throws SQLException {
        statement.setInt(1, student.getId());
        statement.setString(2, student.getFirstName());
        statement.setString(3, student.getLastName());
        statement.setString(4, student.getEmail());
        statement.setBoolean(5, student.isActive());
    }

    //delete student from database
    @Override
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(SEVERE, "Exception occurred while deleting student details: ", e);
        }
        return false;
    }

    //fetch details of all students
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + FIRST_NAME + " ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                students.add(getStudentFromResult(resultSet));
            }
        } catch (SQLException e) {
            logger.log(SEVERE, "Exception occurred while fetching all student details: ", e);
        }
        return students;
    }

    //find details of student
    @Override
    public Student findStudentById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getStudentFromResult(resultSet);
            }
        } catch (SQLException e) {
            logger.log(SEVERE, "Exception occurred while fetching student details", e);
        }
        return null;
    }

    private Student getStudentFromResult(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getInt(ID),
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getString(EMAIL),
                resultSet.getBoolean(ACTIVE)
        );
    }

    //update student status
    @Override
    public boolean updateStudentStatus(int id, boolean isActive) {
        String query = "UPDATE " + TABLE_NAME + " SET " + ACTIVE + " = ? WHERE " + ID + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, isActive);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(SEVERE, "Exception occurred while updating student details", e);
        }
        return false;
    }
}
