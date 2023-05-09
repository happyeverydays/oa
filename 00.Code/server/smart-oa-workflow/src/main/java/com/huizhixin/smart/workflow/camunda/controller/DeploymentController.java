package com.huizhixin.smart.workflow.camunda.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response.Status;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.apache.shiro.SecurityUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.DeploymentRestService;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentDto;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.engine.rest.impl.NamedProcessEngineRestServiceImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.camunda.bpm.model.xml.Model;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.workflow.camunda.dto.BpmnModelElementInstanceDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaFormFieldDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaPropertyDto;
import com.huizhixin.smart.workflow.camunda.dto.ProcessDefinitionDto;
import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.AbstractProcessEngineServiceImpl;
import com.huizhixin.smart.workflow.camunda.service.impl.DeploymentServiceImpl;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Slf4j
@RestController
@Api(tags = "流程发布接口")
@RequestMapping("/workflow" + NamedProcessEngineRestServiceImpl.PATH)
public class DeploymentController {
	@Autowired
	AbstractProcessEngineServiceImpl processEngineService;
	@Autowired
	private WorkFlowCommonService workFlowCommonService ;

	@PostMapping("/{name}" + DeploymentRestService.PATH + "/create")
	public Result<DeploymentDto> createDeployment(@PathVariable(name = "name", required = true) String engineName, @RequestParam(name = "deploymentName") String deploymentName, @RequestParam(name = "enableDuplicateFiltering", defaultValue = "false") boolean enableDuplicateFiltering,
			@RequestParam(name = "deployChangedOnly", defaultValue = "false") boolean deployChangedOnly, @RequestParam(name = "deploymentSource") String deploymentSource, @RequestParam(name = "tenantId", required = false) String deploymentTenantId, @RequestParam(name = "resourceName") String resourceName,
			@RequestParam(name = "resourceText") String resourceText) {
		List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
		Result<DeploymentDto> result = new Result<DeploymentDto>();
		try {
			BpmnModelInstance bpmnModelInstance = workFlowCommonService.getBpmnModelInstance(resourceText);
			Map<String, Object> resourceMap = new HashMap<String, Object>();
			resourceMap.put(DeploymentServiceImpl.resource_Name, resourceName);
			resourceMap.put(DeploymentServiceImpl.resource_Value, bpmnModelInstance);
			resources.add(resourceMap);
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			deploymentTenantId = sysUser.getSysCompanyId();
			
			DeploymentDto deploymentDto = processEngineService.getDeploymentService(engineName).createDeployment(deploymentName, enableDuplicateFiltering, deployChangedOnly, deploymentSource, deploymentTenantId, resources);
			deploymentDto.getSource();
			deploymentDto.getId();
			result.success("流程发布成功");
			result.setResult(deploymentDto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("流程发布失败!" + e.getMessage());
		}
		return result;

	}

	@DeleteMapping("/{name}" + DeploymentRestService.PATH + "/{id}")
	public Result<DeploymentDto> deleteDeployment(@PathVariable(name = "name", required = true) String engineName, @PathVariable("id") String deploymentId) {
		Result<DeploymentDto> result = new Result<DeploymentDto>();
		RepositoryService repositoryService = processEngineService.getProcessEngine(engineName).getRepositoryService();
		try {
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			Deployment deployment = repositoryService.createDeploymentQuery().tenantIdIn(sysUser.getSysCompanyId()).deploymentId(deploymentId).singleResult();
			if (deployment == null) {
				throw new InvalidRequestException(Status.NOT_FOUND, "Deployment with id '" + deploymentId + "' do not exist");
			}
			boolean cascade = true;
			boolean skipCustomListeners = true;
			boolean skipIoMappings = true;
			repositoryService.deleteDeployment(deploymentId, cascade, skipCustomListeners, skipIoMappings);
			result.success("流程删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("流程删除失败!" + e.getMessage());
		}
		return result;

	}
}
