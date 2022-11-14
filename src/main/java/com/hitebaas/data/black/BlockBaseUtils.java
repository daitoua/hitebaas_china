package com.hitebaas.data.black;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hitebaas.data.trade.TradeBody;
import com.hitebaas.data.trade.TradeBodyPool;
import com.hitebaas.entity.AddressCache;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.util.DataUtils;

public class BlockBaseUtils {
	
	
	/**
	 * 生成统计信息
	 * @param tbp
	 * @return
	 */
	public static Map<String , AddressCache> genBalAllMap(TradeBodyPool tbp, Block block) {
		Map<String , AddressCache> balMap = new HashMap<String , AddressCache>();
		BlockBaseUtils.genBalMap(tbp.getTbMap(), balMap);//使用tok的交易
		
		return balMap;
	}
	public static Map<String , AddressCache> genBalMap(Map<String, TradeBody> tbm, Map<String , AddressCache> balMap){
		
		for(Entry<String, TradeBody> entry : tbm.entrySet()) {
			TradeBody tb = entry.getValue();
			String addrFrom = tb.getFrom();
			String addrTo = tb.getTo();
			String c  = tb.getContractNumber();
			_genBlockAddressListMap(addrFrom, c, tb.getTradeAmount(), tb.getGas(),  balMap, 2, HiteBassConstant.TOK_TO_TOK);//记支出
			_genBlockAddressListMap(addrTo, c, tb.getTradeAmount(), "0", balMap, 1, HiteBassConstant.TOK_TO_TOK);//记收入
		}
		
		return balMap;

	}
	/**
	 * 
	 * @param address
	 * @param contractNumber
	 * @param tradeAmount
	 * @param gas
	 * @param balMap
	 * @param type  1:收入 2 支出
	 * @param tradeType 交易类型
	 */
	private static void  _genBlockAddressListMap(String address, String contractNumber, String tradeAmount, String gas, Map<String , AddressCache> balMap, int type, String tradeType) {
		String key = address + contractNumber;
		AddressCache bal = balMap.get(key);
		if(bal == null) {
			bal = new AddressCache();
			bal.setAddress(address);
			bal.setContractNumber(contractNumber);
			bal.setExpenses("0");
			bal.setIncome("0");
			bal.setAmount("0");
			balMap.put(key, bal);
		}
		//update
		BigDecimal amount = new BigDecimal(tradeAmount);
		if(1 == type) {
			BigDecimal income = new BigDecimal(bal.getIncome());
			income = income.add(amount);
			bal.setIncome(DataUtils.getAmountString(income));
		}else {
			BigDecimal out = new BigDecimal(bal.getExpenses());
			BigDecimal gas0 = new BigDecimal(gas);
			out = out.add(amount).add(gas0);//花费=转账金额+邮费
			bal.setExpenses(DataUtils.getAmountString(out));
			/*if(StringUtils.isEmpty(tradeType) 
					|| ContractTradeBodyType.TOK_TO_TOK.equals(tradeType) 
					|| ContractTradeBodyType.CREATE_CONTRACT.equals(tradeType) 
					|| ContractTradeBodyType.CONTRACT_WITHDRAW.equals(tradeType)
					|| ContractTradeBodyType.TOK_TO_CONTRACT_ADDRESS.equals(tradeType)) {
				//交易金额和gas都是tok时候
				out = out.add(amount).add(gas0);//花费=转账金额+邮费
				bal.setExpenses(DataUtils.getAmountString(out));
			}else {
				//转出方是代币，gas是tok,若是合约地址则不需要判断gas
				String key2 = address + BtcConstant.TOK_CONTRACT_NUMBER;
				BlockAddressList bal2 = balMap.get(key2);
				if(bal2 == null) {
					bal2 = new BlockAddressList();
					bal2.setAddress(address);
					bal2.setContractNumber(contractNumber);
					bal2.setExpenses("0");
					bal2.setIncome("0");
					bal2.setLeft("0");
					balMap.put(key, bal2);
				}
				//代币花费
				out = out.add(amount);//花费=转账金额
				bal.setExpenses(DataUtils.getAmountString(out));
				//tok花费
				BigDecimal out2 = new BigDecimal(bal2.getExpenses());
				out2 = out2.add(gas0);
				bal2.setExpenses(DataUtils.getAmountString(out2));
			}*/
		}
	}
}
