package com.huizhixin.smart.workflow.camunda.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.rest.spi.impl.AbstractProcessEngineAware;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.common.util.DateTimeUtil;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;
import com.huizhixin.smart.workflow.camunda.entity.ProcessInstanceDescription;
import com.huizhixin.smart.workflow.camunda.mapper.HistoricProcessInstanceMapper;
import com.huizhixin.smart.workflow.camunda.service.HistoricProcessInstanceService;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class HistoricProcessInstanceServiceImpl extends AbstractProcessEngineAware
		implements HistoricProcessInstanceService {
	HistoricProcessInstanceMapper historicProcessInstanceMapper;

	public HistoricProcessInstanceServiceImpl(String engineName,
			HistoricProcessInstanceMapper historicProcessInstanceMapper2) {
		super(engineName);
		this.historicProcessInstanceMapper = historicProcessInstanceMapper2;
	}

	@Override
	public List<ExecutionExt> getHistoricProcessInstances(IPage<ExecutionExt> page, ProcessInstanceQueryDto queryDto) {
		List<ExecutionExt> instanceResults = historicProcessInstanceMapper.selectHiProcessInstanceByQueryCriteria(page,
				queryDto);
		for (ExecutionExt executionExt : instanceResults) {
			String description = executionExt.getDescription();
			if (StringUtils.isNotEmpty(description)) {
				ProcessInstanceDescription descriptionObject = JSON.parseObject(description,
						ProcessInstanceDescription.class);
				executionExt.setTitle(descriptionObject.getTitle());
				executionExt.setFormKey(descriptionObject.getFormKey());
			}
			// 耗时计算：
			Date startTime = null;
			Date endTime = null;
			try {
				startTime = DateTimeUtil.formatStringToDate2(executionExt.getStartTime(), DateTimeUtil.DATE_FARMAT14);
				endTime = DateTimeUtil.formatStringToDate2(executionExt.getEndTime(), DateTimeUtil.DATE_FARMAT14);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			executionExt.setStartTime(DateTimeUtil.formatDateToString(startTime, DateTimeUtil.DATE_FARMAT14));
			executionExt.setEndTime(DateTimeUtil.formatDateToString(endTime, DateTimeUtil.DATE_FARMAT14));
			executionExt.setDuration(DateTimeUtil.formatDateTime(DateTimeUtil.timeDiffForMills(startTime, endTime),
					DateTimeUtil.STRING_DAY, DateTimeUtil.STRING_HOUR, DateTimeUtil.STRING_MIN));
		}
		return instanceResults;
	}

}
