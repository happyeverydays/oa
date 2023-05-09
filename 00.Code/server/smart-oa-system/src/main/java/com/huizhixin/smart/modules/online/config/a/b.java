package com.huizhixin.smart.modules.online.config.a;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
public class b {
	private String a;
	private String b;
	private String c;
	private String d;

	public String getUrl() {
		return this.a;
	}

	public void setUrl(String url) {
		this.a = url;
	}

	public String getUsername() {
		return this.b;
	}

	public void setUsername(String username) {
		this.b = username;
	}

	public String getPassword() {
		return this.c;
	}

	public void setPassword(String password) {
		this.c = password;
	}

	public String getDriverClassName() {
		return this.d;
	}

	public void setDriverClassName(String driverClassName) {
		this.d = driverClassName;
	}
}