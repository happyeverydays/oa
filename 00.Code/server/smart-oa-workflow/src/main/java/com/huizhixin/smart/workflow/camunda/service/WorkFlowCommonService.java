package com.huizhixin.smart.workflow.camunda.service;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.entity.ApprovalInfo;
import com.huizhixin.smart.workflow.camunda.entity.TaskExt;

public interface WorkFlowCommonService {

	public BpmnModelInstance getBpmnModelInstance(String xml);

	public BpmnModelInstance getBpmnModelInstance(String deploymentId, String resourceName);

	public List<TaskDefinitionDto> getTaskDefinitionDtoList(BpmnModelInstance bpmnModelInstance);

	public List<ProcessDefinition> getLastVersionProcessDefinitions();

	public String getLastVersionProcessDefinitionIdByProcessDefinitionKey(String processDefinitionKey,String tenantIds);

	public ProcessDefinition getProcessDefinitionByprocessDefinitionId(String processDefinitionId);

	public CaseDefinition getCaseProcessDefinitionByprocessDefinitionId(String processDefinitionId);

	public String getFormKey(String processDefinitionId, String caseDefinitionId, String taskDefinitionKey);

	public Map<String, TaskDefinition> getTaskDefinitions(String processDefinitionId, String caseDefinitionId);

	public ActivityImpl getEndActivityNode(String processDefinitionId);

	public ActivityImpl getStartActivityNode(String processDefinitionId);

	public ActivityImpl getFirstActivityNodeAfterStartActivity(String processDefinitionId);

	public List<ActivityImpl> getActivityNodeListByProcDefId(String processDefinitionId);

	public void jumpToTargetActivityNode(String currentKey, String targetKey,String processInstanceId, Map<String, Object> variables);

	public void completeTask(Task task, Map<String, Object> variables);

	public Comment createComment(String taskId,String taskKey,String taskName, String processInstanceId, ApprovalInfo approvalInfo);

	public Map<String, Object> getValueOfvarSql(Map<String, Object> variables, String varSql);

	public List<Map<String, Object>> getValuesOfvarSql(Map<String, Object> variables, String varSql);

	public List<String> getOneFieldValuesOfvarSql(Map<String, Object> variables, String varSql);
}
