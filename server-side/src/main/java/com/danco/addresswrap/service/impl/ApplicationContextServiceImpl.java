package com.danco.addresswrap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.danco.addresswrap.service.ApplicationContextService;

@Service
public class ApplicationContextServiceImpl implements ApplicationContextService {

	@Autowired
    private ApplicationContext applicationContext;

	@Override
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getBean(Class clazz) {
		return applicationContext.getBean(clazz);
	}
	
	
}
