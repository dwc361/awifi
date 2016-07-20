package org.roof.upload.action;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.roof.struts2.RoofActionSupport;
import org.roof.upload.service.UploadService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("imageUploadAction")
@Scope("prototype")
public class ImageUploadAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private File myFile;
	private File[] files; // 批量处理
	private String tableName; // 表名称
	private String myFileFileName;

	private String[] filesFileName;

	private UploadService uploadService;

	public String tempUpload() {
		String root = ServletActionContext.getServletContext().getRealPath("/");
		result = uploadService.uploadTempPoto(myFile, root, myFileFileName);
		String json = JSONObject.fromObject(result).toString();
		HttpServletResponse response = this.getResponse();
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String potoUpload() {
		int x = getParamByName("x1", int.class);
		int y = getParamByName("y1", int.class);
		long id = getParamByName("id", int.class);

		String root = ServletActionContext.getServletContext().getRealPath("/");
		result = uploadService.uploadPhoto(id, root, x, y);
		return JSON;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String[] getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(String[] filesFileName) {
		this.filesFileName = filesFileName;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Resource(name = "uploadService")
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

}
