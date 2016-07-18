package com.zjhcsoft.uac.redis;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.ldap.util.PersonServiceHandler;
import com.zjhcsoft.uac.ldap.util.PersonServiceI;

public class RedisUsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	private PersonServiceHandler personServiceHandler;
	
	
	@Override
	protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		Person person = new Person();
		person.setCn(credentials.getUsername());
		person.setUserPassword(credentials.getPassword());
		return personServiceHandler.getPersonServiceI().authenticate(person);
	}



	public void setPersonServiceHandler(PersonServiceHandler personServiceHandler) {
		this.personServiceHandler = personServiceHandler;
	}
	
	
	

}
