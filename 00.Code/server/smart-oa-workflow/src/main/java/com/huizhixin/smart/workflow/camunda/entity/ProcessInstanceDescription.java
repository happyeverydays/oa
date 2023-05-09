package com.huizhixin.smart.workflow.camunda.entity;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class ProcessInstanceDescription {
	private String title;
	private String formKey;
	public ProcessInstanceDescription(String title, String formKey) {
		super();
		this.title = title;
		this.formKey = formKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}


}
