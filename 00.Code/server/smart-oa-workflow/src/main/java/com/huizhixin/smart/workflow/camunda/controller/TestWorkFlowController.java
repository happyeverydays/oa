package com.huizhixin.smart.workflow.camunda.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.bpmn.diagram.ProcessDiagramLayoutFactory;
import org.camunda.bpm.engine.repository.Deployment;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 数据字典
 * @Author: jeecg-boot
 * @Date: 2019-08-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/test/workflow")
@Slf4j
public class TestWorkFlowController {


	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	public RepositoryService repositoryService;

	@RequestMapping("/demo3")
	public ModelAndView demo3(ModelAndView modelAndView) {
		modelAndView.setViewName("demo3");
		List<String> userList = new ArrayList<String>();
		userList.add("admin");
		userList.add("user1");
		userList.add("user2");
		log.info("--------------test--------------");

		log.info("processEngine.getName()=" + processEngine.getName());
		// repositoryService.mod
		String deploymentId = "";
		String resourceName = "";
		String resource = "";
		Deployment deployment = repositoryService.createDeployment()// 创建部署对象
				.name("helloworld演示")// 声明流程的名称
				.addClasspathResource("diagrams/helloworld.bpmn")// 加载资源文件，一次只能加载一个文件
				.addClasspathResource("diagrams/helloworld.png")//
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());// 1
		System.out.println("部署时间：" + deployment.getDeploymentTime());

		modelAndView.addObject("userList", userList);
		return modelAndView;
	}

	/**
	 * 分页列表查询
	 * 
	 * @param dict
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation("list方法")
	@GetMapping(value = "/list")
	public Object queryPageList(Object dict,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
		
		
 
	return null;
	}

}
