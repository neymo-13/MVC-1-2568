package model.repository;

import util.CsvUtil;
import java.util.*;
import java.util.stream.Collectors;

import model.SubjectStructure;

public class SubjectStructureRepository {
	private final List<SubjectStructure> structures = new ArrayList<>();
	private final CsvUtil csv = new CsvUtil();

	public void loadFromFile(String path) {
		structures.clear();
		List<String[]> rows = csv.read(path);
		if (rows != null) {
			for (String[] r : rows) {
				try {
					structures.add(new SubjectStructure(r[0], r[1], r[2], r[3], Integer.parseInt(r[4])));
				} catch (Exception e) {
					System.err.println("Error loading subject structure: " + Arrays.toString(r));
				}
			}
		}
	}

	public void saveToFile(String path) {
		csv.write(path, structures.stream().map(SubjectStructure::toCsvRow).collect(Collectors.toList()),
				SubjectStructure.getCsvHeader());
	}

	public List<SubjectStructure> getAll() {
		return new ArrayList<>(structures);
	}

	public SubjectStructure findById(String structureId) {
		return structures.stream()
				.filter(s -> s.getSubjectId().equals(structureId))
				.findFirst()
				.orElse(null);
	}
}
