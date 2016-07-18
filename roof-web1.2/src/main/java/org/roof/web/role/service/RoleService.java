package org.roof.web.role.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TableGenerator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.roof.security.entity.BaseRole;
import org.roof.security.entity.Resource;
import org.roof.web.role.dao.RoleDao;
import org.roof.web.role.entity.Roles;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RoleService {

	private RoleDao roleDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Roles roles, String allSrc, String selSrc) {
		Roles old = (Roles) roleDao.reload(roles);
		old.setName(roles.getName());
		old.setDescription(roles.getDescription());
		changeSrc(old, allSrc, selSrc);
		roleDao.update(old);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changeSrc(BaseRole baseRole, String allsrc, String selsrc) {
		String[] allsrcIds = StringUtils.split(allsrc, ",");
		String[] selsrcIds = StringUtils.split(selsrc, ",");
		List<Resource> resources = baseRole.getResources();
		if (resources != null) {
			Iterator<Resource> iterator = resources.iterator();
			while (iterator.hasNext()) {
				Resource resource = (Resource) iterator.next();
				for (String allsrcId : allsrcIds) {
					if (StringUtils.equals(allsrcId, resource.getId()
							.toString())) {
						iterator.remove();
					}
				}
			}
		} else {
			resources = new ArrayList<Resource>();
		}
		for (String selsrcId : selsrcIds) {
			Resource resource = roleDao.load(Resource.class,
					NumberUtils.createLong(selsrcId));
			resources.add(resource);
		}
		baseRole.setResources(resources);
	}

	@javax.annotation.Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
