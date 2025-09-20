package model.repository;

import util.CsvUtil;
import java.util.*;
import java.util.stream.Collectors;

import model.Subject;

public class SubjectRepository {
	private final List<Subject> subjects = new ArrayList<>();
	private final CsvUtil csv = new CsvUtil();

	public void loadFromFile(String path) {
		subjects.clear();
		List<String[]> rows = csv.read(path);
		if (rows != null) {
			for (String[] r : rows) {
				try {
					subjects.add(new Subject(r[0], r[1], Integer.parseInt(r[2]), r[3], r[4]));
				} catch (Exception e) {
					System.err.println("Error loading subject: " + Arrays.toString(r));
				}
			}
		}
	}

	public void saveToFile(String path) {
		csv.write(path, subjects.stream().map(Subject::toCsvRow).collect(Collectors.toList()), Subject.getCsvHeader());
	}

	public List<Subject> getAll() {
		return new ArrayList<>(subjects);
	}

	public Subject findById(String subjectId) {
		return subjects.stream().filter(s -> s.getSubjectId().equals(subjectId)).findFirst().orElse(null);
	}
}
