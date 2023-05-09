package com.huizhixin.smart.workflow.camunda.util;

import org.camunda.bpm.engine.impl.digest.PasswordEncryptor;

import com.huizhixin.smart.common.util.PasswordUtil;

public class MyWorkflowPasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(String password) {
		String userpassword = PasswordUtil.encrypt("admin", password, "RCGTeGiH");
		return userpassword;
	}

	@Override
	public String hashAlgorithmName() {
		return "PBEWITHMD5andDES";
	}

	@Override
	public boolean check(String password, String encrypted) {
	    return encrypt(password).equals(encrypted);
	}
}
