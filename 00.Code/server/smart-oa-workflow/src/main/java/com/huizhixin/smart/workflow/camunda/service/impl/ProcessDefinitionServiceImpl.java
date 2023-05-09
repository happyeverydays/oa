package com.huizhixin.smart.workflow.camunda.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.spi.impl.AbstractProcessEngineAware;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.entity.ApprovalInfo;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;
import com.huizhixin.smart.workflow.camunda.entity.ExtActProcessForm;
import com.huizhixin.smart.workflow.camunda.entity.FormInfo;
import com.huizhixin.smart.workflow.camunda.entity.HistoricProcessInstanceExtEntity;
import com.huizhixin.smart.workflow.camunda.entity.ProcessInstanceDescription;
import com.huizhixin.smart.workflow.camunda.mapper.CommonsMapper;
import com.huizhixin.smart.workflow.camunda.mapper.ExecutionMapper;
import com.huizhixin.smart.workflow.camunda.mapper.HistoricProcessInstanceMapper;
import com.huizhixin.smart.workflow.camunda.service.IExtActProcessFormService;
import com.huizhixin.smart.workflow.camunda.service.ProcessDefinitionService;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl.WORK_FLOW;
import com.huizhixin.smart.workflow.camunda.util.JuelUtil;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Service
public class ProcessDefinitionServiceImpl extends AbstractProcessEngineAware implements ProcessDefinitionService {
	private IExtActProcessFormService extActProcessFormService = SpringContextUtils.getBean(ExtActProcessFormServiceImpl.class);
	private CommonsMapper commonsMapper = SpringContextUtils.getBean(CommonsMapper.class);
	@Autowired
	ExecutionMapper executionMapper;
	@Autowired
	HistoricProcessInstanceMapper historicProcessInstanceMapper;
	@Autowired
	AbstractProcessEngineServiceImpl processEngineService;
	private WorkFlowCommonService workFlowCommonService = SpringContextUtils.getBean(WorkFlowCommonService.class);

	public ProcessDefinitionServiceImpl() {
		super();
	}

	public ProcessDefinitionServiceImpl(String engineName) {
		super(engineName);
	}

	@Override
	public List<ProcessDefinitionDto> getProcessDefinitions(IPage<ProcessDefinitionDto> page, String tenantIds,ProcessDefinitionQuery definitionQuery) {
		List<ProcessDefinitionDto> definitions = new ArrayList<ProcessDefinitionDto>();
		List<ProcessDefinition> matchingDefinitions = definitionQuery.tenantIdIn(tenantIds).listPage(new Long(page.offset()).intValue(), new Long(page.getSize()).intValue());
		for (ProcessDefinition definition : matchingDefinitions) {
			ProcessDefinitionDto def = ProcessDefinitionDto.fromProcessDefinition(definition);
			definitions.add(def);
		}
		
		return definitions;
	}

	@Override
	public Long getProcessDefinitionsCount(IPage<ProcessDefinitionDto> page, String tenantIds, ProcessDefinitionQuery definitionQuery) {
		return definitionQuery.tenantIdIn(tenantIds).count();
	}

	@Override
	public ExtActProcessForm getActProcessForm(String processDefinitionKey, String relationCode) {
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("relation_code", relationCode);
		columnMap.put("process_key", processDefinitionKey);
		List<ExtActProcessForm> extActProcessFormlist = (List) extActProcessFormService.listByMap(columnMap);
		ExtActProcessForm extActProcessForm = null;
		if (CollectionUtils.isNotEmpty(extActProcessFormlist)) {
			extActProcessForm = extActProcessFormlist.get(0);
		}
		return extActProcessForm;
	}

	@Override
	public Map<String, Object> getData(String processDefinitionKey, String relationCode, String businessKey, Map<String, Object> variables) {
		ExtActProcessForm extActProcessForm = getActProcessForm(processDefinitionKey, relationCode);
		return getData(extActProcessForm, businessKey, variables);
	}

	@Override
	public Map<String, Object> getData(ExtActProcessForm extActProcessForm, String businessKey, Map<String, Object> variables) {
		Map<String, Object> data = getFormData(extActProcessForm, businessKey);
		data.putAll(getVarData(extActProcessForm, variables));
		return data;
	}

