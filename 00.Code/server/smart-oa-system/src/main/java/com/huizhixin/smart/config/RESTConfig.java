package com.huizhixin.smart.config;

import javax.ws.rs.ApplicationPath;

import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ApplicationPath("/workflow-rest")
public class RESTConfig extends CamundaJerseyResourceConfig {

}
