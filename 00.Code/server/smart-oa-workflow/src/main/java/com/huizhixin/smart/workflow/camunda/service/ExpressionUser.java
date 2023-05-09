package com.huizhixin.smart.workflow.camunda.service;

import java.util.Collection;
import java.util.List;

import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.VariableScope;

public interface ExpressionUser {
	public String assignee(String type, String rule, String value, VariableScope variableScope);
	public Collection<String> candidate(String type, String rule, String value, VariableScope variableScope);
	public Collection<String> group(String type, String rule, String value, VariableScope variableScope);
	public Collection<String> collection(String type, String rule, String value, VariableScope variableScope);
}
