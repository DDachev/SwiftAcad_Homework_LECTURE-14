package bg.swift.HW14_Ex0;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

public class Task0_Sakila {

	public static void main(String[] args) {
		insertActor("Dimityr", "Naidenov");

		insertActorInFilm(100, 122);

		insertActorWihtProcedure("Ivan", "Ivanov", 800);

	}

	private static void insertActor(String firstName, String lastName) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement(
						"insert into sakila.actor (first_name, last_name, last_update) values (?, ?, ?);")) {
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			LocalDateTime local = LocalDateTime.now();
			ps.setTimestamp(3, Timestamp.valueOf(local));
			ps.execute();
		} catch (SQLException ex) {
			System.out.println("You can't insert this actor, because " + ex.getMessage());
		}
	}

	private static void insertActorInFilm(int actorId, int filmId) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "1234567890");
				PreparedStatement ps = con.prepareStatement("select * from sakila.actor where actor_id = ?;")) {
			ps.setInt(1, actorId);
			try (ResultSet rs = ps.executeQuery();
					PreparedStatement ps1 = con.prepareStatement(
							"insert into sakila.film_actor (actor_id, film_id, last_update) values (?, ?, ?);")) {
				while (rs.next()) {
					ps1.setInt(1, rs.getInt(1));
					ps1.setInt(2, filmId);
					LocalDateTime local = LocalDateTime.now();
					ps1.setTimestamp(3, Timestamp.valueOf(local));
					ps1.execute();
				}
			}
		} catch (SQLException ex) {
			System.out.println("There is no such actor or movie with this id. " + ex.getMessage());
		}
	}

	private static void insertActorWihtProcedure(String firstName, String lastName, int filmID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "1234567890");
				CallableStatement cs = con.prepareCall("{call insert_actor (?, ?, ?, ?)}")) {
			cs.setString(1, firstName);
			cs.setString(2, lastName);
			LocalDateTime local = LocalDateTime.now();
			cs.setTimestamp(3, Timestamp.valueOf(local));
			cs.registerOutParameter("new_actor_id", Types.INTEGER);
			cs.execute();
			Integer newId = cs.getInt("new_actor_id");
			try (PreparedStatement ps = con.prepareStatement("select * from sakila.film where film_id = ?;")) {
				ps.setInt(1, filmID);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						insertActorInFilm(newId, rs.getInt(1));
					}
				}
			}
		} catch (SQLException ex) {
			System.out.println("There is no such actor or movie with this id. " + ex.getMessage());
		}
	}
}
