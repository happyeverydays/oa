package com.huizhixin.smart.modules.online.cgform.enums;

public enum CgformValidPatternEnum {
	ONLY("only", "only", ""), NUM6_16("n6-16", "^[0-9]{6,16}$", "请输入6-16位的数字"), STRING6_16("*6-16", "^(.*?){6,16}$",
			"请输入6-16位任意字符"), LETTER6_18("s6-18", "^[a-zA-Z]{6,18}$", "请输入6-18位字母"), URL("url",
					"^((ht|f)tps?):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:\\/~+#]*[\\w\\-@?^=%&\\/~+#])?$",
					"请输入正规的网址"), MOBILE("m", "^1[3456789]\\d{9}$", "请输入正规的手机号码"), POSTAL("p", "^[1-9][0-9]{5}",
							"请输入正规的邮政编码"), LETTER("s", "^[a-zA-Z]*$", "请输入字母"), NUMBER("n", "^[0-9]*$",
									"请输入数字"), INTEGER("z", "z", "请输入整数"), NOTNULL("*", "", ""), EMAIL("e",
											"^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", "请输入正确格式的邮箱地址");

	String type;
	String pattern;
	String msg;

	private CgformValidPatternEnum(String type, String pattern, String msg) {
		this.pattern = pattern;
		this.msg = msg;
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static CgformValidPatternEnum getPatternInfoByType(String type) {
		CgformValidPatternEnum[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			CgformValidPatternEnum var4 = var1[var3];
			if (var4.type.equals(type)) {
				return var4;
			}
		}

		return null;
	}
}