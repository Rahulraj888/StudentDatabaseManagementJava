import model.StudentDAO;
import view.MainView;
import controller.StudentController;
import view.AddStudentView;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView(); //setup main view
        AddStudentView addStudentView = new AddStudentView();
        StudentDAO studentDAO = new StudentDAO();
        StudentController controller = new StudentController(mainView, addStudentView, studentDAO);
        mainView.setVisible(true);
    }
}