package com.danco.addresswrap.dao;

import java.io.Serializable;

import com.danco.addresswrap.domain.Address;

public interface AddressDao {

	public Serializable saveAddress(Address address);

	public Address getAddress(String city, String street, String building);
	
	public Address getAddressBySynonim(String city, String synonym, String building);
	
	public Address getAddressBySynonim(String city, String synonym);
}
