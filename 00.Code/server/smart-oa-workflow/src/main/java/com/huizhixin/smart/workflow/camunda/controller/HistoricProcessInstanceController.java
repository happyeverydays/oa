package com.huizhixin.smart.workflow.camunda.controller;

import java.util.List;
import org.camunda.bpm.engine.rest.ProcessInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoryRestService;
import org.camunda.bpm.engine.rest.impl.NamedProcessEngineRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
public class HistoricProcessInstanceController {
	@Autowired
	AbstractProcessEngineServiceImpl ProcessEngineService;

	@RequestMapping("/{name}" + HistoryRestService.PATH + ProcessInstanceRestService.PATH)
	public Result<IPage<ExecutionExt>> getProcessInstanceService(@PathVariable("name") String engineName,
			ProcessInstanceQueryDto queryDto, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Result<IPage<ExecutionExt>> result = new Result<IPage<ExecutionExt>>();
		IPage<ExecutionExt> page = new Page<>(pageNo, pageSize);
		List<ExecutionExt> list = null;
		try {
			list = ProcessEngineService.getHistoryProcessInstanceService(engineName).getHistoricProcessInstances(page,
					queryDto);
			page.setRecords(list);
			result.setResult(page);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		return result;
	}

}
