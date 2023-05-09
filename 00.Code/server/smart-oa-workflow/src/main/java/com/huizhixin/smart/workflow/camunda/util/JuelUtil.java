package com.huizhixin.smart.workflow.camunda.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import org.apache.commons.lang3.math.NumberUtils;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class JuelUtil {

	/**
	 * 
	 * TODO To describe the functionality of this method.
	 *
	 * @author wushuo 
	 * @param %param name% %param description%
	 * @return
	 */
	public static String getStringByELAndFormData(String el, Map formData) {
		String result = "";
		try {
			ExpressionFactory factory = new ExpressionFactoryImpl();

			SimpleContext context = new SimpleContext();
			for (Object k : formData.keySet()) {
				if (formData.get(k) != null) {
					context.setVariable(k.toString(), factory.createValueExpression(formData.get(k), formData.get(k).getClass()));
				}
			}
			ValueExpression e = factory.createValueExpression(context, el, String.class);
			result = (String) e.getValue(context);
		} catch (Exception e) {
			e.printStackTrace();
			result = el;
			throw e;
		}

		return result;
	}

	/**
	 * 设置某个工具类所有的METHOD
	 * 
	 * @param context
	 * @param dateUtil
	 */
	private static void MethodSet(SimpleContext context, Class dateUtil, String prefix) {
		Method[] methods = dateUtil.getMethods(); // 用这个方法会有个bug，${du:getYear()} 这种表达式报错。原因：getYear有重名的函数。
//       System.out.println(methods2[0].toString());
//       System.identityHashCode(methods2[0]);
//       Method[] methods=  dateUtil.getDeclaredMethods(); // 20190419
//       System.out.println(methods[0].toString());
//       System.identityHashCode(methods[0]);
		if (methods != null) {
			for (Method method : methods) {
				context.setFunction(prefix, method.getName(), method);
			}
		}
	}

	public static void main(String[] args) {
		// System.out.println(getPropertyTwoLevel("${ccc + cd}"));
		if (NumberUtils.isNumber("1245")) {
			System.out.println("nununu");
		}
		Map formData = new HashMap<String, Object>();
		formData.put("department", "123");
		formData.put("company", "456");
		formData.put("TotalDays", "33");

		Map userEntity = new HashMap<String, Object>();
		userEntity.put("deparment", "001");

		formData.put("loginUser", userEntity);
		try {

			String express = " Department like '${loginUser.deparment}%' ";
			System.out.println(getStringByELAndFormData(express, formData));

			// System.out.println(getStringByELAndFormData("${TotalDays == 33.0}",
			// formData));
			System.out.println(getStringByELAndFormData("${longuser.department},${company},25693366645694346", formData));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
