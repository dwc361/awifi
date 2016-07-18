package org.roof.excel.utils;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.roof.excel.db.DaoTemplate;

/**
 * id生成策略
 * 
 * @author liuxin 2011-3-10
 * 
 */
public class IdGenerator {

	public static Object getId(String idGenerator, String idGeneratorValue) {
		if (StringUtils.equalsIgnoreCase(idGenerator, "uuid")) {
			return getUUID();
		}
		if (StringUtils.equalsIgnoreCase(idGenerator, "sequence")) {
			return getSequence(idGeneratorValue);
		}
		return null;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static int getSequence(String sequenceName) {
		if (StringUtils.isBlank(sequenceName)) {
			throw new IllegalArgumentException(sequenceName
					+ "can not be Blank!");
		}
		String sql = "select " + sequenceName + ".NEXTVAL from DUAL";
		DaoTemplate<?> daoTemplate = new DaoTemplate();
		return daoTemplate.readForInt(sql, new Object[] {});
	}
}
