package com.huizhixin.smart.workflow.camunda.el;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.delegate.ExpressionGetInvocation;
import org.camunda.bpm.engine.impl.delegate.ExpressionSetInvocation;
import org.camunda.bpm.engine.impl.el.Expression;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.javax.el.ELContext;
import org.camunda.bpm.engine.impl.javax.el.ELException;
import org.camunda.bpm.engine.impl.javax.el.MethodNotFoundException;
import org.camunda.bpm.engine.impl.javax.el.PropertyNotFoundException;
import org.camunda.bpm.engine.impl.javax.el.ValueExpression;
import org.camunda.bpm.engine.impl.juel.AstDot;
import org.camunda.bpm.engine.impl.juel.AstEval;
import org.camunda.bpm.engine.impl.juel.AstMethod;
import org.camunda.bpm.engine.impl.juel.AstNode;
import org.camunda.bpm.engine.impl.juel.AstParameters;
import org.camunda.bpm.engine.impl.juel.AstProperty;
import org.camunda.bpm.engine.impl.juel.ExpressionNode;
import org.camunda.bpm.engine.impl.juel.Node;
import org.camunda.bpm.engine.impl.juel.TreeValueExpression;

import com.huizhixin.smart.common.util.ReflectUtil;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.util.JuelUtil;
import com.sun.javafx.binding.SelectBinding.AsInteger;
import com.sun.javafx.binding.SelectBinding.AsString;

import javassist.expr.Instanceof;

/**
*
* @author wushuo <2807972772@qq.com>
*
*/
public class JuelExpression implements Expression {

	protected String expressionText;
	protected ValueExpression valueExpression;
	protected ExpressionManager expressionManager;

	public JuelExpression(ValueExpression valueExpression, ExpressionManager expressionManager, String expressionText) {
		this.valueExpression = valueExpression;
		this.expressionManager = expressionManager;
		this.expressionText = expressionText;
	}

	public Object getValue(VariableScope variableScope) {
		return getValue(variableScope, null);
	}

	public Object getValue(VariableScope variableScope, BaseDelegateExecution contextExecution) {
		ELContext elContext = expressionManager.getElContext(variableScope);
		boolean isCustomerizeExpress = false;
		Object ret = null;
		try {
			System.out.println(valueExpression);
			if (valueExpression instanceof TreeValueExpression) {
				TreeValueExpression treeValueExpression = (TreeValueExpression) valueExpression;
				ExpressionNode node = (ExpressionNode) ReflectUtil.getFieldValue(treeValueExpression, "node");
				Node childNode = node.getChild(0);
				if (childNode instanceof AstMethod) {
					AstProperty property = (AstProperty) ReflectUtil.getFieldValue(childNode, "property");
					AstParameters params = (AstParameters) ReflectUtil.getFieldValue(childNode, "params");
					if (property instanceof AstDot) {
						AstDot astDotProperty = (AstDot) property;
						AstNode prefix = (AstNode) ReflectUtil.getFieldValue(astDotProperty, "prefix");
						String beanName = prefix.toString();
						if (StringUtils.equals(beanName, "expressionUser")) {
							isCustomerizeExpress = true;// 是自定义的表达式。
							String methdName = (String) ReflectUtil.getFieldValue(astDotProperty, "property");
							Object instanceObject = SpringContextUtils.getBean(beanName);
							List<AstNode> parameterList = (List<AstNode>) ReflectUtil.getFieldValue(params, "nodes");
							Object[] parameterValues = new Object[parameterList.size() + 1];
							Class[] parameterTypes = new Class[parameterList.size() + 1];
							int parameterIndex = 0;
							for (AstNode astNode : parameterList) {
								Class parameterType = null;
								Object parameterValue = ReflectUtil.getFieldValue(astNode, "value");
								if (parameterValue instanceof String) {
									parameterType = String.class;
								}

								if (parameterValue instanceof Byte) {
									parameterType = Byte.class;
								}

								if (parameterValue instanceof Character) {
									parameterType = Character.class;
								}

								else if (parameterValue instanceof Short) {
									parameterType = Short.class;
								}

								else if (parameterValue instanceof Integer) {
									parameterType = Integer.class;
								}

								else if (parameterValue instanceof Long) {
									parameterType = Long.class;
								}

								else if (parameterValue instanceof Float) {
									parameterType = Float.class;

								} else if (parameterValue instanceof Double) {

									parameterType = Double.class;
								}

								else {
									parameterType = String.class;
								}
								System.out.println(parameterValue);
								System.out.println(parameterType);
								parameterValues[parameterIndex] = parameterValue;
								parameterTypes[parameterIndex] = parameterType;
								parameterIndex++;
							}
							parameterValues[parameterIndex] = variableScope;
							parameterTypes[parameterIndex] = VariableScope.class;
							ret = ReflectUtil.executeMethod(instanceObject, methdName, parameterTypes, parameterValues);
						}
					}
				}
			}

			if (!isCustomerizeExpress) { // 如果不是自定义。
				ExpressionGetInvocation invocation = new ExpressionGetInvocation(valueExpression, elContext, contextExecution);
				Context.getProcessEngineConfiguration().getDelegateInterceptor().handleInvocation(invocation);
				ret = invocation.getInvocationResult();
			}

			return ret;
		} catch (PropertyNotFoundException pnfe) {
			throw new ProcessEngineException("Unknown property used in expression: " + expressionText + ". Cause: " + pnfe.getMessage(), pnfe);
		} catch (MethodNotFoundException mnfe) {
			throw new ProcessEngineException("Unknown method used in expression: " + expressionText + ". Cause: " + mnfe.getMessage(), mnfe);
		} catch (ELException ele) {
			throw new ProcessEngineException("Error while evaluating expression: " + expressionText + ". Cause: " + ele.getMessage(), ele);
		} catch (Exception e) {
			throw new ProcessEngineException("Error while evaluating expression: " + expressionText + ". Cause: " + e.getMessage(), e);
		}
	}

	public void setValue(Object value, VariableScope variableScope) {
		setValue(value, variableScope, null);
	}

	public void setValue(Object value, VariableScope variableScope, BaseDelegateExecution contextExecution) {
		ELContext elContext = expressionManager.getElContext(variableScope);
		try {
			ExpressionSetInvocation invocation = new ExpressionSetInvocation(valueExpression, elContext, value, contextExecution);
			Context.getProcessEngineConfiguration().getDelegateInterceptor().handleInvocation(invocation);
		} catch (Exception e) {
			throw new ProcessEngineException("Error while evaluating expression: " + expressionText + ". Cause: " + e.getMessage(), e);
		}
	}

	@Override
	public String toString() {
		if (valueExpression != null) {
			return valueExpression.getExpressionString();
		}
		return super.toString();
	}

	@Override
	public boolean isLiteralText() {
		return valueExpression.isLiteralText();
	}

	public String getExpressionText() {
		return expressionText;
	}
}
