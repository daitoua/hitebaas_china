package com.hitebaas.data.dic.model;

public class Dictionary {
	public static final String MODUAL_BLOCK = "block";
	public static final String BLOCKINDEX_KEY = "blockIndex";
	public static final String CURRENTBLOCKINDEX = "currentBlockIndex";
	
	public static final String PROFIT ="Profit";
	
	

	public static final String BLOCKZIPINDEX ="blockZipIndex";



	public static final String DIFFICULTY ="DIFFICULTY";
	
	private String module;
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
}
