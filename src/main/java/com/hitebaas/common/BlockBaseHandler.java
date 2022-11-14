package com.hitebaas.common;

import java.util.ArrayList;
import java.util.List;

import com.hitebaas.crypt.WalletAddress;
import com.hitebaas.data.black.Block;
import com.hitebaas.data.trade.TradeBodyPool;
import com.hitebaas.data.walletAddress.service.impl.WalletAddressServiceImpl;
import com.hitebaas.service.CreateContractRecordsServiceImpl;
import com.hitebaas.service.TradeRecordsServiceImpl;

public class BlockBaseHandler {

	
	/**
	 * 得到新的块，更新区块确认数，更新交易记录
	 * @param b
	 */
	public static void updateAllBlockCounter2(TradeBodyPool tbp, Block b) {
		//获得钱包列表
		List<WalletAddress> was = WalletAddressServiceImpl.queryWalletAddress();
		//更新交易记录，若是收入，新增交易记录。
		TradeRecordsServiceImpl.updateRecordByBlock(b, was);
		//转钱包地址
		List<String> addressList = new ArrayList<String>();
		for(WalletAddress wa : was) {
			addressList.add(wa.getAddress());
		}
		CreateContractRecordsServiceImpl.updateRecordByBlock(tbp, b, addressList);

	}

}
