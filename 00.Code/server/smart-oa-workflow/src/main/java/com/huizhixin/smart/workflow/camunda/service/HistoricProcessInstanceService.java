package com.huizhixin.smart.workflow.camunda.service;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface HistoricProcessInstanceService {
	List<ExecutionExt>  getHistoricProcessInstances(IPage<ExecutionExt> page,ProcessInstanceQueryDto queryDto);

}
