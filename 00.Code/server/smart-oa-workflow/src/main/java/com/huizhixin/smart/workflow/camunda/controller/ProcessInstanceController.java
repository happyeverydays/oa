package com.huizhixin.smart.workflow.camunda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.camunda.bpm.engine.rest.ProcessInstanceRestService;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceSuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.runtime.batch.DeleteProcessInstancesDto;
import org.camunda.bpm.engine.rest.impl.NamedProcessEngineRestServiceImpl;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;
import com.huizhixin.smart.workflow.camunda.service.impl.AbstractProcessEngineServiceImpl;
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
public class ProcessInstanceController {
	@Autowired
	AbstractProcessEngineServiceImpl ProcessEngineService;

	@RequestMapping("/{name}" + ProcessInstanceRestService.PATH)
	public Result<IPage<ExecutionExt>> getProcessInstanceService(@PathVariable("name") String engineName,
			ProcessInstanceQueryDto queryDto, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Result<IPage<ExecutionExt>> result = new Result<IPage<ExecutionExt>>();
		IPage<ExecutionExt> page = new Page<>(pageNo, pageSize);
		List<ExecutionExt> list = null;
		try {
			list = ProcessEngineService.getProcessInstanceService(engineName).getProcessInstances(page, queryDto);
			page.setRecords(list);
			result.setResult(page);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping("/{name}" + ProcessInstanceRestService.PATH + "/{processInstanceId}/comments")
	public List getComments(@PathVariable("name") String engineName,
			@PathVariable("processInstanceId") String processInstanceId) {
		return ProcessEngineService.getProcessInstanceService(engineName).getComments(processInstanceId);
	}

	@PutMapping("/{name}" + ProcessInstanceRestService.PATH + "/{processInstanceId}/suspended")
	public Result<Object> updateSuspensionState(@PathVariable("name") String engineName,
			@PathVariable("processInstanceId") String processInstanceId, HttpServletRequest request,
			@RequestBody Map<String, Object> requestBodyContent) {
		Result<Object> result = new Result<Object>();
		try {
			int suspensionState = (int) requestBodyContent.get("suspensionState");
			ProcessInstanceSuspensionStateDto dto = new ProcessInstanceSuspensionStateDto();
			if (suspensionState == 1) { // 激活
				dto.setSuspended(false);
			} else { // suspensionState 挂起
				dto.setSuspended(true);
			}
			dto.setProcessInstanceId(processInstanceId);
			dto.updateSuspensionState(ProcessEngineService.getProcessEngine(engineName));
			result.success("流程实例挂起或启动成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("流程实例挂起失败!" + e.getMessage());
		}
		return result;
	};

	@DeleteMapping("/{name}" + ProcessInstanceRestService.PATH + "/{processInstanceId}")
	public Result<Object> delete(@PathVariable("name") String engineName,
			@PathVariable("processInstanceId") String processInstanceId, DeleteProcessInstancesDto dto) {
		Result<Object> result = new Result<Object>();
		try {
			List<String> processInstanceIds = new ArrayList<String>();
	  		processInstanceIds.add(processInstanceId);
			ProcessInstanceQuery processInstanceQuery = null;
			if (dto.getProcessInstanceQuery() != null) {
				processInstanceQuery = dto.getProcessInstanceQuery()
						.toQuery(ProcessEngineService.getProcessEngine(engineName));
			}
			dto.setProcessInstanceIds(processInstanceIds);
			ProcessEngineService.getProcessEngine(engineName).getRuntimeService().deleteProcessInstancesAsync(
					dto.getProcessInstanceIds(), processInstanceQuery, dto.getDeleteReason(),
					dto.isSkipCustomListeners(), dto.isSkipSubprocesses());
			result.success("流程实例删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("流程实例删除失败!" + e.getMessage());
		}
		return result;
	};
}
