package com.huizhixin.smart.workflow.camunda.vo;

import java.util.List;

/**
*
* @author wushuo <2807972772@qq.com>
*
*/
public class ProcessCompletionConditionVO {
	private Object data;
	private String controlType;
	private String placeholder;
	
	
	
	public ProcessCompletionConditionVO(Object data, String controlType,String placeholder) {
		super();
		this.data = data;
		this.controlType = controlType;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}


}
