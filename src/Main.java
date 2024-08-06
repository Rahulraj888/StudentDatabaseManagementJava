import model.StudentDAO;
import model.StudentDAOImpl;
import view.MainView;
import controller.StudentController;
import view.AddStudentView;

/**
 * starting point of the project
 * renders the main view and initializes the other classes as well
 */
public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        AddStudentView addStudentView = new AddStudentView();
        StudentDAO studentDAO = new StudentDAOImpl();
        new StudentController(mainView, addStudentView, studentDAO);
        mainView.setVisible(true);
    }
}