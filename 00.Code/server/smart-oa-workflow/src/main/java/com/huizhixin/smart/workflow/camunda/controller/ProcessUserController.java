package com.huizhixin.smart.workflow.camunda.controller;

import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.workflow.camunda.entity.ProcessUser;
import com.huizhixin.smart.workflow.camunda.service.ProcessUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/**
*
* @author wushuo <2807972772@qq.com>
*
*/
@RestController
@RequestMapping("/workflow/processUser")
@Slf4j
public class ProcessUserController {

	@Autowired
    private ProcessUserService processUserService;

    @ApiOperation(value = "工作流获取处理人")
    @GetMapping(value = "/getProcessAssignees")
    public Result<List<ProcessUser>> getProcessUserList() {
        Result<List<ProcessUser>> result = new Result<>();
        List<ProcessUser> userlist = processUserService.getProcessAssigneeList();
        result.setResult(userlist);
        result.setSuccess(true);
        return result;
    }
    
    @ApiOperation(value = "工作流获取用候选人")
    @GetMapping(value = "/getProcessCandidates")
    public Result<Map> getProcessCandidates() {
        Result<Map> result = new Result<>();
        Map<String,Object> groups = processUserService.getProcessCandidateList();
        result.setResult(groups);
        result.setSuccess(true);
        return result;
    }
    

    
    @ApiOperation(value = "工作流会签获得用户下拉数据")
    @GetMapping(value = "/getCollections")
    public Result<Map> getCollectionUserList() {
        Result<Map> result = new Result<>();
        Map<String, Object> userlist = processUserService.getCollectionUserList();
        result.setResult(userlist);
        result.setSuccess(true);
        return result;
    }
    
    @ApiOperation(value = "工作流会签规则条件")
    @GetMapping(value = "/getCompletionConditions")
    public Result<Map> getCompletionConditionList() {
        Result<Map> result = new Result<>();
        Map<String, Object> userlist = processUserService.getCompletionConditionList();
        result.setResult(userlist);
        result.setSuccess(true);
        return result;
    } 
    
    
}
