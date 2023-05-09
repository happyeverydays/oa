package com.huizhixin.smart.workflow.camunda.config;

import org.camunda.bpm.engine.spring.SpringArtifactFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class SpringProcessEngineCustomConfiguration extends SpringTransactionsProcessEngineCustomConfiguration implements ApplicationContextAware {

  protected ApplicationContext applicationContext;

  @Override
  protected void initArtifactFactory() {
    if (artifactFactory == null && applicationContext != null) {
      artifactFactory = new SpringArtifactFactory(applicationContext);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
