package org.roof.upload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.FileUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.struts2.WebUtils;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 FileService.java 2012-8-22
 */
@Component
public class FileService {

	private static final Logger logger = Logger.getLogger(FileService.class);
	private RoofDaoSupport roofDaoSupport;

	public static FileInfo initFileInfo(String path, String displayName, boolean addDateDir, boolean isImage) {
		path = path + File.separator;
		FileInfo fileInfo = new FileInfo();
		String extension = FileUtils.getExtention(displayName);
		String name = UUID.randomUUID().toString();
		String fullName = name + FileUtils.getExtention(displayName);
		fileInfo.setDisplayName(displayName);
		fileInfo.setName(name);
		fileInfo.setType(extension);
		if (addDateDir) {
			path = FileUtils.addDateDir(path);
		}
		fileInfo.setRealPath(path + fullName);
		if (isImage) {
			String thumbPath = path + name + ImageService.THUMB_SUFFIX + FileUtils.getExtention(displayName);
			thumbPath = StringUtils.replace(thumbPath, File.separator, "/");
			fileInfo.setWebPathThumb(thumbPath);
		}
		fileInfo.setWebPath(StringUtils.replace(fileInfo.getRealPath(), File.separator, "/"));
		return fileInfo;
	}

	public FileInfo save(File file, String displayName, String folder) throws Exception {
		if (file == null) {
			throw new IllegalArgumentException("文件不能为空!");
		}
		if (folder == null) {
			String f = null;
			try {
				f = PropertiesUtil.getPorperty("project.upload.file.folder", String.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			folder = FileUtils.formatePath(f);
		} else {
			folder = FileUtils.formatePath(folder);
		}
		FileInfo fileInfo = initFileInfo(folder, displayName, true, false);
		String root = FileUtils.formatePath(WebUtils.getServletContext().getRealPath("/"));
		FileUtils.mkDirs(root + fileInfo.getWebPath());
		File dest = null;
		try {
			dest = FileUtils.createFile(root + fileInfo.getWebPath());
			FileUtils.copy(file, dest);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		roofDaoSupport.save(fileInfo);
		return fileInfo;
	}

	public void delete(Long fileInfoId) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(fileInfoId);
		String root = FileUtils.formatePath(WebUtils.getServletContext().getRealPath("/"));
		FileUtils.deleteFile(root + fileInfo.getWebPath());
		roofDaoSupport.delete(fileInfo);
	}

	public void delete(FileInfo fileInfo) {
		String root = FileUtils.formatePath(WebUtils.getServletContext().getRealPath("/"));
		FileUtils.deleteFile(root + fileInfo.getWebPath());
		roofDaoSupport.delete(fileInfo);
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

}
