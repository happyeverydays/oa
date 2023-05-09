package com.huizhixin.smart.workflow.camunda.even;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.ParallelMultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.SequentialMultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;

import com.alibaba.fastjson.JSON;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.entity.ExtActProcessForm;
import com.huizhixin.smart.workflow.camunda.entity.FormInfo;
import com.huizhixin.smart.workflow.camunda.entity.TaskDescription;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.AbstractProcessEngineServiceImpl;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl.WORK_FLOW;
import com.huizhixin.smart.workflow.camunda.util.JuelUtil;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class WorkflowTaskListener implements TaskListener {
	private static WorkflowTaskListener instance = null;

	protected WorkflowTaskListener() {
	}

	public static WorkflowTaskListener getInstance() {
		if (instance == null) {
			instance = new WorkflowTaskListener();
		}
		return instance;
	}

	@Override
	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		TaskEntity taskEntity = (TaskEntity) delegateTask;
		ActivityImpl actimpl = taskEntity.getExecution().getActivity();

		// 流程变量对象
		FormInfo FormInfo = (com.huizhixin.smart.workflow.camunda.entity.FormInfo) delegateTask.getVariable(WORK_FLOW.formInfo);
		// LoginUser LoginUser = (com.huizhixin.smart.common.system.vo.LoginUser)
		// delegateTask.getVariable(WORK_FLOW.startUser);
		// Map<String, Object> data = (Map<String, Object>)
		// delegateTask.getVariable(WORK_FLOW.data);

		if (StringUtils.equals(eventName, EVENTNAME_CREATE)) {
			String processDefinitionId = delegateTask.getProcessDefinitionId();
			String caseDefinitionId = delegateTask.getCaseDefinitionId();
			String businessKey = delegateTask.getExecution().getBusinessKey();
			String processDefinitionKey = processDefinitionId.substring(0, processDefinitionId.indexOf(":"));
			WorkFlowCommonService workFlowCommonService = SpringContextUtils.getBean(WorkFlowCommonService.class);
			FormInfo formInfo = (FormInfo) delegateTask.getVariable(WORK_FLOW.formInfo);
			Map<String, Object> variables = delegateTask.getVariables(); // 上一次的变量
			AbstractProcessEngineServiceImpl processEngineService = SpringContextUtils.getBean(AbstractProcessEngineServiceImpl.class);
			Map<String, Object> data = processEngineService.getProcessDefinitionService(delegateTask.getProcessEngine().getName()).getData(processDefinitionKey, formInfo.getRelationCode(), businessKey, variables);

			// 更新data 中的数据。
			delegateTask.setVariable(WORK_FLOW.data, data);
			Map<String, Object> new_variables = delegateTask.getVariables();// 最新的变量
			String title = JuelUtil.getStringByELAndFormData(formInfo.getTitleExp(), new_variables);

			// 表单优先级 taskFormKey > processFormKey > formTableName
			// taskFormKey 任务表单
			// processFormKey 流程默认的表单。
			// formTableName 动态表单
			String taskformKey = workFlowCommonService.getFormKey(processDefinitionId, caseDefinitionId, taskDefinitionKey);
			String processFormKey = formInfo.getFormKey();
			String formTableName = formInfo.getFormTableName();

			String retFormKey = null;
			if (StringUtils.isNotBlank(taskformKey)) {
				retFormKey = taskformKey;
			} else {
				retFormKey = processFormKey;
			}

			// formKey动态变量替换。
			retFormKey = JuelUtil.getStringByELAndFormData(retFormKey, new_variables);

			// 任务描述字段添加扩展信息。
			TaskDescription taskDescription = new TaskDescription(title, formTableName, retFormKey, businessKey);
			delegateTask.setDescription(JSON.toJSONString(taskDescription));

			// ----------- 会签处理：
			// if(isMultiInstanceBehavior(actimpl)) {
			// delegateTask.setVariable("assigneeList", );//inputName流程变量名

			// }
		}
	}

	private boolean isMultiInstanceBehavior(ActivityImpl actimpl) {
		// 会签的每个task在生成之后清空task对应的候选人候选组信息，否则会导致某角色收到指派给其他人的task，因为task里面设置了角色作为候选组
		boolean isMultiInstanceBehavior = false;
		ActivityBehavior activityBehavior = actimpl.getActivityBehavior();
		if (activityBehavior instanceof SequentialMultiInstanceActivityBehavior) {
			SequentialMultiInstanceActivityBehavior sequentialMultiInstanceBehavior = (SequentialMultiInstanceActivityBehavior) activityBehavior;
			if (sequentialMultiInstanceBehavior != null && StringUtils.isNotBlank(sequentialMultiInstanceBehavior.getCollectionVariable())) {
				isMultiInstanceBehavior = true;
			}
		} else if (activityBehavior instanceof ParallelMultiInstanceActivityBehavior) {
			ParallelMultiInstanceActivityBehavior parallelMultiInstanceBehavior = (ParallelMultiInstanceActivityBehavior) activityBehavior;
			if (parallelMultiInstanceBehavior != null && StringUtils.isNotBlank(parallelMultiInstanceBehavior.getCollectionVariable())) {
				isMultiInstanceBehavior = true;
			}
		}
		return isMultiInstanceBehavior;
	}
}
