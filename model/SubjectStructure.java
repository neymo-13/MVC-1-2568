package model;

public class SubjectStructure {
    private String curriculumId;
    private String curriculumName;
    private String department;
    private String subjectId;
    private int semester;

    public SubjectStructure(String curriculumId, String curriculumName, String department, String subjectId, int semester) {
        this.curriculumId = curriculumId;
        this.curriculumName = curriculumName;
        this.department = department;
        this.subjectId = subjectId;
        this.semester = semester;
    }

    public String getCurriculumId() { return curriculumId; }
    public String getSubjectId() { return subjectId; }
    public int getSemester() { return semester; }
    public String getCurriculumName() { return curriculumName; }
    public String getDepartment() { return department; }

	public String[] toCsvRow() {
        return new String[]{
            this.curriculumId,
            this.curriculumName,
            this.department,
            this.subjectId,
            String.valueOf(this.semester)
        };
    }

	public boolean isForCurriculum(String curriculumId) {
        return this.curriculumId.equals(curriculumId);
    }

    public static String[] getCsvHeader() {
        return new String[]{
            "curriculumId", "curriculumName", "department", "subjectId", "semester"
        };
    }
}
