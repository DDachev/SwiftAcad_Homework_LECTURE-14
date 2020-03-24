 package bg.swift.HW14_Ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlSchoolData {
	
	public static void insertTeacher(int teacherId, String teacherName, String teacherEmail, double teacherSalary,
			int addressId) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement(
						"insert into school.teachers (teacher_id, teacher_name, teacher_email, teacher_salary, address_id) values (?, ?, ?, ?, ?);")) {
			ps.setInt(1, teacherId);
			ps.setString(2, teacherName);
			ps.setString(3, teacherEmail);
			ps.setDouble(4, teacherSalary);
			ps.setInt(5, addressId);
			ps.execute();
		} catch (SQLException ex) {
			System.out.println("Cannot insert this teacher in school, because " + ex.getMessage());
		}
	}

	public static Teacher getTeacher(int teacherId) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement("select * from school.teachers where teacher_id = ?;")) {
			ps.setInt(1, teacherId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Teacher teacher = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
							rs.getInt(5));
					return teacher;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this teacher, because " + ex.getMessage());
		}
		return null;
	}

	public static List<Teacher> getTeachers(int teacherSalaryX, int teacherSalaryY) {
		List<Teacher> teachers = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement(
						"select * from school.teachers where teacher_salary > ? and teacher_salary < ?;")) {
			ps.setInt(1, teacherSalaryX);
			ps.setInt(2, teacherSalaryY);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Teacher teacher = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
							rs.getInt(5));
					teachers.add(teacher);
				}
				return teachers;
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this teachers, because " + ex.getMessage());
		}
		return null;
	}

	public static void insertStudent(int studentId, String studentName, Date studentEnrollmentDate, int addressId) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement(
						"insert into school.students (student_id, student_name, student_enrollment_date, address_id) values (?, ?, ?, ?);")) {
			ps.setInt(1, studentId);
			ps.setString(2, studentName);
			ps.setDate(3, studentEnrollmentDate);
			ps.setInt(4, addressId);
			ps.execute();
		} catch (SQLException ex) {
			System.out.println("Cannot insert this student in school, because " + ex.getMessage());
		}
	}

	public static Student getStudent(int studentId) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement("select * from school.students where student_id = ?;")) {
			ps.setInt(1, studentId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Student student = new Student(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4));
					return student;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this student, because " + ex.getMessage());
		}
		return null;
	}

	public static List<Student> getStudents(Date enrollmentDateX) {
		List<Student> students = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from school.students where student_enrollment_date > ?;")) {
			ps.setDate(1, enrollmentDateX);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Student student = new Student(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4));
					students.add(student);
				}
				return students;
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this students, because " + ex.getMessage());
		}
		return null;
	}

	public static List<Discipline> getDisciplinesByTeacherId(int teacherId) {
		List<Discipline> disciplines = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from school.disciplines_taught where teacher_id = ?;")) {
			ps.setInt(1, teacherId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					disciplines.add(getDiscipline(rs.getInt(1)));
				}
				return disciplines;
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this teacher, because " + ex.getMessage());
		}
		return null;
	}

	private static Discipline getDiscipline(int disciplineId) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from school.disciplines where discipline_id = ?;")) {
			ps.setInt(1, disciplineId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Discipline discipline = new Discipline(rs.getInt(1), rs.getString(2));
					return discipline;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this discipline, because " + ex.getMessage());
		}
		return null;
	}

	public static List<Teacher> getTeachersByDisciplineName(String disciplineName) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from school.disciplines where discipline_name = ?;")) {
			ps.setString(1, disciplineName);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return getTeachersIdWhoTaughtDiscipline(rs.getInt(1));
				}
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this discipline, because " + ex.getMessage());
		}
		return null;
	}

	private static List<Teacher> getTeachersIdWhoTaughtDiscipline(int disciplineId) {
		List<Teacher> teachers = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from school.disciplines_taught where discipline_id = ?;")) {
			ps.setInt(1, disciplineId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Teacher teacher = getTeacher(rs.getInt(2));
					teachers.add(teacher);
				}
				return teachers;
			}
		} catch (SQLException ex) {
			System.out.println("Cannot get this teacher, because " + ex.getMessage());
		}
		return null;
	}
}
