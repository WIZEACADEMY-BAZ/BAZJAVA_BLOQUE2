package com.wizeline.baz.utils;

public interface Constants {

	String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,18}$";
	String BLOCK_USERS_TOPIC = "blocked-user-accounts";
	String FAILED_LOGINS_TOPIC = "failed-login-users";
	String FAILED_LOGINS_CONSUMER_GROUP = "bank.api.failed.login";
	String BLOCK_USERS_CONSUMER_GROUP = "bank.api.block.users";
}
