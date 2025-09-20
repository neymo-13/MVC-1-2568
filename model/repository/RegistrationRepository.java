package model.repository;

import util.CsvUtil;
import java.util.*;
import java.util.stream.Collectors;

import model.RegisteredSubject;

public class RegistrationRepository {
	private final List<RegisteredSubject> registrations = new ArrayList<>();
	private final CsvUtil csv = new CsvUtil();

	public void loadFromFile(String path) {
		registrations.clear();
		List<String[]> rows = csv.read(path);
		if (rows != null) {
			for (String[] r : rows) {
				try {
					registrations.add(new RegisteredSubject(r[0], r[1], r.length > 2 ? r[2] : null));
				} catch (Exception e) {
					System.err.println("Error loading registration: " + Arrays.toString(r));
				}
			}
		}
	}

	public void saveToFile(String path) {
		csv.write(path, registrations.stream().map(RegisteredSubject::toCsvRow).collect(Collectors.toList()),
				RegisteredSubject.getCsvHeader());
	}

	public List<RegisteredSubject> getByStudentId(String studentId) {
		return registrations.stream().filter(r -> r.getStudentId().equals(studentId)).collect(Collectors.toList());
	}

	public List<RegisteredSubject> getBySubjectId(String subjectId) {
		return registrations.stream().filter(r -> r.getSubjectId().equals(subjectId)).collect(Collectors.toList());
	}

	public boolean addRegistration(String studentId, String subjectId) {
		boolean exists = registrations.stream()
				.anyMatch(r -> r.getStudentId().equals(studentId) && r.getSubjectId().equals(subjectId));
		if (exists)
			return false;
		registrations.add(new RegisteredSubject(studentId, subjectId, null));
		return true;
	}

	public List<RegisteredSubject> getAll() {
		return new ArrayList<>(registrations);
	}

	public boolean updateGrade(String studentId, String subjectId, String grade) {
		for (RegisteredSubject reg : registrations) {
			if (reg.getStudentId().equals(studentId) && reg.getSubjectId().equals(subjectId)) {
				reg.setGrade(grade);
				return true;
			}
		}
		return false;
	}

	public boolean registerStudent(String studentId, String subjectId, String grade) {
		if (isRegistered(studentId, subjectId))
			return false;

		registrations.add(new RegisteredSubject(studentId, subjectId, grade));
		return true;
	}

	public boolean isRegistered(String studentId, String subjectId) {
		return registrations.stream()
				.anyMatch(r -> r.getStudentId().equals(studentId) &&
						r.getSubjectId().equals(subjectId));
	}

	public boolean hasPassedSubject(String studentId, String subjectId) {
		return registrations.stream()
				.anyMatch(r -> r.getStudentId().equals(studentId) &&
						r.getSubjectId().equals(subjectId) &&
						r.getGrade() != null &&
						!"F".equalsIgnoreCase(r.getGrade()));
	}

}
