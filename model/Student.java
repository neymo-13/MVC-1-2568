package model;

import java.time.LocalDate;
import java.time.Period;

public class Student {
	private String studentId;
	private String prefix;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String currentSchool;
	private String email;
	private String courseId;

	public Student(String studentId, String prefix, String firstName, String lastName,
			LocalDate birthDate, String currentSchool, String email, String courseId) {
		this.studentId = studentId;
		this.prefix = prefix;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.currentSchool = currentSchool;
		this.email = email;
		this.courseId = courseId;
	}

	public String[] toCsvRow() {
		return new String[] {
				this.studentId,
				this.prefix,
				this.firstName,
				this.lastName,
				this.birthDate.toString(),
				this.currentSchool,
				this.email,
				this.courseId
		};
	}

	public static String[] getCsvHeader() {
		return new String[] {
				"studentId", "prefix", "firstName", "lastName", "birthDate", "school", "email", "courseId"
		};
	}

	public int getAge() {
		return Period.between(this.birthDate, LocalDate.now()).getYears();
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getCurrentSchool() {
		return currentSchool;
	}

	public void setCurrentSchool(String currentSchool) {
		this.currentSchool = currentSchool;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurriculumId() {
		return courseId;
	}

	public void setCurriculumId(String courseId) {
		this.courseId = courseId;
	}

	public String getFullName() {
		return prefix + " " + firstName + " " + lastName;
	}
}
