package com.huizhixin.smart.workflow.camunda.callback;

import com.huizhixin.smart.workflow.camunda.service.impl.WorkFlowCommonServiceImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.Map;

public class NodeListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateExecution) {
        String eventName = delegateExecution.getEventName();
        listenerLogic(eventName);

        Map<String, Object> variablesMap = delegateExecution.getVariables();
        Object userObject = variablesMap.get(WorkFlowCommonServiceImpl.WORK_FLOW.startUser); // 流程发起人信息
        Object bizFormObject = variablesMap.get(WorkFlowCommonServiceImpl.WORK_FLOW.formInfo); // 表单信息
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        listenerLogic(eventName);
        Map<String, Object> variablesMap = delegateExecution.getVariables();
        delegateExecution.getProcessBusinessKey();//业务主键打样
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
}
