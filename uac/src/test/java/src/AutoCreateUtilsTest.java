package src;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.commons.create.AutoCreateUtils;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AutoCreateUtilsTest extends AbstractJUnit4SpringContextTests {
	AutoCreateUtils autoCreateUtils;
	RoofDaoSupport daoSupport;

	@Test
	public void testUacCreateCode() {
//		autoCreateUtils.setTemplatePrefix("E:/E/work34/infopush/src/test/java/src/template/");
//		String packagePath = "org.roof.infopush.strategy.template";
//		// autoCreateUtils.setActionName("infopush_strategy_variable");
//		packagePath = "org.roof.infopush.strategy.scene";
//		// autoCreateUtils.setActionName("infopush_strategy_arguments");
//		// autoCreateUtils.setActionName("infopush_strategy_scene_type_details");
//		autoCreateUtils.setActionName("infopush_strategy_scene_flow");
//
//		List<String> sourcelist = new ArrayList<String>();
//		// sourcelist.add("I_TEMPLATE");// 添加需要生成的表名
//		// sourcelist.add("I_VARIABLE");// 添加需要生成的表名
//
//		// sourcelist.add("I_SCENE");// 添加需要生成的表名
//		// sourcelist.add("I_ARGUMENTS");// 添加需要生成的表名
//		// sourcelist.add("I_SCENE_TYPE_DETAILS");// 添加需要生成的表名
//		// sourcelist.add("I_MESSAGE_NOTE_LOG");// 添加需要生成的表名
//		sourcelist.add("I_SCENE_FLOW");// 添加需要生成的表名
//
//		autoCreateUtils.createCode(packagePath, sourcelist);
	}

	@Resource
	public void setAutoCreateUtils(AutoCreateUtils autoCreateUtils) {
		this.autoCreateUtils = autoCreateUtils;
	}

	@Resource(name = "roofDaoSupport")
	public void setDaoSupport(RoofDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
}
