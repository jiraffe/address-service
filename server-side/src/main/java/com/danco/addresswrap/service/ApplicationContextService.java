package com.danco.addresswrap.service;

import org.springframework.stereotype.Service;

@Service
public interface ApplicationContextService {

	public Object getBean(String beanName);
	
	@SuppressWarnings("rawtypes")
	public Object getBean(Class clazz);
}
