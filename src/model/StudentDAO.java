package model;

import java.util.List;

/**
 * interface to hide the implementation details
 */
public interface StudentDAO {
    boolean addStudent(Student student);

    boolean deleteStudent(int id);

    List<Student> getAllStudents();

    Student findStudentById(int id);

    boolean updateStudentStatus(int id, boolean isActive);
}