	@Override
	public Map<String, Object> getFormData(ExtActProcessForm extActProcessForm, String businessKey) {
		String tableName = extActProcessForm.getFormTableName();
		return commonsMapper.getFormData(tableName, businessKey);
	}

	@Override
	public Map<String, Object> getVarData(ExtActProcessForm extActProcessForm, Map<String, Object> variables) {
		String varSql = extActProcessForm.getVarSql();
		String varInterface = extActProcessForm.getVarInterface();
		Map<String, Object> varData = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(varSql)) {
			varData = workFlowCommonService.getValueOfvarSql(variables, varSql);
		}
		if (StringUtils.isNotBlank(varInterface)) { // 后续开发。
			// TODO
		}

		return varData;
	}

	@Override
	public ProcessInstance startProcessInstance(String engineName, String businessKey, String processDefinitionKey, String relationCode, StartProcessInstanceDto parameters) throws Exception {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal(); // 获取登录用户信息
		if (sysUser == null) {
			throw new Exception("登录信息获取不到");
		}
		Map<String, Object> variables = new HashMap<String, Object>();// 流程变量

		ExtActProcessForm extActProcessForm = processEngineService.getProcessDefinitionService(engineName).getActProcessForm(processDefinitionKey, relationCode);
		if (extActProcessForm == null) {
			throw new Exception("流成发起失败，业务管理不存在数据。表单编码：" + relationCode + "\t流程key:" + processDefinitionKey);
		}

		// 流程变量对象
		FormInfo formInfo = new FormInfo(extActProcessForm.getTitleExp(), relationCode, extActProcessForm.getFormTableName(), extActProcessForm.getFormKey(), businessKey);
		variables.put(WORK_FLOW.formInfo, formInfo);
		variables.put(WORK_FLOW.startUser, sysUser);
		Map<String, Object> data = processEngineService.getProcessDefinitionService(engineName).getData(extActProcessForm, businessKey, variables);
		variables.put(WORK_FLOW.data, data);

		//processEngineService.getIdentityService(engineName).setAuthenticatedUserId(sysUser.getId());
		processEngineService.getIdentityService(engineName).setAuthentication(sysUser.getId(), null, Collections.singletonList(sysUser.getSysCompanyId()));
		// runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,
		// variables);

		// 默认跳过第一个节点。
		ActivityImpl firstActivity = workFlowCommonService.getFirstActivityNodeAfterStartActivity(workFlowCommonService.getLastVersionProcessDefinitionIdByProcessDefinitionKey(processDefinitionKey,sysUser.getSysCompanyId()));
		ProcessInstance processInstance = processEngineService.getRuntimeService(engineName).createProcessInstanceByKey(processDefinitionKey).processDefinitionTenantId(sysUser.getSysCompanyId()).businessKey(businessKey).startAfterActivity(firstActivity.getActivityId()).setVariables(variables).execute();
//		 processEngineService.getProcessDefinitionService(engineName).getFormData(processDefinitionKey, relationCode, businessKey);

		// 第一个节点添加个审批记录
		workFlowCommonService.createComment("0", firstActivity.getId(),firstActivity.getName(), processInstance.getProcessInstanceId(), new ApprovalInfo(""));
		// 流程实例的描述添加：
		updateProcessInstanceDescription(variables, formInfo, processInstance.getId());
		return processInstance;
	}

	private void updateProcessInstanceDescription(Map<String, Object> variables, FormInfo formInfo, String processInstanceId) {
		String title = JuelUtil.getStringByELAndFormData(formInfo.getTitleExp(), variables);
		ProcessInstanceDescription description = new ProcessInstanceDescription(title, formInfo.getFormTableName());
		String descriptionJson = JSON.toJSONString(description);

		// 更新运行实例描述
		ExecutionExt executionExt = new ExecutionExt();
		executionExt.setId(processInstanceId);
		executionExt.setDescription(descriptionJson);
		executionMapper.updateExecutiondById(executionExt);

		// 更新历史实例的描述
		HistoricProcessInstanceExtEntity hiProcInsEntity = new HistoricProcessInstanceExtEntity();
		hiProcInsEntity.setProcessInstanceId(processInstanceId);
		hiProcInsEntity.setDescription(descriptionJson);
		historicProcessInstanceMapper.updateHistoricProcessInstanceByProcessInstanceId(hiProcInsEntity);
	}

}
