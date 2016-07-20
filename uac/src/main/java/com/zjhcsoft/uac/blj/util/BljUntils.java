package com.zjhcsoft.uac.blj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.roof.commons.PropertiesUtil;
import org.roof.web.org.entity.Organization;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.authorization.resource.entity.Db;
import com.zjhcsoft.uac.authorization.resource.entity.Host;
import com.zjhcsoft.uac.authorization.resource.entity.NetDevice;
import com.zjhcsoft.uac.authorization.resource.entity.SysResource;
import com.zjhcsoft.uac.authorization.resource.entity.System;
import com.zjhcsoft.uac.ldap.util.LdapUtils;
import com.zjhcsoft.uac.ldap.util.PersonServiceI;

@Component
public class BljUntils {
	
	public PersonServiceI ldapUtils;
	private final static String USERNAME = "username";
	private final static String REALNAME = "realname";
	private final static String STATUS = "status";
	private final static String FLAG = "flag";
	private final static String PASSWORD = "password";
	private final static String CELLPHONE = "cellphone";
	private final static String EMAIL = "email";
	private final static String ORGANIZATION = "organization";
	private final static String REMARK = "remark";
	//资源
	private final static String IPS = "ips";
	private final static String NAME = "name";
	private final static String CLASSNAME = "classname";
	private final static String TYPENAME = "typename";
	private final static String SUCMD = "sucmd";
	private final static String SUPROMPT = "suprompt";
	private final static String OWNER = "owner";
	private final static String SUPERVISOR = "supervisor";
	private final static String SN = "sn";
	private final static String GROUP = "group";
	//资源服务
	private final static String SERVICENAME = "servicename";
	private final static String RESOURCE = "resource";
	private final static String PORT = "port";
	private final static String IP = "ip";
	private final static String SERVICE = "service";
	
	//资源账号
	private final static String PARAM = "param";
	private final static String SERVICES = "services";
	private final static String ACCOUNTNAME = "accountname";
	private final static String ACCOUNT = "account";
	private final static String CREDTYPE = "credtype";
	private final static String CREDENTIAL = "credential";
	private final static String PRIVILEGED = "privileged";
	//加密
	private static final String KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM = "AES/CTR/PKCS5Padding";
	private static final String ivParameter = "1234567890abcdef";
	
