import model.Student;
import model.StudentDAO;

public class Main {

    public static void main(String[] args) {
        dbCheck();
    }

    static void dbCheck() {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.addStudent(new Student(1, "Rahul", "Reddaveni",
                "test@gmail.com", true));
        studentDAO.addStudent(new Student(2, "Malavika", "Ajit",
                "test@gmail.com", true));
        studentDAO.getAllStudents().forEach(System.out::println);
    }

}