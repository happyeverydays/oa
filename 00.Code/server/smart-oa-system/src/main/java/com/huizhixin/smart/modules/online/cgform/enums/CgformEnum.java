package com.huizhixin.smart.modules.online.cgform.enums;

public enum CgformEnum {
	ONE("one", "/jeecg/code-template-online/one"), MANY("many", "/jeecg/code-template-online/onetomany"), TREE("tree",
			"/jeecg/code-template-online/tree");

	String type;
	String templatePath;

	public static String getTemplatePathByConfig(int tableType, String isTree) {
		return getCgformEnumByConfig(tableType, isTree).templatePath;
	}

	private CgformEnum(String type, String templatePath) {
		this.type = type;
		this.templatePath = templatePath;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplatePath() {
		return this.templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public static CgformEnum getCgformEnumByConfig(int tableType, String isTree) {
		if (tableType == 1) {
			return "Y".equals(isTree) ? TREE : ONE;
		} else {
			return MANY;
		}
	}
}