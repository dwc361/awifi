package org.roof.file.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.log4j.Logger;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileInfoService;
import org.roof.fileupload.api.FileManager;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.exception.FileInfoNotFoundException;
import org.roof.fileupload.utils.FileUtils;
import org.roof.spring.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("fileAction")
public class FileAction {
	private FileManager fileManager;
	private FileInfoService fileInfoService;
	private Logger LOGGER = Logger.getLogger(FileAction.class);

	/**
	 * 上传普通文件
	 */
	@RequestMapping("/upload")
	public @ResponseBody Result upload(@RequestParam("file") MultipartFile file) {
		Map<String, Object> xdata = new HashMap<String, Object>();
		xdata.put("displayName", file.getOriginalFilename());
		xdata.put("fileSize", file.getSize());
		xdata.put("ContentType", file.getContentType());
		FileInfo fileInfo = null;
		try (InputStream in = file.getInputStream()) {
			fileInfo = fileManager.saveFile(in, xdata);
		} catch (FileException | IOException e) {
			LOGGER.error(e.getMessage(), e);
			return new Result("数据传输失败:" + e.getMessage());
		}
		return new Result(Result.SUCCESS, fileInfo);
	}

	/**
	 * 上传Base64格式的文件
	 */
	@RequestMapping("/uploadBase64")
	public @ResponseBody Result uploadBase64(String fileName, String file) {
		Map<String, Object> xdata = new HashMap<String, Object>();
		xdata.put("displayName", fileName);
		xdata.put("fileSize", file.length());
		FileInfo fileInfo = null;
		try (ByteArrayInputStream in = new ByteArrayInputStream(Base64Utils.decode(file.getBytes()))) {

			fileInfo = fileManager.saveFile(in, xdata);
		} catch (FileException | IOException e) {
			LOGGER.error(e.getMessage(), e);
			return new Result("数据传输失败:" + e.getMessage());
		}
		return new Result(Result.SUCCESS, fileInfo);
	}

	/**
	 * 展示Base64格式的文件
	 */
	@RequestMapping("/getBase64/{name}")
	public void getBase64(@PathVariable("name") String name, HttpServletResponse response) {
		try (InputStream in = fileManager.getFile(name);
				OutputStream out = new Base64OutputStream(response.getOutputStream());) {
			FileUtils.copy(in, out);
			out.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 展示普通文件
	 */
	@RequestMapping("/get/{name}")
	public void get(@PathVariable("name") String name, HttpServletResponse response) {
		try (InputStream in = fileManager.getFile(name); OutputStream out = response.getOutputStream();) {
			FileInfo fileInfo = fileInfoService.loadByName(name);
			response.setContentType(fileInfo.getType());
			FileUtils.copy(in, out);
			out.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (FileInfoNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 删除文件
	 * @return 
	 */
	@RequestMapping("/delete/{name}")
	public @ResponseBody Result delete(@PathVariable("name") String name, HttpServletRequest request) throws Exception {
		try {
			fileManager.deleteFile(name);
			return new Result(Result.SUCCESS, "删除成功!");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new Result(Result.FAIL, "删除失败:"+e.toString());
		}
	}

	@RequestMapping("/upload_page")
	public String upload_page() {
		return "/file/test/fileupload.jsp";
	}

	@Autowired(required = true)
	public void setFileManager(@Qualifier("fileManager") FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@Autowired(required = true)
	public void setFileInfoService(@Qualifier("fileInfoService") FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

}
