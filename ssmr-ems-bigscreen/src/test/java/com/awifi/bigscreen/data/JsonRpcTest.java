package com.awifi.bigscreen.data;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.data.service.api.IPullData;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class JsonRpcTest extends AbstractJUnit4SpringContextTests {
	private IPullData pullData;

	@Test
	public void testJsonRpc() {
		pullData.Pull();
	}

	@Autowired
	public void setPullData(IPullData pullData) {
		this.pullData = pullData;
	}

}
