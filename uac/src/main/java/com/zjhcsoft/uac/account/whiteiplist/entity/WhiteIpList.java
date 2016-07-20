package com.zjhcsoft.uac.account.whiteiplist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "U_WHITEIP_LIST")
public class WhiteIpList {
	private Long id;
	private String nameIP;// ip名称
	private String whiteIP;// 主账号
	private Date startDate;// 开始日期
	private Date endDate;// 结束日期

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
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

	public String getWhiteIP() {
		return whiteIP;
	}

	public void setWhiteIP(String whiteIP) {
		this.whiteIP = whiteIP;
	}

	public String getNameIP() {
		return nameIP;
	}

	public void setNameIP(String nameIp) {
		this.nameIP = nameIp;
	}

}
