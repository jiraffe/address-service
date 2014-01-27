package com.danco.addresswrap.dao;

import com.danco.addresswrap.domain.Address;

public interface AddressDao {

	public void saveAddress(Address address);

	public Address getAddress(String city, String street, String building);
	
}
