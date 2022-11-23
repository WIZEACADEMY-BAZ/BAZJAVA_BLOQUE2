package com.wizeline.baz.batch;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.model.KafkaMessage;
import com.wizeline.baz.model.batch.BlockedUserAccount;
import com.wizeline.baz.model.batch.FailedLoginInfo;


@Component
public class FailedLoginItemProcessor implements ItemProcessor<KafkaMessage, KafkaMessage> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FailedLoginItemProcessor.class);
	private ObjectMapper objectMapper = new ObjectMapper(); 
	
	@Override
	public KafkaMessage process(KafkaMessage item) throws Exception {
		if(item == null)
			return null;
		
		LOGGER.info("Item received -> {}", objectMapper.writeValueAsString(item));
		FailedLoginInfo failedLogin = FailedLoginInfo.fromMap(item.getData());
		
		//some blocking logic
		
		BlockedUserAccount blockedUser = new BlockedUserAccount();
		blockedUser.setEmail(failedLogin.getEmail());
		blockedUser.setUserId(failedLogin.getUserId());
		blockedUser.setTime(LocalDateTime.now());
		
		return new KafkaMessage(UUID.randomUUID().toString(), "BLOCKED_USERS", blockedUser.toMap());
	}

}
