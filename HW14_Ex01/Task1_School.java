package bg.swift.HW14_Ex01;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task1_School {

	public static void main(String[] args) {
//		INSERT TEACHER
		MySqlSchoolData.insertTeacher(8, "Mihail Ivanov", "mihail@abv.bg", 2000.5, 2);

//		GET TEACHER
		Teacher t = MySqlSchoolData.getTeacher(1);
		System.out.println(t.toString());
		
//		GET TEACHERS
		List <Teacher> teachers = new ArrayList<>();
		teachers = MySqlSchoolData.getTeachers(1500, 2000);
		for(int i = 0; i < teachers.size(); i++) {
		   System.out.println(teachers.get(i).toString());
		}
		
//		INSERT STUDENT
		LocalDate local = LocalDate.of(2000, 10, 12);
		MySqlSchoolData.insertStudent(7, "Nikola Nikolov", Date.valueOf(local), 8);
		
//		GET STUDENT
		Student student = MySqlSchoolData.getStudent(7);
		System.out.println(student.toString());
		
//		GET STUDENTS
		LocalDate local2 = LocalDate.of(2001, 10, 10);
		List <Student> students = new ArrayList<>();
		students = MySqlSchoolData.getStudents(Date.valueOf(local2));
		for(int i = 0; i < students.size(); i++) {
			System.out.println(students.get(i).toString());
		}
		
//		GET DISCIPLINES BY TEACHER ID
		List<Discipline> disciplines = new ArrayList<>();
		disciplines = MySqlSchoolData.getDisciplinesByTeacherId(1);
		for(int i = 0; i <disciplines.size(); i++) {
			System.out.println(disciplines.get(i).toString());
		}
		
//		GET TEACHERS BY DISCIPLINE NAME
		List <Teacher> teachers1 = new ArrayList<>();
		teachers1 = MySqlSchoolData.getTeachersByDisciplineName("Geography");
		for(int i = 0; i < teachers1.size(); i++) {
			System.out.println(teachers1.get(i).toString());
		}	
	}
}
