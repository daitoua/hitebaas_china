package com.hitebaas.data.trade;

/**
 * 高级合约交易类型
 *
 */
public class ContractTradeBodyType {
	/**
	 * tok转tok
	 */
	public static final String TOK_TO_TOK = "0";
	/**
	 * 代币转代币交易类型
	 */
	public static final String CONTRACT_TO_CONTRACT = "1";
	/**
	 * tok转代币合约交易类型
	 */
	public static final String TOK_TO_CONTRACT_ADDRESS = "2";
	/**
	 * 代币转合约地址交易类型
	 */
	public static final String CONTRACT_TO_CONTRACT_ADDRESS = "3";
	/**
	 * 创建代币合约交易类型
	 */
	public static final String CREATE_CONTRACT = "4";
	/**
	 * 合约地址提取交易类型
	 */
	public static final String CONTRACT_WITHDRAW = "5";
	/**
	 * 代币转交易所地址
	 */
	public static final String CONTRACT_TO_BOURSE_ADDRESS = "6";
	/**
	 * 时间银行交易存储
	 */
	public static final String TIMEBANK_STORAGE = "7";
	/**
	 * 时间银行交易存储状态修改
	 */
	public static final String TIMEBANK_TRADE_STATUS = "8";
	/**
	 * 时间银行贷款使用tok
	 */
	public static final String TIMEBANK_LOAN_BY_TOK = "9";
	/**
	 * 时间银行贷款使用FBDT
	 */
	public static final String TIMEBANK_LOAN_BY_FBDT = "a";
	/**
	 * 时间银行还款交易
	 */
	public static final String TIMEBANK_REPAYMENT = "b";
	
	/**
	 * 币币转换交易
	 */
	public static final String CONTRACT_CONVERT = "c";
	/**
	 * 创建智能合约交易
	 */
	public static final String CREATE_INTELLIGENCE_CONTRACT = "d";
	/**
	 * 智能合约方法交易
	 */
	public static final String INTELLIGENCE_CONTRACT_METHOD = "e";

	public static final String WITHDRAWTRADEBODY_TOK = "1";//提现类型tok币
	public static final String WITHDRAWTRADEBODY_CONTRACT = "2";//提现类型合约币
}
