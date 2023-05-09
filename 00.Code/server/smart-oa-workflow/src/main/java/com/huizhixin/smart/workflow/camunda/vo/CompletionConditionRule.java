package com.huizhixin.smart.workflow.camunda.vo;

public class CompletionConditionRule {
	private String key;
	private String value;
	private String text;

	public CompletionConditionRule() {
		super();
	}

	public CompletionConditionRule(String key, String value, String text) {
		super();
		this.key = key;
		this.value = value;
		this.text = text;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
