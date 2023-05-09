package com.huizhixin.smart.workflow.camunda.even;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class ListenerPlugin extends AbstractProcessEnginePlugin {

	@Override
	public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();
		if (preParseListeners == null) {
			preParseListeners = new ArrayList<BpmnParseListener>();
		}
		preParseListeners.add(new WorkflowBpmnParseListener()); // 添加自定义监听。
		processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
	}

}
