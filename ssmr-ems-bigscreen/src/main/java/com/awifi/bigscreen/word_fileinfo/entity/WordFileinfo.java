package com.awifi.bigscreen.word_fileinfo.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 模版生成 <br/>
 *         表名： e_word_fileinfo <br/>
 *         描述：e_word_fileinfo <br/>
 */
public class WordFileinfo implements Serializable {
	// 需要手动添加非默认的serialVersionUID
	protected Long id;// id
	protected String name;// name
	protected String type;// type
	protected String wordid;// wordId

	public WordFileinfo() {
		super();
	}

	public WordFileinfo(Long id) {
		super();
		this.id = id;
	}
	
	@Id// 主键
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getWordid() {
		return wordid;
	}
	public void setWordid(String wordid) {
		this.wordid = wordid;
	}
}
