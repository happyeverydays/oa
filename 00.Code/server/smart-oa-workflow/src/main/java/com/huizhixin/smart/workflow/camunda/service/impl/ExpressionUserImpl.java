package com.huizhixin.smart.workflow.camunda.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.mapper.ProcessUserMapper;
import com.huizhixin.smart.workflow.camunda.service.ExpressionUser;
import com.huizhixin.smart.workflow.camunda.service.WorkFlowCommonService;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl.WORK_FLOW;

@Service("expressionUser")
public class ExpressionUserImpl implements ExpressionUser {
	@Autowired
	private ProcessUserMapper processUserMapper;
	@Autowired
	private WorkFlowCommonService workFlowCommonService;

	@Override
	public String assignee(String type, String rule, String value, VariableScope variableScope) {
		System.out.println("a812b886755043f58303c8a72821e2f5==========");
		return "a812b886755043f58303c8a72821e2f5"; // TODO
	}

	@Override
	public Collection<String> candidate(String type, String rule, String value, VariableScope variableScope) {
//		if (variableScope instanceof ExecutionEntity) {
//			ExecutionEntity ExecutionEntity = (ExecutionEntity) variableScope;
//			ExecutionEntity.getProcessBusinessKey();
//			ExecutionEntity.getProcessInstanceId();
//			LoginUser startUser = (LoginUser) ExecutionEntity.getVariable(WORK_FLOW.startUser);
//
//		}

		LoginUser startUser = (LoginUser) variableScope.getVariable(WORK_FLOW.startUser);
		Map<String, Object> variables = variableScope.getVariables();
		Collection<String> retUsers = new ArrayList<String>();

		// 1.角色
		if (StringUtils.equals(type, "role")) {
			if (StringUtils.equals(rule, "org")) {// 本部门
				String roleIds = value;
				List<String> roleListresult = Arrays.asList(roleIds.split(","));
				List<String> userids = processUserMapper.getUserIds(startUser.getDepartIdList(), roleListresult);
				retUsers = userids;
			} else if (StringUtils.equals(rule, "porg")) {// 父部门

			} else if (StringUtils.equals(rule, "rorg")) { // 集团

			} else {
			}

		}
		// 2.部门
		else if (StringUtils.equals(type, "position")) {
			if (StringUtils.equals(rule, "org")) {// 本部门

			} else if (StringUtils.equals(rule, "porg")) {// 父部门

			} else if (StringUtils.equals(rule, "rorg")) { // 集团

			} else {
			}

		}
		// 3.自定义sql
		else if (StringUtils.equals(type, "customize")) {
			String varSql = value;
			retUsers = workFlowCommonService.getOneFieldValuesOfvarSql(variables, varSql);
		} else {

		}

		return retUsers;
	}

	@Override
	public Collection<String> group(String type, String rule, String value, VariableScope variableScope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> collection(String type, String rule, String value, VariableScope variableScope) {
		// TODO Auto-generated method stub
		return null;
	}

}
