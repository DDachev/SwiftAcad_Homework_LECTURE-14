package bg.swift.HW14_Ex03;

public class DataAccessLayer {

	public static void main(String[] args) {
		MySqlAddressStorage addressStorage = new MySqlAddressStorage();
		try {
			Address address = addressStorage.getAddress(1);
			System.out.println(address.toString());

			Address address2 = new Address("Bulgaria", "Sofia", "Sofia", 1000, "Alexander Pushkin", 10, 3, 21);
			System.out.println("ID of the new address is: " + addressStorage.insertAddress(address2));

		} catch (DALException e) {
			System.out.println("You don't have access, because " + e.getMessage());
		}
	}
}
