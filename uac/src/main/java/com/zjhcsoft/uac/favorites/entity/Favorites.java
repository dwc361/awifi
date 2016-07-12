package com.zjhcsoft.uac.favorites.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.roof.web.user.entity.Staff;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.authorization.resource.entity.App;

/**
 * 收藏夹
 * 
 */
@Entity
@Table(name = "U_FAVORITES")
@SequenceGenerator(name = "SEQ_FAVORITES", sequenceName = "SEQ_FAVORITES")
public class Favorites {
	private Long id;
	private App app;// 系统名称@app_id
	private SubUser subUser;// 子账号@sub_user_id
	private Staff staff; // 收藏人@staff_id

	@Id
	@GeneratedValue(generator = "SEQ_FAVORITES", strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "app_id")
	public App getApp() {
		return app;
	}

	@ManyToOne
	@JoinColumn(name = "sub_user_id")
	public SubUser getSubUser() {
		return subUser;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public void setSubUser(SubUser subUser) {
		this.subUser = subUser;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id")
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}
