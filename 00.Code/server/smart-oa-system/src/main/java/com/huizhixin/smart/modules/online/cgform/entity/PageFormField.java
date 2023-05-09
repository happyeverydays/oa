package com.huizhixin.smart.modules.online.cgform.entity;

import java.util.List;
import java.util.Map;

import com.huizhixin.smart.common.system.vo.DictModel;

public class PageFormField {
	private String label;
	private String icon;
	private String placeholder;
	private boolean required;
	private String defaultValue;
	private String dataType;
	private String width = "100%";
	private String pattern;
	private String model;
	private List<FieldRule> rules;
	private List<DictModel> optinons;
	private String type;
	private String key;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<FieldRule> getRules() {
		return rules;
	}
	public void setRules(List<FieldRule> rules) {
		this.rules = rules;
	}
	public List<DictModel> getOptinons() {
		return optinons;
	}
	public void setOptinons(List<DictModel> optinons) {
		this.optinons = optinons;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
