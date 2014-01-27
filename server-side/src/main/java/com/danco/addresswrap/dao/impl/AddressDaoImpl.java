package com.danco.addresswrap.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danco.addresswrap.dao.AddressDao;
import com.danco.addresswrap.domain.Address;

@Repository
public class AddressDaoImpl implements AddressDao	{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveAddress(Address address) {
		sessionFactory.getCurrentSession().save(address);
	}

	@Override
	public Address getAddress(String city, String street, String building) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Address.class);
		
		c
			.add(Restrictions.eq("city", city))
			.add(Restrictions.eq("street", street))
			.add(Restrictions.eq("building", building));
		
		return (Address) c.uniqueResult();
	}

}
