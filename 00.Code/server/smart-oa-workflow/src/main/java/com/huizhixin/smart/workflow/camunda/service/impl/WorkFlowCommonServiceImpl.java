package com.huizhixin.smart.workflow.camunda.service.impl;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.cmmn.entity.repository.CaseDefinitionEntity;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Event;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.cmd.AddCommentCustomCmd;
import com.huizhixin.smart.workflow.camunda.dto.BpmnModelElementInstanceDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaFormFieldDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaPropertyDto;
import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.entity.ApprovalInfo;
import com.huizhixin.smart.workflow.camunda.entity.CommentExtEntity;
import com.huizhixin.smart.workflow.camunda.entity.TaskExt;
import com.huizhixin.smart.workflow.camunda.mapper.CommentMapper;
import com.huizhixin.smart.workflow.camunda.mapper.CommonsMapper;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.util.JuelUtil;

/**
 * 
 * activiti工具类
 *
 * @author wushuo
 */
@Service
public class WorkFlowCommonServiceImpl implements WorkFlowCommonService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String START_EVENT = "startEvent";
	public static final String END_EVENT = "endEvent";
	public static final String TASK = "task";
	public static final String USER_TASK = "userTask";
	public static final String EXCLUSIVE_GATEWAY = "exclusiveGateway";
	public static final String PARALLEL_GATEWAY = "parallelGateway";
	public static final String INCLUSIVE_GATEWAY = "inclusiveGateway";
	public static final String EVENT_GATEWAY = "eventGateway";
	public static final String TYPE = "type";

	@Autowired
	private HistoryService historyService;
	@Autowired
	public RepositoryService repositoryService;
	@Autowired
	public TaskService taskService;
	@Autowired
	public RuntimeService runtimeService;
	@Autowired
	public CommentMapper commentMapper;
	@Autowired
	private CommonsMapper commonsMapper;

	@Override
	public BpmnModelInstance getBpmnModelInstance(String xml_utf8) {

		ByteArrayInputStream bpmnStream = null;
		try {
			bpmnStream = new ByteArrayInputStream(xml_utf8.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(bpmnStream);
		return bpmnModelInstance;
	}

	@Override
	public BpmnModelInstance getBpmnModelInstance(String deploymentId, String resourceName) {
		InputStream is = repositoryService.getResourceAsStream(deploymentId, resourceName);
		BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(is);
		return bpmnModelInstance;
	}

	public List<TaskDefinitionDto> getTaskDefinitionDtoList(BpmnModelInstance bpmnModelInstance) {
		Collection<org.camunda.bpm.model.bpmn.instance.Task> taskDefs = bpmnModelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Task.class);
		List<TaskDefinitionDto> TaskDefinitionDtos = new ArrayList<TaskDefinitionDto>();
		for (org.camunda.bpm.model.bpmn.instance.Task task : taskDefs) {//
			ExtensionElements extensionElements = task.getExtensionElements();
			List<BpmnModelElementInstanceDto> extensionElementDtos = new ArrayList<BpmnModelElementInstanceDto>();
			if (extensionElements != null) {
				List extensionElementList = extensionElements.getElementsQuery().list();
				for (Object element : extensionElementList) {
					if (element instanceof CamundaProperties) {
						CamundaProperties camundaProperties = (org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties) element;
						for (CamundaProperty property : camundaProperties.getCamundaProperties()) {
							CamundaPropertyDto camundaPropertyDtos = new CamundaPropertyDto();
							camundaPropertyDtos.setCamundaId(property.getCamundaId());
							camundaPropertyDtos.setCamundaName(property.getCamundaName());
							camundaPropertyDtos.setCamundaValue(property.getCamundaValue());
							extensionElementDtos.add(camundaPropertyDtos);
						}
					} else if (element instanceof CamundaFormData) {
						CamundaFormData camundaFormData = (CamundaFormData) element;
						for (CamundaFormField camundaFormField : camundaFormData.getCamundaFormFields()) {
							CamundaFormFieldDto camundaFormFieldDto = new CamundaFormFieldDto();
							camundaFormFieldDto.setCamundaDefaultValue(camundaFormField.getCamundaDefaultValue());
							camundaFormFieldDto.setCamundaId(camundaFormField.getCamundaId());
							camundaFormFieldDto.setCamundaLabel(camundaFormField.getCamundaLabel());
							camundaFormFieldDto.setCamundaType(camundaFormField.getCamundaType());
							extensionElementDtos.add(camundaFormFieldDto);
						}
					}
				}
			}
			TaskDefinitionDtos.add(new TaskDefinitionDto(task.getId(), task.getName(), extensionElementDtos));
		}
		return TaskDefinitionDtos;
	}

	/**
	 * 
	 * 根据流程key,获取最新的流程定义id
	 *
	 * @author wushuo
	 * @param
	 * @return
	 */
	public String getLastVersionProcessDefinitionIdByProcessDefinitionKey(String processDefinitionKey,String tenantIds) {
		ProcessDefinition pdf = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).tenantIdIn(tenantIds).latestVersion().singleResult();
		//repositoryService.createProcessDefinitionQuery().list();

		if (pdf == null) {
			return null;
		} else {
			return pdf.getId();
		}
	}

	/**
	 * 
	 *
	 * @author wushuo
	 * @param
	 * @return
	 */
	public List<ProcessDefinition> getLastVersionProcessDefinitions() {
		List<ProcessDefinition> pdfList = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		return pdfList;
	}

	/**
	 * 
	 * 根据流程定义id
	 *
	 * @author wushuo
	 * @param
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByprocessDefinitionId(String processDefinitionId) {
		ProcessDefinition pdf = repositoryService.getProcessDefinition(processDefinitionId);
		return pdf;
	}

	@Override
	public CaseDefinition getCaseProcessDefinitionByprocessDefinitionId(String processDefinitionId) {
		return repositoryService.getCaseDefinition(processDefinitionId);
	}

	public Map<String, TaskDefinition> getTaskDefinitions(String processDefinitionId, String caseDefinitionId) {
		Map<String, TaskDefinition> taskDefinitions = null;

		if (processDefinitionId != null) {
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) getProcessDefinitionByprocessDefinitionId(processDefinitionId);
			taskDefinitions = processDefinition.getTaskDefinitions();
		} else {
			CaseDefinitionEntity caseDefinition = (CaseDefinitionEntity) getCaseProcessDefinitionByprocessDefinitionId(caseDefinitionId);
			taskDefinitions = caseDefinition.getTaskDefinitions();
		}
		return taskDefinitions;
	}

	public String getFormKey(String processDefinitionId, String caseDefinitionId, String taskDefinitionKey) {
		if (taskDefinitionKey != null && processDefinitionId != null) {
			TaskDefinition taskDefinition = null;
			Map<String, TaskDefinition> taskDefinitions = getTaskDefinitions(processDefinitionId, caseDefinitionId);
			taskDefinition = taskDefinitions.get(taskDefinitionKey);
			Expression formKey = taskDefinition.getFormKey();
			if (formKey == null) {
				return null;
			} else {
				return formKey.getExpressionText();
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取流程结束的环节id
	 * 
	 * @Description:
	 * @date 2019年9月27日 下午3:29:05
	 * @author wushuo[2807972772@qq.com]
	 * @param processDefinitionId
	 * @return
	 * @return
	 */
	public ActivityImpl getEndActivityNode(String processDefinitionId) {
		ActivityImpl endActivity = null;
		List<ActivityImpl> allActivities = getActivityNodeListByProcDefId(processDefinitionId);
		for (ActivityImpl activity : allActivities) {
			String type = activity.getProperty("type").toString();
			if (type.equals(END_EVENT)) {
				endActivity = activity;
				break;
			}
		}
		return endActivity;
	}

	/**
	 * 获取流程开始的环节id
	 * 
	 * @Description: TODO
	 * @date 2019年10月27日
	 * @author wushuo[2807972772@qq.com]
	 * @param processDefinitionId
	 * @return
	 */
	public ActivityImpl getStartActivityNode(String processDefinitionId) {
		ActivityImpl endActivity = null;
		List<ActivityImpl> allActivities = getActivityNodeListByProcDefId(processDefinitionId);
		for (ActivityImpl activity : allActivities) {
			String type = activity.getProperty("type").toString();
			if (type.equals(START_EVENT)) {
				endActivity = activity;
				break;
			}
		}
		return endActivity;
	}

	@Override
	public void jumpToTargetActivityNode(String currentKey, String targetKey,String processInstanceId,Map<String, Object> variables) {
		runtimeService.createProcessInstanceModification(processInstanceId).startBeforeActivity(targetKey).setVariables(variables).cancelAllForActivity(currentKey).execute();
	}

	/**
	 * 得到开始节点后面的第一个环节。
	 */
	@Override
	public ActivityImpl getFirstActivityNodeAfterStartActivity(String processDefinitionId) {
		List<PvmTransition> pvmOutTransitions = getStartActivityNode(processDefinitionId).getOutgoingTransitions();
		if (!CollectionUtils.isEmpty(pvmOutTransitions)) {
			PvmTransition ts = pvmOutTransitions.get(0);
			PvmActivity ac = ts.getDestination(); // 获取线路的到达节点
			return (ActivityImpl) ac;
		}
		return null;
	}

	/**
	 * 获取所有节点
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public List<ActivityImpl> getActivityNodeListByProcDefId(String processDefinitionId) {
		List<ActivityImpl> activityList = null;
		if (StringUtils.isBlank(processDefinitionId)) {
			return null;
		}
		try {
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
			activityList = processDefinitionEntity.getActivities();
		} catch (Exception e) {

		}

		return activityList;
	}

	/**
	 * 
	 * @Description: TODO
	 * @date 2019年10月8日 上午10:08:16
	 * @author wushuo[2807972772@qq.com]
	 * @param taskId
	 * @param processInstanceId
	 * @param message
	 * @param approvalResult
	 * @param approvalResultName
	 * @return
	 */
	public Comment createComment(String taskId, String taskKey,String taskName, String processInstanceId, ApprovalInfo approvalInfo) {
		// return taskService.addComment(taskId, processInstanceId, message);
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		Comment comment = commandExecutor.execute(new AddCommentCustomCmd(taskId, taskKey ,taskName, processInstanceId, approvalInfo));
		return comment;
	}

	public List<CommentExtEntity> getCommentsByProcessInstanceId(String processInstanceId) {
		List<CommentExtEntity> list = commentMapper.selectCommentsByProcessInstanceId(processInstanceId);
		return list;
	}

	public Map<String, Object> getValueOfvarSql(Map<String, Object> variables, String varSql) {
		Map<String, Object> varData = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(varSql)) {
			String selectSql = JuelUtil.getStringByELAndFormData(varSql, variables);// 变量
			String[] selectSqls = selectSql.split(";");
			for (String sql : selectSqls) {
				if (StringUtils.isNotBlank(sql)) {
					varData.putAll(commonsMapper.getVarData(sql).get(0));
				}
			}
		}

		return varData;
	}

	@Override
	public List<Map<String, Object>> getValuesOfvarSql(Map<String, Object> variables, String varSql) {
		List<Map<String, Object>> varData = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(varSql)) {
			String selectSql = JuelUtil.getStringByELAndFormData(varSql, variables);// 变量
			String[] selectSqls = selectSql.split(";");
			for (String sql : selectSqls) {
				if (StringUtils.isNotBlank(sql)) {
					varData.addAll((commonsMapper.getVarData(sql)));
				}
			}
		}
		return varData;
	}

	@Override
	public List<String> getOneFieldValuesOfvarSql(Map<String, Object> variables, String varSql) {
		List<String> varData = new ArrayList<String>();
		if (StringUtils.isNotBlank(varSql)) {
			String selectSql = JuelUtil.getStringByELAndFormData(varSql, variables);// 变量
			String[] selectSqls = selectSql.split(";");
			for (String sql : selectSqls) {
				if (StringUtils.isNotBlank(sql)) {
					varData.addAll((commonsMapper.getOneFieldVarData(selectSql)));
				}
			}
		}
		return varData;
	}

	/**
	 * 工作流相关常量
	 */
	public interface WORK_FLOW {
		final String processStartUsername = "processStartUsername";
		final String processStartFullname = "processStartFullname";
		final String processStartGroupId = "processStartGroupId";
		final String processStartCompanyId = "processStartCompanyId";
		final String processStartDepartmentIds = "processStartDepartmentIds";
		final String startUser = "startUser";
		final String loginUser ="loginUser";
		final String lastAssignee = "lastAssignee"; // 当前待办任务处理人信息
		final String processInfoList = "processInfoList";
		final String processInfo = "processInfo";
		final String approvalInfo = "approvalInfo";
		final String taskInfo = "taskInfo";
		final String formInfo = "formInfo";
		final String data = "data";
		final String processInstanceId = "processInstanceId";
		final String processKey = "key";
		final String processName = "name";
		final String processTitle = "title";
		final String resultCount = "RC";
		final String endNoneEventId = "END";
		final String startFormHandler = "StartFormHandler";

		final String AUDIT_STATUS = "AUDIT_STATUS";
		final String AUDIT_ACTION = "AUDIT_ACTION";
		final String PROCESS_INSTANCE_ID = "PROCESS_INSTANCE_ID";
		final String PROCESS_KEY = "PROCESS_KEY";
		final String PROCESS_DEFINITION_ID = "PROCESS_DEFINITION_ID";

		// 历史节点参与者
		String IDENTITYLINKTYPE_CANDIDATE = "candidate"; // 候选人和候选组
		String IDENTITYLINKTYPE_PARTICIPANT = "participant";
		String IDENTITYLINKTYPE_STARTER = "starter";

		// 环节配置相关常量
		String ENABLED = "enabled";
		String SQL = "sql";
		String TASK_BEGIN_SQL = "taskBeginSql";
		String TASK_END_SQL = "taskEndSql";
		String PROC_BEGIN_SQL = "procBeginSql";
		String PROC_END_SQL = "procEndSql";
		String NEXT_PROCESS = "nextProcess";
		String BACK_TRACK = "backtrack";
		String PREFIX_PRE = "pre_";
	}
	
	public class Backtrack{
		private String nodeId;
		private boolean enable;
		private boolean isEnable;
		public String getNodeId() {
			return nodeId;
		}
		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}
		public boolean getEnable() {
			return enable;
		}
		public void setEnable(boolean enable) {
			this.enable = enable;
		}
		
		public boolean isEnable() {
			return enable;
		}
		
	}

	@Override
	public void completeTask(Task task, Map<String, Object> variables)

	{
		if (task.getDelegationState() == DelegationState.PENDING) {
			taskService.resolveTask(task.getId(), variables);
		}
		taskService.complete(task.getId(), variables);

	}

}
