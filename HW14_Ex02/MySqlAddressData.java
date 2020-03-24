package bg.swift.HW14_Ex02;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlAddressData {

//	FIRST VARIANT of method getFullAddress();
	
//	public static String getFullAddress(int addressID) {
//		StringBuilder fullAddress = new StringBuilder();
//		
//		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
//				"1234567890");
//				PreparedStatement ps = con.prepareStatement("select * from addressdb.addresses where id = ?;")) {
//			ps.setInt(1, addressID);
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					fullAddress.append(rs.getString(3));
//					try (PreparedStatement ps1 = con
//							.prepareStatement("select * from addressdb.streets where id = ?;")) {
//						ps1.setInt(1, rs.getInt(2));
//						try (ResultSet rs1 = ps1.executeQuery()) {
//							while (rs1.next()) {
//								fullAddress.append(" " + rs1.getString(2));
//								fullAddress.append(" Street, Ap. " + rs.getString(4));
//								try (PreparedStatement ps2 = con
//										.prepareStatement("select * from addressdb.municipalities where id = ?;")) {
//									ps2.setInt(1, rs1.getInt(3));
//									try (ResultSet rs2 = ps2.executeQuery()) {
//										while (rs2.next()) {
//											fullAddress.append("\n" + rs2.getString(2));
//											try (PreparedStatement ps3 = con.prepareStatement(
//													"select * from addressdb.populated_areas where id = ?;")) {
//												ps3.setInt(1, rs2.getInt(4));
//												try (ResultSet rs3 = ps3.executeQuery()) {
//													while (rs3.next()) {
//														fullAddress.append(", " + rs3.getString(2));
//														try (PreparedStatement ps4 = con.prepareStatement(
//																"select * from addressdb.regions where id = ?;")) {
//															ps4.setInt(1, rs3.getInt(4));
//															try (ResultSet rs4 = ps4.executeQuery()) {
//																while (rs4.next()) {
//																	fullAddress.append("\n" + rs4.getString(2));
//																	try (PreparedStatement ps5 = con.prepareStatement(
//																			"select * from addressdb.countries where id = ?;")) {
//																		ps5.setInt(1, rs4.getInt(3));
//																		try (ResultSet rs5 = ps5.executeQuery()) {
//																			while (rs5.next()) {
//																				fullAddress.append(", " + rs5.getString(2));
//																			}
//																		}
//																	}
//																}
//															}
//														}
//													}
//												}
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			return fullAddress.toString();
//		} catch (SQLException ex) {
//			System.out.println("Can't get full address, because " + ex.getMessage());
//		}
//		return null;
//	}
	
	
//	SECOND VARIANT of method getFullAddress();
	
	public static String getFullAddress(int addressID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.addresses where id = ?;")) {
			ps.setInt(1, addressID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String number = rs.getString(3);
					String apartmentNo = rs.getString(4);
					return printFullAddress(number, getStreet(rs.getInt(2)), apartmentNo);
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get full address, because " + ex.getMessage());
		}
		return null;
	}

	private static List<String> getStreet(int streetID) {
		List<String> output = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.streets where id = ?;")) {
			ps.setInt(1, streetID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String nameOfStreet = rs.getString(2);
					output.add(nameOfStreet);
					output.add(getMunicipality(rs.getInt(3)));
					return output;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get street, because " + ex.getMessage());
		}
		return null;
	}

	private static String getMunicipality(int municipalityID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.municipalities where id = ?;")) {
			ps.setInt(1, municipalityID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String nameOfMunicipality = rs.getString(2);
					String output = getPopulatedArea(rs.getInt(4));
					return nameOfMunicipality + ", " + output;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get municipality, because " + ex.getMessage());
		}
		return null;
	}

	private static String getPopulatedArea(int populatedAreaID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.populated_areas where id = ?;")) {
			ps.setInt(1, populatedAreaID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String nameOfPopulatedArea = rs.getString(2);
					String output = getRegion(rs.getInt(4));
					return nameOfPopulatedArea + "\n" + output;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get populated area, because " + ex.getMessage());
		}
		return null;
	}

	private static String getRegion(int regionID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.regions where id = ?;")) {
			ps.setInt(1, regionID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String nameOfRegion = rs.getString(2);
					String output = getCountry(rs.getInt(3));
					return nameOfRegion + ", " + output;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get populated area, because " + ex.getMessage());
		}
		return null;
	}

	private static String getCountry(int countryID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.countries where id = ?;")) {
			ps.setInt(1, countryID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String nameOfCountry = rs.getString(2);
					return nameOfCountry;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get populated area, because " + ex.getMessage());
		}
		return null;
	}

	private static String printFullAddress(String numberOfStreet, List<String> outputFromMethods, String apartmentNo) {
		StringBuilder str = new StringBuilder();
		str.append(numberOfStreet).append(" " + outputFromMethods.get(0)).append(" Street, Ap. " + apartmentNo)
				.append("\n" + outputFromMethods.get(1));
		return str.toString();
	}
	
	public static void addAddress(int streetID, int numberOfStreet, int apartmentNo) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root", "1234567890");
				CallableStatement cs = con.prepareCall("{call add_address(?, ?, ?)}"))
		{
			cs.setInt(1, streetID);
			cs.setInt(2, numberOfStreet);
			cs.setInt(3, apartmentNo);
			cs.execute();
		} catch (SQLException ex) {
			System.out.println("Can't add this address, because " + ex.getMessage());
		}
	}
	
