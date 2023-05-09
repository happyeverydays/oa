package com.huizhixin.smart.workflow.camunda.service;

import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.workflow.camunda.entity.ExtActProcessForm;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface ProcessDefinitionService {
	List<ProcessDefinitionDto> getProcessDefinitions(IPage<ProcessDefinitionDto> page, String tenantIds, ProcessDefinitionQuery definitionQuery);

	public ProcessInstance startProcessInstance(String engineName, String businessKey, String processDefinitionKey, String relationCode, StartProcessInstanceDto parameters) throws Exception;

	Long getProcessDefinitionsCount(IPage<ProcessDefinitionDto> page, String tenantIds,ProcessDefinitionQuery definitionQuery);

	ExtActProcessForm getActProcessForm(String processDefinitionKey, String relationCode);

	public Map<String, Object> getData(String processDefinitionKey, String relationCode, String businessKey, Map<String, Object> variables);

	Map<String, Object> getData(ExtActProcessForm extActProcessForm, String businessKey, Map<String, Object> variables);

	Map<String, Object> getVarData(ExtActProcessForm extActProcessForm, Map<String, Object> variables);

	Map<String, Object> getFormData(ExtActProcessForm extActProcessForm, String businessKey);

}
