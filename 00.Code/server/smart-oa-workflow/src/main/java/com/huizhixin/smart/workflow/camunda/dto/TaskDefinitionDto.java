package com.huizhixin.smart.workflow.camunda.dto;

import java.util.List;


public class TaskDefinitionDto {
	private String key;
	private String name;
	private List<BpmnModelElementInstanceDto> extensionElements;
	
	public TaskDefinitionDto() {
		super();
	}
	
	public TaskDefinitionDto(String key, String name, List<BpmnModelElementInstanceDto> extensionElements) {
		super();
		this.key = key;
		this.name = name;
		this.extensionElements = extensionElements;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BpmnModelElementInstanceDto> getExtensionElements() {
		return extensionElements;
	}
	public void setExtensionElements(List<BpmnModelElementInstanceDto> extensionElements) {
		this.extensionElements = extensionElements;
	}

	

}
