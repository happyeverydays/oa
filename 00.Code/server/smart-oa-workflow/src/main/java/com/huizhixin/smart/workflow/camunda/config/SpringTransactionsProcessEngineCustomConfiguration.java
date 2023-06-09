package com.huizhixin.smart.workflow.camunda.config;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipInputStream;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.interceptor.CommandContextInterceptor;
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor;
import org.camunda.bpm.engine.impl.interceptor.LogInterceptor;
import org.camunda.bpm.engine.impl.interceptor.ProcessApplicationContextInterceptor;
import org.camunda.bpm.engine.impl.util.ReflectUtil;
import org.camunda.bpm.engine.impl.variable.serializer.jpa.EntityManagerSession;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.spring.SpringEntityManagerSessionFactory;
import org.camunda.bpm.engine.spring.SpringTransactionContextFactory;
import org.camunda.bpm.engine.spring.SpringTransactionInterceptor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class SpringTransactionsProcessEngineCustomConfiguration extends ProcessEngineConfigurationImpl {
	public static final String DEFAULT_MYBATIS_MAPPING_FILE_CUSTOM = "com/huizhixin/smart/workflow/camunda/mapper/xml/mappings.xml";

	protected PlatformTransactionManager transactionManager;
	protected String deploymentName = "SpringAutoDeployment";
	protected Resource[] deploymentResources = new Resource[0];
	protected String deploymentTenantId;
	protected boolean deployChangedOnly;

	public SpringTransactionsProcessEngineCustomConfiguration() {
		transactionsExternallyManaged = true;
	}

	@Override
	public ProcessEngine buildProcessEngine() {
		ProcessEngine processEngine = super.buildProcessEngine();
		autoDeployResources(processEngine);
		return processEngine;
	}

	protected Collection<? extends CommandInterceptor> getDefaultCommandInterceptorsTxRequired() {
		if (transactionManager == null) {
			throw new ProcessEngineException(
					"transactionManager is required property for SpringProcessEngineConfiguration, use "
							+ StandaloneProcessEngineConfiguration.class.getName() + " otherwise");
		}

		List<CommandInterceptor> defaultCommandInterceptorsTxRequired = new ArrayList<CommandInterceptor>();
		defaultCommandInterceptorsTxRequired.add(new LogInterceptor());
		defaultCommandInterceptorsTxRequired.add(new ProcessApplicationContextInterceptor(this));
		defaultCommandInterceptorsTxRequired
				.add(new SpringTransactionInterceptor(transactionManager, TransactionTemplate.PROPAGATION_REQUIRED));
		CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor(commandContextFactory,
				this);
		defaultCommandInterceptorsTxRequired.add(commandContextInterceptor);
		return defaultCommandInterceptorsTxRequired;
	}

	protected Collection<? extends CommandInterceptor> getDefaultCommandInterceptorsTxRequiresNew() {
		List<CommandInterceptor> defaultCommandInterceptorsTxRequiresNew = new ArrayList<CommandInterceptor>();
		defaultCommandInterceptorsTxRequiresNew.add(new LogInterceptor());
		defaultCommandInterceptorsTxRequiresNew.add(new ProcessApplicationContextInterceptor(this));
		defaultCommandInterceptorsTxRequiresNew.add(
				new SpringTransactionInterceptor(transactionManager, TransactionTemplate.PROPAGATION_REQUIRES_NEW));
		CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor(commandContextFactory, this,
				true);
		defaultCommandInterceptorsTxRequiresNew.add(commandContextInterceptor);
		return defaultCommandInterceptorsTxRequiresNew;
	}

	@Override
	protected void initTransactionContextFactory() {
		if (transactionContextFactory == null && transactionManager != null) {
			transactionContextFactory = new SpringTransactionContextFactory(transactionManager);
		}
	}

	@Override
	protected void initJpa() {
		super.initJpa();
		if (jpaEntityManagerFactory != null) {
			sessionFactories.put(EntityManagerSession.class, new SpringEntityManagerSessionFactory(
					jpaEntityManagerFactory, jpaHandleTransaction, jpaCloseEntityManager));
		}
	}

	protected void autoDeployResources(ProcessEngine processEngine) {
		if (deploymentResources != null && deploymentResources.length > 0) {
			RepositoryService repositoryService = processEngine.getRepositoryService();

			DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
					.enableDuplicateFiltering(deployChangedOnly).name(deploymentName).tenantId(deploymentTenantId);

			for (Resource resource : deploymentResources) {
				String resourceName = null;

				if (resource instanceof ContextResource) {
					resourceName = ((ContextResource) resource).getPathWithinContext();

				} else if (resource instanceof ByteArrayResource) {
					resourceName = resource.getDescription();

				} else {
					resourceName = getFileResourceName(resource);
				}

				try {
					if (resourceName.endsWith(".bar") || resourceName.endsWith(".zip")
							|| resourceName.endsWith(".jar")) {
						deploymentBuilder.addZipInputStream(new ZipInputStream(resource.getInputStream()));
					} else {
						deploymentBuilder.addInputStream(resourceName, resource.getInputStream());
					}
				} catch (IOException e) {
					throw new ProcessEngineException(
							"couldn't auto deploy resource '" + resource + "': " + e.getMessage(), e);
				}
			}

			deploymentBuilder.deploy();
		}
	}

	protected String getFileResourceName(Resource resource) {
		try {
			return resource.getFile().getAbsolutePath();
		} catch (IOException e) {
			return resource.getFilename();
		}
	}

	@Override
	protected InputStream getMyBatisXmlConfigurationSteam() {
		return ReflectUtil.getResourceAsStream(DEFAULT_MYBATIS_MAPPING_FILE_CUSTOM);
	}

	@Override
	public ProcessEngineConfigurationImpl setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			return super.setDataSource(dataSource);
		} else {
			// Wrap datasource in Transaction-aware proxy
			DataSource proxiedDataSource = new TransactionAwareDataSourceProxy(dataSource);
			return super.setDataSource(proxiedDataSource);
		}
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public String getDeploymentName() {
		return deploymentName;
	}

	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	public Resource[] getDeploymentResources() {
		return deploymentResources;
	}

	public void setDeploymentResources(Resource[] deploymentResources) {
		this.deploymentResources = deploymentResources;
	}

	public String getDeploymentTenantId() {
		return deploymentTenantId;
	}

	public void setDeploymentTenantId(String deploymentTenantId) {
		this.deploymentTenantId = deploymentTenantId;
	}

	public boolean isDeployChangedOnly() {
		return deployChangedOnly;
	}

	public void setDeployChangedOnly(boolean deployChangedOnly) {
		this.deployChangedOnly = deployChangedOnly;
	}

}
