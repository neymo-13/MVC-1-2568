package controller;

import model.AuthService;
import model.DataManager;
import model.Student;
import view.LoginView;

public class AuthController {
	private AppController appController;
	private DataManager dataManager;
	private AuthService authService;
	private LoginView loginView;

	public AuthController(AppController appController, DataManager dataManager, AuthService authService) {
		this.appController = appController;
		this.dataManager = dataManager;
		this.authService = authService;
	}

	public void showLoginView() {
		loginView = new LoginView();
		loginView.addLoginListener(e -> handleLogin());
		loginView.setVisible(true);
	}

	private void handleLogin() {
		String username = loginView.getUsername();
		String password = loginView.getPassword();
		String role = authService.login(username, password);

		switch (role) {
			case "ADMIN":
				loginView.dispose();
				appController.showAdminDashboard();
				break;
			case "INVALID":
				loginView.displayError("Invalid username or password.");
				break;
			default:
				Student student = dataManager.findStudentById(role);
				if (student != null) {
					loginView.dispose();
					appController.showStudentDashboard(student.getStudentId());
				} else {
					loginView.displayError("Student data not found for ID: " + role);
				}
				break;
		}
	}
}
