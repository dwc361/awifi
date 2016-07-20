package org.roof.upload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.roof.upload.FileInfo;
import org.roof.upload.UploadUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("uploadService")
@Transactional
public class UploadService {

	private static String TEMP_PATH = "upload_file/temp/";
	private static String PHOTO_PATH = "upload_file/photo/";
	private static String File_PATH = "upload_file/file/";
	private static int WIDTH = 140;
	private static int HEIGHT = 180;
	private static int BOX_WIDTH = 800;
	private static int BOX_HEIGHT = 425;
	private UploadUtil uploadUtil;
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo uploadTempPoto(File uploaded, String root, String displayName) {
		FileInfo fileInfo = null;
		try {
			fileInfo = uploadUtil.saveImageBox(uploaded, root, TEMP_PATH, displayName, true, BOX_WIDTH, BOX_HEIGHT);
		} catch (IOException e) {
			logger.error(e);
		}
		return fileInfo;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo uploadPhoto(Long cuttedId, String root, int x, int y) {
		FileInfo photoFileInfo = null;
		FileInfo cuttedFileInfo = uploadUtil.selectById(cuttedId);
		File cutted = new File(root + cuttedFileInfo.getRealPath());
		try {
			photoFileInfo = uploadUtil.cutImage(cutted, root, PHOTO_PATH, cuttedFileInfo.getDisplayName(), true, x, y,
					WIDTH, HEIGHT);
			uploadUtil.delete(root, cuttedFileInfo);
		} catch (IOException e) {
			logger.error(e);
		}
		return photoFileInfo;
	}
	
	/**
	 * 上传文件
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public FileInfo uploadFile(File uploaded, String root, String tableName,String fileName) {
		FileInfo fileInfo = null;
		try {
			//往数据库里查文件记录
			fileInfo = uploadUtil.saveFile(uploaded, root, File_PATH, tableName,fileName);
			
			//新的文件地址,把uploaded文件写入des
			File des = new File(root + fileInfo.getRealPath());
			
			 //写入文件
			InputStream inputStream = new FileInputStream(uploaded);
			OutputStream outStream =  new FileOutputStream(des);
			
			int bytesum = 0;
			int byteread = 0;
			byte[] buffer = new byte[1444];
			while((byteread =inputStream.read(buffer))!=-1)
			{
				bytesum += byteread;
				outStream.write(buffer, 0, byteread);
			}
			inputStream.close();
			outStream.close();
			
		} catch (IOException e) {
			logger.error(e);
		}
		return fileInfo;
	}
	
	

	@Resource(name = "uploadUtil")
	public void setUploadUtil(UploadUtil uploadUtil) {
		this.uploadUtil = uploadUtil;
	}

}
