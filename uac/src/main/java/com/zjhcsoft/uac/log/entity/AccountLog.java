package com.zjhcsoft.uac.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.user.entity.Staff;

/**
 * 账号管理审计
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "u_account_log")
public class AccountLog implements Serializable {

	private static final long serialVersionUID = 26857113716474920L;
	private Long id;
	private Staff user;// 操作人@USER_ID
	private Dictionary opType;// 账号操作类型ACCOUNT_OP_TYPE@op_type_id
	private Date op_time;// 操作时间
	private Staff opStaff;// 被操作账号@OP_STAFF_ID
	private String op_result;// 操作结果
	private String resourcetype;// 资源类型

	private Date op_time_end;// 操作时间

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public Staff getUser() {
		return user;
	}

	@ManyToOne
	@JoinColumn(name = "OP_TYPE_ID")
	public Dictionary getOpType() {
		return opType;
	}

	@Column(columnDefinition = "DATE")
	public Date getOp_time() {
		return op_time;
	}

	@Transient
	public Date getOp_time_end() {
		return op_time_end;
	}

	public void setOp_time_end(Date op_time_end) {
		this.op_time_end = op_time_end;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(Staff user) {
		this.user = user;
	}

	public void setOpType(Dictionary opType) {
		this.opType = opType;
	}

	public void setOp_time(Date op_time) {
		this.op_time = op_time;
	}

	public void setOp_result(String op_result) {
		this.op_result = op_result;
	}

	@ManyToOne
	@JoinColumn(name = "OP_STAFF_ID")
	public Staff getOpStaff() {
		return opStaff;
	}

	public void setOpStaff(Staff opStaff) {
		this.opStaff = opStaff;
	}

	public String getOp_result() {
		return op_result;
	}

	public String getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}
}
