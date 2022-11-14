package com.hitebaas.data.pending;

import java.util.Date;

public class Pending {
	
	public static String DATA_STORAGE ="1";//数据存储
	
	public static String  CURRENCY="2";//货币
	
	public static String TRACING_THE_SOURCE="3";//溯源
	
	public static String CROSS_CHAIN_ASSETS="4";//跨链资产

	
	
	

	
	public static String AUCTION_START="9";//发布交易
	//1数据存储 2 货币 3溯源 4 跨链资产 5 电子合同 6 固定资产 7 NFT数据类型 8 NFT货币类型 9 NFT拍卖发布
	
	private String orderNo;
	private String createTime;
	private String gas;
	private String tradeType;//交易类型
	private String content;
	private String isFile;
	
	private String fileMD5;
	
	private String fileName;
	

	private String tradeName;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsFile() {
		return isFile;
	}
	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}
	public String getFileMD5() {
		return fileMD5;
	}
	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
}