	private static String key;
	
	
	public Map<String, Object> Acctomap(SubUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, 1);
		map.put(USERNAME, user.getUser().getUsername());
		map.put(ACCOUNT, ResourceParm(user.getSysResource(),user.getUsername(),false));
		map.put(SERVICE, ResourceParm(user.getSysResource(),null,false));
		return map;
	}
	public Map<String, Object> Acctomap(SubUser user,String del) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(USERNAME, user.getUser().getUsername());
		map.put(ACCOUNT, ResourceParm(user.getSysResource(),user.getUsername(),false));
		map.put(SERVICE, ResourceParm(user.getSysResource(),null,false));
		return map;
	}
	
	public Map<String, Object> Usertomap(User user,String del) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(USERNAME, user.getUsername());
		return map;
	}

	public Map<String, Object> Usertomap(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(USERNAME, user.getUsername());
		map.put(REALNAME, user.getName());
		map.put(STATUS, user.getEnabled()==true ? "1":"0");
		map.put(FLAG, 0x00000002);
		map.put(PASSWORD, user.getPassword());
		map.put(CELLPHONE, user.getTel());
		map.put(EMAIL, user.getMail());
		map.put(ORGANIZATION,Org(user.getOrg()));
		map.put(REMARK,"");
		return map;
	}
	
	public Map<String, Object> SubUsertomap (SubUser user,String del) {
		Map<String, Object> map = new HashMap<String, Object>();
		if("delete".endsWith(del)){
			map.put(IP, Resource(user.getSysResource(),"ip"));
			map.put(PORT, Resource(user.getSysResource(),"port"));
			map.put(ACCOUNTNAME, user.getUsername());
		}else if("update".endsWith(del)){
			List<Object> o = new ArrayList();
			o.add(ResourceParm(user.getSysResource(),null,true));
			map.put(SERVICES, o);
			//map.put(RESOURCE, Resource(user.getSysResource(),"ip"));
			map.put(ACCOUNT, ResourceParm(user.getSysResource(),user.getUsername(),false));
			map.put(STATUS, user.getEnabled()==true ? 1:0);
			map.put(CREDTYPE, 1);
			String pwd = user.getPassword() != null ?ldapUtils.decodeEncryptStringKey(user.getPassword()):"";
			map.put(CREDENTIAL,pwd);
			map.put(PRIVILEGED, 0);
			map.put(REMARK,"");
		}
		return map;
	}

	public Map<String, Object> SubUsertomap (SubUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> o = new ArrayList();
		o.add(ResourceParm(user.getSysResource(),null,true));
		map.put(SERVICES, o);
		map.put(RESOURCE, Resource(user.getSysResource(),"ip"));
		map.put(ACCOUNTNAME, user.getUsername());
		map.put(STATUS, user.getEnabled()==true ? 1:0);
		map.put(CREDTYPE, 1);
		String pwd = user.getPassword() != null ?ldapUtils.decodeEncryptStringKey(user.getPassword()):"";
		map.put(CREDENTIAL,pwd);
		map.put(PRIVILEGED, 0);
		map.put(REMARK,"");
		return map;
	}
	
	public Map<String, Object> Systemtomap(System sys,String del) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IP, sys.getIp());
		return map;
	}
	
	public Map<String, Object> Systemtomap (System sys ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IPS, iptolist(sys.getIp()));
		String name = sys.getName();
		int pos = name.lastIndexOf(":");
		if(pos != -1){
			name = name.substring(0,pos);
		}
		map.put(NAME, name);
		map.put(STATUS, sys.getState().getId()==107L?1:0);
		map.put(SUCMD,"");
		map.put(SUPROMPT,"");
		map.put(OWNER,"");
		map.put(SUPERVISOR,"");
		map.put(SN,"");
		map.put(GROUP,sys.getGroup()!=null?sys.getGroup().getText():"");
		map.put(REMARK,"");
		if(sys instanceof Host){
			Host h = (Host) sys;
			map.put(CLASSNAME, "主机");
			map.put(TYPENAME,h.getTypename().getText());
		}else if(sys instanceof Db){
			Db h = (Db) sys;
			map.put(CLASSNAME, "数据库");
			map.put(TYPENAME,h.getTypename().getText());
		}else if(sys instanceof NetDevice){
			NetDevice h = (NetDevice) sys;
			map.put(CLASSNAME, "网络设备");
			map.put(TYPENAME,h.getTypename().getText());
			
		}
		return map;
	}
	
	public Map<String, Object> Servicetomap (System u,String del) {
		Map<String, Object> map = new HashMap<String, Object>();
		if("delete".endsWith(del)){
			map.put(IP, Resource(u,"ip"));
			map.put(PORT, Resource(u,"port"));
		}else if("update".endsWith(del)){
			map.put(SERVICENAME, Resource(u,"servicename"));
			map.put(STATUS, u.getState().getId()==107L ? 1:0);
			map.put(SERVICE, ResourceParm(u,null,false));
			map.put(TYPENAME, Resource(u,"typename"));
			map.put(PORT, Resource(u,"port"));
			map.put(REMARK,"");
		}
		return map;
	}
	
	
	public Map<String, Object> Servicetomap (System u) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SERVICENAME, Resource(u,"servicename"));
		map.put(STATUS, u.getState().getId()==107L ? 1:0);
		map.put(RESOURCE, Resource(u,"ip"));
		map.put(TYPENAME, Resource(u,"typename"));
		map.put(PORT, Resource(u,"port"));
		map.put(REMARK,"");
		return map;
	}
	
	public String Org(Organization o) {
		String s = "";
		if(o != null && StringUtils.isNotBlank(o.getOrg_name())){
			s = Org(o.getParent())+"/" +o.getOrg_name();
		}
		return s;
	}
	
	public Map<String, Object> ResourceParm(SysResource object,String accountname,boolean is_param) {
		Map<String, Object> m = new HashMap<String, Object>();
		if(object instanceof Host){
			Host h = (Host) object;
			m.put(IP, h.getIp());
			m.put(PORT, h.getPort());
			if(accountname != null){
				m.put(ACCOUNTNAME, accountname);
			}
		}else if(object instanceof Db){
			Db h = (Db) object;
			m.put(IP, h.getIp());
			m.put(PORT, h.getPort());
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("dbname",h.getDb_name());
			if("ORACLE".equals(h.getDbType().getText())){
				mm.put("dbtype",h.getDbType().getText().toLowerCase());
				mm.put("loginas","normal");
			}else if("DB2".equals(h.getDbType().getText())){
				mm.put("dbtype",h.getDbType().getText().toLowerCase());
				mm.put("db2instance","");
			}else if("INFORMIX".equals(h.getDbType().getText())){
				mm.put("dbtype",h.getDbType().getText().toLowerCase());
				mm.put("servername",h.getDb_name());
			}else if("SYBASE".equals(h.getDbType().getText())){
				mm.put("dbtype",h.getDbType().getText().toLowerCase());
				mm.put("servername",h.getDb_name());
			}else if("POSTGRESQL".equals(h.getDbType().getText())){
				mm.put("dbtype",h.getDbType().getText().toLowerCase());
			} 
			List<Object> o = new ArrayList();
			o.add(mm);
			if(is_param){
				m.put("param", o);
			}
			if(accountname != null){
				m.put(ACCOUNTNAME, accountname);
			}
		}else if(object instanceof NetDevice){
			NetDevice h = (NetDevice) object;
			m.put(IP, h.getIp());
			m.put(PORT, h.getPort());
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("url","");
			List<Object> o = new ArrayList();
			o.add(mm);
			if(is_param){
				m.put("param", o);
			}
			if(accountname != null){
				m.put(ACCOUNTNAME, accountname);
			}
		}else if(object != null && object instanceof HibernateProxy){
			 Object  realEntity= ((HibernateProxy)object).getHibernateLazyInitializer().getImplementation();  
			 if(realEntity instanceof Host){
					Host h = (Host) realEntity;
					m.put(IP, h.getIp());
					m.put(PORT, h.getPort());
					if(accountname != null){
						m.put(ACCOUNTNAME, accountname);
					}
				}else if(realEntity instanceof Db){
					Db h = (Db) realEntity;
					m.put(IP, h.getIp());
					m.put(PORT, h.getPort());
					Map<String, Object> mm = new HashMap<String, Object>();
					mm.put("dbname",h.getDb_name() );
					if("ORACLE".equals(h.getDbType().getText())){
						mm.put("dbtype",h.getDbType().getText().toLowerCase());
						mm.put("loginas","normal");
					}else if("DB2".equals(h.getDbType().getText())){
						mm.put("dbtype",h.getDbType().getText().toLowerCase());
						mm.put("db2instance","");
					}else if("INFORMIX".equals(h.getDbType().getText())){
						mm.put("dbtype",h.getDbType().getText().toLowerCase());
						mm.put("servername",h.getDb_name());
					}else if("SYBASE".equals(h.getDbType().getText())){
						mm.put("dbtype",h.getDbType().getText().toLowerCase());
						mm.put("servername",h.getDb_name());
					}else if("POSTGRESQL".equals(h.getDbType().getText())){
						mm.put("dbtype",h.getDbType().getText().toLowerCase());
					} 
					List<Object> o = new ArrayList();
					o.add(mm);
					if(is_param){
						m.put("param", o);
					}
					if(accountname != null){
						m.put(ACCOUNTNAME, accountname);
					}
			}else if(realEntity instanceof NetDevice){
				NetDevice h = (NetDevice) realEntity;
				//List<Object> o = new ArrayList();
				m.put(IP, h.getIp());
				m.put(PORT, h.getPort());
				Map<String, Object> mm = new HashMap<String, Object>();
				mm.put("url","" );
				List<Object> o = new ArrayList();
				o.add(mm);
				if(is_param){
					m.put("param", o);
				}
				if(accountname != null){
					m.put(ACCOUNTNAME, accountname);
				}
			}
		} 
		return m;
	}
	
	
	
	public String Resource(SysResource object,String parm) {
		String s = "";
		if(object instanceof Host){
			Host h = (Host) object;
			if("ip".endsWith(parm)){
				s = h.getIp();
			}else if("port".endsWith(parm)){
				s = h.getPort();
			}else if("typename".endsWith(parm)){
				s = h.getServe_type().getText();
			}else if("servicename".endsWith(parm)){
				s = h.getServe_name()+"_"+h.getPort();
			}else if("type".endsWith(parm)){
				//s = h.getHost_type();
			}
		}else if(object instanceof Db){
			Db h = (Db) object;
			if("ip".endsWith(parm)){
				s = h.getIp();
			}else if("port".endsWith(parm)){
				s = h.getPort();
			}else if("servicename".endsWith(parm)){
				s = h.getDbType().getText()+"_"+h.getPort();
			}else if("typename".endsWith(parm)){
				s = h.getDbType().getText();
			}
		}else if(object instanceof NetDevice){
			NetDevice h = (NetDevice) object;
			if("ip".endsWith(parm)){
				s = h.getIp();
			}else if("port".endsWith(parm)){
				s = h.getPort();
			}else if("servicename".endsWith(parm)){
				s = h.getServe_name()+"_"+h.getPort();
			}else if("typename".endsWith(parm)){
				s = h.getServe_type().getText();
			}
		}else if(object != null && object instanceof HibernateProxy){
			 Object  realEntity= ((HibernateProxy)object).getHibernateLazyInitializer().getImplementation();  
			 if(realEntity instanceof Host){
					Host h = (Host) realEntity;
					if("ip".endsWith(parm)){
						s = h.getIp();
					}else if("port".endsWith(parm)){
						s = h.getPort();
					}else if("servicename".endsWith(parm)){
						s = h.getServe_name()+"_"+h.getPort();
					}else if("typename".endsWith(parm)){
						s = h.getServe_type().getText();
					}else if("type".endsWith(parm)){
						//s = h.getHost_type();
					}
				}else if(realEntity instanceof Db){
					Db h = (Db) realEntity;
					if("ip".endsWith(parm)){
						s = h.getIp();
					}else if("port".endsWith(parm)){
						s = h.getPort();
					}else if("servicename".endsWith(parm)){
						s = h.getDbType().getText()+"_"+h.getPort();
					}else if("typename".endsWith(parm)){
						s = h.getDbType().getText();
					}
				}else if(realEntity instanceof NetDevice){
					NetDevice h = (NetDevice) realEntity;
					if("ip".endsWith(parm)){
						s = h.getIp();
					}else if("port".endsWith(parm)){
						s = h.getPort();
					}else if("servicename".endsWith(parm)){
						s = h.getServe_name()+"_"+h.getPort();
					}else if("typename".endsWith(parm)){
						s = h.getServe_type().getText();
					}
				}
		}
		return s;
	}
	
	private  String readKey() {
		if (key == null || "".equals(key) ) {
			key =PropertiesUtil.getPorpertyString("blj.pwd");
			}
		return key;
	}
	
	public  String Encrypt(String text) {
		try {
			byte[] keyBytes = readKey().getBytes();
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			SecretKeySpec sKeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
			byte[] encrypted = cipher.doFinal(text.getBytes("utf-8"));
			return new BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public  String Decrypt(String text) {
		try {
			byte[] raw = readKey().getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());			
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(text);			
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Object> iptolist(String s) {
		List<Object> o = new ArrayList<Object>();
		String ss[] = s.split(",");
		/*for(int i = ss.length-1;i>=0;i--){
			o.add(ss[i]);
		}*/
		for(String d:ss){
			o.add(d);
		}
		
		return o;
	}
	
	@Resource
	public void setLdapUtils(PersonServiceI ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	
}
