package bg.swift.HW14_Ex01;

import java.sql.Date;

public class Student {
	private int studentID;
	private String studentName;
	private Date studentEnrollmentDate;
	private int addressID;

	public Student(int studentID, String studentName, Date studentEnrollmentDate, int addressID) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.studentEnrollmentDate = studentEnrollmentDate;
		this.addressID = addressID;
	}
	
	@Override
	public String toString() {
		String output = String.format(
				"ID: %d%nName: %s%nEnrollment date: " + this.studentEnrollmentDate + "%nAddressID: %s", this.studentID,
				this.studentName, this.addressID);
		return output;
	}
}
