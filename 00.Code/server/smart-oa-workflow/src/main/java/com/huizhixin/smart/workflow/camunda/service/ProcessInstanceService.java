package com.huizhixin.smart.workflow.camunda.service;

import java.text.ParseException;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.workflow.camunda.dto.ProcessInstanceQueryDto;
import com.huizhixin.smart.workflow.camunda.entity.CommentExtEntity;
import com.huizhixin.smart.workflow.camunda.entity.ExecutionExt;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface ProcessInstanceService {
	List<ExecutionExt> getProcessInstances(IPage<ExecutionExt> page,ProcessInstanceQueryDto queryDto) throws ParseException;
	List<CommentExtEntity> getComments(String processInstanceId);
}
