package bg.swift.HW14_Ex03;

public interface AddressStorage {
	public Address getAddress(int id) throws DALException;

	public int insertAddress(Address address) throws DALException;
}
