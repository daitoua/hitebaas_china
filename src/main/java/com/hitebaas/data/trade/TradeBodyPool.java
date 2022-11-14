package com.hitebaas.data.trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hitebaas.data.NFT;
import com.hitebaas.data.NftTradeBody;
import com.hitebaas.data.PetInfo;
import com.hitebaas.data.pending.Pending;
import com.hitebaas.data.trade.ContractToContractTradeBody;
import com.hitebaas.data.trade.TokScriptContractTradeBody;
import com.hitebaas.data.trade.TokScriptFunctionTradeBody;
import com.hitebaas.data.trade.TradeBody;
import com.hitebaas.entity.CreateContractTradeBody;

public class TradeBodyPool {
	
	
	Map<String, TradeBody> tbMap;
	/**
	 * 创建合约 的交易信息
	 * @return
	 */
	Map<String, CreateContractTradeBody> cctbMap;

	/**
	 * 代币转代币交易信息
	 * @return
	 */
	Map<String, ContractToContractTradeBody> ctctbMap;
	/**
	 * 智能合约调用
	 */
	Map<String, TokScriptFunctionTradeBody> tsftbMap;
	/**
	 * 智能合约创建
	 */
	Map<String, TokScriptContractTradeBody> tsctbMap;
	
	Map<String, NFT> nfttbMap;
	

	Map<String, PetInfo> pettbMap;
	
	private List<Pending> pending;
	
	public TradeBodyPool() {
		super();
		tbMap = new HashMap<String, TradeBody>();
		ctctbMap = new HashMap<String, ContractToContractTradeBody>();
		cctbMap = new HashMap<String, CreateContractTradeBody>();
		tsftbMap = new HashMap<String, TokScriptFunctionTradeBody>();
		tsctbMap = new HashMap<String, TokScriptContractTradeBody>();
		nfttbMap = new HashMap<String, NFT>();
		pettbMap= new HashMap<String,PetInfo>();
	}
	
	public Map<String, TradeBody> getTbMap() {
		return tbMap;
	}

	public void setTbMap(Map<String, TradeBody> tbMap) {
		this.tbMap = tbMap;
	}

	public Map<String, CreateContractTradeBody> getCctbMap() {
		return cctbMap;
	}

	public void setCctbMap(Map<String, CreateContractTradeBody> cctbMap) {
		this.cctbMap = cctbMap;
	}

	public Map<String, ContractToContractTradeBody> getCtctbMap() {
		return ctctbMap;
	}

	public void setCtctbMap(Map<String, ContractToContractTradeBody> ctctbMap) {
		this.ctctbMap = ctctbMap;
	}

	public List<Pending> getPending() {
		return pending;
	}

	public void setPending(List<Pending> pending) {
		this.pending = pending;
	}

	public Map<String, TokScriptFunctionTradeBody> getTsftbMap() {
		return tsftbMap;
	}

	public void setTsftbMap(Map<String, TokScriptFunctionTradeBody> tsftbMap) {
		this.tsftbMap = tsftbMap;
	}

	public Map<String, TokScriptContractTradeBody> getTsctbMap() {
		return tsctbMap;
	}

	public void setTsctbMap(Map<String, TokScriptContractTradeBody> tsctbMap) {
		this.tsctbMap = tsctbMap;
	}

	public Map<String, NFT> getNfttbMap() {
		return nfttbMap;
	}

	public void setNfttbMap(Map<String, NFT> nfttbMap) {
		this.nfttbMap = nfttbMap;
	}

	public Map<String, PetInfo> getPettbMap() {
		return pettbMap;
	}

	public void setPettbMap(Map<String, PetInfo> pettbMap) {
		this.pettbMap = pettbMap;
	}
	
}
