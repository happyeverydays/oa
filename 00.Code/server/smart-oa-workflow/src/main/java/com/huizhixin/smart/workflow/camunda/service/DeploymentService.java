package com.huizhixin.smart.workflow.camunda.service;
import java.util.List;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
import java.util.Map;

import org.camunda.bpm.engine.rest.dto.repository.DeploymentDto;
public interface DeploymentService {
	  DeploymentDto createDeployment(String deploymentName ,boolean enableDuplicateFiltering,boolean deployChangedOnly,String deploymentSource,String deploymentTenantId,List<Map<String,Object>> resources);
}
