package com.huizhixin.smart.workflow.camunda.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Data
public class FormInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String titleExp;
	private String relationCode;
	private String formTableName;
	private String formKey;
	private String businessKey;

	public FormInfo() {
		super();
	}

	public FormInfo(String titleExp, String relationCode, String formTableName, String formKey, String businessKey) {
		super();
		this.titleExp = titleExp;
		this.relationCode = relationCode;
		this.formTableName = formTableName;
		this.formKey = formKey;
		this.businessKey = businessKey;
	}

}
