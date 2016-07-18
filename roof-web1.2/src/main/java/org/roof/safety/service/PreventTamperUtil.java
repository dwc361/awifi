package org.roof.safety.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.roof.commons.cache.CacheUtil;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.safety.entity.FileRecover;
import org.roof.spring.CurrentSpringContext;

/**
 * 防篡改工具类
 * 
 * @author hongzc
 * 
 */
public class PreventTamperUtil {

	public final static String encode = "UTF-8";// 文件的字符集类型

	public final static String cacheName = "protectList";// 防篡改的缓存名

	public final static String protectDir = "E:/work/bizmgr/WebRoot/testbak";// 保护的目录

	private static RoofDaoSupport roofDaoSupport = (RoofDaoSupport) CurrentSpringContext
			.getBean("roofDaoSupport");

	/**
	 * 读取文件夹下所有子目录下的文件全路径(不包含目录)，并将其保存到list集合中。
	 * 
	 * @param url
	 *            目录路径
	 * @return List 文件集合
	 * @throws IOException
	 */
	public List<String> readFilesFromFolder(String url, List<String> list) {
		if (list == null) {
			list = new ArrayList<String>();
		}
		// 通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例。
		File file = new File(url);

		// 测试此抽象路径名表示的文件或目录是否存在
		if (file.exists()) {
			// 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				// 判断是否是文件夹，如果是文件夹则继续读取文件夹下的子目录或文件
				if (files[i].isDirectory()) {
					this.readFilesFromFolder(files[i].getPath(), list);
				} else {
					if (!this.ignoreFile(files[i].getName())) {
						list.add(files[i].getAbsolutePath().replaceAll("\\\\",
								"/"));// 全路径文件名
					}
				}
			}
		}
		return list;
	}

	/**
	 * 忽略保护的文件类型
	 * @param filename
	 * @return
	 */
	private boolean ignoreFile(String filename) {
		filename = filename.toLowerCase();
		if (filename.endsWith(".bmp")) {
			return true;
		}
		if (filename.endsWith(".jpg")) {
			return true;
		}
		if (filename.endsWith(".png")) {
			return true;
		}
		if (filename.endsWith(".swf")) {
			return true;
		}
		return false;
	}

	/**
	 * 从全路径读数据存放到防篡改类中
	 * 
	 * @throws IOException
	 */
	public FileRecover fillFileRecover(String filepath) {
		File file = new File(filepath);
		FileRecover fileRecover = new FileRecover();
		fileRecover.setContent(this.readContent(filepath));
		fileRecover.setUrl(filepath);
		fileRecover.setCapacity(file.length());
		fileRecover.setLast_modify_date(new Date(file.lastModified()));
		fileRecover.setPublish_date(new Date());
		fileRecover.setEncrypt_code(MD5Util.getMD5String(fileRecover
				.getContent()));
		return fileRecover;
	}

	/**
	 * 从全路径读取文件内容
	 * 
	 * @param filepath
	 * @return
	 */
	public String readContent(String filepath) {
		String s = null;
		StringBuffer text = new StringBuffer();
		InputStreamReader iReader = null;
		BufferedReader bReader = null;
		try {
			iReader = new InputStreamReader(new FileInputStream(filepath),
					encode);
			bReader = new BufferedReader(iReader);
			while ((s = bReader.readLine()) != null) {
				text.append(s + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bReader.close();
				iReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return text.toString();
	}

	/**
	 * 将恢复后文件的最后修改时间入库
	 * 
	 * @param fileRecover
	 */
	public boolean updateFileRecover(FileRecover fileRecover) {
		boolean flag = false;
		if (this.write(fileRecover.getUrl(), fileRecover.getContent())) {
			File file = new File(fileRecover.getUrl());
			fileRecover.setLast_modify_date(new Date(file.lastModified()));
			roofDaoSupport.update(fileRecover);
			flag = true;
		}
		return flag;
	}

	/**
	 * 文件恢复，将内容写入到指定的文件内容目录
	 * 
	 * @param filepath
	 * @param dir
	 * @param content
	 * @return
	 */
	public boolean write(String filepath, String content) {
		boolean flag = false;
		String dir = filepath.substring(0, filepath.lastIndexOf("/"));
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		OutputStreamWriter oWriter = null;
		BufferedWriter bWriter = null;
		try {
			oWriter = new OutputStreamWriter(new FileOutputStream(filepath),
					encode);
			bWriter = new BufferedWriter(oWriter);
			bWriter.write(content);
			flag = true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bWriter.close();
				oWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 比较服务器文件和数据库文件是否一致
	 * 
	 * @param fileRecover
	 * @return
	 */
	public FileRecover compareTo(FileRecover fileRecover) {
		try {
			File file = new File(fileRecover.getUrl());
			if (!file.exists()) {
				return fileRecover;
			}
			if (!MD5Util.getMD5String(this.readContent(fileRecover.getUrl()))
					.equals(fileRecover.getEncrypt_code())) {
				return fileRecover;
			}
			// if (Math.abs(file.lastModified()
			// - fileRecover.getLast_modify_date().getTime()) > 1000) {
			// return fileRecover;
			// }
			// if (Math.abs(file.length() - fileRecover.getCapacity()) > 10) {
			// return fileRecover;
			// }
			fileRecover = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileRecover;
	}

	/**
	 * 加载防篡改的集合列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileRecover> loadFileRecoverList() {
		if (CacheUtil.get(cacheName, cacheName) == null) {
			List<FileRecover> list = (List<FileRecover>) roofDaoSupport
					.loadAll(FileRecover.class);
			CacheUtil.addCache(cacheName);
			CacheUtil.put(cacheName, cacheName, list);
		}
		return (List<FileRecover>) CacheUtil.get(cacheName, cacheName);
	}

	/**
	 * 将保护目录下的所有文件保存到数据库
	 */
	public void initFilesToDB() {
		List<String> list = readFilesFromFolder(protectDir, null);
		for (int i = 0; i < list.size(); i++) {// 初始化时，先将刚发布的文件内容入库
			FileRecover fileRecover = this.fillFileRecover(list.get(i));
			roofDaoSupport.save(fileRecover);
		}
	}

	/**
	 * 比较值是否在列表中(source列表中的元素为domain类型，取domain中的field属性与字符串值compare进行比较)
	 * 
	 * @param compare
	 * @param source
	 * @param field
	 * @return
	 */
	public boolean containsField(String compare, List source, String field) {
		boolean flag = false;
		for (int i = 0; i < source.size(); i++) {
			String listVal = findDomainValue(field, source.get(i));
			if (listVal.equals(compare)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 获得对象的指定属性
	 * 
	 * @param field
	 * @param domain
	 * @return
	 */
	public String findDomainValue(String field, Object domain) {
		Class[] clazzArr = null;// 表示该方法无参数
		// {java.lang.Long.class,java.lang.String.class}
		Object[] argsArr = null;// 表示该方法无参数 {5L,"test"}
		String result = "";
		try {
			Method method = domain.getClass().getDeclaredMethod(
					"get" + StringUtils.capitalize(field), clazzArr);// 调用指定属性的get方法
			result = method.invoke(domain, argsArr).toString();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return result;
	}
}
