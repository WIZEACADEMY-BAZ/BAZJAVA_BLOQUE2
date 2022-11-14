package com.wizeline.baz.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.wizeline.baz.model.batch.BlockedUserAccount;
import com.wizeline.baz.model.batch.FailedLoginInfo;


@Component
public class FailedLoginItemProcessor implements ItemProcessor<FailedLoginInfo, BlockedUserAccount> {
	
	@Override
	public BlockedUserAccount process(FailedLoginInfo item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
