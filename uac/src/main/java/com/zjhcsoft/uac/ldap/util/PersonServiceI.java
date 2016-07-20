package com.zjhcsoft.uac.ldap.util;

import java.util.List;

public interface PersonServiceI {
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	public boolean createOnePerson(Person person);
	/**
	 * 根据自定义的属性值查询person列表
	 * 
	 * @param person
	 * @return
	 */
	public List<Person> getPersonList(Person person);
	/**
	 * 更新 一条记录
	 * @param person
	 * @return
	 */
	public boolean updateOnePerson(Person person);
	/**
	 * 更新 新增 一条记录
	 * @param person
	 * @return
	 */
	public boolean removeOnePerson(Person person);
	
	/**
	 * 用户认证
	 * @param person
	 * @return
	 */
	public boolean authenticate(Person person);
	
	
	public String encodePwdSHA(String pwd);
	
	public String encodeEncryptStringKey(String pwd);
	
	public String decodeEncryptStringKey(String pwd);

}
