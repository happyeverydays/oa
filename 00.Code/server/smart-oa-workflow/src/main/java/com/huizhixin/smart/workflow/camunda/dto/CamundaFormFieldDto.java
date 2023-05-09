package com.huizhixin.smart.workflow.camunda.dto;

public class CamundaFormFieldDto extends BpmnModelElementInstanceDto {
	private String camundaLabel;
	private String camundaType;
	private String camundaDefaultValue;
	public String getCamundaLabel() {
		return camundaLabel;
	}
	public void setCamundaLabel(String camundaLabel) {
		this.camundaLabel = camundaLabel;
	}
	public String getCamundaType() {
		return camundaType;
	}
	public void setCamundaType(String camundaType) {
		this.camundaType = camundaType;
	}
	public String getCamundaDefaultValue() {
		return camundaDefaultValue;
	}
	public void setCamundaDefaultValue(String camundaDefaultValue) {
		this.camundaDefaultValue = camundaDefaultValue;
	}

}
