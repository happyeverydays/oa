package com.huizhixin;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@ServletComponentScan
@SpringBootApplication
public class SmartApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SmartApplication.class);
	}
	
	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(SmartApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		log.info("\n----------------------------------------------------------\n\t"
				+ "Application Smart-Boot is running! Access URLs:\n\t" + "Local: \t\thttp://localhost:" + port + path
				+ "/\n\t" + "External: \thttp://" + ip + ":" + port + path + "/\n\t" + "swagger-ui: \thttp://" + ip
				+ ":" + port + path + "/swagger-ui.html\n\t" + "Doc: \t\thttp://" + ip + ":" + port + path
				+ "/doc.html\n" + "----------------------------------------------------------");
	}
}