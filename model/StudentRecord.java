package model;

public class StudentRecord {
	private String studentId;
	private String subjectId;
	private String grade;

	public StudentRecord(String studentId, String subjectId, String grade) {
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.grade = grade;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public boolean isPassed() {
		return grade != null && !"F".equals(grade);
	}

	public String[] toCsvRow() {
		return new String[] {
				studentId, subjectId, grade != null ? grade : ""
		};
	}

	public static String[] getCsvHeader() {
		return new String[] { "studentId", "subjectId", "grade" };
	}
}
