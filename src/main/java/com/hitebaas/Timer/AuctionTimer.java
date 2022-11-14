package com.hitebaas.Timer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hitebaas.crypt.EncryptUtil;
import com.hitebaas.crypt.RSAUtils;
import com.hitebaas.crypt.WalletAddress;
import com.hitebaas.data.NftTradeBody;
import com.hitebaas.data.trade.ContractToContractTradeBody;
import com.hitebaas.data.walletAddress.service.impl.WalletAddressServiceImpl;
import com.hitebaas.entity.AuctionOrder;
import com.hitebaas.entity.DepositOrder;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.entity.TradeBody;
import com.hitebaas.entity.TradeJson;
import com.hitebaas.service.CacheUtilsService;
import com.hitebaas.util.DataUtils;
import com.hitebaas.util.http.client.HttpClientHelper;


/**
 * 每日凌晨1点把所有处于在拍中的订单取出 改状态 执行后执行完成的订单
 * @author wwb
 *
 */

@Component
public class AuctionTimer {
	
	
	@Autowired
    CacheUtilsService cacheUtilsService;
	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	
	@Scheduled(cron = "0 * * * * ?")
	public void updateAuctonOrder() {

		String time=DataUtils.getCurrentTime();
		kafkaTemplate.send("timer", time+" 心跳执行");
	
	}
	
	
}
