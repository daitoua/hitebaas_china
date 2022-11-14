package com.hitebaas.data.trade;

public class TradeBody {
	private String tradeNo;
	private String from;
	private String publicKey;
	private String preBlockIndex;
	private String preTradeNo;
	private String fromLeft;
	private String tradeAmount;
	private String to;
	private String gas;
	private String tradeTime;
	private String contractNumber;
	private String tradeLimit;
	private String sign;
	
	private String tradeType;//common or ico ,1 or 2 
	private String returnNewCoint;//when tradeType ==2 
	private String returnContractNumber;//when tradeType ==2 
	private String unfreezeTime;//
	
	
	public String genString() {
		return "TradeBody [from=" + from + ", publicKey=" + publicKey + ", preBlockIndex=" + preBlockIndex
				+ ", preTradeNo=" + preTradeNo + ", fromLeft=" + fromLeft + ", tradeAmount=" + tradeAmount + ", to="
				+ to + ", gas=" + gas + ", tradeTime=" + tradeTime + ", contractNumber=" + contractNumber
				+ ", tradeLimit=" + tradeLimit + ", tradeType=" + tradeType + ", returnNewCoint=" + returnNewCoint
				+ ", returnContractNumber=" + returnContractNumber + ", unfreezeTime=" + unfreezeTime + "]";
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPreBlockIndex() {
		return preBlockIndex;
	}
	public void setPreBlockIndex(String preBlockIndex) {
		this.preBlockIndex = preBlockIndex;
	}
	public String getPreTradeNo() {
		return preTradeNo;
	}
	public void setPreTradeNo(String preTradeNo) {
		this.preTradeNo = preTradeNo;
	}
	public String getFromLeft() {
		return fromLeft;
	}
	public void setFromLeft(String fromLeft) {
		this.fromLeft = fromLeft;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getTradeLimit() {
		return tradeLimit;
	}

	public void setTradeLimit(String tradeLimit) {
		this.tradeLimit = tradeLimit;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getReturnNewCoint() {
		return returnNewCoint;
	}
	public void setReturnNewCoint(String returnNewCoint) {
		this.returnNewCoint = returnNewCoint;
	}
	public String getUnfreezeTime() {
		return unfreezeTime;
	}
	public void setUnfreezeTime(String unfreezeTime) {
		this.unfreezeTime = unfreezeTime;
	}

	public String getReturnContractNumber() {
		return returnContractNumber;
	}

	public void setReturnContractNumber(String returnContractNumber) {
		this.returnContractNumber = returnContractNumber;
	}
	
}
