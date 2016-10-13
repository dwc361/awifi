package com.awifi.bigscreen.word_fileinfo.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： e_word_fileinfo <br/>
 *         描述：e_word_fileinfo <br/>
 */
public class WordFileinfoVo extends WordFileinfo {

	private List<WordFileinfoVo> wordFileinfoList;

	public WordFileinfoVo() {
		super();
	}

	public WordFileinfoVo(Long id) {
		super();
		this.id = id;
	}

	public List<WordFileinfoVo> getWordFileinfoList() {
		return wordFileinfoList;
	}

	public void setWordFileinfoList(List<WordFileinfoVo> wordFileinfoList) {
		this.wordFileinfoList = wordFileinfoList;
	}

}
