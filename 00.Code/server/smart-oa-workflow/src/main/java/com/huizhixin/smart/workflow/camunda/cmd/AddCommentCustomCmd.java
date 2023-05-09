package com.huizhixin.smart.workflow.camunda.cmd;

import static org.camunda.bpm.engine.ProcessEngineConfiguration.HISTORY_REMOVAL_TIME_STRATEGY_START;
import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;
import java.util.Date;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.CommentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Event;

import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.entity.ApprovalInfo;
import com.huizhixin.smart.workflow.camunda.entity.CommentExtEntity;
import com.huizhixin.smart.workflow.camunda.mapper.CommentMapper;
/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class AddCommentCustomCmd implements Command<Comment> {
	protected String taskId;
	protected String taskKey;
	protected String taskName;
	protected String processInstanceId;
	protected String type;
	protected String message;
	protected ApprovalInfo approvalInfo;
	protected CommentMapper commentMapper = SpringContextUtils.getBean(CommentMapper.class);

	public AddCommentCustomCmd(String taskId,String taskKey,String taskName, String processInstanceId, ApprovalInfo approvalInfo) {
		this.taskId = taskId;
		this.processInstanceId = processInstanceId;
		this.message = approvalInfo.getComment();
		this.approvalInfo = approvalInfo;
		this.taskKey = taskKey;
		this.taskName = taskName;
	}

	public Comment execute(CommandContext commandContext) {

		if (processInstanceId == null && taskId == null) {
			throw new ProcessEngineException("Process instance id and task id is null");
		}
		
		ProcessEngineConfigurationImpl peImpl= 	commandContext.getProcessEngineConfiguration();
        
		
		ensureNotNull("Message", message);
		CommentExtEntity comment = new CommentExtEntity();
		comment.setUserId(approvalInfo.getUserId());
		comment.setUserName(approvalInfo.getUserName());
		comment.setType(CommentEntity.TYPE_COMMENT);
		comment.setTime(ClockUtil.getCurrentTime());
		comment.setTaskId(taskId);
		comment.setTaskName(taskName);
		comment.setTaskKey(taskKey);
		comment.setProcessInstanceId(processInstanceId);
		comment.setAction(Event.ACTION_ADD_COMMENT);
		comment.setApprovalResultCode(approvalInfo.getApprovalResultCode());
		comment.setApprovalResultDesc(approvalInfo.getApprovalResultDesc());

		ExecutionEntity execution = getExecution(commandContext, taskId, processInstanceId);
		if (execution != null) {
			comment.setRootProcessInstanceId(execution.getRootProcessInstanceId());
		}

		if (isHistoryRemovalTimeStrategyStart()) {
			provideRemovalTime(comment);
		}
		String eventMessage = message.replaceAll("\\s+", " ");
//		    if (eventMessage.length() > 163) {
//		      eventMessage = eventMessage.substring(0, 160) + "...";
//		    }
		comment.setMessage(eventMessage);
		comment.setFullMessage(message);
		comment.setId(commandContext.getProcessEngineConfiguration().getIdGenerator().getNextId());
		commentMapper.insertCommentExt(comment);
		return comment;
	}

	protected ExecutionEntity getExecution(CommandContext commandContext, String taskId, String processInstanceId) {
		ExecutionEntity execution = null;
		if (taskId != null) {
			TaskEntity task = commandContext.getTaskManager().findTaskById(taskId);

			if (task != null) {
				execution = task.getExecution();
			}
		} else if (processInstanceId != null) {
			execution = commandContext.getExecutionManager().findExecutionById(processInstanceId);
		}

		return execution;
	}

	protected boolean isHistoryRemovalTimeStrategyStart() {
		return HISTORY_REMOVAL_TIME_STRATEGY_START.equals(getHistoryRemovalTimeStrategy());
	}

	protected String getHistoryRemovalTimeStrategy() {
		return Context.getProcessEngineConfiguration().getHistoryRemovalTimeStrategy();
	}

	protected HistoricProcessInstanceEventEntity getHistoricRootProcessInstance(String rootProcessInstanceId) {
		return Context.getCommandContext().getDbEntityManager().selectById(HistoricProcessInstanceEventEntity.class, rootProcessInstanceId);
	}

	protected void provideRemovalTime(CommentEntity comment) {
		String rootProcessInstanceId = comment.getRootProcessInstanceId();
		if (rootProcessInstanceId != null) {
			HistoricProcessInstanceEventEntity historicRootProcessInstance = getHistoricRootProcessInstance(rootProcessInstanceId);

			if (historicRootProcessInstance != null) {
				Date removalTime = historicRootProcessInstance.getRemovalTime();
				comment.setRemovalTime(removalTime);
			}
		}
	}

}
