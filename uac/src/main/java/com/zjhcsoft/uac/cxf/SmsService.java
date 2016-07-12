package com.zjhcsoft.uac.cxf;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.stereotype.Component;

import com.tydic.smpin.intf.BillDataSvrHttpBindingStub;
import com.tydic.smpin.intf.BillDataSvrLocator;
import com.tydic.smpin.intf.util.HexUtil;
import com.tydic.smpin.intf.vo.BillReqVo;
import com.tydic.smpin.intf.vo.BillResVo;
import com.tydic.smpin.intf.vo.UserVo;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.cxf.entity.SmsLog;

@Component
public class SmsService {

	private static final Logger LOGGER = Logger.getLogger(SmsService.class);

	private RoofDaoSupport roofDaoSupport;

	private String latnId = "931";
	
	
	public Page querySmsPage(User paramObj, Page page) {
		SmsLog smsLog = new SmsLog();
		smsLog.setStaff_code(paramObj.getUsername());
		smsLog.setTel(paramObj.getTel());
		smsLog.setStaff_id(paramObj.getId());
		PageQuery pageQuery = new PageQuery(page, "SmsLog_exp_select_sms_code", "SmsLog_exp_select_sms_code_by_staff_count");
		return pageQuery.findByMappedQuery(smsLog);
	}
	

	/**
	 * 查询短信是否开启
	 * 
	 * @return
	 */
	public boolean isOpen() {
		Dictionary dictionary = new Dictionary();
		dictionary.setType("SMS_SWITCH");
		List<Dictionary> list = (List<Dictionary>) roofDaoSupport.findByExample(dictionary);
		if (list == null || list.size() == 0) {
			return false;
		}
		if ("SMS_SWITCH_Y".equals(list.get(0).getVal())) {
			return true;
		}
		return false;
	}

