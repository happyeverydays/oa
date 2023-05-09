package com.huizhixin.smart.workflow.camunda.service.impl;

import com.huizhixin.smart.workflow.camunda.entity.ProcessUser;
import com.huizhixin.smart.workflow.camunda.mapper.ProcessUserMapper;
import com.huizhixin.smart.workflow.camunda.service.ProcessUserService;
import com.huizhixin.smart.workflow.camunda.vo.CompletionConditionRule;
import com.huizhixin.smart.workflow.camunda.vo.ProcessCandidateVO;
import com.huizhixin.smart.workflow.camunda.vo.ProcessCompletionConditionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("processUserService")
public class ProcessUserServiceImpl implements ProcessUserService {
	@Autowired
	private ProcessUserMapper processUserMapper;

	@Override
	public List<ProcessUser> getProcessAssigneeList() {
		List<ProcessUser> processUserList = null;
		processUserList = processUserMapper.getProcessUserList();
		return processUserList;

	}

	@Override
	public Map<String, Object> getProcessCandidateList() {
		List<ProcessUser> staticGroups = processUserMapper.getProcessGroupList();
		List<ProcessUser> processUserList = processUserMapper.getProcessUserList();
		List<ProcessUser> processPositionList = processUserMapper.getProcessGroupList(); // 岗位 // TODO
		Map<String, Object> groups = new LinkedHashMap<String, Object>();
		List<String> commonrules = new ArrayList<String>();
		commonrules.add("org|本部门");
		commonrules.add("porg|父部门");
		commonrules.add("rorg|集团");

		groups.put("user|多用户|", new ProcessCandidateVO(commonrules, processUserList, "checkbox", ""));
		groups.put("role|角色|${expressionUser.candidate('role','#rule#','')}", new ProcessCandidateVO(commonrules, staticGroups, "checkbox", ""));
		groups.put("position|岗位|${expressionUser.candidate('position','#rule#','')}", new ProcessCandidateVO(commonrules, processPositionList, "checkbox", ""));
		groups.put("customize|自定义|${expressionUser.candidate('customize','#rule#','')}", new ProcessCandidateVO(null, null, "textarea", "select id userId from sys_user where org_code = ${startUser.orgCode} "));
		return groups;
	}

	/**
	 * 会签
	 **/
	public Map<String, Object> getCollectionUserList() {

		return getProcessCandidateList();
	}

	@Override
	public Map<String, Object> getCompletionConditionList() {
		Map<String, Object> completionCondition = new LinkedHashMap<String, Object>();

		Map<String, Object> commonCondtion = new LinkedHashMap<String, Object>();
		List<CompletionConditionRule>  list= new ArrayList<CompletionConditionRule>(); 
		
		list.add(new CompletionConditionRule("proportion_100", "${nrOfCompletedInstances/nrOfInstances >= 1}", "全票审批"));
		list.add(new CompletionConditionRule("proportion_060", "${nrOfCompletedInstances/nrOfInstances >= 0.6}", "半票以上审批"));

		list.add(new CompletionConditionRule("number_1", "${nrOfCompletedInstances >= 1}", "一人及以上审批"));
		list.add(new CompletionConditionRule("number_2", "${nrOfCompletedInstances >= 2}", "两人及以上审批"));
		list.add(new CompletionConditionRule("number_3", "${nrOfCompletedInstances >= 3}", "三人及以上审批"));
		list.add(new CompletionConditionRule("number_4", "${nrOfCompletedInstances >= 4}", "四人及以上审批"));
		list.add(new CompletionConditionRule("number_5", "${nrOfCompletedInstances >= 5}", "五人及以上审批"));
		list.add(new CompletionConditionRule("number_6", "${nrOfCompletedInstances >= 6}", "六人及以上审批"));

		list.add(new CompletionConditionRule("proportion_030", "${nrOfCompletedInstances/nrOfInstances >= 0.3333}", "1/3票以上审批"));
		list.add(new CompletionConditionRule("proportion_025", "${nrOfCompletedInstances/nrOfInstances >= 0.25}", "1/4票以上审批"));
		list.add(new CompletionConditionRule("proportion_020", "${nrOfCompletedInstances/nrOfInstances >= 0.2}", "1/5票以上审批"));

		list.add(new CompletionConditionRule("proportion_066", "${nrOfCompletedInstances/nrOfInstances >= 0.6666}", "2/3票以上审批"));
		list.add(new CompletionConditionRule("proportion_040", "${nrOfCompletedInstances/nrOfInstances >= 0.4}", "2/5票以上审批"));
		list.add(new CompletionConditionRule("proportion_080", "${nrOfCompletedInstances/nrOfInstances >= 0.8}", "4/5票以上审批"));

		completionCondition.put("commonCondition|常用规则", new ProcessCompletionConditionVO(list, "select", null));
		completionCondition.put("expressionCondition|表达式", new ProcessCompletionConditionVO(null, "input", "el表达式"));

		return completionCondition;
	}

}
