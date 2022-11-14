package com.hitebaas.data;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class DataHelperEntity<T> {

	
	
	
	private T t;
	
	private String appId;
	
	private String appKey;
	
	private String secretKey;
	
	private Map<String,MultipartFile> files;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Map<String, MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(Map<String, MultipartFile> files) {
		this.files = files;
	}

	
	
	
	

	
	
}
