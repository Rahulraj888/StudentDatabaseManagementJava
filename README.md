# Student Management System

## Project Overview

The Student Management System is a Java-based desktop application designed to manage student records efficiently. It provides functionalities to add, delete, activate, deactivate, search, and display student information. The application follows the MVC (Model-View-Controller) pattern, ensuring a clear separation of concerns and ease of maintenance.

## Project Structure

### Patterns Used

- **MVC (Model-View-Controller) Pattern**: The project is structured to follow the MVC pattern, which separates the application logic into three interconnected components:
    - **Model**: Manages the data and business logic (e.g., `Student`, `StudentDAO`).
    - **View**: Handles the presentation layer and user interface (e.g., `MainView`, `AddStudentView`).
    - **Controller**: Manages communication between the Model and View, handling user inputs and updating the view accordingly (e.g., `StudentController`).

### Class Structure

1. **Config**
    - `LocalConfig`: Stores database credentials and URL.

2. **Constants**
    - `DatabaseConstants`: Declares constants for table and column names used in the database.

3. **Controller**
    - `StudentController`: Manages communication between the UI and the database. It sets action listeners for all the buttons and handles adding, deleting, activating, deactivating, searching, and displaying students.

4. **Model**
    - `DatabaseConnection`: Handles creating and managing the database connection using the Singleton pattern.
    - `Student`: Represents the student entity with fields for ID, first name, last name, email, and active status.
    - `StudentDAO`: Handles database operations such as inserting, updating, deleting, and fetching student records.

5. **View**
    - `AddStudentView`: Popup view for adding a student, including input fields for ID, first name, last name, email, and active status.
    - `MainView`: The main interface that contains buttons for different operations and a list to display student records.
    - `StudentListRenderer`: Custom renderer for displaying student details in the list.

6. **Main**
    - `Main`: The entry point of the application. It initializes the views, DAO, and controller, and sets the main view to visible.

## How Modules are Connected

- **Main Class**: Initializes the main view, add student view, and student DAO. It then creates an instance of the `StudentController`, passing these components to it.
- **StudentController**: Sets action listeners on the main view's buttons to handle user actions. It interacts with `StudentDAO` to perform database operations and updates the views accordingly.
- **Views**: `MainView` displays the list of students and buttons for various operations. `AddStudentView` provides a form to add new students. Both views call methods in `StudentController` to perform actions.
- **Model**: `Student` represents student data. `StudentDAO` performs CRUD operations on the database, using `DatabaseConnection` to connect to the database.

## Usage

### Adding a Student

1. Click the "Add Student" button in the main view.
2. Fill in the ID, first name, last name, email, and active status in the popup form.
3. Click "Submit" to add the student to the database.

### Deleting a Student

1. Click the "Delete Student" button in the main view.
2. Enter the student ID to delete in the input dialog.
3. Confirm the deletion.

### Activating/Deactivating a Student

1. Click the "Activate Student" or "Deactivate Student" button in the main view.
2. Enter the student ID to activate/deactivate in the input dialog.
3. Confirm the action.

### Searching for a Student

1. Click the "Search Student" button in the main view.
2. Enter the student ID to search in the input dialog.
3. The student details will be displayed in a message dialog if found.

### Displaying All Students

1. Click the "Display All Students" button in the main view.
2. The list of all students will be displayed in the main view.

## Dependencies

- **Java SE Development Kit (JDK)**
- **MySQL Database** (Ensure MySQL is installed and running)
- **JDBC Driver** for MySQL

## Setup Instructions

1. Clone the repository.
2. Set up the MySQL database and create a database named `studentmanagement`.
3. Update the `LocalConfig` class with your MySQL username and password.
4. Compile and run the project using your preferred Java IDE or command line tools.
