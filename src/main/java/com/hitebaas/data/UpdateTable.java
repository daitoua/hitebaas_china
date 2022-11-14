package com.hitebaas.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateTable {

	
	private  List<String>str=new ArrayList<String>();
	
	private String tradeName;
	
	private Map<String,String> map;

	public List<String> getStr() {
		return str;
	}

	public void setStr(List<String> str) {
		this.str = str;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	
	
	
}
