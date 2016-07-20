package org.roof.commons;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 
 * @author liuxin 2011-5-9
 * 
 */
@Component
public class EmailSender {

	private static final Logger logger = Logger.getLogger(EmailSender.class);

	private JavaMailSender javaMailSender;
	private VelocityEngine velocityEngineFactoryBean;

	public void send(String from, String to, String tempAddress,
			Map<String, Object> model) throws MessagingException, IOException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		if (StringUtils.isBlank(from)) {
			from = PropertiesUtil.getPorjectProperty("mail.from",
					String.class);
		}
		helper.setFrom(from);
		helper.setTo(to);

		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngineFactoryBean, tempAddress, model);
		helper.setText(text);

		if (logger.isDebugEnabled()) {
			logger.debug("Sending email [" + tempAddress + "] to [" + to + "]");
		}

		javaMailSender.send(message);

	}

	@Resource(name = "mailSender")
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Resource(name = "velocityEngine")
	public void setVelocityEngineFactoryBean(
			VelocityEngine velocityEngineFactoryBean) {
		this.velocityEngineFactoryBean = velocityEngineFactoryBean;
	}

}
