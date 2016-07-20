package com.zjhcsoft.uac.account.user.service;

import org.roof.struts2.Result;

import com.zjhcsoft.uac.ldap.util.Person;

public interface LoginCheckUnit {

	Result check(Person person);

}
