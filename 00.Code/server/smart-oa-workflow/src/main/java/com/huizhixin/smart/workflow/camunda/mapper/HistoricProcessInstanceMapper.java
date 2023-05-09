package com.huizhixin.smart.workflow.camunda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;
import com.huizhixin.smart.workflow.camunda.entity.HistoricProcessInstanceExtEntity;

/**
 * 
 * @author wushuo 2807972772@qq.com
 *
 */
public interface HistoricProcessInstanceMapper {
	public List<com.huizhixin.smart.workflow.camunda.entity.ExecutionExt> selectHiProcessInstanceByQueryCriteria(
			IPage<ExecutionExt> page, @Param("processInstanceQuery") ProcessInstanceQueryDto queryDto);

	int updateHistoricProcessInstanceByProcessInstanceId(HistoricProcessInstanceExtEntity hiProcInsEntity);
}
