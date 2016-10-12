package com.awifi.bigscreen.bigscreen.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： e_bigscreen <br/>
 *         描述：e_bigscreen <br/>
 */
public class BigscreenVo extends Bigscreen {

	private List<BigscreenVo> bigscreenList;

	public BigscreenVo() {
		super();
	}

	public BigscreenVo(Long id) {
		super();
		this.id = id;
	}

	public List<BigscreenVo> getBigscreenList() {
		return bigscreenList;
	}

	public void setBigscreenList(List<BigscreenVo> bigscreenList) {
		this.bigscreenList = bigscreenList;
	}

}
