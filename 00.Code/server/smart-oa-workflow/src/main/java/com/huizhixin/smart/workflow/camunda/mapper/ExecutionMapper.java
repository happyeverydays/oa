package com.huizhixin.smart.workflow.camunda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface ExecutionMapper {
	public List<com.huizhixin.smart.workflow.camunda.entity.ExecutionExt> selectProcessInstanceByQueryCriteria(
			IPage<ExecutionExt> page, @Param("processInstanceQuery") ProcessInstanceQueryDto queryDto);

	public void updateExecutiondById(ExecutionExt executionExt);
}
