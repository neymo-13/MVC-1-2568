package model;

public class Subject {
	private String subjectId;
	private String name;
	private int credits;
	private String instructor;
	private String prerequisiteSubjectId;

	public Subject(String subjectId, String name, int credits, String instructor, String prerequisiteSubjectId) {
		this.subjectId = subjectId;
		this.name = name;
		this.credits = credits;
		this.instructor = instructor;
		this.prerequisiteSubjectId = (prerequisiteSubjectId == null || prerequisiteSubjectId.trim().isEmpty()) ? null
				: prerequisiteSubjectId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getPrerequisiteSubjectId() {
		return prerequisiteSubjectId;
	}

	public void setPrerequisiteSubjectId(String prerequisiteSubjectId) {
		this.prerequisiteSubjectId = prerequisiteSubjectId;
	}

	public String[] toCsvRow() {
		return new String[] {
				this.subjectId,
				this.name,
				String.valueOf(this.credits),
				this.instructor,
				this.prerequisiteSubjectId != null ? this.prerequisiteSubjectId : ""
		};
	}

	public static String[] getCsvHeader() {
		return new String[] {
				"subjectId", "subjectName", "credits", "instructor", "prerequisiteSubjectId"
		};
	}

	public String toString() {
		return this.getName();
	}

}
