package com.zjhcsoft.uac.ldap.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PersonServiceHandler {
	
	private PersonServiceI personServiceI;
	

	@Resource
	public void setPersonServiceI(PersonServiceI personServiceI) {
		this.personServiceI = personServiceI;
	}


	public PersonServiceI getPersonServiceI() {
		Assert.notNull(personServiceI);
		return personServiceI;
	}
	
	
	
	
	

}
