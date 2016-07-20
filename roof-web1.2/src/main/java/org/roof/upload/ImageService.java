package org.roof.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.FileUtils;
import org.roof.commons.ImageUtil;
import org.roof.commons.PropertiesUtil;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.struts2.WebUtils;
import org.springframework.stereotype.Component;

/**
 * 图片上传服务类
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 ZcswImageUtils.java 2012-8-20
 */
@Component
public class ImageService {

	private static final int THUMB_HEIGHT = 300;
	private static final int THUMB_WIDTH = 300;
	static final Logger logger = Logger.getLogger(ImageService.class);
	public static final String THUMB_SUFFIX = "-thumb";

	private RoofDaoSupport roofDaoSupport;

	public FileInfo save(File file, String displayName, String folder) throws Exception {
		if (file == null) {
			throw new IllegalArgumentException("file should be not null !");
		}
		FileInfo fileInfo = null;
		BufferedImage image = null;
		BufferedImage imageThumb = null;
		String root = FileUtils.formatePath(WebUtils.getServletContext().getRealPath("/"));
		try {
			image = ImageUtil.read(file);
			imageThumb = ImageUtil.compressToBox(image, THUMB_WIDTH, THUMB_HEIGHT); // 压缩略图
			fileInfo = saveFileInfo(displayName, folder);
			imageWriteToFile(image, root + fileInfo.getWebPath());
			imageWriteToFile(imageThumb, root + fileInfo.getWebPathThumb());
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("文件必须为图片格式!");
		}
		return fileInfo;
	}

	public void delete(Long fileInfoId) {
		if (fileInfoId == null) {
			throw new IllegalArgumentException("fileInfoId should be not null !");
		}
		FileInfo fileInfo = roofDaoSupport.load(FileInfo.class, fileInfoId);
		String root = FileUtils.formatePath(WebUtils.getServletContext().getRealPath("/"));
		FileUtils.deleteFile(root + fileInfo.getWebPath());
		FileUtils.deleteFile(root + fileInfo.getWebPathThumb());
		roofDaoSupport.delete(fileInfo);
	}

	private FileInfo saveFileInfo(String displayName, String folder) {
		FileInfo fileInfo = null;
		try {
			if (StringUtils.isBlank(folder)) {
				folder = PropertiesUtil.getPorperty("project.upload.image.folder", String.class);
			}
			fileInfo = FileService.initFileInfo(folder, displayName, true, true);
			roofDaoSupport.save(fileInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileInfo;
	}

	private File imageWriteToFile(BufferedImage image, String path) throws IOException {
		if (logger.isInfoEnabled()) {
			logger.info("正在写入文件[" + path + "]...");
		}
		FileUtils.mkDirs(path);
		File file = FileUtils.createFile(path);
		ImageUtil.write(image, file);
		if (logger.isInfoEnabled()) {
			logger.info("文件写入完毕[" + path + "]");
		}
		return file;
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}
}
