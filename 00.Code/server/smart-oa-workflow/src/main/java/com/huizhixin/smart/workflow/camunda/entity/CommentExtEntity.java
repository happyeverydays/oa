package com.huizhixin.smart.workflow.camunda.entity;

import org.camunda.bpm.engine.impl.persistence.entity.CommentEntity;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class CommentExtEntity extends CommentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskKey;
	private String taskName;// 环节名字
	private String userName;
	private String approvalResultCode; // 审批结果code
	private String approvalResultDesc;// 审批结果描述。

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApprovalResultCode() {
		return approvalResultCode;
	}

	public void setApprovalResultCode(String approvalResultCode) {
		this.approvalResultCode = approvalResultCode;
	}

	public String getApprovalResultDesc() {
		return approvalResultDesc;
	}

	public void setApprovalResultDesc(String approvalResultDesc) {
		this.approvalResultDesc = approvalResultDesc;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

}
