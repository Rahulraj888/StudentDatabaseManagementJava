import model.StudentDAO;
import view.MainView;
import controller.StudentController;
import view.AddStudentView;

/**
 * starting point of the project
 * renders the main view and initializes the other classes as well
 */
public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView(); //setup main view
        AddStudentView addStudentView = new AddStudentView();
        StudentDAO studentDAO = new StudentDAO();
        StudentController controller = new StudentController(mainView, addStudentView, studentDAO);
        mainView.setVisible(true);
    }
}