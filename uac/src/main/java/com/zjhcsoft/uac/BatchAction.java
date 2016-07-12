package com.zjhcsoft.uac;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.upload.FileInfo;
import org.roof.upload.FileService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.AccountImportor;
import com.zjhcsoft.uac.blj.service.BljService;

@Component("batchAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class BatchAction extends RoofActionSupport {



	/**
	 * 
	 */
	private static final long serialVersionUID = 6059803536602578466L;

	private static final Logger LOGGER = Logger.getLogger(BatchAction.class);

	private File file;
	private String fileFileName;
	private FileService fileService;
	
	private AccountImportor accountImportor;
	private XslDb xslDb;
	private XslDb userxslDb;
	
	
	private RoofDaoSupport roofDaoSupport;
	private BljService bljService;
	
	public String page() {
		result = "/uac/batch_page.jsp";
		return JSP;
	}

		
	public String upload() {
		try {
			if (StringUtils.isEmpty(fileFileName)) {
				return NONE;
			}
			HttpServletResponse response = WebUtils.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-type", "text/html;charset=UTF-8");
			OutputStream os = response.getOutputStream();
			if (!fileFileName.toLowerCase().endsWith(".xls")) {
				result = new Result(Result.FAIL, "请选择xls格式");
				os.write(Result.getStr(result).getBytes("UTF-8"));
				os.flush();
				return NONE;
			}
			FileInfo fileInfo = fileService.save(file, fileFileName, null);
			
			result = new Result(Result.SUCCESS, fileInfo.getWebPath() + "文件导入成功!",fileInfo.getWebPath());
			os.write(Result.getStr(result).getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String batch_blj(){
		String path = (String) super.getParamByName("filepaths");
		if(!"".endsWith(path) && path != null ){
			path = super.getWebRoot()+"/"+path;
		}
		accountImportor.sysresource(new File(path), xslDb,null);
		File defile = new File(path);  
		    // 路径为文件且不为空则进行删除  
		    if (defile.isFile() && defile.exists()) {  
		    	defile.delete();  
		    } 
		result = new Result(Result.SUCCESS,"导入完成");
		return JSON;
	} 
	
	public String batch(){
		String path = (String) super.getParamByName("filepaths");
		if(!"".endsWith(path) && path != null ){
			path = super.getWebRoot()+"/"+path;
		}
		accountImportor.exc(new File(path), userxslDb, 0L);
		File defile = new File(path);  
	    // 路径为文件且不为空则进行删除  
	    if (defile.isFile() && defile.exists()) {  
	    	defile.delete();  
	    } 
		result = new Result(Result.SUCCESS,"导入完成");
		return JSON;
	}
	
	
	public String batchbljuser(){
		String  subhql="from SubUser t where t.sysResource.id in( select id from com.zjhcsoft.uac.authorization.resource.entity.System where  state.id = 107 ) order by id";
		String  subsql="select t.staff_id from staff t where t.dtype = 'SubUser'  and  t.staff_id in(select s.staff_id from STAFF_ROLE s where s.role_id = 1050)";
		List<Long> subusers = (List<Long>) roofDaoSupport.selectForList("com.zjhcsoft.uac.account.subuser.dao.SubUserDao.findSub_blj");
		//List<SubUser> subusers = (List<SubUser>) roofDaoSupport.findForList(subhql);
		List<User> us = new ArrayList<User>();
		try {
			java.lang.System.out.println(subusers.size());
			for(Long sub:subusers){
				Long id = Long.valueOf(sub);
				SubUser su = roofDaoSupport.load(SubUser.class, id);
				boolean flag = true;
				/*for(User u:us){
					if(u.getUsername().endsWith(su.getUser().getUsername())){
						flag = false;
						break;
					}
				}*/
				if(flag){
					us.add(su.getUser());
				}
				
			}
			for(User u:us){
				String r = bljService.Useradd(u);
				//java.lang.System.out.println(r);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		result = new Result(Result.SUCCESS,"同步完成");
		return JSON;
	}
	

	
	public File getFile() {
		return file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	@Resource
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	@Resource
	public void setAccountImportor(AccountImportor accountImportor) {
		this.accountImportor = accountImportor;
	}

	@Resource(name = "systemXsDB")
	public void setXslDb(XslDb xslDb) {
		this.xslDb = xslDb;
	}
	
	@Resource(name = "userXsDB")
	public void setUserxslDb(XslDb userxslDb) {
		this.userxslDb = userxslDb;
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}



	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}

}
