package com.hitebaas.data.black;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hitebaas.data.pending.Pending;
import com.hitebaas.entity.AddressCache;


public class BlockDownLoad {

	
	
	private Block block;
	
	private  String fileString ;
	
	private List<Pending> list =new ArrayList<Pending>();

	private String maxBlockIndex;
	

	private String blockFileStr;
	
	private Map<String , AddressCache> balMap;
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}



	public String getMaxBlockIndex() {
		return maxBlockIndex;
	}

	public void setMaxBlockIndex(String maxBlockIndex) {
		this.maxBlockIndex = maxBlockIndex;
	}



	public String getFileString() {
		return fileString;
	}

	public void setFileString(String fileString) {
		this.fileString = fileString;
	}

	public String getBlockFileStr() {
		return blockFileStr;
	}

	public void setBlockFileStr(String blockFileStr) {
		this.blockFileStr = blockFileStr;
	}

	public List<Pending> getList() {
		return list;
	}

	public void setList(List<Pending> list) {
		this.list = list;
	}

	public Map<String, AddressCache> getBalMap() {
		return balMap;
	}

	public void setBalMap(Map<String, AddressCache> balMap) {
		this.balMap = balMap;
	}

	
	
	
	
	
}
