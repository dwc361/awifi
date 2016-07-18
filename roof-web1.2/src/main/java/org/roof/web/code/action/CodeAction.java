package org.roof.web.code.action;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang.StringUtils;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.code.service.VerificationCode;
import org.roof.web.code.service.VerificationImage;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CodeAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	// 图片流
	private ByteArrayInputStream imageStream;

	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}

	public synchronized String image() throws Exception {
		// 如果开启Hard模式，可以不区分大小写
		// String securityCode =
		// SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard,
		// false).toLowerCase();
		String dcode = super.getParamByName("dcode", String.class);

		// 获取默认难度和长度的验证码
		String securityCode = VerificationCode.getSecurityCode();
		if (StringUtils.isNotEmpty(dcode)) {
			securityCode = dcode;
		}
		imageStream = VerificationImage.getImageAsInputStream(securityCode);
		// 放入session中
		WebUtils.getSessionMap().put("SESSION_SECURITY_CODE", securityCode);
		this.addParameter("contentType", "image/jpeg");
		this.addParameter("inputName", "imageStream");
		this.addParameter("bufferSize", "2048");
		return STREAM;
	}

}
