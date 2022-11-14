package com.hitebaas.config;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitebaas.entity.AuctionOrder;
import com.hitebaas.entity.Bidding;
import com.hitebaas.entity.BindRecord;
import com.hitebaas.entity.DepositOrder;
import com.hitebaas.service.CacheUtilsService;
import com.hitebaas.util.DataUtils;

public class KafkaListeners {

    private Gson gson = new GsonBuilder().create();


	@Autowired
    CacheUtilsService cacheUtilsService;


//    @KafkaListener(topics = {"bidding"})
//    public void saveBidding(String content) {
//
//    	Bidding b = new Gson().fromJson(content, Bidding.class);
//
//    	Bidding bi=cacheUtilsService.queryBiddingById(String.valueOf(b.getAuctionId()));
//    	BigDecimal oldAmount=new BigDecimal("0");
//    	if(bi==null) {
//    		bi=b;
//    		AuctionOrder ao=cacheUtilsService.queryAuctionOrderByAuctionId(b.getAuctionId());
//    		oldAmount=new BigDecimal(ao.getDeposit());
//    		List<BindRecord> record=new ArrayList<BindRecord>();
//    		bi.setRecord(record);
//    	}else {
//    		 oldAmount=new BigDecimal(bi.getAmount());
//    	}
//
//
//		BigDecimal nAmount=new BigDecimal(b.getAmount());
//		if(oldAmount.compareTo(nAmount)>=0) {
//			return;
//		}
//		bi.setAddress(b.getAddress());
//		bi.setAmount(b.getAmount());
//		bi.setCreateTime(b.getCreateTime());
//		bi.setHash(b.getHash());
//		bi.setbTime(b.getbTime());
//		String bTime=bi.getbTime();
//		String eTime=bi.getEndTime();
//		/**
//		 *检查拍卖的结束时间是否小于该订单加5分钟 如果小于的话则结束时间为加5分钟的时间
//		 */
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			Date b1=sdf.parse(bTime);
//			String afterDate =sdf.format( b1.getTime() + 300000);//5分钟后的时间
//			boolean isEnTime=DataUtils.checkTime(eTime, afterDate);
//			if(!isEnTime) {
//				bi.setEndTime(afterDate);
//
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		BindRecord br=new BindRecord();
//		br.setAddr(b.getAddress());
//		br.setAmount(b.getAmount());
//		br.setbTime(b.getCreateTime());
//		List<BindRecord>  list=bi.getRecord();
//
//		list.add(br);
//		bi.setRecord(list);
//		cacheUtilsService.saveBidding(bi);
//		/**
//		 * 修改买家地址并增加次数
//		 */
//
//		cacheUtilsService.updateAuctionOrderCount (b.getAddress(),String.valueOf(b.getAuctionId()),b.getAmount(),bi.getEndTime());
//    }



}
