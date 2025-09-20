package controller;

import model.DataManager;
import model.Student;
import model.Subject;
import model.RegisteredSubject;
import view.AdminDashboardView;
import view.GradeEntryView;
import view.StudentDashboardView;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminController {
	private AppController appController;
	private DataManager dataManager;

	private AdminDashboardView adminDashboardView;
	private GradeEntryView gradeView;

	public AdminController(AppController appController, DataManager dataManager) {
		this.appController = appController;
		this.dataManager = dataManager;
	}

	public void showAdminDashboard() {
		adminDashboardView = new AdminDashboardView();
		adminDashboardView.addFilterListener(e -> handleFilter());
		adminDashboardView.addGradeEntryButtonListener(e -> handleShowGradeEntryView());
		List<Student> sortedStudents = dataManager.getStudentsSortedByName();
		adminDashboardView.displayStudents(sortedStudents);
		adminDashboardView.addRowClickListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					handleShowStudentDetail();
				}
			}
		});

		adminDashboardView.setVisible(true);
	}

	private void handleFilter() {
		String filterText = adminDashboardView.getFilterText().toLowerCase();
		List<Student> filtered = dataManager.getStudentsSortedByName().stream()
				.filter(s -> s.getCurrentSchool().toLowerCase().contains(filterText))
				.collect(Collectors.toList());
		adminDashboardView.displayStudents(filtered);
	}

	private void handleShowStudentDetail() {
		String selectedId = adminDashboardView.getSelectedStudentId();
		if (selectedId != null) {
			Student selectedStudent = dataManager.findStudentById(selectedId);
			if (selectedStudent != null) {
				StudentDashboardView detailView = new StudentDashboardView();
				detailView.setRegisterButtonVisible(false);
				detailView.displayStudentInfo(selectedStudent);
				detailView.displayGrades(dataManager.getCoursesForStudent(selectedId));
				detailView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				detailView.setVisible(true);
			}
		}
	}

	private void handleShowGradeEntryView() {
		gradeView = new GradeEntryView();

		List<Subject> allSubjects = dataManager.getAllSubjects();
		gradeView.populateSubjectDropdown(allSubjects);
		gradeView.getSubjectComboBox().addActionListener(e -> handleLoadStudentsForGrading());
		gradeView.getSaveGradeButton().addActionListener(e -> handleSaveGrade());
		gradeView.setVisible(true);
	}

	private void handleLoadStudentsForGrading() {
		if (gradeView == null)
			return;

		Subject subject = gradeView.getSelectedSubject();
		if (subject == null) {
			gradeView.showMessage("Please select a subject.");
			return;
		}

		Map<Student, String> enrolled = dataManager.getEnrolledStudentsWithGrades(subject.getSubjectId());
		if (enrolled.isEmpty()) {
			gradeView.showMessage("No students registered in this course.");
		} else {
			gradeView.displayEnrolledStudents(enrolled);
		}
	}

	private void handleSaveGrade() {
		if (gradeView == null)
			return;

		Subject subject = gradeView.getSelectedSubject();
		if (subject == null) {
			gradeView.showMessage("Please select a subject first.");
			return;
		}

		int selectedRow = gradeView.getGradesTable().getSelectedRow();
		if (selectedRow == -1) {
			gradeView.showMessage("Please select a student first.");
			return;
		}

		String studentId = (String) gradeView.getTableModel().getValueAt(selectedRow, 0);
		String grade = (String) gradeView.getGradeComboBox().getSelectedItem();

		RegisteredSubject update = new RegisteredSubject(studentId, subject.getSubjectId(), grade);
		boolean success = dataManager.updateGrades(List.of(update));

		if (success) {
			gradeView.updateStudentGrade(studentId, grade);
			gradeView.showMessage("Grade saved successfully!");
		} else {
			gradeView.showError("Failed to save grade.");
		}
	}

}
