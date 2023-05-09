package com.huizhixin.smart.workflow.camunda.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeleteProcessDefinitionsBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.rest.ProcessDefinitionRestService;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionQueryDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.impl.NamedProcessEngineRestServiceImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.workflow.camunda.service.ProcessDefinitionService;
import com.huizhixin.smart.workflow.camunda.service.impl.AbstractProcessEngineServiceImpl;
import com.huizhixin.smart.workflow.camunda.util.RequestParamUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Slf4j
@RestController
@Api(tags = "流程定义接口")
@RequestMapping("/workflow" + NamedProcessEngineRestServiceImpl.PATH)
public class ProcessDefinitionController {
	@Autowired
	AbstractProcessEngineServiceImpl processEngineService;
	@Autowired
	ProcessDefinitionService processDefinitionService;

	@RequestMapping("/{name}" + ProcessDefinitionRestService.PATH)
	public Result<IPage<ProcessDefinitionDto>> getProcessInstanceService(@PathVariable("name") String engineName, HttpServletRequest request, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Result<IPage<ProcessDefinitionDto>> result = new Result<>();
		ProcessDefinitionQuery definitionQuery = new ProcessDefinitionQueryDto(new ObjectMapper(), RequestParamUtil.convertMultivaluedMap(request.getParameterMap())).toQuery(processEngineService.getProcessEngine(engineName));
		IPage<ProcessDefinitionDto> page = new Page<>(pageNo, pageSize);
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String tenantId = sysUser.getSysCompanyId();
		
		List<ProcessDefinitionDto> definitons = processEngineService.getProcessDefinitionService(engineName).getProcessDefinitions(page,tenantId, definitionQuery);
		long total = processEngineService.getProcessDefinitionService(engineName).getProcessDefinitionsCount(page,tenantId, definitionQuery);

		page.setTotal(total);
		page.setRecords(definitons);
		result.setResult(page);
		result.setSuccess(true);
		return result;
	}

	@PostMapping("/{name}" + ProcessDefinitionRestService.PATH + "/key/{processDefinitionKey}/start")
	public Result<ProcessInstanceDto> startProcessInstance(@PathVariable(name = "name", required = true) String engineName, @RequestParam(name = "businessKey", required = true) String businessKey, @PathVariable("processDefinitionKey") String processDefinitionKey,
			@RequestParam(name = "relationCode", required = true) String relationCode, StartProcessInstanceDto parameters) {
		Result<ProcessInstanceDto> result = new Result<ProcessInstanceDto>();
		try {
			ProcessInstance processInstance = processDefinitionService.startProcessInstance(engineName, businessKey,processDefinitionKey, relationCode, parameters);
			result.setResult(new ProcessInstanceDto(processInstance));
			result.success("流程发起成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("流程发起失败!" + e.getMessage());
		}
		return result;

	}

	@DeleteMapping("/{name}" + ProcessDefinitionRestService.PATH + "/key/{processDefinitionKey}/delete")
	public Result<ProcessInstanceDto> deleteProcessDefinitionsByKey(@PathVariable(name = "name", required = true) String engineName, @PathVariable("processDefinitionKey") String processDefinitionKey) {
		Result<ProcessInstanceDto> result = new Result<ProcessInstanceDto>();
		try {
			RepositoryService repositoryService = processEngineService.getProcessEngine(engineName).getRepositoryService();
			DeleteProcessDefinitionsBuilder builder = repositoryService.deleteProcessDefinitions().byKey(processDefinitionKey);
//			if (skipCustomListeners) {
//				builder = builder.skipCustomListeners();
//			}
//
//			if (cascade) {
//				builder = builder.cascade();
//			}
//
//			if (skipIoMappings) {
//				builder = builder.skipIoMappings();
//			}

			builder.delete();
			result.success("流程删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("流程删除失败!" + e.getMessage());
		}

		return result;
	}
}
