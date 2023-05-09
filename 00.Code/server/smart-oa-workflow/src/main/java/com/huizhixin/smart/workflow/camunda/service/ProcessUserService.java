package com.huizhixin.smart.workflow.camunda.service;

import com.huizhixin.smart.workflow.camunda.entity.ProcessUser;

import java.util.List;
import java.util.Map;

public interface ProcessUserService {

	List<ProcessUser> getProcessAssigneeList();

	Map<String, Object> getProcessCandidateList();

	Map<String, Object> getCollectionUserList();
	
	Map<String, Object> getCompletionConditionList();
	
	
	

}
