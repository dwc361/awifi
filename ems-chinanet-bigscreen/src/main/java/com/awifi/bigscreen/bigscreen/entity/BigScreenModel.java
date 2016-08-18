package com.awifi.bigscreen.bigscreen.entity;

import java.io.Serializable;
import java.util.List;

import com.awifi.bigscreen.templates.entity.Templates;
import com.awifi.bigscreen.theme.entity.Theme;

public class BigScreenModel implements Serializable {
	private List<BigScreenHandlebarsVo> vos;
	private Templates templates;
	private Theme theme;

	public List<BigScreenHandlebarsVo> getVos() {
		return vos;
	}

	public void setVos(List<BigScreenHandlebarsVo> vos) {
		this.vos = vos;
	}

	public Templates getTemplates() {
		return templates;
	}

	public void setTemplates(Templates templates) {
		this.templates = templates;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

}
