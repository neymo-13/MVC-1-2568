package model;

public class AuthService {

	private final String ADMIN_USERNAME = "admin";
	private final String ADMIN_PASSWORD = "admin1234";

	private DataManager dataManager;

	public AuthService(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public String login(String username, String password) {
		if (ADMIN_USERNAME.equalsIgnoreCase(username) && ADMIN_PASSWORD.equals(password)) {
			return "ADMIN";
		}

		Student student = dataManager.findStudentById(username);

		if (student != null && student.getStudentId().equals(password)) {
			return student.getStudentId();
		}

		return "INVALID";
	}
}