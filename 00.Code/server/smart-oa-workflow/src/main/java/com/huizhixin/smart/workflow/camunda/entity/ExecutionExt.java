package com.huizhixin.smart.workflow.camunda.entity;

import java.io.Serializable;

/**
 * 
 * @author wushuo 2807972772@qq.com 流程实例扩展类
 */
public class ExecutionExt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String revision;
	private String rootProcessInstanceId;
	private String processInstanceId;
	private String businessKey;
	private String processDefinitionId;
	private String activityId;
	private String activityInstanceId;
	private String isActive;
	private String isConcurrent;
	private String isScope;
	private String isEventScope;
	private String parentId;
	private String superExecutionId;
	private String superCaseExecutionId;
	private String caseInstanceId;
	private String suspensionState;
	private String cachedEntityState;
	private String sequenceCounter;
	private String tenantId;
	private String state;

	private String name;
	private String key;
	private String startTime;
	private String endTime;
	private String duration;
	private String startUserId;
	private String startActId;
	private String endActId;
	private String superProcessInstanceId;
	private String deleteReason;
	private String startUserName;
	private String title;
	private String description;
	private String formKey;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRootProcessInstanceId() {
		return rootProcessInstanceId;
	}

	public void setRootProcessInstanceId(String rootProcessInstanceId) {
		this.rootProcessInstanceId = rootProcessInstanceId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityInstanceId() {
		return activityInstanceId;
	}

	public void setActivityInstanceId(String activityInstanceId) {
		this.activityInstanceId = activityInstanceId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getIsScope() {
		return isScope;
	}

	public void setIsScope(String isScope) {
		this.isScope = isScope;
	}

	public String getIsEventScope() {
		return isEventScope;
	}

	public void setIsEventScope(String isEventScope) {
		this.isEventScope = isEventScope;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSuperExecutionId() {
		return superExecutionId;
	}

	public void setSuperExecutionId(String superExecutionId) {
		this.superExecutionId = superExecutionId;
	}

	public String getSuperCaseExecutionId() {
		return superCaseExecutionId;
	}

	public void setSuperCaseExecutionId(String superCaseExecutionId) {
		this.superCaseExecutionId = superCaseExecutionId;
	}

	public String getCaseInstanceId() {
		return caseInstanceId;
	}

	public void setCaseInstanceId(String caseInstanceId) {
		this.caseInstanceId = caseInstanceId;
	}

	public String getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(String suspensionState) {
		this.suspensionState = suspensionState;
	}

	public String getCachedEntityState() {
		return cachedEntityState;
	}

	public void setCachedEntityState(String cachedEntityState) {
		this.cachedEntityState = cachedEntityState;
	}

	public String getSequenceCounter() {
		return sequenceCounter;
	}

	public void setSequenceCounter(String sequenceCounter) {
		this.sequenceCounter = sequenceCounter;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartActId() {
		return startActId;
	}

	public void setStartActId(String startActId) {
		this.startActId = startActId;
	}

	public String getEndActId() {
		return endActId;
	}

	public void setEndActId(String endActId) {
		this.endActId = endActId;
	}

	public String getSuperProcessInstanceId() {
		return superProcessInstanceId;
	}

	public void setSuperProcessInstanceId(String superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
