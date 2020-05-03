package cn.gszy.oa.view.action;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.gszy.oa.base.BaseAction;
import cn.gszy.oa.domain.Application;
import cn.gszy.oa.domain.ApplicationTemplate;
import cn.gszy.oa.domain.TaskView;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class FlowAction extends BaseAction {
	
	private File upload; // 上传的文件
	private String taskId;
	private Long applicationId;
	private Long applicationTamplateId;

	
	// ================================== 申请人有关

	/** 起草申请（模板列表） */
	public String applicationTemplateList() throws Exception {
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
		return "applicationTemplateList";
	}

	/** 提交申请页面 */
	public String submitUI() throws Exception {
		return "submitUI";
	}

	/** 提交申请 */
	public String submit() throws Exception {
		// 封装申请信息
		Application application = new Application();

		application.setApplicant(getCurrentUser()); // 申请人，当前用户
		application.setPath(saveUploadFile(upload)); // 保存上传的文件并设置路径
		application.setApplicationTemplate(applicationTemplateService.getById(applicationTamplateId));

		// 调用业务方法（保存申请信息，并启动流程开始流转）
		applicationService.submit(application);

		return "toMyApplicationList"; // 成功后转到"我的申请查询"
	}

	/** 我的申请查询 */
	public String myApplicationList() throws Exception {
		return "myApplicationList";
	}

	// ================================== 审批人有关

	/** 待我审批（我的任务列表） */
	public String myTaskList() throws Exception {
		List<TaskView> taskViewList = applicationService.getMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() throws Exception {
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() throws Exception {
		return "toMyTaskList"; // 成功后转到待我审批页面
	}

	/** 查看流转记录 */
	public String approveHistory() throws Exception {
		return "approveHistory";
	}

	// --------

	public Long getApplicationTamplateId() {
		return applicationTamplateId;
	}

	public void setApplicationTamplateId(Long applicationTamplateId) {
		this.applicationTamplateId = applicationTamplateId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
