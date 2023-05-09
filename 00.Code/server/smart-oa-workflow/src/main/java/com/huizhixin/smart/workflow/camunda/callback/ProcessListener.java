package com.huizhixin.smart.workflow.camunda.callback;

import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.service.ProcessDefinitionService;
import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.Map;

// 流程监听整个流程启动时监听1次，初始化信息
public class ProcessListener implements ExecutionListener {

    private org.camunda.bpm.engine.impl.el.FixedValue userid;

    private WorkFlowCommonServiceImpl workFlowCommonServiceImpl = null;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        String activityName = delegateExecution.getCurrentActivityName();
        if (workFlowCommonServiceImpl == null) {
            workFlowCommonServiceImpl = SpringContextUtils.getBean(WorkFlowCommonServiceImpl.class);
        }
        ProcessDefinition processDefinition = workFlowCommonServiceImpl.getProcessDefinitionByprocessDefinitionId(delegateExecution.getProcessDefinitionId());
        String workFlowName = processDefinition.getName();
        System.out.println(workFlowName + ":" + activityName);

        String eventName = delegateExecution.getEventName();

        listenerLogic(eventName);
        Map<String, Object> variablesMap = delegateExecution.getVariables();
        Object userObject = variablesMap.get(WorkFlowCommonServiceImpl.WORK_FLOW.startUser); // 流程发起人信息
        Object bizFormObject = variablesMap.get(WorkFlowCommonServiceImpl.WORK_FLOW.formInfo); // 表单信息
    }


    private void listenerLogic(String eventName) {
        if ("create".equals(eventName)) {
            System.out.println("create===========流程启动");
        } else if ("assigment".equals(eventName)) {
            System.out.println("assigment===========流程部署");
        } else if ("complete".equals(eventName)) {
            System.out.println("complete===========流程完成");
        } else if ("delete".equals(eventName)) {
            System.out.println("delete===========流程结束");
        } else if ("start".equals(eventName)) {
            System.out.println("start===========流程启动");
        } else if ("end".equals(eventName)) {
            System.out.println("end===========流程结束");
        }
    }

    public void setUserid(FixedValue userid) {
        this.userid = userid;
    }
}
