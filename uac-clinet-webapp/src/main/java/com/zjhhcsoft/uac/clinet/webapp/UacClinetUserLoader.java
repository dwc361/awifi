package com.zjhhcsoft.uac.clinet.webapp;

import java.io.File;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.zjhhcsoft.uac.clinet.EncrypDES;
import com.zjhhcsoft.uac.clinet.UserLoader;

public class UacClinetUserLoader implements UserLoader {
	private Key key;

	@Override
	public Object load(Object obj, ServletRequest request,
			ServletResponse response) {
		if (key == null) {
			URL url = this.getClass().getClassLoader().getResource("key.des");
			File file = new File(url.getPath());
			key = EncrypDES.readKey(file);
		}

		if (obj instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) obj;
			System.out.println("加载用户:" + map.get("username"));
			String password = map.get("password").toString();
			if (password != null) {
				System.out.println("加密密码:" + password);
				try {
					System.out
							.println("密码:" + EncrypDES.decrypt(password, key));
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}
			}

		} else {
			System.out.println("加载用户:" + obj);
		}
		return obj;
	}
}
