package controller;

import model.AuthService;
import model.DataManager;

public class AppController {

    private DataManager dataManager;
    private AuthService authService;

    private AuthController authController;
    private AdminController adminController;
    private StudentController studentController;

    public AppController() {
        dataManager = new DataManager();
        authService = new AuthService(dataManager);

        authController = new AuthController(this, dataManager, authService);
        adminController = new AdminController(this, dataManager);
        studentController = new StudentController(this, dataManager);
    }

    public void start() {
        authController.showLoginView();
    }

    public void showAdminDashboard() {
        adminController.showAdminDashboard();
    }

    public void showStudentDashboard(String studentId) {
        studentController.showStudentDashboard(studentId);
    }
}
