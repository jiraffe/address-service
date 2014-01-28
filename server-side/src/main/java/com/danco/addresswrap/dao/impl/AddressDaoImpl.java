package com.danco.addresswrap.dao.impl;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danco.addresswrap.dao.AddressDao;
import com.danco.addresswrap.domain.Address;
import com.danco.addresswrap.domain.Synonym;

@Repository
public class AddressDaoImpl implements AddressDao	{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Serializable saveAddress(Address address) {
		return sessionFactory.getCurrentSession().save(address);
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

	@Override
	public Address getAddressBySynonim(String city, String synonym, String building) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Synonym.class);
		
		c
			.createAlias("address", "a")
			.setFetchMode("address", FetchMode.JOIN)
			.setFetchMode("address.synonims", FetchMode.JOIN)
			.add(Restrictions.eq("synonymKey", synonym))
			.add(Restrictions.eq("a.city", city));
		
		if(building != null)	{
			c.add(Restrictions.eq("a.building", building));
		}
		
		Address address = ((Synonym) c.uniqueResult()).getAddress();
		
		return address;
	}

	@Override
	public Address getAddressBySynonim(String city, String synonym) {
		return this.getAddressBySynonim(city, synonym, null);
	}

}
