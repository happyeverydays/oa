package com.huizhixin.smart.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.camunda.bpm.application.impl.EmbeddedProcessApplication;
import org.camunda.bpm.application.impl.event.ProcessApplicationEventListenerPlugin;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.application.ProcessApplicationManager;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.core.instance.CoreExecution;
import org.camunda.bpm.engine.impl.core.model.Properties;
import org.camunda.bpm.engine.impl.db.DbIdGenerator;
import org.camunda.bpm.engine.impl.db.sql.DbSqlSessionFactory;
import org.camunda.bpm.engine.impl.event.CompensationEventHandler;
import org.camunda.bpm.engine.impl.event.ConditionalEventHandler;
import org.camunda.bpm.engine.impl.event.EventHandler;
import org.camunda.bpm.engine.impl.event.EventHandlerImpl;
import org.camunda.bpm.engine.impl.event.EventType;
import org.camunda.bpm.engine.impl.event.SignalEventHandler;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.SpringProcessEngineServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.workflow.camunda.config.SpringProcessEngineCustomConfiguration;
import com.huizhixin.smart.workflow.camunda.even.ListenerPlugin;
import com.huizhixin.smart.workflow.camunda.el.SpringExpressionManager;
import com.huizhixin.smart.workflow.camunda.util.MyWorkflowIdentityServiceImpl;

@Configuration
@Import(SpringProcessEngineServicesConfiguration.class)
public class ProcessEngineConfig {
	private static final String BPMN_PATH = "diagrams/";

	@Value("${camunda.bpm.history-level:full}")
	private String historyLevel;
	// add more configuration here
	// ---------------------------
	// configure data source via application.properties
	@Autowired
	private DataSource dataSource;

	@Bean
	public SpringProcessEngineCustomConfiguration processEngineConfiguration() throws IOException {
		SpringProcessEngineCustomConfiguration config = new SpringProcessEngineCustomConfiguration();
		config.setDataSource(dataSource);
		config.setDatabaseSchemaUpdate("true");
		config.setTransactionManager(transactionManager());
		config.setHistory(historyLevel);
		config.setJobExecutorActivate(true);
		config.setMetricsEnabled(false);
		config.setIdGenerator(new StrongUuidGenerator()); //
		config.setDbIdentityUsed(false);//
		// config.setPasswordEncryptor(new MyWorkflowPasswordEncryptor());
		// config.setPasswordManager(new
		// MyWorkflowPasswordManager(config.getPasswordEncryptor(),Collections.emptyList()));
		config.setIdentityService(new MyWorkflowIdentityServiceImpl()); // 内置管理端免密码

		ProcessEnginePlugin processEnginePlugin = new ListenerPlugin();
		List<ProcessEnginePlugin> processEnginePlugins = new ArrayList();
		processEnginePlugins.add(processEnginePlugin);
		processEnginePlugins.add(new ProcessApplicationEventListenerPlugin());
		config.setProcessEnginePlugins(processEnginePlugins);
		
		config.setExpressionManager(new SpringExpressionManager(SpringContextUtils.getApplicationContext(),config.getBeans()));
		
		// deploy all processes from folder 'processes'
		// Resource[] resources =
		// resourceLoader.getResources("classpath:/"+BPMN_PATH+"/*.bpmn");
		// config.setDeploymentResources(resources);

		return config;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public ProcessEngineFactoryBean processEngine() throws IOException {
		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		return factoryBean;
	}

}
