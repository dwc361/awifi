package org.roof.upload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.roof.commons.ImageUtil;
import org.roof.commons.RoofDateUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件上传工具类 <br />
 * 每一个上传的文件都会在sf_fileInfo表中保存记录
 * 
 * @see org.seek.roof.upload.FileInfo
 * 
 * @author liuxin
 * 
 */
@Component("uploadUtil")
@Transactional
public class UploadUtil {
	private UploadDao uploadDao;
	private static final int BUFFER_SIZE = 16 * 1024;

	/*********************************
	 * 文件是否超过指定大小 true为超过大小 作者： hongzc 时间： 2011-3-2
	 * 
	 * @param file
	 *            文件 File
	 * @param fileSizeK
	 *            指定大小 单位K
	 * @return
	 * @throws Exception
	 *********************************/
	public static boolean isExcessSize(File file, int fileSizeK)
			throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return (((double) fis.available() / 1024) > fileSizeK);
		} finally {
			fis.close();
		}
	}

	public static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存文件同时在数据库中生成记录
	 * 
	 * @param uploaded
	 *            需要保存的文件
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @param displayName
	 *            显示的名称
	 * @return 文件的保存记录
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo save(File uploaded, String root, String path,
			String displayName) {
		return this.save(uploaded, root, path, displayName, false);
	}

	/**
	 * 保存文件同时在数据库中生成记录
	 * 
	 * @param uploaded
	 *            需要保存的文件
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @param displayName
	 *            显示的名称
	 * @param addDateDir
	 *            存放在日期的子目录下
	 * @return 文件的保存记录
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo save(File uploaded, String root, String path,
			String displayName, boolean addDateDir) {
		FileInfo fileInfo = initFileInfo(root, path, displayName, addDateDir);
		File dst = new File(root + fileInfo.getRealPath());
		copy(uploaded, dst);
		// long size = GetFileSize.getFileSize(dst);
		// fileInfo.setSize(size);
		// long size = GetFileSize.getFileSize(dst);
		// fileInfo.setSize(size);
		uploadDao.save(fileInfo);
		return fileInfo;
	}

	/**
	 * 生成要保存的文件记录
	 * 
	 * @param uploaded
	 *            需要保存的文件
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @param displayName
	 *            显示的名称
	 * @param addDateDir
	 *            存放在日期的子目录下
	 * @return 文件记录
	 */
	private FileInfo initFileInfo(String root, String path, String displayName,
			boolean addDateDir) {
		root = formatePath(root);
		path = formatePath(path);
		FileInfo fileInfo = new FileInfo();
		String extension = getExtention(displayName);
		String name = UUID.randomUUID().toString();
		String fullName = name + getExtention(displayName);

		if (addDateDir) {
			path = addDateDir(root, path);
		}
		fileInfo.setDisplayName(displayName);
		fileInfo.setName(name);
		fileInfo.setType(extension);
		fileInfo.setRealPath(path + fullName);
		fileInfo.setWebPath(path + fullName);
		return fileInfo;
	}

	/**
	 * 将图片压缩为最大限度能放到指定大小容器的中
	 * 
	 * @param uploaded
	 *            需要保存的图片
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @param displayName
	 *            显示的名称
	 * @param addDateDir
	 *            是否存放在日期的子目录下
	 * @param width
	 *            容器的宽度
	 * @param height
	 *            容器的高度
	 * @return 文件的保存记录
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo saveImageBox(File uploaded, String root, String path,
			String displayName, boolean addDateDir, int width, int height)
			throws IOException {
		FileInfo fileInfo = this.initFileInfo(root, path, displayName,
				addDateDir);
		File des = new File(root + fileInfo.getRealPath());
		ImageUtil imageUtil = new ImageUtil();
		BufferedImage source = imageUtil.read(uploaded);
		BufferedImage compressed = imageUtil.compressToBox(source, width,
				height);
		imageUtil.write(compressed, des);
		// long size = GetFileSize.getFileSize(des);
		// fileInfo.setSize(size);
		uploadDao.save(fileInfo);
		return fileInfo;
	}

	/**
	 * 将图片压缩为最大限度能放到指定大小容器的中
	 * 
	 * @param uploaded
	 *            需要保存的图片
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @param displayName
	 *            显示的名称
	 * @param width
	 *            容器的宽度
	 * @param height
	 *            容器的高度
	 * @return 文件的保存记录
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo saveImageBox(File uploaded, String root, String path,
			String displayName, int width, int height) throws IOException {
		return this.saveImageBox(uploaded, root, path, displayName, false,
				width, height);
	}

	/**
	 * 将图片剪裁到需要的大小并保存
	 * 
	 * @param cutted
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @param displayName
	 *            显示的名称
	 * @param addDateDir
	 *            是否存放在日期的子目录下
	 * @param x
	 *            剪裁的x坐标
	 * @param y
	 *            剪裁的y坐标
	 * @param width
	 *            需要剪裁块的宽度
	 * @param height
	 *            需要块的高度
	 * @return
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo cutImage(File cutted, String root, String path,
			String displayName, boolean addDateDir, int x, int y, int width,
			int height) throws IOException {
		FileInfo fileInfo = this.initFileInfo(root, path, displayName,
				addDateDir);
		File des = new File(root + fileInfo.getRealPath());
		ImageUtil imageUtil = new ImageUtil();
		BufferedImage source = imageUtil.read(cutted);
		BufferedImage cutting = imageUtil
				.cutBySize(source, x, y, width, height);
		imageUtil.write(cutting, des);
		// long size = GetFileSize.getFileSize(des);
		// fileInfo.setSize(size);
		uploadDao.save(fileInfo);
		return fileInfo;

	}

	/**
	 * 上传文件
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo saveFile(File uploaded, String root, String path,
			String tableName, String fileName) throws IOException {

		root = formatePath(root);
		path = formatePath(path);
		FileInfo fileInfo = new FileInfo();

		String extension = getExtention(fileName);
		String name = UUID.randomUUID().toString();
		String fullName = name + getExtention(fileName);
		path = addDateDir(root, path);

		fileInfo.setDisplayName(fileName);
		fileInfo.setName(name);
		fileInfo.setType(extension);
		fileInfo.setRealPath(path + fullName);
		fileInfo.setWebPath(path + fullName);

		fileInfo.setTableName(tableName);
		// 这时ID是不知道的

		fileInfo.setFileSize(uploaded.length()); // 文件大小
		uploadDao.save(fileInfo);
		return fileInfo;
	}

	/**
	 * 删除文件 同时删除数据库文件记录
	 * 
	 * @param root
	 *            根目录
	 * @param id
	 *            文件记录id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String root, Long id) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(id);
		delete(root, fileInfo);
	}

	/**
	 * 删除文件 同时删除数据库文件记录
	 * 
	 * @param root
	 *            根目录
	 * @param fileInfo
	 *            文件记录
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String root, FileInfo fileInfo) {
		fileInfo = uploadDao.load(FileInfo.class, fileInfo.getId());
		File file = new File(root + fileInfo.getRealPath());
		if (file.exists()) {
			file.delete();
		}
		uploadDao.delete(fileInfo);
	}

	public FileInfo selectById(Long id) {
		return uploadDao.load(FileInfo.class, id);
	}

	/**
	 * 格式化路径 删除开始的"/" 增加 结尾的"/"
	 * 
	 * @param path
	 *            格式化后的路径
	 */
	private String formatePath(String path) {
		if (path.startsWith(File.separator)) {
			path = path.substring(1);
		}
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path;
	}

	/**
	 * 在文件夹下加入今天的日期(yyyyMMdd)为名称的子文件夹 <br />
	 * 有则返回没有则创建
	 * 
	 * @param root
	 *            根目录
	 * @param path
	 *            相对目录
	 * @return 加入日期文件夹的目录
	 */
	private String addDateDir(String root, String path) {
		String date = RoofDateUtils.dateToString(new Date(), "yyyyMMdd");
		date = formatePath(date);
		String fullPath = root + path + date;
		File file = new File(fullPath);
		if (!file.exists()) {
			file.mkdir();
		}
		return path + date;
	}

	/**
	 * 取得文件扩展名
	 * 
	 * @param fileName
	 *            文件全名
	 * @return 文件扩展名(例如: .gif)
	 */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	@Resource(name = "uploadDao")
	public void setUploadDao(UploadDao uploadDao) {
		this.uploadDao = uploadDao;
	}

}
