package com.huizhixin.smart.workflow.camunda.util;

import java.util.List;

import org.camunda.bpm.engine.impl.digest.PasswordEncryptor;
import org.camunda.bpm.engine.impl.digest.PasswordManager;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class MyWorkflowPasswordManager extends PasswordManager {

	public MyWorkflowPasswordManager(PasswordEncryptor defaultPasswordEncryptor, List<PasswordEncryptor> customPasswordChecker) {
		super(defaultPasswordEncryptor, customPasswordChecker);
	}

	public boolean check(String password, String encrypted) {
		PasswordEncryptor encryptor = getCorrectEncryptorForPassword(encrypted);
		return encryptor.check(password, encrypted);
	}

	protected PasswordEncryptor getCorrectEncryptorForPassword(String encryptedPassword) {
		return passwordChecker.get(defaultPasswordEncryptor.hashAlgorithmName());
	}
}
