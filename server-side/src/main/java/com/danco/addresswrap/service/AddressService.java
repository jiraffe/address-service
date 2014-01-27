package com.danco.addresswrap.service;

import com.danco.addresswrap.domain.Address;

public interface AddressService {

	public void saveAddress(Address address);

	Address getAddress(String city, String street, String building);
	
}
