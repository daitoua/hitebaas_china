package com.hitebaas.data.trade;

import java.io.Serializable;


public class CreateContractRecords extends ContractBaseRecords implements Serializable{
	private String tradeNo;//交易编号
	private String tradeAmount;//交易币值-创建合约需要支付的币值
	private String contractNumber;//合约简称 10个长度-唯一性
	private String unit;//合约单位
	private String contractAddress;//合约地址。
	private String paymentAddress;//支付地址-合约拥有地址
	private String incomeAddress;//收入地址-客户端写死
	private String gas;//手续费
	private String initContractAmount;//初始化合约数量
	private String icoLimitTime;//ico活动限制时间
	private String enlargeLimitTime;//代币放大活动限制时间
	private String ep;//tok兑换比例 1个tok= ep个该代币,不需要分期返利时使用
	private String ep2;//代币兑换比例 1个代币= ep2个该代币,不需要分期返利时使用
	private String tradeTime;//交易时间
	private String content;//合约内容
	private String control;//是否返利开关
	private String intervalPeriod;//tok返利设置
	private String intervalPeriod2;//合约返利设置
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getPaymentAddress() {
		return paymentAddress;
	}
	public void setPaymentAddress(String paymentAddress) {
		this.paymentAddress = paymentAddress;
	}
	public String getIncomeAddress() {
		return incomeAddress;
	}
	public void setIncomeAddress(String incomeAddress) {
		this.incomeAddress = incomeAddress;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getInitContractAmount() {
		return initContractAmount;
	}
	public void setInitContractAmount(String initContractAmount) {
		this.initContractAmount = initContractAmount;
	}
	public String getIcoLimitTime() {
		return icoLimitTime;
	}
	public void setIcoLimitTime(String icoLimitTime) {
		this.icoLimitTime = icoLimitTime;
	}
	public String getEp() {
		return ep;
	}
	public void setEp(String ep) {
		this.ep = ep;
	}
	public String getEp2() {
		return ep2;
	}
	public void setEp2(String ep2) {
		this.ep2 = ep2;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getIntervalPeriod() {
		return intervalPeriod;
	}
	public void setIntervalPeriod(String intervalPeriod) {
		this.intervalPeriod = intervalPeriod;
	}
	public String getIntervalPeriod2() {
		return intervalPeriod2;
	}
	public void setIntervalPeriod2(String intervalPeriod2) {
		this.intervalPeriod2 = intervalPeriod2;
	}
	public String getEnlargeLimitTime() {
		return enlargeLimitTime;
	}
	public void setEnlargeLimitTime(String enlargeLimitTime) {
		this.enlargeLimitTime = enlargeLimitTime;
	}
}
