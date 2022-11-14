package com.hitebaas.data.black;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hitebaas.data.pending.Pending;
import com.hitebaas.data.trade.TradeBodyPool;

public class MiningInfo {
	private BlockDownLoad bdl;
	private String nextBlockIndex;
	private String blockHash;
	private Date beginTime;
	private String blockString;
	private TradeBodyPool tbp;
	private Map<String, List<Pending>> map  ;
	
	
	public MiningInfo(BlockDownLoad bdl, String nextBlockIndex, String blockHash, Date beginTime, String blockString) {
		super();
		this.bdl = bdl;
		this.nextBlockIndex = nextBlockIndex;
		this.blockHash = blockHash;
		this.beginTime = beginTime;
		this.blockString = blockString;
		
	}



	public  BlockDownLoad getBdl() {
		return bdl;
	}



	public void setBdl( BlockDownLoad bdl) {
		this.bdl = bdl;
	}



	public String getNextBlockIndex() {
		return nextBlockIndex;
	}

	public void setNextBlockIndex(String nextBlockIndex) {
		this.nextBlockIndex = nextBlockIndex;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getBlockString() {
		return blockString;
	}

	public void setBlockString(String blockString) {
		this.blockString = blockString;
	}

	public Map<String, List<Pending>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<Pending>> map) {
		this.map = map;
	}



	public TradeBodyPool getTbp() {
		return tbp;
	}



	public void setTbp(TradeBodyPool tbp) {
		this.tbp = tbp;
	}

	

}
