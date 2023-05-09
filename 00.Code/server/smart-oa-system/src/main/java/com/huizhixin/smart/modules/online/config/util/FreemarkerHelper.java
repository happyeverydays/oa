package com.huizhixin.smart.modules.online.config.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreemarkerHelper {
	private static final Logger a = LoggerFactory.getLogger(FreemarkerHelper.class);
	private static Configuration b;

	public static String a(String var0, String var1, Map<String, Object> var2) {
		try {
			StringWriter var3 = new StringWriter();
			Template var4 = null;
			var4 = b.getTemplate(var0, var1);
			var4.process(var2, var3);
			return var3.toString();
		} catch (Exception var5) {
			a.error(var5.getMessage(), var5);
			return var5.toString();
		}
	}

	public static String a(String var0, Map<String, Object> var1) {
		return a(var0, "utf-8", var1);
	}

	public static void main(String[] args) {
		String var1 = a("com/huizhixin/smart/modules/online/config/engine/tableTemplate.ftl", (Map) null);
		System.out.println(var1);
	}

	static {
		b = new Configuration(Configuration.VERSION_2_3_28);
		b.setNumberFormat("0.#####################");
		b.setClassForTemplateLoading(FreemarkerHelper.class, "/");
	}
}