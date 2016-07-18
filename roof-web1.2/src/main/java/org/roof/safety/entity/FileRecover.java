package org.roof.safety.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 防篡改
 * 
 */
//@Entity
@Table(name = "S_FILE_RECOVER")
@SequenceGenerator(name = "SEQ_S_FILE_RECOVER", sequenceName = "SEQ_S_FILE_RECOVER")
public class FileRecover {

	private Long id;

	private String content;// 内容
	
	private String url;// 请求路径
	
	private String encrypt_code;// 文件的加密码
	
	private String sts = "1";// 状态
	
	private Long capacity;// 文件容量
	
	private Date last_modify_date;// 最后修改时间
	
	private Date publish_date;// 发布时间

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_FILE_RECOVER")
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "CLOB")
	public String getContent() {
		return content;
	}

	@Column(columnDefinition = "DATE")
	public Date getLast_modify_date() {
		return last_modify_date;
	}

	@Column(columnDefinition = "DATE")
	public Date getPublish_date() {
		return publish_date;
	}

	public Long getCapacity() {
		return capacity;
	}

	public String getUrl() {
		return url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLast_modify_date(Date last_modify_date) {
		this.last_modify_date = last_modify_date;
	}

	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getEncrypt_code() {
		return encrypt_code;
	}

	public void setEncrypt_code(String encrypt_code) {
		this.encrypt_code = encrypt_code;
	}

}