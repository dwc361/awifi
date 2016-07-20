package org.roof.upload;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 文件描述与上传到服务器的文件对应
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "sys_fileInfo")
public class FileInfo implements Serializable {
	private static final long serialVersionUID = 4803351739523633666L;
	private Long id;
	private String displayName;// 显示的文件名
	private String name; // 存放在服务器端的文件名
	private String realPath; // 服务器物理地址
	private String webPath; // web相对路径
	private String webPathThumb;// 缩略图路径
	private Long fileSize; // 文件大小
	private String type; // 文件类型(后缀)
	private String description;// 文件描述
	private String tableName;// 对应表名
	private String primaryId;// 对应表的主键值

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public String getRealPath() {
		return realPath;
	}

	public String getWebPathThumb() {
		return webPathThumb;
	}

	public String getWebPath() {
		return webPath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setWebPathThumb(String webPathThumb) {
		this.webPathThumb = webPathThumb;
	}

}
