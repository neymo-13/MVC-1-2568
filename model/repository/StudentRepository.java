package model.repository;

import util.CsvUtil;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import model.Student;

public class StudentRepository {
	private final List<Student> students = new ArrayList<>();
	private final CsvUtil csv = new CsvUtil();

	public void loadFromFile(String path) {
		students.clear();
		List<String[]> rows = csv.read(path);
		if (rows != null) {
			for (String[] r : rows) {
				try {
					LocalDate birthDate = LocalDate.parse(r[4]);
					int age = Period.between(birthDate, LocalDate.now()).getYears();

					if (age < 15) {
						System.out.println("Skipping student under 15: " + r[0] + " " + r[2] + " " + r[3]);
						continue;
					}
					students.add(new Student(r[0], r[1], r[2], r[3],
							LocalDate.parse(r[4]), r[5], r[6], r[7]));
				} catch (Exception e) {
					System.err.println("Error loading student: " + Arrays.toString(r));
				}
			}
		}
	}

	public void saveToFile(String path) {
		csv.write(path, students.stream().map(Student::toCsvRow).collect(Collectors.toList()), Student.getCsvHeader());
	}

	public List<Student> getAll() {
		return new ArrayList<>(students);
	}

	public Student findById(String studentId) {
		return students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst().orElse(null);
	}

	public List<Student> getAllSortedByName() {
		return students.stream()
				.sorted(Comparator.comparing(s -> (s.getFirstName() + " " + s.getLastName()).toLowerCase()))
				.collect(Collectors.toList());
	}

	public List<Student> getAllSortedByAge() {
		return students.stream().sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList());
	}
}
