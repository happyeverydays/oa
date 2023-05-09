package com.huizhixin.smart.workflow.camunda.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.TaskQueryImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cmmn.entity.repository.CaseDefinitionEntity;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.delegate.ExpressionGetInvocation;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.javax.el.ELContext;
import org.camunda.bpm.engine.impl.javax.el.ELException;
import org.camunda.bpm.engine.impl.javax.el.MethodNotFoundException;
import org.camunda.bpm.engine.impl.javax.el.PropertyNotFoundException;
import org.camunda.bpm.engine.impl.javax.el.ValueExpression;
import org.camunda.bpm.engine.impl.persistence.deploy.cache.DeploymentCache;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.camunda.bpm.engine.rest.spi.impl.AbstractProcessEngineAware;
import org.camunda.bpm.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.cache.ProcessDefinitionCache;
import com.huizhixin.smart.workflow.camunda.dto.BpmnModelElementInstanceDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaPropertyDto;
import com.huizhixin.smart.workflow.camunda.dto.ProcessDefinitionDto;
import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.entity.TaskDescription;
import com.huizhixin.smart.workflow.camunda.entity.TaskExt;
import com.huizhixin.smart.workflow.camunda.mapper.TaskMapper;
import com.huizhixin.smart.workflow.camunda.service.TaskExtService;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl.Backtrack;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl.WORK_FLOW;
import com.huizhixin.smart.workflow.camunda.util.JuelUtil;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Transactional
public class TaskExtServiceImpl extends AbstractProcessEngineAware implements TaskExtService {

	TaskMapper taskMapper;
	private WorkFlowCommonService workFlowCommonService = SpringContextUtils.getBean(WorkFlowCommonService.class);
	private TaskService taskService = SpringContextUtils.getBean(TaskService.class);

	public TaskExtServiceImpl(String engineName, TaskMapper taskMapper) {
		super(engineName);
		this.taskMapper = taskMapper;
	}

	@Override
	public List<TaskExt> getTasks(IPage<TaskExt> page, TaskQueryDto queryDto) {
		List<TaskExt> list = queryTasks(page, queryDto);
		Map<String, Map<String, TaskDefinition>> processDefinition_taskDefinitions_cahe = new HashMap<String, Map<String, TaskDefinition>>();
		ProcessEngineConfigurationImpl config = (ProcessEngineConfigurationImpl) this.processEngine.getProcessEngineConfiguration();
		// Context.setCommandContext(new CommandContext(config));
		// DeploymentCache deploymentCache = config.getDeploymentCache();
		// ProcessDefinitionEntity processDefinition222 =
		// deploymentCache.findDeployedProcessDefinitionById(someDefinitionId);
		for (TaskExt task : list) {
			System.out.println(task.getFormKey());
			String description = task.getDescription();
			if (StringUtils.isNotEmpty(description)) {
				TaskDescription taskDescription = JSON.parseObject(description, TaskDescription.class);
				task.setDescription(taskDescription.getTitle());
				task.setFormKey(taskDescription.getFormKey());
				task.setFormTableName(taskDescription.getFormTableName());
				task.setBusinessKey(taskDescription.getBusinessKey());
			}
		}
		return list;
	}

	public List<TaskExt> queryTasks(IPage<TaskExt> page, TaskQueryDto queryDto) {
		TaskQueryImpl taskQuery = (TaskQueryImpl) queryDto.toQuery(processEngine);

		return taskMapper.selectTaskByQueryCriteria(page, taskQuery);
	}

