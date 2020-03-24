package bg.swift.HW14_Ex02;


public class Task2_AddressDB {

	public static void main(String[] args) {
		
		System.out.println(MySqlAddressData.getFullAddress(1));
		
		MySqlAddressData.addAddress(5, 44, 3);
		
//		FIRST VARIANT of method getAddresses
//		System.out.println(MySqlAddressData.getAddresses("Ivanovo"));
		
//		SECOND VARIANT of method getAddresses
	    MySqlAddressData.getAddresses("Ivanovo");

	}
}
