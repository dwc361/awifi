package org.roof.commons;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FtpUtil {
	private Log _logger = LogFactory.getLog(this.getClass());

	FtpClient ftpClient;

	/**
	 * connectServer 连接ftp服务器
	 * 
	 * @throws java.io.IOException
	 * @param path
	 *            文件夹，空代表根目录
	 * @param password
	 *            密码
	 * @param user
	 *            登录用户
	 * @param server
	 *            服务器地址
	 */
	public boolean connectServer(String server, int port, String user, String password, String path) throws IOException {
		// server：FTP服务器的IP地址；user:登录FTP服务器的用户名
		// password：登录FTP服务器的用户名的口令；path：FTP服务器上的路径
		try {
//			ftpClient = new FtpClient();
//			_logger.info("FTP连接中...");
//			ftpClient.openServer(server, port);
//			ftpClient.login(user, password);
//			_logger.info("FTP连接成功...");
//			// path是ftp服务下主目录的子目录
//			if (path.length() != 0)
//				ftpClient.cd(path);
//			// 用2进制上传、下载
//			ftpClient.binary();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(ExceptionUtils.getFullStackTrace(e));
			_logger.info("FTP连接失败...");
			return false;
		}

	}

	/**
	 * upload 上传文件
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在 -2 文件内容为空 >0 成功上传，返回文件的大小
	 * @param newname
	 *            上传后的新文件名
	 * @param remoteFile
	 *            上传的文件
	 */
	public long upload(String filename, String newname) throws Exception {
		long result = 0;
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			java.io.File file_in = new java.io.File(filename);
			if (!file_in.exists())
				return -1;
			if (file_in.length() == 0)
				return -2;
//			os = ftpClient.put(newname);
			result = file_in.length();
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result;
	}

	/**
	 * upload
	 * 
	 * @throws java.lang.Exception
	 * @return
	 * @param remoteFile
	 */
	public long upload(String filename) throws Exception {
		String newname = "";
		if (filename.indexOf("/") > -1) {
			newname = filename.substring(filename.lastIndexOf("/") + 1);
		} else {
			newname = filename;
		}
		return upload(filename, newname);
	}

	/**
	 * download 从ftp下载文件到本地
	 * 
	 * @throws java.lang.Exception
	 * @return
	 * @param remoteFile
	 *            服务器上的文件名
	 * @param localFile
	 *            本地生成的文件名
	 */
	public long download(String remoteFile, String localFile) throws Exception {
		long result = 0;
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {
			if(!this.needDownloadType(remoteFile)){
				return result;
			}
//			is = ftpClient.get(remoteFile);
			File outfile = new File(localFile);
			os = new FileOutputStream(outfile);
			byte[] bytes = new byte[1024000];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result = result + c;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			_logger.info("文件[" + remoteFile + "]于[" + dateFormat.format(new Date()) + "]已经下载到" + localFile);
		} catch (IOException e) {
			e.printStackTrace();
			_logger.error(ExceptionUtils.getFullStackTrace(e));
			_logger.info("文件[" + remoteFile + "]下载到" + localFile + "失败...");
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result;
	}

	/**
	 * 取得某个目录下的所有文件列表
	 * 
	 */
	public List getFileList(String path) {
		List list = new ArrayList();
		try {
			DataInputStream dis = new DataInputStream(ftpClient.nameList(path));
			String filename = "";
			while ((filename = dis.readLine()) != null) {
				list.add(filename);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * closeServer 断开与ftp服务器的链接
	 * 
	 * @throws java.io.IOException
	 */
	public void closeServer() {
//		try {
//			if (ftpClient != null) {
//				ftpClient.closeServer();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 拷贝远程文件到本地工程
	 * 
	 * @param needFileName 如果文件名为空，则下载全部文件；
	 */
	public void downloadFiles(String needFileName) {
		try {
			String ip = PropertiesUtil.getPorpertyString("ftp_ip");
			String port = PropertiesUtil.getPorpertyString("ftp_port");
			String user = PropertiesUtil.getPorpertyString("ftp_user");
			String pwd = PropertiesUtil.getPorpertyString("ftp_pwd");
			String dir = PropertiesUtil.getPorpertyString("ftp_dir");
			String copy_temp_path = PropertiesUtil.getPorpertyString("ftp_copy_temp_path");

			// 先通过FTP，将文件拷贝到本地
			boolean flag = this.connectServer(ip, Integer.parseInt(port), user, pwd, dir);
			if(!flag){
				return;
			}
			List list = new ArrayList();
			if(StringUtils.isEmpty(needFileName)){
				list = this.getFileList("");
			}else{
				list = this.getFileList(needFileName);
			}
			_logger.info("找到文件[" + dir + needFileName + "]数量：" + list.size() + "，下载中...");
			for (int i = 0; i < list.size(); i++) {
				String file = list.get(i).toString();
				this.download(file, copy_temp_path + file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			this.closeServer();
		}
	}
	
	public static void main(String[] args) throws Exception {
		FtpUtil ftp = new FtpUtil();
		try {
			// 连接ftp服务器
			ftp.connectServer("134.98.100.211", 21, "root", "123456itsm", "/root/share/");

			/** 上传文件到 ftpdir 文件夹下 */
			// System.out.println("filesize:"
			// +
			// ftp.upload("/home/dbbackup/indicator/BK_dev_table_others201203300204.dmp.rar")
			// + "字节");
			/** 取得ftpdir文件夹下的所有文件列表,并下载到 E盘下 */
			// List list = ftp.getFileList(".");// 参数.表示所有的文件
			// for (int i = 0; i < list.size(); i++) {
			// String filename = (String) list.get(i);
			// System.out.println(filename);
			// ftp.download(filename, "E:/" + filename);
			// }
			// List list = ftp.getFileList("M896000_20120508.Z");
			// List list = ftp.getFileList("bank2.txt");
			List list = ftp.getFileList(".");
			for (int i = 0; i < list.size(); i++) {
				String filename = (String) list.get(i);
				System.out.println(new String(filename.getBytes("GBK"), "ISO-8859-1"));
				System.err.println(ftp.download(filename, "E:/temp/" + filename));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftp.closeServer();
		}
	}

	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private static List<String> typeList = new ArrayList<String>();// 需要下载过来的文件类型
	/**
	 * 需要下载过来的文件类型
	 * 
	 * @param value
	 * @return
	 * @throws IOException 
	 */
	private boolean needDownloadType(String value) throws IOException {
		if (CollectionUtils.isEmpty(typeList)) {
			typeList = PropertiesUtil.initPropList(typeList, "ftp_need_file_types");
		}
		if (CollectionUtils.isEmpty(typeList)) {
			return true;
		}
		boolean flag = false;
		for (String rule : typeList) {
			if (pathMatcher.match(rule, value)) {
				return true;
			}
		}
		return flag;
	}
}
