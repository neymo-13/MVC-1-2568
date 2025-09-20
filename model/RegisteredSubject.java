package model;

public class RegisteredSubject {
	private String studentId;
	private String subjectId;
	private String grade;

	public RegisteredSubject(String studentId, String subjectId, String grade) {
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.grade = (grade == null || grade.trim().isEmpty()) ? null : grade;
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

	public boolean hasPassed() {
		return (grade != null && !(grade.equals("F")));
	}

	public String[] toCsvRow() {
		return new String[] {
				this.studentId,
				this.subjectId,
				this.grade
		};
	}

	public static String[] getCsvHeader() {
		return new String[] {
				"studentId", "subjectId", "grade"
		};
	}

}
