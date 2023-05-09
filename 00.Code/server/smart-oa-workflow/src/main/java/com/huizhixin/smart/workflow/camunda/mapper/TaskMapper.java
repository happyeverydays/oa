package com.huizhixin.smart.workflow.camunda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.camunda.bpm.engine.impl.TaskQueryImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.workflow.camunda.entity.TaskExt;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface TaskMapper {
	public List<TaskExt> selectTaskByQueryCriteria(IPage<TaskExt> page,@Param("taskQuery") TaskQueryImpl taskQueryImpl);
	public List<TaskExt> selectTaskCountByQueryCriteria(IPage<TaskExt> page,@Param("taskQuery") TaskQueryImpl taskQueryImpl);


}
