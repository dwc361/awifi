package com.zjhcsoft.uac.util;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class EncrypDESTest {

	@Test
	public void testCreateKey() throws IOException, NoSuchAlgorithmException {
		Resource resource = new ClassPathResource("key.des");
		File file = resource.getFile();
		if (!file.exists()) {
			file.createNewFile();
		}
		EncrypDES.createKey(file);
	}

	@Test
	public void testReadKey() throws IOException {
		Assert.assertNotNull(readKey());
	}

	private Key readKey() throws IOException {
		Resource resource = new ClassPathResource("key.des");
		File file = resource.getFile();
		Key key = EncrypDES.readKey(file);
		return key;
	}

	@Test
	public void testEncryptStringKey() throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		String s = "Not yet implemented只哦那个";
		Key key = readKey();
		String r = EncrypDES.encrypt(s, key);
		System.out.println(r);
	}

	@Test
	public void testDecryptStringKey() throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		String s = "Not yet implemented只哦那个1212";
		Key key = readKey();
		String r = EncrypDES.encrypt(s, key);
		String s2 = EncrypDES.decrypt(r, key);
		junit.framework.Assert.assertEquals(s, s2);
		System.out.println(s2);
	}

	@Test
	public void getPassword() {
		String password = "622901197008100546";
		String r = StringUtils.substring(password, password.length() - 7,
				password.length() - 1);
		System.out.println(r);
	}
}
