package cn.gszy.oa.service;

import cn.gszy.oa.base.BaseDao;
import cn.gszy.oa.domain.User;

public interface UserService extends BaseDao<User> {

	/**
	 * 查询用户
	 * 
	 * @param loginName
	 * @param password
	 *            明文密码
	 * @return
	 */
	User getByLoginNameAndPassword(String loginName, String password);

}
