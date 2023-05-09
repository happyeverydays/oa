package com.huizhixin.smart.workflow.camunda.even;

import java.util.List;

import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.camunda.bpm.engine.impl.variable.VariableDeclaration;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class WorkflowBpmnParseListener implements BpmnParseListener {

	@Override
	public void parseProcess(Element processElement, ProcessDefinitionEntity processDefinition) {
		System.out.println("=======parseProcess=====================================");
	}

	@Override
	public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {

		ActivityBehavior activityBehavior = startEventActivity.getActivityBehavior();
		System.out.println("=======parseStartEvent=====================================" + startEventActivity.getProperties().toString());
	}

	@Override
	public void parseTask(Element taskElement, ScopeImpl scope, ActivityImpl activity) {
		System.out.println("=======parseTask=====================================" + activity.getProperties().toString());
	}

	@Override
	public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
		System.out.println("=======parseUserTask=====================================" + activity.getProperties().toString());
		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		if (activityBehavior instanceof UserTaskActivityBehavior) {
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			userTaskActivityBehavior.getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_CREATE, WorkflowTaskListener.getInstance());
			userTaskActivityBehavior.getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_COMPLETE, WorkflowTaskListener.getInstance());
			userTaskActivityBehavior.getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_ASSIGNMENT, WorkflowTaskListener.getInstance());
			userTaskActivityBehavior.getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_DELETE, WorkflowTaskListener.getInstance());
		}

	}

	@Override
	public void parseManualTask(Element manualTaskElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseEndEvent(Element endEventElement, ScopeImpl scope, ActivityImpl activity) {
		System.out.println("=======parseEndEvent=====================================" + activity.getProperties().toString());
	}

	@Override
	public void parseBoundaryTimerEventDefinition(Element timerEventDefinition, boolean interrupting, ActivityImpl timerActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBoundaryErrorEventDefinition(Element errorEventDefinition, boolean interrupting, ActivityImpl activity, ActivityImpl nestedErrorEventActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseMultiInstanceLoopCharacteristics(Element activityElement, Element multiInstanceLoopCharacteristicsElement, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseIntermediateTimerEventDefinition(Element timerEventDefinition, ActivityImpl timerActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseRootElement(Element rootElement, List<ProcessDefinitionEntity> processDefinitions) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseReceiveTask(Element receiveTaskElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseIntermediateSignalCatchEventDefinition(Element signalEventDefinition, ActivityImpl signalActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseIntermediateMessageCatchEventDefinition(Element messageEventDefinition, ActivityImpl nestedActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBoundarySignalEventDefinition(Element signalEventDefinition, boolean interrupting, ActivityImpl signalActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseEventBasedGateway(Element eventBasedGwElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseTransaction(Element transactionElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseCompensateEventDefinition(Element compensateEventDefinition, ActivityImpl compensationActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseIntermediateThrowEvent(Element intermediateEventElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseIntermediateCatchEvent(Element intermediateEventElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBoundaryEvent(Element boundaryEventElement, ScopeImpl scopeElement, ActivityImpl nestedActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBoundaryMessageEventDefinition(Element element, boolean interrupting, ActivityImpl messageActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBoundaryEscalationEventDefinition(Element escalationEventDefinition, boolean interrupting, ActivityImpl boundaryEventActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBoundaryConditionalEventDefinition(Element element, boolean interrupting, ActivityImpl conditionalActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseIntermediateConditionalEventDefinition(Element conditionalEventDefinition, ActivityImpl conditionalActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseConditionalStartEventForEventSubprocess(Element element, ActivityImpl conditionalActivity, boolean interrupting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseExclusiveGateway(Element exclusiveGwElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseInclusiveGateway(Element inclusiveGwElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseParallelGateway(Element parallelGwElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseScriptTask(Element scriptTaskElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseBusinessRuleTask(Element businessRuleTaskElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseSubProcess(Element subProcessElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseCallActivity(Element callActivityElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseProperty(Element propertyElement, VariableDeclaration variableDeclaration, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseSequenceFlow(Element sequenceFlowElement, ScopeImpl scopeElement, TransitionImpl transition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseSendTask(Element sendTaskElement, ScopeImpl scope, ActivityImpl activity) {
		// TODO Auto-generated method stub

	}
}
