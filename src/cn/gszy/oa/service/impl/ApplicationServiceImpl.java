package cn.gszy.oa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import cn.gszy.oa.base.BaseDaoImpl;
import cn.gszy.oa.domain.Application;
import cn.gszy.oa.domain.TaskView;
import cn.gszy.oa.domain.User;
import cn.gszy.oa.service.ApplicationService;

@Service
public class ApplicationServiceImpl extends BaseDaoImpl<Application> implements ApplicationService {

	@Resource
	private ProcessEngine processEngine;

	public void submit(Application application) {
		// 1，设置属性并保存application
		// application.setApplyTime(new Date()); // 申请时间，当前时间
		// application.setTitle(title);
		// application.setStatus(status);
		getSession().save(application); // 保存

		// 2，启动程程实例开始流转
		// >> 准备流程变量
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		variablesMap.put("application", application);
		// >> 启动程程实例
		String pdKey = null;
		processEngine.getExecutionService().startProcessInstanceByKey(pdKey, variablesMap);
		// >> 办理完第1个任务“提交申请”，并带上流程变量（当前的申 请信息）
		String taskId = null;
		processEngine.getTaskService().completeTask(taskId);
	}

	public List<TaskView> getMyTaskViewList(User currentUser) {
		// 查询我的任务列表
		String userId = currentUser.getLoginName(); // 约定使用loginName作为JBPM用的用户标识符
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks(userId);

		// 找出每个任务对应的申请信息
		List<TaskView> resultList = new ArrayList<TaskView>();
		for (Task task : taskList) {
			Application application = (Application) processEngine.getTaskService().getVariable(task.getId(), "application");
			resultList.add(new TaskView(task, application));
		}

		// 返回“任务--申请信息”的结果
		return resultList;
	}

}
