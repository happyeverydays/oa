package com.huizhixin.smart.workflow.camunda.entity;

import lombok.Data;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Data
public class TaskDescription {
	private String title;
	private String formTableName;
	private String formKey;
	private String businessKey;

	public TaskDescription(String title, String formTableName, String formKey, String businessKey) {
		super();
		this.formTableName = formTableName;
		this.title = title;
		this.formKey = formKey;
		this.businessKey = businessKey;
	}
}
