package bg.swift.HW14_Ex01;

public class Teacher {
	private int teacherID;
	private String teacherName;
	private String teacherEmail;
	private double teacherSalary;
	private int addressID;

	public Teacher(int teacherID, String teacherName, String teacherEmail, double teacherSalary, int addressID) {
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.teacherEmail = teacherEmail;
		this.teacherSalary = teacherSalary;
		this.addressID = addressID;
	}
	
	@Override
	public String toString() {
		String output = String.format("ID: %d%nName: %s%nemail: %s%nsalary: %.2f%naddressID: %d", this.teacherID,
				this.teacherName, this.teacherEmail, this.teacherSalary, this.addressID);
		return output;
	}
}
