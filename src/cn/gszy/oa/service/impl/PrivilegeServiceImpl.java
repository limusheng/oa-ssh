package cn.gszy.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.gszy.oa.base.BaseDaoImpl;
import cn.gszy.oa.domain.Privilege;
import cn.gszy.oa.service.PrivilegeService;

@Service
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService {

	public List<Privilege> findTopList() {
		return getSession().createQuery(//
				"FROM Privilege p WHERE p.parent IS NULL")//
				.list();
	}

	public List<String> getAllPrivilegeUrls() {
		return getSession().createQuery(//
				"SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL")//
				.list();
	}

}
