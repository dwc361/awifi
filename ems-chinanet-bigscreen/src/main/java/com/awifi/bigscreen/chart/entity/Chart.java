package com.awifi.bigscreen.chart.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 模版生成 <br/>
 *         表名： e_chart <br/>
 *         描述：e_chart <br/>
 */
public class Chart implements Serializable {
	// 需要手动添加非默认的serialVersionUID
	protected Long id;// 主键
	protected String icon;// 图标
	protected String name;// 图表名称
	protected String enabled;// 是否可用
	protected Long re_times;// 刷新时间
	protected String configure;// 配置json
	protected String path;// 代码路径
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date create_time;// 新建时间
	protected String create_by;// 所属用户
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date update_time;// 更新时间
	protected String update_by;// 更新用户

	public Chart() {
		super();
	}

	public Chart(Long id) {
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
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public Long getRe_times() {
		return re_times;
	}
	public void setRe_times(Long re_times) {
		this.re_times = re_times;
	}
	
	public String getConfigure() {
		return configure;
	}
	public void setConfigure(String configure) {
		this.configure = configure;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
}
