package com.hitebaas.data.walletAddress.model;

import com.hitebaas.entity.PaperBean;

public class OutLineWalletAddress {
	private String type;
	private String address;
	private String publicKey;
	private String privateKey;
	private String StringTokenName;    //钱包 名称
	private String Password;    //  密码
	private String createTime;    //时间
	private String status;
	private PaperBean pb;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStringTokenName() {
		return StringTokenName;
	}
	public void setStringTokenName(String stringTokenName) {
		StringTokenName = stringTokenName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PaperBean getPb() {
		return pb;
	}
	public void setPb(PaperBean pb) {
		this.pb = pb;
	}
	
}
