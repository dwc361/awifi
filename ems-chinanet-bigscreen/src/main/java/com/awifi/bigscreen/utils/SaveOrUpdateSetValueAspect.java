package com.awifi.bigscreen.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * 新增和修改时自动填充entity的值
 * 
 */
public class SaveOrUpdateSetValueAspect {
	private static final String[] ARRAY_UPDATE = new String[] { "update_time" };
	private static final String[] ARRAY_CREATE_UPDATE = new String[] { "create_time" };
	private static Logger LOGGER = Logger.getLogger(SaveOrUpdateSetValueAspect.class);

	public void beforeSave(JoinPoint point) {
		Object[] args = point.getArgs();
		if (ArrayUtils.isEmpty(args)) {
			return;
		}
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}
			setDateVal(arg, ARRAY_CREATE_UPDATE);
		}
	}

	public void beforeUpdate(JoinPoint point) {
		Object[] args = point.getArgs();
		if (ArrayUtils.isEmpty(args)) {
			return;
		}
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}
			setDateVal(arg, ARRAY_UPDATE);
		}
	}

	private void setDateVal(Object arg, String[] properties) {
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(arg);
		for (PropertyDescriptor descriptor : descriptors) {
			if (ArrayUtils.contains(properties, descriptor.getDisplayName())) {
				try {
					descriptor.getWriteMethod().invoke(arg, new Date());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

}
