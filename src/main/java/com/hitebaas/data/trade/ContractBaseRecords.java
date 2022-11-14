package com.hitebaas.data.trade;

/**
 * 所有合约记录共用的信息
 */
public class ContractBaseRecords {
	public static final String TRADESTATUS_FAILURE = "failure";
	public static final String TRADESTATUS_NORMAL = "normal";
	public static final String TRADESTATUS_COMPLETE = "complete";

	public static final String AMOUNTTYPE_PLUS = "+";
	public static final String AMOUNTTYPE_REDUCE = "-";
	/**
	 * 区块号
	 */
	private String blockIndex;
	/**
	 * 确认数
	 */
	private String confirmNumber;
	/**
	 * failure
	 * normal
	 * complete
	 */
	private String tradeStatus;
	/**
	 * 交易类型
	 * +
	 * -
	 */
	private String amountType;
	/**
	 * 状态描述，用于描述 失败原因、比如超时、分支切换导致的失败。
	 */
	private String statusDescription;
	public String getBlockIndex() {
		return blockIndex;
	}
	public void setBlockIndex(String blockIndex) {
		this.blockIndex = blockIndex;
	}
	public String getConfirmNumber() {
		return confirmNumber;
	}
	public void setConfirmNumber(String confirmNumber) {
		this.confirmNumber = confirmNumber;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
}
