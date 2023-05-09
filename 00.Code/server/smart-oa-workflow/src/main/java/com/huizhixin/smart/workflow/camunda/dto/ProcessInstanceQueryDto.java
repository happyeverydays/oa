package com.huizhixin.smart.workflow.camunda.dto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.camunda.bpm.engine.impl.persistence.entity.SuspensionState;
import org.camunda.bpm.engine.rest.dto.VariableQueryParameterDto;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class ProcessInstanceQueryDto {

	private static final String SORT_BY_INSTANCE_ID_VALUE = "instanceId";
	private static final String SORT_BY_DEFINITION_KEY_VALUE = "definitionKey";
	private static final String SORT_BY_DEFINITION_ID_VALUE = "definitionId";
	private static final String SORT_BY_TENANT_ID = "tenantId";
	private static final String SORT_BY_BUSINESS_KEY = "businessKey";

	private static final List<String> VALID_SORT_BY_VALUES;
	static {
		VALID_SORT_BY_VALUES = new ArrayList<String>();
		VALID_SORT_BY_VALUES.add(SORT_BY_INSTANCE_ID_VALUE);
		VALID_SORT_BY_VALUES.add(SORT_BY_DEFINITION_KEY_VALUE);
		VALID_SORT_BY_VALUES.add(SORT_BY_DEFINITION_ID_VALUE);
		VALID_SORT_BY_VALUES.add(SORT_BY_TENANT_ID);
		VALID_SORT_BY_VALUES.add(SORT_BY_BUSINESS_KEY);
	}

	private String deploymentId;
	private String processDefinitionKey;
	private String businessKey;
	private String businessKeyLike;
	private String caseInstanceId;
	private String processDefinitionId;
	private String superProcessInstance;
	private String subProcessInstance;
	private String superCaseInstance;
	private String subCaseInstance;
	private Boolean active;
	private Boolean suspended;
	private Set<String> processInstanceIds;
	private Boolean withIncident;
	private String incidentId;
	private String incidentType;
	private String incidentMessage;
	private String incidentMessageLike;
	private List<String> tenantIds;
	private Boolean withoutTenantId;
	private List<String> activityIds;
	private Boolean rootProcessInstances;
	private Boolean leafProcessInstances;
	private Boolean isProcessDefinitionWithoutTenantId;
	private String title;
	private String titleLike;
	private List<VariableQueryParameterDto> variables;
	private static final long serialVersionUID = 1L;
	private String processInstanceId;
	private String superProcessInstanceId;
	private String subProcessInstanceId;
	private SuspensionState suspensionState;
	private String superCaseInstanceId;
	private String subCaseInstanceId;
	private boolean isRootProcessInstances;
	private boolean isLeafProcessInstances;
	private boolean isTenantIdSet = false;
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getBusinessKeyLike() {
		return businessKeyLike;
	}
	public void setBusinessKeyLike(String businessKeyLike) {
		this.businessKeyLike = businessKeyLike;
	}
	public String getCaseInstanceId() {
		return caseInstanceId;
	}
	public void setCaseInstanceId(String caseInstanceId) {
		this.caseInstanceId = caseInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getSuperProcessInstance() {
		return superProcessInstance;
	}
	public void setSuperProcessInstance(String superProcessInstance) {
		this.superProcessInstance = superProcessInstance;
	}
	public String getSubProcessInstance() {
		return subProcessInstance;
	}
	public void setSubProcessInstance(String subProcessInstance) {
		this.subProcessInstance = subProcessInstance;
	}
	public String getSuperCaseInstance() {
		return superCaseInstance;
	}
	public void setSuperCaseInstance(String superCaseInstance) {
		this.superCaseInstance = superCaseInstance;
	}
	public String getSubCaseInstance() {
		return subCaseInstance;
	}
	public void setSubCaseInstance(String subCaseInstance) {
		this.subCaseInstance = subCaseInstance;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getSuspended() {
		return suspended;
	}
	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}
	public Set<String> getProcessInstanceIds() {
		return processInstanceIds;
	}
	public void setProcessInstanceIds(Set<String> processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}
	public Boolean getWithIncident() {
		return withIncident;
	}
	public void setWithIncident(Boolean withIncident) {
		this.withIncident = withIncident;
	}
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	public String getIncidentMessage() {
		return incidentMessage;
	}
	public void setIncidentMessage(String incidentMessage) {
		this.incidentMessage = incidentMessage;
	}
	public String getIncidentMessageLike() {
		return incidentMessageLike;
	}
	public void setIncidentMessageLike(String incidentMessageLike) {
		this.incidentMessageLike = incidentMessageLike;
	}
	public List<String> getTenantIds() {
		return tenantIds;
	}
	public void setTenantIds(List<String> tenantIds) {
		this.tenantIds = tenantIds;
	}
	public Boolean getWithoutTenantId() {
		return withoutTenantId;
	}
	public void setWithoutTenantId(Boolean withoutTenantId) {
		this.withoutTenantId = withoutTenantId;
	}
	public List<String> getActivityIds() {
		return activityIds;
	}
	public void setActivityIds(List<String> activityIds) {
		this.activityIds = activityIds;
	}
	public Boolean typeOfRootProcessInstances() {
		return rootProcessInstances;
	}
	public void setRootProcessInstances(Boolean rootProcessInstances) {
		this.rootProcessInstances = rootProcessInstances;
	}
	public Boolean typeOfLeafProcessInstances() {
		return leafProcessInstances;
	}
	public void setLeafProcessInstances(Boolean leafProcessInstances) {
		this.leafProcessInstances = leafProcessInstances;
	}
	public Boolean getIsProcessDefinitionWithoutTenantId() {
		return isProcessDefinitionWithoutTenantId;
	}
	public void setIsProcessDefinitionWithoutTenantId(Boolean isProcessDefinitionWithoutTenantId) {
		this.isProcessDefinitionWithoutTenantId = isProcessDefinitionWithoutTenantId;
	}
	public String getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
	public List<VariableQueryParameterDto> getVariables() {
		return variables;
	}
	public void setVariables(List<VariableQueryParameterDto> variables) {
		this.variables = variables;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getSuperProcessInstanceId() {
		return superProcessInstanceId;
	}
	public void setSuperProcessInstanceId(String superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}
	public String getSubProcessInstanceId() {
		return subProcessInstanceId;
	}
	public void setSubProcessInstanceId(String subProcessInstanceId) {
		this.subProcessInstanceId = subProcessInstanceId;
	}
	public SuspensionState getSuspensionState() {
		return suspensionState;
	}
	public void setSuspensionState(SuspensionState suspensionState) {
		this.suspensionState = suspensionState;
	}
	public String getSuperCaseInstanceId() {
		return superCaseInstanceId;
	}
	public void setSuperCaseInstanceId(String superCaseInstanceId) {
		this.superCaseInstanceId = superCaseInstanceId;
	}
	public String getSubCaseInstanceId() {
		return subCaseInstanceId;
	}
	public void setSubCaseInstanceId(String subCaseInstanceId) {
		this.subCaseInstanceId = subCaseInstanceId;
	}
	public boolean isRootProcessInstances() {
		return isRootProcessInstances;
	}
	public void setRootProcessInstances(boolean isRootProcessInstances) {
		this.isRootProcessInstances = isRootProcessInstances;
	}
	public boolean isLeafProcessInstances() {
		return isLeafProcessInstances;
	}
	public void setLeafProcessInstances(boolean isLeafProcessInstances) {
		this.isLeafProcessInstances = isLeafProcessInstances;
	}
	public boolean isTenantIdSet() {
		return isTenantIdSet;
	}
	public void setTenantIdSet(boolean isTenantIdSet) {
		this.isTenantIdSet = isTenantIdSet;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
