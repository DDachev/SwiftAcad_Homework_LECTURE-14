package bg.swift.HW14_Ex03;

public class Address {
	private String country;
	private String city;
	private String municipality;
	private int postalCode;
	private String street;
	private int number;
	private int floor;
	private int apartamentNo;

	public Address(String country, String city, String municipality, int postalCode, String street, int number,
			int floor, int apartamentNo) {
		this.country = country;
		this.city = city;
		this.municipality = municipality;
		this.postalCode = postalCode;
		this.street = street;
		this.number = number;
		this.floor = floor;
		this.apartamentNo = apartamentNo;
	}

	@Override
	public String toString() {
		if (this.floor != 0 && this.apartamentNo != 0) {
			String output = String.format("%d %s Street%nfl. %d, ap. %d%n%d %s%n%s, %s%n", this.number,
					this.street, this.floor, this.apartamentNo, this.postalCode, this.municipality, this.city,
					this.country);
			return output;
		}
		String output = String.format("%d %s Street%n%d %s%n%s, %s%n", this.number, this.street,
				this.postalCode, this.municipality, this.city, this.country);
		return output;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Address) {
			Address other = (Address) obj;
			if (this.floor != 0 && this.apartamentNo != 0) {
				if (this.country == other.country && this.city == other.city && this.municipality == other.municipality
						&& this.postalCode == other.postalCode && this.street == other.street
						&& this.number == other.number && this.floor == other.floor
						&& this.apartamentNo == other.apartamentNo) {
					return true;
				}
			}
			if (this.country == other.country && this.city == other.city && this.municipality == other.municipality
					&& this.postalCode == other.postalCode && this.street == other.street
					&& this.number == other.number) {
				return true;
			}
		}
		return false;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getMunicipality() {
		return municipality;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public String getStreet() {
		return street;
	}

	public int getNumber() {
		return number;
	}

	public int getFloor() {
		return floor;
	}

	public int getApartamentNo() {
		return apartamentNo;
	}
}
