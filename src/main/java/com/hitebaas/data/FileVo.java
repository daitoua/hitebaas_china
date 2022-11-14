package com.hitebaas.data;

import java.io.InputStream;

public class FileVo {

	
	private InputStream inputStream ;
	
	private byte [] byteArr;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public byte[] getByteArr() {
		return byteArr;
	}

	public void setByteArr(byte[] byteArr) {
		this.byteArr = byteArr;
	}
	
	
	
	
}
