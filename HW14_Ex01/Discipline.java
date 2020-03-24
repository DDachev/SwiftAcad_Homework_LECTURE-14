package bg.swift.HW14_Ex01;

public class Discipline {
	private int disciplineID;
	private String disciplineName;

	public Discipline(int disciplineID, String disciplineName) {
		this.disciplineID = disciplineID;
		this.disciplineName = disciplineName;
	}

	@Override
	public String toString() {
		String output = String.format("ID: %s%nName: %s", this.disciplineID, this.disciplineName);
		return output;
	}
}
