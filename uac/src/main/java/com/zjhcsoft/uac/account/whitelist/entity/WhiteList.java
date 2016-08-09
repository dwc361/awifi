package com.zjhcsoft.uac.account.whitelist.entity;

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

import com.zjhcsoft.uac.account.user.entity.User;

@Entity
@Table(name = "u_white_list")
public class WhiteList {
	private Long id;
	private User user;// 主账号
	private Date startDate;// 开始日期
	private Date endDate;// 结束日期

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	@Column(columnDefinition = "DATE")
	public Date getStartDate() {
		return startDate;
	}

	@Column(columnDefinition = "DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
