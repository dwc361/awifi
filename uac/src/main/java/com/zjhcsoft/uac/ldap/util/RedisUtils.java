package com.zjhcsoft.uac.ldap.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.DefaultRedisSet;
import org.springframework.stereotype.Component;


public class RedisUtils extends AbstractPersonService {
	private static final Logger _logger = Logger.getLogger(RedisUtils.class);

	private DefaultRedisMap<String, Person> redisUserMap;
	
	
	private static final String USER_KEY = "USER_KEY-";
	private static final String SUBUSER_KEY = "SUBUSER_KEY-";

	
	
	private RedisTemplate<String, Person> redisTemplate;

	/**
	 * 添加 一条记录
	 * 
	 * @param person
	 */
	public boolean createOnePerson(Person person) {
		if(StringUtils.isNotEmpty(person.getParNode())){
			DefaultRedisSet<Person> redisSubUserSet = new DefaultRedisSet<Person>(getSubkey(person), redisTemplate);
			redisSubUserSet.add(person);
		}else{
			redisUserMap.put(USER_KEY + person.getCn(), person);
		}
		return true;
	}

	

	/**
	 * 根据自定义的属性值查询person列表
	 * 
	 * @param person
	 * @return
	 */
	public List<Person> getPersonList(Person person) {
		List<Person> list = new ArrayList<Person>();
		if(StringUtils.isNotEmpty(person.getParNode())){
			Set<Person> pp = redisTemplate.boundSetOps(getSubkey(person)).intersect(getSubkey(person));
			Iterator<Person> iterator = pp.iterator();
			while (iterator.hasNext()) {
				Person p = iterator.next();
				if(StringUtils.isNotEmpty(person.getSn())){
					if(person.getSn().equals(p.getSn())){
						list.add(p);	
					}
				}else{
					list.add(p);	
				}
				
			}
			
		}else{
			Person u = redisUserMap.get(USER_KEY + person.getCn());
			if(u != null){
				list.add(u);	
			}
		}
		return list;
	}

	

	/**
	 * 删除一条记录，根据dn
	 * 
	 * @param cn
	 */
	public boolean removeOnePerson(Person person) {
		if(StringUtils.isNotEmpty(person.getParNode())){
			Set<Person> pp = redisTemplate.boundSetOps(getSubkey(person)).intersect(getSubkey(person));
			Iterator<Person> iterator = pp.iterator();
			while (iterator.hasNext()) {
				Person p = iterator.next();
				if(StringUtils.isNotEmpty(person.getSn())){
					if(person.getSn().equals(p.getSn())){
						redisTemplate.boundSetOps(getSubkey(person)).remove(p);
					}
				}else{
					
				}
				
			}
		}else{
			redisUserMap.remove(USER_KEY + person.getCn());
		}
		return true;
	}

	

	/**
	 * 修改操作
	 * 
	 * @param person
	 */
	public boolean updateOnePerson(Person person) {
		if(StringUtils.isNotEmpty(person.getParNode())){
			Set<Person> pp = redisTemplate.boundSetOps(getSubkey(person)).intersect(getSubkey(person));
			Iterator<Person> iterator = pp.iterator();
			while (iterator.hasNext()) {
				Person p = iterator.next();
				if(StringUtils.isNotEmpty(person.getSn())){
					if(person.getSn().equals(p.getSn())){
						redisTemplate.boundSetOps(getSubkey(person)).remove(p);
						redisTemplate.boundSetOps(getSubkey(person)).add(person);
					}
				}else{
					
				}
				
			}
			
			
		}else{
			
		}
		return true;
	}

	
	
	private String getSubkey(Person person) {
		return SUBUSER_KEY+person.getParNode();
	}
	
	

	
	@Resource(name="uacUserMap")
	public void setRedisUserMap(DefaultRedisMap<String, Person> redisUserMap) {
		this.redisUserMap = redisUserMap;
	}



	@Resource
	public void setRedisTemplate(RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
