package com.huizhixin.smart.workflow.camunda.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class HistoricProcessInstanceExtEntity  implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String businessKey;
	protected String startUserId;
	protected String superProcessInstanceId;
	protected String superCaseInstanceId;
	protected String deleteReason;
	protected String endActivityId;
	protected String startActivityId;
	protected String tenantId;
	protected String state;
	protected Long durationInMillis;
	protected Date startTime;
	protected Date endTime;

	protected String id;

	protected String rootProcessInstanceId;

	protected String processInstanceId;

	protected String executionId;

	protected String processDefinitionId;

	protected String processDefinitionKey;

	protected String processDefinitionName;

	protected Integer processDefinitionVersion;

	protected String caseInstanceId;

	protected String caseExecutionId;

	protected String caseDefinitionId;

	protected String caseDefinitionKey;

	protected String caseDefinitionName;

	protected String eventType;

	protected long sequenceCounter;

	protected Date removalTime;
	
	protected String description;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getSuperProcessInstanceId() {
		return superProcessInstanceId;
	}

	public void setSuperProcessInstanceId(String superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}

	public String getSuperCaseInstanceId() {
		return superCaseInstanceId;
	}

	public void setSuperCaseInstanceId(String superCaseInstanceId) {
		this.superCaseInstanceId = superCaseInstanceId;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getEndActivityId() {
		return endActivityId;
	}

	public void setEndActivityId(String endActivityId) {
		this.endActivityId = endActivityId;
	}

	public String getStartActivityId() {
		return startActivityId;
	}

	public void setStartActivityId(String startActivityId) {
		this.startActivityId = startActivityId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public Integer getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}

	public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}

	public String getCaseInstanceId() {
		return caseInstanceId;
	}

	public void setCaseInstanceId(String caseInstanceId) {
		this.caseInstanceId = caseInstanceId;
	}

	public String getCaseExecutionId() {
		return caseExecutionId;
	}

	public void setCaseExecutionId(String caseExecutionId) {
		this.caseExecutionId = caseExecutionId;
	}

	public String getCaseDefinitionId() {
		return caseDefinitionId;
	}

	public void setCaseDefinitionId(String caseDefinitionId) {
		this.caseDefinitionId = caseDefinitionId;
	}

	public String getCaseDefinitionKey() {
		return caseDefinitionKey;
	}

	public void setCaseDefinitionKey(String caseDefinitionKey) {
		this.caseDefinitionKey = caseDefinitionKey;
	}

	public String getCaseDefinitionName() {
		return caseDefinitionName;
	}

	public void setCaseDefinitionName(String caseDefinitionName) {
		this.caseDefinitionName = caseDefinitionName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public long getSequenceCounter() {
		return sequenceCounter;
	}

	public void setSequenceCounter(long sequenceCounter) {
		this.sequenceCounter = sequenceCounter;
	}

	public Date getRemovalTime() {
		return removalTime;
	}

	public void setRemovalTime(Date removalTime) {
		this.removalTime = removalTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
