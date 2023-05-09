package com.huizhixin.smart.workflow.camunda.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author wushuo 2807972772@qq.com
 *
 */
public class ApprovalInfo implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String taskId; //	
	private String comment; // 审批意见	
	private String approvalResultCode; //审批结果code
	private String approvalResultDesc;// 审批结果描述。
	private List<String> nextOperaterIds;// 下一操作人id
    private List<String> nextOperaterNames;// 下一操作人名字
    private String nextNodeId; //下一个环节。
    private String userId;
    private String userName;
    private  Object formData;
    
    
    
    
    
	public ApprovalInfo() {
		super();
	}
	
	
	public ApprovalInfo(String comment) {
		super();
		this.comment = comment;
	}

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public List<String> getNextOperaterIds() {
		return nextOperaterIds;
	}
	public void setNextOperaterIds(List<String> nextOperaterIds) {
		this.nextOperaterIds = nextOperaterIds;
	}
	public List<String> getNextOperaterNames() {
		return nextOperaterNames;
	}
	public void setNextOperaterNames(List<String> nextOperaterNames) {
		this.nextOperaterNames = nextOperaterNames;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getNextNodeId() {
		return nextNodeId;
	}


	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}


	public Object getFormData() {
		return formData;
	}


	public void setFormData(Object formData) {
		this.formData = formData;
	}
    
    

}
