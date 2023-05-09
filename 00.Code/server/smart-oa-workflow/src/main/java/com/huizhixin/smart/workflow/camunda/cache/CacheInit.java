package com.huizhixin.smart.workflow.camunda.cache;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.dto.ProcessDefinitionDto;
import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl;

@Component
public class CacheInit {

	private WorkFlowCommonService workFlowCommonService = SpringContextUtils.getBean(WorkFlowCommonService.class);
	@Autowired
	public RepositoryService repositoryService;

	@PostConstruct
	public void initProcessDefinitionLoad() {
		//
		List<ProcessDefinition> pdList = workFlowCommonService.getLastVersionProcessDefinitions();
		for (ProcessDefinition processDefinition : pdList) {
			InputStream is = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
			BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(is);
			List<TaskDefinitionDto> taskDefinitionDtos = workFlowCommonService.getTaskDefinitionDtoList(bpmnModelInstance);
			ProcessDefinitionDto processDefinitionDto = new ProcessDefinitionDto(processDefinition.getId(), processDefinition.getKey(), processDefinition.getName(), processDefinition.getDescription(), processDefinition.getCategory(), processDefinition.getVersion(), processDefinition.getVersionTag(),
					taskDefinitionDtos);
			// 添加缓存
			ProcessDefinitionCache.getCache(processDefinitionDto.getKey()).add(processDefinitionDto);
		}

	}

}
