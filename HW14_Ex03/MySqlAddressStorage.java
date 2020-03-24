package bg.swift.HW14_Ex03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlAddressStorage implements AddressStorage {

	@Override
	public Address getAddress(int id) throws DALException {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/citizen_registrations", "root", "1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from citizen_registrations.addresses where id = ?;")) {
			ps.setInt(1, 1);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Address address = new Address(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
							rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
					return address;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get address, because " + ex.getMessage());
		}
		return null;
	}

	@Override
	public int insertAddress(Address address) throws DALException {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/citizen_registrations", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select max(id) from citizen_registrations.addresses;");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				try (PreparedStatement ps1 = con.prepareStatement(
						"insert into citizen_registrations.addresses (id, country, city, municipality, postal_code, street, number, floor, apartmentNo) values(?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
					int id = rs.getInt(1) + 1;
					ps1.setInt(1, id);
					ps1.setString(2, address.getCountry());
					ps1.setString(3, address.getCity());
					ps1.setString(4, address.getMunicipality());
					ps1.setString(5, String.valueOf(address.getPostalCode()));
					ps1.setString(6, address.getStreet());
					ps1.setString(7, String.valueOf(address.getNumber()));
					ps1.setInt(8, address.getFloor());
					ps1.setInt(9, address.getApartamentNo());
					ps1.execute();
					return id;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't insert address, because " + ex.getMessage());
		}
		return 0;
	}
}
