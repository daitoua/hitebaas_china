package com.hitebaas.data;

/**
 * 发起拍卖请求参数
 * @author wwb
 *
 */
public class AuctonVo {

	/**
	 * 拍卖方地址
	 */
	private String sellerAddress;
	
	
	/**
	 * 合约名称
	 */
	private String tradeName;
	
	/**
	 * 单位
	 */
	private String unit;
	
	/**
	 * 合约描述
	 */
	private String content;
	
	/**
	 * 合约类型
	 */
	private String tradeTypeNum;
	
	/**
	 * 图片地址
	 */
	
	private String imgUrl;
	
	/**
	 * 卖家描述
	 */
	private String sellerContent;
	
	/**
	 * 开拍时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 起拍价格
	 * @return
	 */
	private String deposit;
	
	/**
	 * 交易币种
	 * @return
	 */
	private String coinType;
	
	/**
	 * 拍卖天数
	 * @return
	 */

	
	private String auctionTime;
	
	
	/**
	 * 当前价格
	 */
	private String currentPrice;
	
	
	
	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTradeTypeNum() {
		return tradeTypeNum;
	}

	public void setTradeTypeNum(String tradeTypeNum) {
		this.tradeTypeNum = tradeTypeNum;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSellerContent() {
		return sellerContent;
	}

	public void setSellerContent(String sellerContent) {
		this.sellerContent = sellerContent;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public String getAuctionTime() {
		return auctionTime;
	}

	public void setAuctionTime(String auctionTime) {
		this.auctionTime = auctionTime;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	
	
	
}