	private String findRandomCode(Long staff_id, String tel, String staff_code) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("staff_id", staff_id);
		parameterObject.put("tel", tel);
		parameterObject.put("staff_code", staff_code);
		@SuppressWarnings("unchecked")
		List<SmsLog> list = (List<SmsLog>) roofDaoSupport.selectForList("SmsLog_exp_select_sms_code_by_staff", parameterObject);
		SmsLog obj = null;
		if(list.size() >0){
			obj= list.get(0);
		}
		if (obj == null) {
			return "";
		}
		return ObjectUtils.toString(obj.getMessage());
	}

	/**
	 * 通过用户ID去查找手机验证码
	 * 
	 * @param staff_id
	 * @return
	 */
	public String findRandomCodeByStaffId(Long staff_id) {
		return this.findRandomCode(staff_id, null, null);
	}

	/**
	 * 通过号码去查找手机验证码
	 * 
	 * @param tel
	 * @return
	 */
	public String findRandomCodeByTel(String tel) {
		return this.findRandomCode(null, tel, null);
	}

	/**
	 * 通过主账号去查找手机验证码
	 * 
	 * @param staff_code
	 * @return
	 */
	public String findRandomCodeByStaffCode(String staff_code) {
		return this.findRandomCode(null, null, staff_code);
	}
	
	private boolean findoneminute(Long staff_id, String tel, String staff_code) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("staff_id", staff_id);
		parameterObject.put("tel", tel);
		parameterObject.put("staff_code", staff_code);
		Long count = (Long) roofDaoSupport.selectForObject("SmsLog_exp_select_sms_code_by_staff_1_count", parameterObject);
		LOGGER.error(count);
		if(count != null && count > 0 ){
			return true;
		}
		return false;
	}
	
	/**
	 * 通过用户手机去查找1分钟内是否发送过
	 * 
	 * @param staff_id
	 * @return
	 */
	public boolean isoneminute(String staff_code) {
		return this.findoneminute(null, null, staff_code);
	}
	
	public BillReqVo bill(String id,String code,String tel) {
		BillReqVo bill = new BillReqVo();
		bill.setChannelType(1);// 1:短信,2:彩信,3:PUSH,5:邮件
		bill.setFlowCode(id);
		bill.setLatnId(latnId);
		try {
			bill.setParams(HexUtil.convertStringToHexString("{\"code\":\"" + code + "\"}", "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		bill.setSentType("SUB");
		bill.setSysCode(PropertiesUtil.getPorpertyString("sms.sysCode"));
		bill.setBusinessId(Long.valueOf(PropertiesUtil.getPorpertyString("sms.businessId")));
		bill.setToTel(tel);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date new_date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new_date);
		calendar.add(Calendar.HOUR, 8);
		System.out.println(dateFormat.format(calendar.getTime()));
		bill.setBeginDate(new_date);
		calendar.add(Calendar.DATE, 1);
		bill.setEndDate(calendar.getTime());
		return bill;
	}
	
	public UserVo BuildUserVo() {
		UserVo userVo = new UserVo();
		userVo.setUserName(PropertiesUtil.getPorpertyString("sms.user"));
		userVo.setPassWord(PropertiesUtil.getPorpertyString("sms.password"));
		userVo.setSysCode(PropertiesUtil.getPorpertyString("sms.sysCode"));
		userVo.setProductId("1");
		return userVo;
	}
	
	public boolean send(String id,String staff_code,String tel,String code) {
		boolean flag = false;
		
		String webserviceUrl = PropertiesUtil.getPorpertyString("sms.url");
		try {
			BillDataSvrLocator locator = new BillDataSvrLocator();
			locator.setBillDataSvrHttpPortEndpointAddress(webserviceUrl);
			URL url = new URL(webserviceUrl);
			BillDataSvrHttpBindingStub stub = new BillDataSvrHttpBindingStub(url, locator);

			BillReqVo[] arrayOfBillReqVo = new BillReqVo[1];
			
			arrayOfBillReqVo[0] = bill(id,code,tel);

			BillResVo vo = stub.billInfo(BuildUserVo(), arrayOfBillReqVo);
			String[] errCodes = vo.getErrorFlowCode();
			if (errCodes != null) {
				for (int i = 0; i < errCodes.length; i++) {
					LOGGER.info(errCodes[i] + ",");
					return flag;
				}
			}
			if ("000".equals(vo.getState())) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 发送验证码
	 * 
	 * @param staff_code
	 * @param tel
	 * @return
	 */
	public boolean send(String staff_code, String tel) {
		return this.send(null, staff_code, tel);
	}

	/**
	 * 发送验证码
	 * 
	 * @param staff_id
	 * @param staff_code
	 * @param tel
	 * @return
	 */
	private boolean send(Long staff_id, String staff_code, String tel) {
		boolean flag = false;
		String code = this.getRandomCode();
		String webserviceUrl = PropertiesUtil.getPorpertyString("sms.url");
		try {
			BillDataSvrLocator locator = new BillDataSvrLocator();
			locator.setBillDataSvrHttpPortEndpointAddress(webserviceUrl);
			URL url = new URL(webserviceUrl);
			BillDataSvrHttpBindingStub stub = new BillDataSvrHttpBindingStub(url, locator);

			UserVo userVo = new UserVo();
			userVo.setUserName(PropertiesUtil.getPorpertyString("sms.user"));
			userVo.setPassWord(PropertiesUtil.getPorpertyString("sms.password"));
			userVo.setSysCode(PropertiesUtil.getPorpertyString("sms.sysCode"));
			userVo.setProductId("1");

			SmsLog smsLog = new SmsLog();
			smsLog.setStaff_id(staff_id);
			smsLog.setStaff_code(staff_code);
			smsLog.setTel(tel);
			smsLog.setLog_time(new Date());
			smsLog.setMessage(code);
			smsLog.setSts(false);
			smsLog.setUsed(false);
			roofDaoSupport.save(smsLog);

			BillReqVo[] arrayOfBillReqVo = new BillReqVo[1];
			BillReqVo bill = new BillReqVo();
			bill.setChannelType(1);// 1:短信,2:彩信,3:PUSH,5:邮件
			bill.setFlowCode(smsLog.getId().toString());
			bill.setLatnId(latnId);
			bill.setParams(HexUtil.convertStringToHexString("{\"code\":\"" + code + "\"}", "GBK"));
			bill.setSentType("SUB");
			bill.setSysCode(PropertiesUtil.getPorpertyString("sms.sysCode"));
			bill.setBusinessId(Long.valueOf(PropertiesUtil.getPorpertyString("sms.businessId")));
			bill.setToTel(tel);
			/**
			 * 短信发送时间增加
			 */
//			SimpleDateFormat dateFormat = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");
//			Date new_date = new Date();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(new_date);
//			calendar.add(Calendar.HOUR, 8);
//			System.out.println(dateFormat.format(calendar.getTime()));
//			bill.setBeginDate(calendar.getTime());
//			calendar.add(Calendar.DATE, 1);
//			bill.setEndDate(calendar.getTime());
			/**
			 * end短信发送时间增加
			 */
			
			arrayOfBillReqVo[0] = bill;

			BillResVo vo = stub.billInfo(userVo, arrayOfBillReqVo);
			String[] errCodes = vo.getErrorFlowCode();
			if (errCodes != null) {
				for (int i = 0; i < errCodes.length; i++) {
					LOGGER.info(errCodes[i] + ",");
					return flag;
				}
			}
			if ("000".equals(vo.getState())) {
				smsLog.setSts(true);
				roofDaoSupport.update(smsLog);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 获得4位随机数字
	 * 
	 * @return
	 */
	private String getRandomCode() {
		Random r = new Random();
		String code = "";
		for (int i = 0; i < 4; i++) {
			code += r.nextInt(10);
		}
		return code;
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

}
