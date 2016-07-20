package org.roof.ckedit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.roof.commons.FileUtils;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.upload.FileInfo;
import org.roof.upload.FileService;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 CkEditAction.java 2012-9-2
 */
@Component
public class CkEditAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;
	private File upload;
	private String uploadFileName;

	public String uploadImage() throws IOException {
		String CKEditorFuncNum = this.getParamByName("CKEditorFuncNum",
				String.class);
		if (upload != null) {
			FileInfo fileInfo = FileService.initFileInfo(
					"upload_file" + File.separator + "bbs" + File.separator
							+ "article_image", uploadFileName, true, true);
			String root = WebUtils.getServletContext().getRealPath("/");
			File dst = new File(root + fileInfo.getWebPath());
			FileUtils.copy(upload, dst);
			String imagePath = "<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum
					+ ", '"
					+ StringUtils.replace(fileInfo.getWebPath(),
							File.separator, "/")
					+ "' , '"
					+ ""
					+ "');</script>";
			WebUtils.getResponse().getWriter().write(imagePath);
		}
		return null;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}
