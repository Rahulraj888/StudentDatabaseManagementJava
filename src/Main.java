import model.Student;
import model.StudentDAO;
import view.MainView;
import controller.StudentController;
import view.AddStudentView;

public class Main {

    public static void main(String[] args) {
//        dbCheck();

        MainView mainView = new MainView();
        AddStudentView addStudentView = new AddStudentView();
        StudentDAO studentDAO = new StudentDAO();
        StudentController controller = new StudentController(mainView, addStudentView, studentDAO);
        mainView.setVisible(true);

    }

    static void dbCheck() {
        StudentDAO studentDAO = new StudentDAO();
        System.out.println(studentDAO.addStudent(new Student(1, "Rahul", "Reddaveni",
                "test@gmail.com", true)));
        System.out.println(studentDAO.addStudent(new Student(2, "Malavika", "Ajit",
                "test@gmail.com", true)));
        studentDAO.getAllStudents().forEach(System.out::println);
    }

}