package com.hitebaas.data;

import org.springframework.web.multipart.MultipartFile;

public class AutographVo {
	
	/**
	 * 附件
	 */
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
	

}
