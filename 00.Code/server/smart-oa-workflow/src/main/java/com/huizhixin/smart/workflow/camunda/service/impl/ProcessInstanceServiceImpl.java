package com.huizhixin.smart.workflow.camunda.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.rest.spi.impl.AbstractProcessEngineAware;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.common.util.DateTimeUtil;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.CommentExtEntity;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;
import com.huizhixin.smart.workflow.camunda.entity.ProcessInstanceDescription;
import com.huizhixin.smart.workflow.camunda.mapper.CommentMapper;
import com.huizhixin.smart.workflow.camunda.mapper.ExecutionMapper;
import com.huizhixin.smart.workflow.camunda.service.ProcessInstanceService;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Transactional
public class ProcessInstanceServiceImpl extends AbstractProcessEngineAware implements ProcessInstanceService {
	ExecutionMapper executionMapper;
	CommentMapper commentMapper = SpringContextUtils.getBean(CommentMapper.class);

	public ProcessInstanceServiceImpl(String engineName, ExecutionMapper executionMapper) {
		super(engineName);
		this.executionMapper = executionMapper;
	}

	public List<ExecutionExt> getProcessInstances(IPage<ExecutionExt> page, ProcessInstanceQueryDto queryDto) throws ParseException {
		return queryProcessInstances(page, queryDto);
	}

	public List<ExecutionExt> queryProcessInstances(IPage<ExecutionExt> page, ProcessInstanceQueryDto queryDto) throws ParseException {
		List<ExecutionExt> instanceResults = new ArrayList<ExecutionExt>();
		instanceResults = executionMapper.selectProcessInstanceByQueryCriteria(page, queryDto);
		for (ExecutionExt executionExt : instanceResults) {
			String description = executionExt.getDescription();
			if (StringUtils.isNotEmpty(description)) {
				ProcessInstanceDescription descriptionObject = JSON.parseObject(description,
						ProcessInstanceDescription.class);
				executionExt.setTitle(descriptionObject.getTitle());
				executionExt.setFormKey(descriptionObject.getFormKey());
			}
			// 耗时计算：
			
			Date startTime = DateTimeUtil.formatStringToDate2(executionExt.getStartTime(), DateTimeUtil.DATE_FARMAT14);
			Date endTime = DateTimeUtil.formatStringToDate2(executionExt.getEndTime(), DateTimeUtil.DATE_FARMAT14);
			executionExt.setStartTime(DateTimeUtil.formatDateToString(startTime,DateTimeUtil.DATE_FARMAT14));
			executionExt.setEndTime(DateTimeUtil.formatDateToString(endTime,DateTimeUtil.DATE_FARMAT14));
			executionExt.setDuration(DateTimeUtil.formatDateTime(DateTimeUtil.timeDiffForMills(startTime, endTime), DateTimeUtil.STRING_DAY, DateTimeUtil.STRING_HOUR, DateTimeUtil.STRING_MIN));
		}
		return instanceResults;
	}

	@Override
	public List<CommentExtEntity> getComments(String processInstanceId) {
		List<CommentExtEntity> list = commentMapper.selectCommentsByProcessInstanceId(processInstanceId);
		return list;
	}

}
