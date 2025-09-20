package model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.repository.*;

public class DataManager {

	private final StudentRepository studentRepo;
	private final SubjectRepository subjectRepo;
	private final RegistrationRepository registrationRepo;
	private final SubjectStructureRepository structureRepo;

	public DataManager() {
		studentRepo = new StudentRepository();
		subjectRepo = new SubjectRepository();
		registrationRepo = new RegistrationRepository();
		structureRepo = new SubjectStructureRepository();
		loadDataFromFiles();
	}

	public void loadDataFromFiles() {
		studentRepo.loadFromFile("data/students.csv");
		subjectRepo.loadFromFile("data/subjects.csv");
		structureRepo.loadFromFile("data/subjectstructure.csv");
		registrationRepo.loadFromFile("data/registered.csv");
	}

	public void saveDataToFiles() {
		studentRepo.saveToFile("data/students.csv");
		subjectRepo.saveToFile("data/subjects.csv");
		structureRepo.saveToFile("data/subjectstructure.csv");
		registrationRepo.saveToFile("data/registered.csv");
	}

	public void saveData() {
		saveDataToFiles();
	}

	public List<Student> getStudents() {
		return studentRepo.getAll();
	}

	public Student findStudentById(String studentId) {
		return studentRepo.findById(studentId);
	}

	public List<Student> getStudentsSortedByName() {
		return studentRepo.getAllSortedByName();
	}

	public List<Student> getStudentsSortedByAge() {
		return studentRepo.getAllSortedByAge();
	}

	public List<Subject> getAllSubjects() {
		return subjectRepo.getAll();
	}

	public Subject findSubjectById(String subjectId) {
		return subjectRepo.findById(subjectId);
	}

	public List<RegisteredSubject> getCoursesForStudent(String studentId) {
		return registrationRepo.getByStudentId(studentId);
	}

	public List<RegisteredSubject> getRegisteredCoursesByStudent(String studentId) {
		return registrationRepo.getByStudentId(studentId);
	}

	public Map<Student, String> getEnrolledStudentsWithGrades(String subjectId) {
		Map<Student, String> map = new HashMap<>();
		for (RegisteredSubject reg : registrationRepo.getBySubjectId(subjectId)) {
			Student s = findStudentById(reg.getStudentId());
			if (s != null) {
				map.put(s, reg.getGrade() != null ? reg.getGrade() : " ");
			}
		}
		return map;
	}

	public boolean updateGrades(List<RegisteredSubject> updates) {
		try {
			for (RegisteredSubject update : updates) {
				registrationRepo.updateGrade(update.getStudentId(), update.getSubjectId(), update.getGrade());
			}
			saveDataToFiles();
			return true;
		} catch (Exception e) {
			System.err.println("Error updating grades: " + e.getMessage());
			return false;
		}
	}

	public boolean canRegisterSubject(String studentId, String subjectId) {
		Student student = findStudentById(studentId);
		Subject subject = findSubjectById(subjectId);
		if (student == null || subject == null)
			return false;

		int age = student.getAge();
		if (age < 15)
			return false;

		String prereqId = subject.getPrerequisiteSubjectId();
		if (prereqId == null || prereqId.isBlank())
			return true;

		return registrationRepo.hasPassedSubject(studentId, prereqId);
	}

	public void addRegistration(Student student, Subject subject) {
		registrationRepo.registerStudent(student.getStudentId(), subject.getSubjectId(), "W");
		saveDataToFiles();
	}

	public List<Subject> getAvailableSubjectsForStudent(String studentId) {
		Student student = findStudentById(studentId);
		if (student == null)
			return new ArrayList<>();

		return subjectRepo.getAll().stream()
				.filter(s -> !registrationRepo.isRegistered(studentId, s.getSubjectId()))
				.filter(s -> canRegister(studentId, s.getSubjectId()))
				.collect(Collectors.toList());
	}

	public boolean registerStudentToSubject(String studentId, String subjectId) {
		if (registrationRepo.isRegistered(studentId, subjectId))
			return false;
		registrationRepo.registerStudent(studentId, subjectId, null);
		saveDataToFiles();
		return true;
	}

	public boolean registerStudent(String studentId, String subjectId) {
		if (!canRegister(studentId, subjectId))
			return false;
		return registrationRepo.registerStudent(studentId, subjectId, null);
	}

	public boolean canRegister(String studentId, String subjectId) {
		Student student = studentRepo.findById(studentId);
		Subject subject = subjectRepo.findById(subjectId);
		if (student == null || subject == null)
			return false;

		int age = Period.between(student.getBirthDate(), LocalDate.now()).getYears();
		if (age < 15)
			return false;

		String prereqId = subject.getPrerequisiteSubjectId();
		if (prereqId != null && !prereqId.isBlank()) {
			return registrationRepo.hasPassedSubject(studentId, prereqId);
		}

		return true;
	}
}
