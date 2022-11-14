package com.hitebaas.data.trade;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.hitebaas.entity.HiteBassConstant;


/**
 * 代币普通交易报文体
 *
 */
public class ContractToContractTradeBody  implements Serializable{

	private String tradeType = ContractTradeBodyType.CONTRACT_TO_CONTRACT;
	private String tradeNo;//交易编号
	private String from;//转出地址
	private String publicKey;//公钥
	private String tradeAmount;//交易币值
	private String to;//转入地址
	private String gas;//手续费
	private String tradeTime;//交易时间
	private String contractNumber;//合约名称
	private String contractAddress;//合约地址。
	private String sign;//交易签名
	

	
	public String genString() {
		return "ContractToContractTradeBody [tradeType=" + tradeType + ", from=" + from + ", publicKey=" + publicKey
				+ ", tradeAmount=" + tradeAmount + ", to=" + to + ", gas=" + gas + ", tradeTime=" + tradeTime
				+ ", contractNumber=" + contractNumber + ", contractAddress=" + contractAddress + "]";
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
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
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
	
	public String getTo32() {
		if(StringUtils.isNotBlank(to) && to.length() > HiteBassConstant.TOKEN_ADDRESS_LENGTH ) {
			//return to.substring(0, 32);
			return to.substring(0, HiteBassConstant.TOKEN_ADDRESS_LENGTH);
		}
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
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}
