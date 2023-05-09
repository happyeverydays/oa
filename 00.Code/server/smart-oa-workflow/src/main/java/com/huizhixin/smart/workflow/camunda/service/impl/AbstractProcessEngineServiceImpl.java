package com.huizhixin.smart.workflow.camunda.service.impl;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.rest.util.EngineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huizhixin.smart.workflow.camunda.mapper.ExecutionMapper;
import com.huizhixin.smart.workflow.camunda.mapper.HistoricProcessInstanceMapper;
import com.huizhixin.smart.workflow.camunda.mapper.TaskMapper;
import com.huizhixin.smart.workflow.camunda.service.DeploymentService;
import com.huizhixin.smart.workflow.camunda.service.HistoricProcessInstanceService;
import com.huizhixin.smart.workflow.camunda.service.ProcessDefinitionService;
import com.huizhixin.smart.workflow.camunda.service.ProcessInstanceService;
import com.huizhixin.smart.workflow.camunda.service.TaskExtService;
import com.huizhixin.smart.workflow.camunda.service.impl.ProcessInstanceServiceImpl;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@Service
public class AbstractProcessEngineServiceImpl {
	@Autowired
	ExecutionMapper executionMapper;
	@Autowired
	HistoricProcessInstanceMapper historicProcessInstanceMapper;
	@Autowired
	TaskMapper taskMapper;

	public ProcessEngine getProcessEngine(String engineName) {
		return EngineUtil.lookupProcessEngine(engineName);
	}

	public ProcessDefinitionService getProcessDefinitionService(String engineName) {
		ProcessDefinitionService processDefinitionService = new ProcessDefinitionServiceImpl(engineName);

		return processDefinitionService;
	}

	public ProcessInstanceService getProcessInstanceService(String engineName) {
		ProcessInstanceService processInstanceService = new ProcessInstanceServiceImpl(engineName, executionMapper);
		return processInstanceService;
	}
	
	  public HistoricProcessInstanceService getHistoryProcessInstanceService(String engineName) {
		  HistoricProcessInstanceService historicProcessInstanceService = new HistoricProcessInstanceServiceImpl(engineName, historicProcessInstanceMapper);
			return historicProcessInstanceService;
		  }

//	  public ExecutionService getExecutionService(String engineName) {
//	    
//	    return null;
//	  }
//
	public TaskExtService getTaskExtService(String engineName) {
		TaskExtService taskService = new TaskExtServiceImpl(engineName, taskMapper);
		return taskService;
	}

	public TaskService getTaskService(String engineName) {

		return getProcessEngine(engineName).getTaskService();
	}

//
	public IdentityService getIdentityService(String engineName) {
		return getProcessEngine(engineName).getIdentityService();
	}

	public RuntimeService getRuntimeService(String engineName) {

		return getProcessEngine(engineName).getRuntimeService();
	}

//
//	  public MessageService getMessageService(String engineName) {
//	 
//	    return null;
//	  }
//
//	  public VariableInstanceService getVariableInstanceService(String engineName) {
//	   
//	    return null;
//	  }
//
//	  public JobDefinitionService getJobDefinitionService(String engineName) {
//
//	    return null;
//	  }
//
//	  public JobService getJobService(String engineName) {
//	  	
//	  	return null;
//	  }
//
//	  public GroupService getGroupService(String engineName) {
//	   
//	    return null;
//	  }
//
//	  public UserService getUserService(String engineName) {
//	
//	    return null;
//	  }
//
//	  public AuthorizationService getAuthorizationService(String engineName) {
//	    	    return null;
//	  }
//
//	  public IncidentService getIncidentService(String engineName) {
//	  
//	    return null;
//	  }
//
//	  public HistoryService getHistoryService(String engineName) {
//	   
//	    return null;
//	  }
//
	public DeploymentService getDeploymentService(String engineName) {
		return new DeploymentServiceImpl(engineName);
	}

}
