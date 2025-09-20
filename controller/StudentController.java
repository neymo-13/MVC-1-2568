package controller;

import model.DataManager;
import model.Student;
import model.Subject;
import view.StudentDashboardView;
import view.CourseRegistrationView;

import java.util.List;

public class StudentController {
	private AppController appController;
	private DataManager dataManager;

	private StudentDashboardView studentDashboardView;
	private Student currentStudent;

	public StudentController(AppController appController, DataManager dataManager) {
		this.appController = appController;
		this.dataManager = dataManager;
	}

	public void showStudentDashboard(String studentId) {
		currentStudent = dataManager.findStudentById(studentId);
		if (currentStudent == null)
			return;

		studentDashboardView = new StudentDashboardView();
		studentDashboardView.addRegisterButtonListener(e -> handleShowCourseRegistration());
		studentDashboardView.displayStudentInfo(currentStudent);
		studentDashboardView.displayGrades(dataManager.getCoursesForStudent(studentId));
		studentDashboardView.setVisible(true);
	}

	private void handleShowCourseRegistration() {
		if (currentStudent == null)
			return;

		CourseRegistrationView registrationView = new CourseRegistrationView();

		List<Subject> available = dataManager.getAvailableSubjectsForStudent(currentStudent.getStudentId());
		registrationView.displayAvailableSubjects(available);

		registrationView.getBackButton().addActionListener(e -> registrationView.dispose());

		registrationView.getRegisterButton().addActionListener(e -> {
			String subjectId = registrationView.getSelectedSubjectId();
			if (subjectId == null) {
				registrationView.showError("Please select a subject first.");
				return;
			}

			if (currentStudent.getAge() < 15) {
				registrationView.showError("You are too young to register for courses.");
				return;
			}
			boolean canRegister = dataManager.canRegisterSubject(currentStudent.getStudentId(), subjectId);
			if (!canRegister) {
				registrationView.showError("You must complete required subjects first!");
				return;
			}

			dataManager.registerStudentToSubject(currentStudent.getStudentId(), subjectId);
			registrationView.showMessage("Registration successful!");

			studentDashboardView.displayGrades(
					dataManager.getCoursesForStudent(currentStudent.getStudentId()));

			List<Subject> updatedAvailable = dataManager.getAvailableSubjectsForStudent(currentStudent.getStudentId());
			registrationView.displayAvailableSubjects(updatedAvailable);
		});

		registrationView.setVisible(true);
	}
}
