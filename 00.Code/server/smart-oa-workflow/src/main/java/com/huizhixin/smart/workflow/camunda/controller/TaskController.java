package com.huizhixin.smart.workflow.camunda.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.camunda.bpm.engine.SuspendedEntityInteractionException;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.camunda.bpm.engine.rest.impl.NamedProcessEngineRestServiceImpl;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.workflow.camunda.cache.ProcessDefinitionCache;
import com.huizhixin.smart.workflow.camunda.dto.BpmnModelElementInstanceDto;
import com.huizhixin.smart.workflow.camunda.dto.CamundaPropertyDto;
import com.huizhixin.smart.workflow.camunda.dto.ProcessDefinitionDto;
import com.huizhixin.smart.workflow.camunda.dto.TaskDefinitionDto;
import com.huizhixin.smart.workflow.camunda.entity.ApprovalInfo;
import com.huizhixin.smart.workflow.camunda.entity.ApprovalResultEnum;
import com.huizhixin.smart.workflow.camunda.entity.TaskExt;
import com.huizhixin.smart.workflow.camunda.service.TaskExtService;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.AbstractProcessEngineServiceImpl;
import com.huizhixin.smart.workflow.camunda.service.impl.DeploymentServiceImpl;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl.WORK_FLOW;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Slf4j
@RestController
@Api(tags = "流程实例接口")
@RequestMapping("/workflow" + NamedProcessEngineRestServiceImpl.PATH)
public class TaskController {
	@Autowired
	AbstractProcessEngineServiceImpl ProcessEngineService;
	@Autowired
	private WorkFlowCommonService workFlowCommonService;

	@RequestMapping("/{name}" + TaskRestService.PATH)
	public Result<IPage<TaskExt>> getProcessInstanceService(@PathVariable("name") String engineName, TaskQueryDto queryDto, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Result<IPage<TaskExt>> result = new Result<IPage<TaskExt>>();
		IPage<TaskExt> page = new Page<>(pageNo, pageSize);
		// 获取登录用户信息
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		queryDto.setName(null);// 清空。
		// 根据用户名字查询
//		queryDto.setCandidateUser(sysUser.getUsername());
//		queryDto.setAssignee(sysUser.getUsername());

		// 根据用户id查询
		queryDto.setCandidateUser(sysUser.getId());
		queryDto.setAssignee(sysUser.getId());
		queryDto.setTenantIdIn(sysUser.getSysCompanyId().split(","));
		List<TaskExt> list = ProcessEngineService.getTaskExtService(engineName).getTasks(page, queryDto);
		page.setRecords(list);
		result.setResult(page);
		result.setSuccess(true);
		return result;
	}

	@PostMapping("/{name}" + TaskRestService.PATH + "/{taskId}" + "/complete")
	public Result<TaskExt> complete(@PathVariable("name") String engineName, @PathVariable(name = "taskId", required = true) String taskId, @RequestBody ApprovalInfo approvalInfo) {
		Result<TaskExt> result = new Result<TaskExt>();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();
			Map<String, Object> taskLocalVariables = new HashMap<String, Object>();
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			approvalInfo.setUserId(sysUser.getId());
			approvalInfo.setUserName(sysUser.getUsername());

			taskLocalVariables.put(WORK_FLOW.approvalInfo, approvalInfo);
			Task task = (Task) ProcessEngineService.getTaskService(engineName).createTaskQuery().taskId(taskId).tenantIdIn(sysUser.getSysCompanyId()).singleResult();
			if (task == null) {
				result.error500("待办任务不存在或已经处理完成");
				return result;
			}
			workFlowCommonService.createComment(taskId,task.getTaskDefinitionKey(), task.getName(), task.getProcessInstanceId(), approvalInfo);
			ProcessEngineService.getTaskService(engineName).setVariablesLocal(taskId, taskLocalVariables);
			if (StringUtils.equals(approvalInfo.getApprovalResultCode(), ApprovalResultEnum.Back.toValue())) {// 驳回
				if (StringUtils.isNotBlank(approvalInfo.getNextNodeId())) {
					workFlowCommonService.jumpToTargetActivityNode(task.getTaskDefinitionKey(), approvalInfo.getNextNodeId(), task.getProcessInstanceId(), taskLocalVariables);
					result.success("处理成功！");
				} else {
					result.error500("驳回失败！请选择下一环节。");
				}

			} else {// 处理
				ProcessEngineService.getTaskExtService(engineName).complete(task, variables);
				result.success("处理成功！");
			}

		} catch (SuspendedEntityInteractionException se) {
			result.error500("流程已经挂起，不能处理该任务");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;

	}

	/**
	 * 初始化数据
	 * 
	 * @param engineName
	 * @param taskId
	 * @param approvalInfo
	 * @return
	 */
	@PostMapping("/{name}" + TaskRestService.PATH + "/{taskId}" + "/initData")
	public Result<Map> initData(@PathVariable("name") String engineName, @PathVariable(name = "taskId", required = true) String taskId) {
		Result<Map> result = new Result<Map>();
		try {
			Map<String, Object> variables = new HashMap<String, Object>();
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			Task task = (Task) ProcessEngineService.getTaskService(engineName).createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				result.error500("待办任务不存在或已经处理完成");
				return result;
			}

			Map map = ProcessEngineService.getTaskExtService(engineName).initData(task, sysUser);
			System.out.println(map);
			result.setResult(map);
		} catch (SuspendedEntityInteractionException se) {
			result.error500("流程已经挂起，不能处理该任务");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;

	}

}
