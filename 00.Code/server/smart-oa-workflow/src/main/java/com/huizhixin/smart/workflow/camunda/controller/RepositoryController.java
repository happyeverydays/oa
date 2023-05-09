package com.huizhixin.smart.workflow.camunda.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
@RestController
@RequestMapping("/workflow")
@Slf4j
public class RepositoryController {
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
	public Object queryPageList(Object dict, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {

		return null;
	}

}
