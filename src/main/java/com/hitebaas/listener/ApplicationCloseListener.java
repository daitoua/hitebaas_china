package com.hitebaas.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.hitebaas.cache.EhcacheUtils;

@Component
public class ApplicationCloseListener implements ApplicationListener<ContextClosedEvent> {

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		EhcacheUtils.flushRecordsCache();
	//	UserCacheUtils.shutdown();
	}

}
