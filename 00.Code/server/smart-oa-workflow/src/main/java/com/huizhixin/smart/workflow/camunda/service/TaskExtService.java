package com.huizhixin.smart.workflow.camunda.service;

import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.camunda.bpm.engine.task.Task;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.workflow.camunda.entity.TaskExt;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface TaskExtService {
	List<TaskExt> getTasks(IPage<TaskExt> page, TaskQueryDto queryDto);

	void complete(String taskId, Map<String, Object> variables) throws Exception;

	void complete(Task task, Map<String, Object> variables) throws Exception;

	Map<String, Object> initData(Task task,LoginUser sysUser);

}
