package org.roof.web.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.exceptions.DaoException;
import org.roof.web.resources.entity.Module;
import org.roof.web.resources.entity.Privilege;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * 资源表导入导出
 * 
 * @author liuxin
 * 
 */
@Component
public class ResourcesUtils {

	private RoofDaoSupport roofDaoSupport;

	private static Logger logger = Logger.getLogger(ResourcesUtils.class);

	public void initBasicOperation(Module module) {
		Resource resource = new ClassPathResource("org/roof/web/resources/init_basic_resources.txt");
		FileReader in = null;
		BufferedReader br = null;
		try {
			in = new FileReader(resource.getFile());
			br = new BufferedReader(in);
			String s = null;
			while ((s = br.readLine()) != null) {
				s = StringUtils.replace(s, "#name#", module.getName());
				s = StringUtils.replace(s, "#path#", module.getPath());
				org.roof.security.entity.Resource res = deseqResource(s);
				if (res != null) {
					roofDaoSupport.save(res);
				}
			}
		} catch (IOException e) {
			logger.error(e);
		} catch (DaoException e) {
			logger.error(e);
		} finally {
			try {
				in.close();
				br.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 导出资源表到文件
	 * 
	 * @param file
	 * @throws DaoException
	 */
	public void exportToFile(File file) throws DaoException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			Module root = findRoot();
			seq(root, fileWriter);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				logger.error(e1);
			}
		}
		return;
	}

	private void seq(Module module, FileWriter fileWriter) throws IOException {
		List<Module> ms = findChildren(module.getId());
		if (CollectionUtils.isNotEmpty(ms)) {
			for (Module m : ms) {
				fileWriter.write(seqModule(m) + "\n");
				seq(m, fileWriter);
			}
		}
	}

	public List<Module> findChildren(Long pid) {
		String hql = "from Module t where t.parent.id = ?";
		@SuppressWarnings("unchecked")
		List<Module> result = (List<Module>) roofDaoSupport.findForList(hql, pid);
		return result;
	}

	private String seqModule(Module o) {
		String s = StringUtils.EMPTY;
		s = s + getPorp(o, "name") + "|" + getPorp(o, "pattern") + "|" + getPorp(o, "description") + "|"
				+ getPorp(o, "identify") + "|" + getPorp(o, "path") + "|" + getPorp(o, "seq") + "|" + getPorp(o, "lvl")
				+ "|" + (StringUtils.isBlank(getPorp(o, "parent")) ? "" : getPorp(o, "parent.path")) + "|"
				+ getPorp(o, "leaf");
		if (o instanceof Privilege) {
			s = "Privilege|" + s + seqPrivilege((Privilege) o);
		} else if (o instanceof Module) {
			s = "Module|" + s;
		}
		return s;
	}

	private String seqPrivilege(Privilege o) {
		String s = "|" + getPorp(o, "remark") + "|" + getPorp(o, "format");
		return s;
	}

	private String getPorp(Object o, String porpName) {
		try {
			return ObjectUtils.toString(PropertyUtils.getProperty(o, porpName), "");
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		}
		return "";
	}

	private void setProp(Object o, String porpName, Object value) {
		try {
			PropertyUtils.setProperty(o, porpName, value);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		}
	}

	private Module findRoot() throws DaoException {
		String hql = "from Module t where t.parent = null";
		return (Module) roofDaoSupport.findSingle(hql);
	}

	/**
	 * 从文件导入资源
	 * 
	 * @param file
	 * @throws DaoException
	 */
	public void importFromFile(File file) throws DaoException {
		FileReader in = null;
		BufferedReader br = null;
		try {
			in = new FileReader(file);
			br = new BufferedReader(in);
			String s = null;
			while ((s = br.readLine()) != null) {
				org.roof.security.entity.Resource res = deseqResource(s);
				if (res != null) {
					roofDaoSupport.save(res);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				in.close();
				br.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return;
	}

	private org.roof.security.entity.Resource deseqResource(String s) throws DaoException {
		String[] vals = StringUtils.splitByWholeSeparatorPreserveAllTokens(s, "|");
		String path = vals[5];
		String type = vals[0];
		if (resourceExists(path)) {
			return null;
		}
		org.roof.security.entity.Resource resource = null;
		if (StringUtils.equals(type, "Module")) {
			resource = new Module();
		}
		if (StringUtils.equals(type, "Privilege")) {
			resource = new Privilege();
		}
		deseqModule(resource, vals);
		return resource;
	}

	private void deseqModule(org.roof.security.entity.Resource o, String[] vals) throws DaoException {
		setProp(o, "name", vals[1]);
		setProp(o, "pattern", vals[2]);
		setProp(o, "description", vals[3]);
		setProp(o, "identify", vals[4]);
		setProp(o, "path", vals[5]);
		setProp(o, "seq", NumberUtils.createInteger(StringUtils.isBlank(vals[6]) ? null : vals[6]));
		setProp(o, "lvl", NumberUtils.createInteger(StringUtils.isBlank(vals[7]) ? null : vals[7]));
		Module parent = findModuleByPath(vals[8]);
		setProp(o, "parent", parent);
		setProp(o, "leaf", BooleanUtils.toBoolean(vals[9]));
		if (o instanceof Privilege) {
			deseqPrivilege(o, vals);
		}
	}

	private void deseqPrivilege(org.roof.security.entity.Resource o, String[] vals) {
		setProp(o, "remark", vals[10]);
		setProp(o, "format", vals[11]);
	}

	public boolean resourceExists(String path) throws DaoException {
		if (findModuleByPath(path) != null) {
			return true;
		}
		return false;
	}

	private Module findModuleByPath(String path) throws DaoException {
		String hql = "from Module where path = ?";
		Module m = (Module) roofDaoSupport.findSingle(hql, path);
		return m;
	}

	@javax.annotation.Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

}
