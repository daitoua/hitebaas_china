package com.hitebaas.data;

import java.io.Serializable;

/**
 * 注册请求参数
 *
 */
public class RegisterReqVo implements Serializable{
	/**
	 * 昵称
	 * 长度8个字符
	 */
	private String nickname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 验证码
	 */
	private String validCode;
	
	/**
	 * 短信验证码
	 */
	private String smsCode;
	
	private String k;
	
	private String phone;

	
	private String recommendCode;
	
	private String contentMsg;
	
	private String onlyCode;
	
	
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	
	public String getRecommendCode() {
		return recommendCode;
	}
	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}
	
	
	public String getContentMsg() {
		return contentMsg;
	}
	public void setContentMsg(String contentMsg) {
		this.contentMsg = contentMsg;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOnlyCode() {
		return onlyCode;
	}
	public void setOnlyCode(String onlyCode) {
		this.onlyCode = onlyCode;
	}
	
}
