package com.awifi.bigscreen.data.service.Impl;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.roof.commons.PropertiesUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.data.entity.GetUserData;
import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

@Service
public class ZpullData implements IPullData<UserData>,InitializingBean{
	
	private String url = PropertiesUtil.getPorpertyString("zabbix.url");
	private String user = PropertiesUtil.getPorpertyString("zabbix.user");
	private String password = PropertiesUtil.getPorpertyString("zabbix.password");


	JsonRpcHttpClient client = null;
	@Override
	public void afterPropertiesSet() throws Exception {
		client = new JsonRpcHttpClient(new URL(url));
	}

	public boolean isExist() {
		return false;
	}

	public UserData Pull() {
		String auth = null;
		try {
			auth = getAuth();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		Map<String,Object> headers = new HashMap<String,Object>();
        //headers.put("id", "1");
        headers.put("auth", auth);

        client.setAdditionalJsonContent(headers);
        
        Map<String,Object> params = new HashMap<String,Object>();

        params.put("output", "extend");
        params.put("history", 3);
        params.put("itemids", "47523");
        params.put("sortfield", "clock");
        params.put("sortorder", "DESC");
        params.put("limit", 1);
        List<UserData> userDatas = null;
		try {
			ObjectMapper mapper = new ObjectMapper(); 
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, UserData.class); 
			userDatas = (List<UserData>) client.invoke("history.get",params,javaType);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return userDatas.get(0);
		
	}

	
	private String getAuth() throws Throwable {
        Map<String,String> params = new HashMap<String,String>();
        params.put("user", user);
        params.put("password", password);
        
		String s = client.invoke("user.login",params, String.class);
		return s;
	}

	

}
