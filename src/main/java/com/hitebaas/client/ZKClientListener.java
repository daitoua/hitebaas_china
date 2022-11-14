package com.hitebaas.client;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import com.hitebaas.data.ZKClientInfo;

public class ZKClientListener implements LeaderLatchListener{

	private static Log log = LogFactory.getLog(ZKClientListener.class);
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void isLeader() {
		log.info( simpleDateFormat.format(new Date()) + "当前服务已变为leader，将从事消费业务");
		

		System.out.println(1111);
		ZKClientInfo.isLeader = true;
		
	}

	@Override
	public void notLeader() {
		log.info( simpleDateFormat.format(new Date()) + "当前服务已退出leader，不再从事消费业务");
		ZKClientInfo.isLeader = false;
	}

}
