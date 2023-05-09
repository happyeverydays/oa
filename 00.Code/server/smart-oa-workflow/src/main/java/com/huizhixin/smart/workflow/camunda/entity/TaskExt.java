package com.huizhixin.smart.workflow.camunda.entity;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
import java.util.Date;
import lombok.Data;
@Data
public class TaskExt {
	private String id;
	private int revision;
	private String owner;
	private String assignee;
	private String parentTaskId;
	private String name;
	private String description;
	private int priority;
	private Date createTime; // The time when the task has been created
	private Date dueDate;
	private Date followUpDate;
	private int suspensionState;
	private String tenantId;
	private String delegationStateString;
	// execution
	private String executionId;
	private String processInstanceId;
	private String processDefinitionId;
	// caseExecution
	private String caseExecutionId;
	private String caseInstanceId;
	private String caseDefinitionId;

	private String taskDefinitionKey;
	private boolean isDeleted;
	private String deleteReason;
	private String eventName;
	private boolean isFormKeyInitialized = false;
	private String formKey;

	// 扩张任务常用信息。
	private String identitylinkType;
	private String processKey;
	private String processName;
	private String startUserId;
	private String startUsername;
	private String businessKey;
	private String formTableName;
}
