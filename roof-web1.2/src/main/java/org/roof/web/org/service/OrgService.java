package org.roof.web.org.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.roof.web.org.OrgVo;
import org.roof.web.org.dao.OrgDao;
import org.roof.web.org.entity.Organization;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class OrgService {
	private OrgDao orgDao;

	public Organization load(Long id) {
		return orgDao.load(Organization.class, id);
	}

	/**
	 * 读取自己
	 * 
	 * @param parentId
	 * @return
	 */
	public List<OrgVo> readMyNode(Long parentId) {
		Organization org = this.load(parentId);
		List<OrgVo> result = new ArrayList<OrgVo>();
		OrgVo orgVo = new OrgVo();
		copyProperties(org, orgVo);
		result.add(orgVo);
		return result;
	}

	public List<OrgVo> read(Long parentId) {
		List<Organization> orgs = orgDao.findOrgByParent(parentId);
		return this.read(orgs);
	}

	public List<OrgVo> read(List<Organization> orgs) {
		List<OrgVo> result = new ArrayList<OrgVo>();
		for (Organization org : orgs) {
			OrgVo orgVo = new OrgVo();
			copyProperties(org, orgVo);
			result.add(orgVo);
		}
		return result;
	}

	private void copyProperties(Organization org, OrgVo orgVo) {
		orgVo.setId(org.getOrg_id().toString());
		orgVo.setName(org.getOrg_name());
		orgVo.setLeaf(org.getLeaf());
		orgVo.setLvl(org.getLvl());
	}

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	public Organization create(Long parentId, Organization org) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		Organization parent = orgDao.load(Organization.class, parentId);
		if (parent.getLeaf() != null && parent.getLeaf()) {
			parent.setLeaf(false);
			orgDao.update(parent);
		}
		org.setParent(parent);
		org.setLvl(parent.getLvl() + 1);
		org.setLeaf(true);
		org.setUsable(true);
		orgDao.save(org);
		return org;
	}

	/**
	 * 删除一个组织
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		Organization org = orgDao.load(Organization.class, id);
		Organization parent = (Organization) org.getParent();
		Long count = orgDao.childrenCount(parent.getOrg_id());
		if (count == 1) {
			parent.setLeaf(true);
			orgDao.update(parent);
		}
		org.setOrg_id(id);
		try {
			orgDao.delete(org);
		} catch (DataIntegrityViolationException e) {
			orgDao.disable(org);
		}
	}

	@Resource
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

}
