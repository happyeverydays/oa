package com.huizhixin.smart.workflow.camunda.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response.Status;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentWithDefinitionsDto;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.engine.rest.spi.impl.AbstractProcessEngineAware;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;

import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.cache.ProcessDefinitionCache;
import com.huizhixin.smart.workflow.camunda.dto.BpmnModelElementInstanceDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaFormFieldDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaPropertyDto;
import com.huizhixin.smart.workflow.camunda.dto.ProcessDefinitionDto;
import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.service.DeploymentService;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class DeploymentServiceImpl extends AbstractProcessEngineAware implements DeploymentService {
	public final static String resource_Name = "resourceName";
	public final static String resource_Value = "resourceText";
	private WorkFlowCommonService workFlowCommonService = SpringContextUtils.getBean(WorkFlowCommonService.class);

	public DeploymentServiceImpl(String engineName) {
		super(engineName);
	}

	@Override
	public DeploymentWithDefinitionsDto createDeployment(String deploymentName, boolean enableDuplicateFiltering, boolean deployChangedOnly, String deploymentSource, String deploymentTenantId, List<Map<String, Object>> resources) {
		DeploymentBuilder deploymentBuilder = extractDeploymentInformation(deploymentName, enableDuplicateFiltering, deployChangedOnly, deploymentSource, deploymentTenantId, resources);
		if (!deploymentBuilder.getResourceNames().isEmpty()) {
			DeploymentWithDefinitions deployment = deploymentBuilder.deployWithResult();
			List<ProcessDefinition> ProcessDefinitionList = deployment.getDeployedProcessDefinitions();
			DeploymentWithDefinitionsDto deploymentDto = DeploymentWithDefinitionsDto.fromDeployment(deployment);
			// ==============================================

			// URI uri =
			// uriInfo.getBaseUriBuilder().path(relativeRootResourcePath).path(DeploymentRestService.PATH).path(deployment.getId()).build();
			// GET
			// deploymentDto.addReflexiveLink(uri, HttpMethod.GET, "self");
			BpmnModelInstance bpmnModelInstance = (BpmnModelInstance)  resources.get(0).get(resource_Value);
			List<TaskDefinitionDto> taskDefinitionDtos = workFlowCommonService.getTaskDefinitionDtoList(bpmnModelInstance);
			ProcessDefinition processDefinition = ProcessDefinitionList.get(0);
			ProcessDefinitionDto processDefinitionDto = new ProcessDefinitionDto(processDefinition.getId(), processDefinition.getKey(), processDefinition.getName(), processDefinition.getDescription(), processDefinition.getCategory(), processDefinition.getVersion(), processDefinition.getVersionTag(),
					taskDefinitionDtos);
			// 存入缓存
			ProcessDefinitionCache.getCache(processDefinition.getKey()).add(processDefinitionDto);
			return deploymentDto;
		} else {
			throw new InvalidRequestException(Status.BAD_REQUEST, "No deployment resources contained in the form upload.");
		}
	}

	private DeploymentBuilder extractDeploymentInformation(String deploymentName, boolean enableDuplicateFiltering, boolean deployChangedOnly, String deploymentSource, String deploymentTenantId, List<Map<String, Object>> resources) {
		DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
		for (Map<String, Object> resource : resources) {
			String resourceName = (String) resource.get(resource_Name);
			Object resourceText = resource.get(resource_Value);
			if (resourceName != null) {
				if (resourceText instanceof String) {
					String resourceTextv = (String) resourceText;
					deploymentBuilder.addString(resourceName, resourceTextv);
				} else if (resourceText instanceof BpmnModelInstance) {
					BpmnModelInstance modelInstance = (BpmnModelInstance) resourceText;
					deploymentBuilder.addModelInstance(resourceName, modelInstance);

				} else {
				}

			} else {
				throw new InvalidRequestException(Status.BAD_REQUEST, "No file name found in the deployment resource described by form parameter '" + resourceName + "'.");
			}
		}
		if (deploymentName != null) {
			deploymentBuilder.name(deploymentName);
		}
		if (deploymentSource != null) {
			deploymentBuilder.source(deploymentSource);
		}
		if (deploymentTenantId != null) {
			deploymentBuilder.tenantId(deploymentTenantId);
		}
		extractDuplicateFilteringForDeployment(enableDuplicateFiltering, deployChangedOnly, deploymentBuilder);
		return deploymentBuilder;
	}

	private void extractDuplicateFilteringForDeployment(boolean enableDuplicateFiltering, boolean deployChangedOnly, DeploymentBuilder deploymentBuilder) {
		// deployChangedOnly overrides the enableDuplicateFiltering setting
		if (deployChangedOnly) {
			deploymentBuilder.enableDuplicateFiltering(true);
		} else if (enableDuplicateFiltering) {
			deploymentBuilder.enableDuplicateFiltering(false);
		}
	}
}
