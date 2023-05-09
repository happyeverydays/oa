package com.huizhixin.smart.workflow.camunda.entity;

import lombok.Data;

@Data
public class ProcessUser {
	private String id;
	// private String code;
	private String name;
	private String param;

	public ProcessUser(String id, String code, String name, String param) {
		this.id = id;
		// this.code = code;
		this.name = name;
		this.param = param;
	}

	public ProcessUser() {
		super();
	}

}
