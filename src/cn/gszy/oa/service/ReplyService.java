package cn.gszy.oa.service;

import java.util.List;

import cn.gszy.oa.base.BaseDao;
import cn.gszy.oa.domain.PageBean;
import cn.gszy.oa.domain.Reply;
import cn.gszy.oa.domain.Topic;
import cn.gszy.oa.util.HqlHelper;

public interface ReplyService extends BaseDao<Reply> {

	/**
	 * 查询指定主题中所有的回复列表，排序：最前面的是最早发表的回帖
	 * 
	 * @param topic
	 * @return
	 */
	@Deprecated
	List<Reply> findByTopic(Topic topic);

	/**
	 * 获取分页信息
	 * 
	 * @param pageNum
	 * @param topic
	 * @return
	 */
	@Deprecated
	PageBean getPageBean(int pageNum, Topic topic);


}