	@Override
	public void complete(String taskId, Map<String, Object> variables) throws Exception {
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).singleResult();
		complete(task, variables);
	}

	@Override
	public void complete(Task task, Map<String, Object> variables) throws Exception {
		if (task == null) {
			throw new Exception();
		}
		workFlowCommonService.completeTask(task, variables);
	}

	public void setFormKey(TaskExt task, Map<String, Map<String, TaskDefinition>> processDefinition_taskDefinitions_cahe) {
		String taskDefinitionKey = task.getTaskDefinitionKey();
		String processDefinitionId = task.getProcessDefinitionId();
		if (taskDefinitionKey != null && processDefinitionId != null) {
			TaskDefinition taskDefinition = null;
			if (processDefinition_taskDefinitions_cahe.get(processDefinitionId) != null) {
				taskDefinition = processDefinition_taskDefinitions_cahe.get(processDefinitionId).get(taskDefinitionKey);
			}

			if (taskDefinition == null) {
				Map<String, TaskDefinition> taskDefinitions = workFlowCommonService.getTaskDefinitions(task.getProcessDefinitionId(), task.getCaseDefinitionId());
				processDefinition_taskDefinitions_cahe.put(processDefinitionId, taskDefinitions);
				taskDefinition = taskDefinitions.get(taskDefinitionKey);
			}
			Expression formKey = taskDefinition.getFormKey();
			if (formKey != null) {
				task.setFormKey(formKey.getExpressionText());
			}

		}
	}

	@Override
	public Map<String, Object> initData(Task task, LoginUser sysUser) {
		// WORK_FLOW.BACK_TRACK; // 退回
		Map<String, Object> retMap = new HashMap<String, Object>();
		String processKey = task.getProcessDefinitionId().split(":")[0];
		String taskDefinitionKey = task.getTaskDefinitionKey();
		ProcessDefinitionDto ProcessDefinitionDto = ProcessDefinitionCache.getLastVersionCache(processKey);
		List<TaskDefinitionDto> list = ProcessDefinitionDto.getTaskDefinitions();
		TaskDefinitionDto taskDefinitionDto = null;
		for (TaskDefinitionDto tmpTaskDefinitionDto : list) {
			String taskKey = tmpTaskDefinitionDto.getKey();
			if (StringUtils.equals(taskKey, taskDefinitionKey)) {
				taskDefinitionDto = tmpTaskDefinitionDto;
				break;
			}
		}

		List<BpmnModelElementInstanceDto> bpmnModelElementList = taskDefinitionDto.getExtensionElements();
		Map<String, Object> new_variables = taskService.getVariables(task.getId());
		new_variables.put(WORK_FLOW.loginUser, sysUser);
		for (BpmnModelElementInstanceDto bpmnModelElementInstanceDto : bpmnModelElementList) {
			if (bpmnModelElementInstanceDto instanceof CamundaPropertyDto) {
				CamundaPropertyDto camundaPropertyDto = (CamundaPropertyDto) bpmnModelElementInstanceDto;
				String camundaName = camundaPropertyDto.getCamundaName();
				String camundaId = camundaPropertyDto.getCamundaId();
				String camundaValue = camundaPropertyDto.getCamundaValue();
				String retCamundaValue = JuelUtil.getStringByELAndFormData(camundaValue, new_variables);
				if (StringUtils.equals(camundaName, WORK_FLOW.BACK_TRACK)) {
					List<Backtrack> backtrackList = JSON.parseArray(retCamundaValue, Backtrack.class);
					List<JSONObject> backtrackArray = JSON.parseArray(retCamundaValue, JSONObject.class);
					List<JSONObject> filterAfterBacktrackList = new ArrayList<JSONObject>();
					for (JSONObject backtrack : backtrackArray) {
						if (backtrack.getBooleanValue("enable")) {
							filterAfterBacktrackList.add(backtrack);
						}
					}
					retMap.put(WORK_FLOW.BACK_TRACK, filterAfterBacktrackList);
				}
			}
		}
		return retMap;
	}
//
//	public Object getExpressValue(VariableScope variableScope, String expressionText) {
//		ExpressionManager expressionManager = new ExpressionManager();
//		ELContext elContext = expressionManager.getElContext(variableScope);
//		try {
//			ValueExpression valueExpression = expressionManager.createValueExpression(expressionText);
//			ExpressionGetInvocation invocation = new ExpressionGetInvocation(valueExpression, elContext, null);
//			ProcessEngineConfigurationImpl config = (ProcessEngineConfigurationImpl) this.processEngine
//					.getProcessEngineConfiguration();
//			config.getDelegateInterceptor().handleInvocation(invocation);
//			return invocation.getInvocationResult();
//		} catch (PropertyNotFoundException pnfe) {
//			throw new ProcessEngineException(
//					"Unknown property used in expression: " + expressionText + ". Cause: " + pnfe.getMessage(), pnfe);
//		} catch (MethodNotFoundException mnfe) {
//			throw new ProcessEngineException(
//					"Unknown method used in expression: " + expressionText + ". Cause: " + mnfe.getMessage(), mnfe);
//		} catch (ELException ele) {
//			throw new ProcessEngineException(
//					"Error while evaluating expression: " + expressionText + ". Cause: " + ele.getMessage(), ele);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ProcessEngineException(
//					"Error while evaluating expression: " + expressionText + ". Cause: " + e.getMessage(), e);
//		}
//	}
}
