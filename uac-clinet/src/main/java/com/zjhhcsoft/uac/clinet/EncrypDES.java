package com.zjhhcsoft.uac.clinet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

/**
 * DES算法加解密
 * 
 * @author liuxin
 *
 */
public class EncrypDES {

	private static final Logger LOGGER = Logger.getLogger(EncrypDES.class);

	/**
	 * 生成加密Key
	 * 
	 * @param file
	 *            Key存放的文件
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static void createKey(File file) throws NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(random);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		Key key = kg.generateKey();
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(key);
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
				}
			}

		}
	}

	/**
	 * 从文件读取加密Key
	 * 
	 * @param file
	 *            Key 文件
	 * @return
	 */
	@SuppressWarnings("resource")
	public static Key readKey(File file) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Key key = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			key = (Key) ois.readObject();
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return key;
	}

	/**
	 * 加密
	 * 
	 * @param is
	 *            原文流
	 * @param os
	 *            密文流
	 * @param key
	 *            Key 文件
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static void encrypt(InputStream is, OutputStream os, Key key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException {
		byte[] buff = new byte[1024];
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		CipherInputStream cis = null;
		try {
			cis = new CipherInputStream(is, cipher);
			int i = 0;
			while ((i = cis.read(buff)) != -1) {
				os.write(buff, 0, i);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

	}

	/**
	 * 加密
	 * 
	 * @param info
	 *            原文字符串
	 * @param key
	 *            Key 文件
	 * @return 加密后的16进制字符串
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static String encrypt(String info, Key key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;
		byte[] bytes = null;
		try {
			bis = new ByteArrayInputStream(info.getBytes());
			bos = new ByteArrayOutputStream();
			encrypt(bis, bos, key);
			bytes = bos.toByteArray();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return asHex(bytes);
	}

	/**
	 * 解密
	 * 
	 * @param is
	 *            密文流
	 * @param os
	 *            原文流
	 * @param key
	 *            Key 文件
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static void decrypt(InputStream is, OutputStream os, Key key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException {
		byte[] buff = new byte[1024];
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		CipherOutputStream cos = null;
		try {
			cos = new CipherOutputStream(os, cipher);
			int i = 0;
			while ((i = is.read(buff)) != -1) {
				cos.write(buff, 0, i);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (cos != null) {
				try {
					cos.flush();
					cos.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}

		}

	}

	/**
	 * 解密
	 * 
	 * @param info
	 *            密文16进制字符串
	 * @param key
	 *            Key 文件
	 * @return 原文字符串
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static String decrypt(String info, Key key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		byte[] bytes = asByteArray(info);
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			bos = new ByteArrayOutputStream();
			decrypt(bis, bos, key);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return bos.toString();
	}

	/**
	 * Returns a hexadecimal representation of the given byte array.
	 * 
	 * @param bytes
	 *            the array to output to an hex string
	 * @return the hex representation as a string
	 */
	public static String asHex(byte[] bytes) {
		return asHex(bytes, null);
	}

	/**
	 * Returns a hexadecimal representation of the given byte array.
	 * 
	 * @param bytes
	 *            the array to output to an hex string
	 * @param separator
	 *            the separator to use between each byte in the output string.
	 *            If null no char is inserted between each byte value.
	 * @return the hex representation as a string
	 */
	public static String asHex(byte[] bytes, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String code = Integer.toHexString(bytes[i] & 0xFF);
			if ((bytes[i] & 0xFF) < 16) {
				sb.append('0');
			}

			sb.append(code);

			if (separator != null && i < bytes.length - 1) {
				sb.append(separator);
			}
		}

		return sb.toString();
	}

	/**
	 * Converts a hex string representation to a byte array.
	 * 
	 * @param hex
	 *            the string holding the hex values
	 * @return the resulting byte array
	 */
	public static byte[] asByteArray(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
					16);
		}

		return bts;
	}

}
