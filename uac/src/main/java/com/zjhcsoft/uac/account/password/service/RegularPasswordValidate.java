package com.zjhcsoft.uac.account.password.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;

@Component
public class RegularPasswordValidate extends AbstractPasswordValidator {
	private String name; // char_minlength
	private String operator; // [a-z]
	private String regular; // <

	@Override
	public String validate(String password) {
		PasswordPolicy passwordPolicy = findPasswordPolicy(name);
		if (StringUtils.isEmpty(passwordPolicy.getVal())) {
			return null;
		}
		Pattern p = Pattern.compile(regular);
		Matcher m = p.matcher(password);
		int i = 0;
		while (m.find()) {
			i++;
		}
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(i + " " + operator + " "
				+ passwordPolicy.getVal());
		if (exp.getValue(Boolean.class)) {
			return formatMessage(passwordPolicy.getMessage(),
					passwordPolicy.getVal());
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getOperator() {
		return operator;
	}

	public String getRegular() {
		return regular;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setRegular(String regular) {
		this.regular = regular;
	}

}
