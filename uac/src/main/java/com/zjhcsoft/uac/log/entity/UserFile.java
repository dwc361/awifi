package com.zjhcsoft.uac.log.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.GroupSequence;

import org.roof.upload.FileInfo;
import org.roof.web.user.entity.Staff;

/**
 * 用户文件
 * 
 * @author liuxin
 *
 */
@Entity
@Table(name = "u_user_file")
public class UserFile {
	private Long id;
	private FileInfo fileInfo;
	private Staff staff;
	private Integer status;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "FILEINFO_ID")
	public FileInfo getFileInfo() {
		return fileInfo;
	}

	@ManyToOne
	@JoinColumn(name = "STAFF_ID")
	public Staff getStaff() {
		return staff;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
