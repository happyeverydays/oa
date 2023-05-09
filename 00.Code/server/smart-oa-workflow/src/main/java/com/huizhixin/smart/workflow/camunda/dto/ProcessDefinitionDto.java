package com.huizhixin.smart.workflow.camunda.dto;

import java.util.List;

public class ProcessDefinitionDto {

	private String id;
	private String key;
	private String name;
	private String description;
	private String category;
	private int version;
	private String versionTag;
	private List<TaskDefinitionDto> taskDefinitions;

	public ProcessDefinitionDto() {
		super();
	}

	public ProcessDefinitionDto(String id, String key, String name, String description, String category, int version, String versionTag, List<TaskDefinitionDto> taskDefinitions) {
		super();
		this.id = id;
		this.key = key;
		this.name = name;
		this.description = description;
		this.category = category;
		this.version = version;
		this.versionTag = versionTag;
		this.taskDefinitions = taskDefinitions;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVersionTag() {
		return versionTag;
	}

	public void setVersionTag(String versionTag) {
		this.versionTag = versionTag;
	}

	public List<TaskDefinitionDto> getTaskDefinitions() {
		return taskDefinitions;
	}

	public void setTaskDefinitions(List<TaskDefinitionDto> taskDefinitions) {
		this.taskDefinitions = taskDefinitions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
