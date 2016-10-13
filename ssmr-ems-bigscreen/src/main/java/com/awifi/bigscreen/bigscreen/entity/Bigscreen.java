package com.awifi.bigscreen.bigscreen.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 模版生成 <br/>
 *         表名： e_bigscreen <br/>
 *         描述：e_bigscreen <br/>
 */
public class Bigscreen implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1093482633325384825L;
	// 需要手动添加非默认的serialVersionUID
	protected Long id;// 主键
	protected String name;// 名称
	protected Long template_id;// 模板id
	protected Long theme_id;// 主题id
	protected String publish;// 是否发布
	protected Long re_time;// 刷新时间
	protected String re_type;// 大屏架构类型
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date create_time;// 新建时间
	protected String create_by;// 所属用户
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date update_time;// 更新时间
	protected String update_by;// 更新用户
	protected String enabled;// 是否可用

	public Bigscreen() {
		super();
	}

	public Bigscreen(Long id) {
		super();
		this.id = id;
	}

	@Id // 主键
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

	public Long getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}

	public Long getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(Long theme_id) {
		this.theme_id = theme_id;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public Long getRe_time() {
		return re_time;
	}

	public void setRe_time(Long re_time) {
		this.re_time = re_time;
	}

	public String getRe_type() {
		return re_type;
	}

	public void setRe_type(String re_type) {
		this.re_type = re_type;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
}
