package org.roof.web.resources.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.exceptions.DaoException;
import org.roof.web.resources.entity.QueryResource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liuxin
 * 
 */
@Component
public class QueryResourceDao extends RoofDaoSupport {

	private static final Logger LOGGER = Logger
			.getLogger(QueryResourceDao.class);

	public QueryResource findByIdentify(String identify) {
		String hql = "from QueryResource a left join fetch a.parent where a.identify = ?";
		QueryResource queryResource = null;
		try {
			queryResource = (QueryResource) findSingle(hql, identify);
		} catch (DaoException e) {
			LOGGER.error(e);
		}
		return queryResource;
	}

	@SuppressWarnings("unchecked")
	public List<QueryResource> findAll() {
		String hql = "from QueryResource q left join fetch q.baseRole where q.class != 'query_filter'";
		return (List<QueryResource>) findForList(hql);
	}

}
