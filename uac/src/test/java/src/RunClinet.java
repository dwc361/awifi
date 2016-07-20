package src;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.poi.hssf.record.chart.CatLabRecord;

import com.tydic.smpin.intf.BillDataSvrHttpBindingStub;
import com.tydic.smpin.intf.BillDataSvrLocator;
import com.tydic.smpin.intf.util.HexUtil;
import com.tydic.smpin.intf.vo.BillReqVo;
import com.tydic.smpin.intf.vo.BillResVo;
import com.tydic.smpin.intf.vo.UserVo;
import com.zjhcsoft.uac.cxf.CxfService;

public class RunClinet {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// queryResourceData();
		send();
		// send2();
		// queryBaseData();
		Random r = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			code += r.nextInt(10);
		}
		System.out.println(code);

	}

	static void queryBaseData() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();// 初始或工厂类
		factory.getOutInterceptors().add(new LoggingInInterceptor());// 判断是否抛出异常
		factory.setServiceClass(CxfService.class);// 设置需调用的接口对象
		factory.setAddress("http://135.149.16.26:8180/uac/cxf/4AService?wsdl");// 设置发布接口在JVM中的访问地址
		CxfService client = (CxfService) factory.create();// 获得接口对象
		String reply = client.queryBaseData();// 调用接口方法
		System.out.println("Server said:\n" + reply);
		System.exit(0);// 关闭接口连接
	}

	static void queryResourceData() {
		String clientXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<root FuncName=\"queryResourceData\">"
				+ "	<request>50</request>" + "	<region>103</region>"
				+ "</root>";
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();// 初始或工厂类
		factory.getOutInterceptors().add(new LoggingInInterceptor());// 判断是否抛出异常
		factory.setServiceClass(CxfService.class);// 设置需调用的接口对象
		factory.setAddress("http://10.52.4.33:9099/uac/cxf/4AService?wsdl");// 设置发布接口在JVM中的访问地址
		CxfService client = (CxfService) factory.create();// 获得接口对象
		String reply = client.queryResourceData(clientXML);// 调用接口方法
		System.out.println("Server said:\n" + reply);
		System.exit(0);// 关闭接口连接
	}

	static void send2() {
		String clientXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<BillInfo>" + "	<UserVo>" + "		<userName>user4a</userName>"
				+ "		<passWord>e10adc3949ba59abbe56e057f20f883e</passWord>"
				+ "		<sysCode>022</sysCode>" + "		<productId>1</productId>"
				+ "	</UserVo>" + "	<ArrayOfBillReqVo>" + "		<BillReqVo>"
				+ "			<channelType>1</channelType>"
				+ "			<params>7b22636f6465223a22383930363933227d</params>"
				+ "			<latnId>931</latnId>" + "			<toTel>18919816671</toTel>"
				+ "			<flowCode>111</flowCode>"
				+ "			<businessId>2200001</businessId>"
				+ "			<sentType>SUB</sentType>" + "			<sysCode>022</sysCode>"
				+ "		</BillReqVo>" + "	</ArrayOfBillReqVo>" + "</BillInfo>";
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();// 初始或工厂类
		factory.getOutInterceptors().add(new LoggingInInterceptor());// 判断是否抛出异常
		factory.setAddress("http://135.129.9.227:7003/smp_in/servlet/BillDataServlet");// 设置发布接口在JVM中的访问地址
		// // factory.setServiceClass(SmsService.class);// 设置需调用的接口对象
		// // SmsService client = (SmsService) factory.create();// 获得接口对象
		// String reply = client.BillInfo(clientXML);// 调用接口方法
		// System.out.println("Server said:\n" + reply);
		System.exit(0);// 关闭接口连接
	}

	static void send() {
//		String webserviceUrl = "http://135.129.9.227:7003/smp_in/servlet/BillDataServlet";
		String webserviceUrl = "http://135.129.9.164/smp_in/servlet/BillDataServlet";
		try {
			BillDataSvrLocator locator = new BillDataSvrLocator();
			locator.setBillDataSvrHttpPortEndpointAddress(webserviceUrl);
			URL url = new URL(webserviceUrl);
			BillDataSvrHttpBindingStub stub = new BillDataSvrHttpBindingStub(
					url, locator);

			UserVo userVo = new UserVo();
			userVo.setUserName("user4a");
			userVo.setPassWord("e10adc3949ba59abbe56e057f20f883e");
			userVo.setSysCode("022");
			userVo.setProductId("1");

			int size = 1;
			BillReqVo[] arrayOfBillReqVo = new BillReqVo[size];
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < size; i++) {
				BillReqVo bill = new BillReqVo();
				// 1:短信,2:彩信,3:PUSH,5:邮件
				bill.setChannelType(1);
				/**
				 * 自增序列
				 */
				bill.setFlowCode("5");
				bill.setLatnId("931");
				// bill.setParams("7b22636f6465223a22383930363933227d");
				bill.setParams(HexUtil.convertStringToHexString(
						"{\"code\":\"298708\"}", "GBK"));
				bill.setSentType("SUB");
				bill.setSysCode("022");
				bill.setBusinessId(Long.valueOf("2200001"));
				bill.setToTel("18919816671");
				/**
				 * 增加开始结束时间
				 */
				Date new_date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new_date);
				calendar.add(Calendar.HOUR, 8);
				System.out.println(dateFormat.format(calendar.getTime()));
				bill.setBeginDate(new_date);
				calendar.add(Calendar.DATE, 1);
				bill.setEndDate(calendar.getTime());
				/**
				 * end
				 */
				arrayOfBillReqVo[i] = bill;
			}

			// System.out.println(dateFormat.format(new Date()));

			BillResVo vo = stub.billInfo(userVo, arrayOfBillReqVo);
			System.out.println(vo.getState() + "  " + vo.getStateDesc());
			String[] errCodes = vo.getErrorFlowCode();
			if (errCodes != null) {
				for (int i = 0; i < errCodes.length; i++) {
					System.out.println(errCodes[i] + ",");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