//	FIRST VARIANT of method getAddresses();
	
//	public static StringBuilder getAddresses(String nameOfMunicipality) {
//		StringBuilder addresses = new StringBuilder();
//		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
//				"1234567890");
//				PreparedStatement ps = con.prepareStatement("select * from addressdb.municipalities where name = ?")) {
//			ps.setString(1, nameOfMunicipality);
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					try (PreparedStatement ps1 = con
//							.prepareStatement("select * from addressdb.streets where municipality_id = ?")) {
//						ps1.setInt(1, rs.getInt(1));
//						try (ResultSet rs1 = ps1.executeQuery()) {
//							while (rs1.next()) {
//								addresses.append(rs1.getString(2) + " No ");
//								try (PreparedStatement ps2 = con
//										.prepareStatement("select * from addressdb.addresses where street_id = ?")) {
//									ps2.setInt(1, rs1.getInt(1));
//									try (ResultSet rs2 = ps2.executeQuery()) {
//										while (rs2.next()) {
//											addresses.append(rs2.getString(3) + ", ap. " + rs2.getString(4) + "\n");
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			return addresses;
//		} catch (SQLException ex) {
//			System.out.println("Can't get addresses from this municipality, because " + ex.getMessage());
//		}
//		return null;
//	}
	
//	SECOND VARIANT of method getAddresses();
	
	public static void getAddresses(String nameOfMunicipality) {
		List<String> addresses = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.municipalities where name = ?")) {
			ps.setString(1, nameOfMunicipality);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					addresses = getStreets(rs.getInt(1));
					for(int i = 0; i < addresses.size(); i++) {
						System.out.print(addresses.get(i));
					}
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get addresses from this municipality, because" + ex.getMessage());
		}
	}

	private static List<String> getStreets(int municipalityID) {
		List<String> streets = new ArrayList<>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con
						.prepareStatement("select * from addressdb.streets where municipality_id = ?")) {
			ps.setInt(1, municipalityID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					streets.add(rs.getString(2) + " No " + getNumberOfStreetsAndApartmentNo(rs.getInt(1)));
				}
				return streets;
			}
		} catch (SQLException ex) {
			System.out.println("Can't get streets from this municipality, because" + ex.getMessage());
		}
		return null;
	}

	private static String getNumberOfStreetsAndApartmentNo(int streetID) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressdb", "root",
				"1234567890");
				PreparedStatement ps = con.prepareStatement("select * from addressdb.addresses where street_id = ?")) {
			ps.setInt(1, streetID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String output = rs.getString(3) + ", ap. " + rs.getString(4) + "\n";
					return output;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Can't get number of street or apartmentNo, because" + ex.getMessage());
		}
		return null;
	}
}
