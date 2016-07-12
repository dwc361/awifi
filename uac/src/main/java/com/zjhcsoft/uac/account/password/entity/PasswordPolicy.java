package com.zjhcsoft.uac.account.password.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * 密码规则<br>
 * (1)required:true 必输字段<br>
 * (2)remote:"check.php" 使用ajax方法调用check.php验证输入值<br>
 * (3)email:true 必须输入正确格式的电子邮件<br>
 * (4)url:true 必须输入正确格式的网址<br>
 * (5)date:true 必须输入正确格式的日期 日期校验ie6出错，慎用<br>
 * (6)dateISO:true 必须输入正确格式的日期(ISO)，例如：2009-06-23，1998/01/22 只验证格式，不验证有效性<br>
 * (7)number:true 必须输入合法的数字(负数，小数)<br>
 * (8)digits:true 必须输入整数<br>
 * (9)creditcard: 必须输入合法的信用卡号<br>
 * (10)equalTo:"#field" 输入值必须和#field相同</br> (11)accept: 输入拥有合法后缀名的字符串（上传文件的后缀）<br>
 * (12)maxlength:5 输入长度最多是5的字符串(汉字算一个字符)<br>
 * (13)minlength:10 输入长度最小是10的字符串(汉字算一个字符)<br>
 * (14)rangelength:[5,10] 输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)<br>
 * (15)range:[5,10] 输入值必须介于 5 和 10 之间<br>
 * (16)max:5 输入值不能大于5<br>
 * (17)min:10 输入值不能小于10<br>
 * 
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "U_PASSWORD_POLICY", uniqueConstraints = { @UniqueConstraint(columnNames = "NAME") })
@SequenceGenerator(name = "seq_u_password_policy", sequenceName = "SEQ_U_PASSWORD_POLICY")
public class PasswordPolicy {

	private Long id;
	private String name;// 英文表示
	private String val;// 判断值
	private String message;// 提示

	private String unit;// 单位
	private String nameCn;// 中文名
	private String expression; // 表达式

	@Id
	@GeneratedValue(generator = "seq_u_password_policy", strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	@Column(unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public String getVal() {
		return val;
	}

	public String getMessage() {
		return message;
	}

	public String getUnit() {
		return unit;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
