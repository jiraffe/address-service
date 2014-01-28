package com.danco.addresswrap.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;

public class HibernateAwareObjectMapper extends ObjectMapper{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -5806420240168104981L;

	public HibernateAwareObjectMapper() {
	    Hibernate3Module hm = new Hibernate3Module();
	    registerModule(hm);
	}
}
