package com.hitebaas.data;

import java.util.ArrayList;
import java.util.List;

import com.hitebaas.entity.AuctionOrder;

public class AuctionBiddingVo {

	private List<AuctionOrder> list = new ArrayList<AuctionOrder>();
	
	private List<BiddingVo> b = new ArrayList<BiddingVo>();

	public List<AuctionOrder> getList() {
		return list;
	}

	public void setList(List<AuctionOrder> list) {
		this.list = list;
	}

	public List<BiddingVo> getB() {
		return b;
	}

	public void setB(List<BiddingVo> b) {
		this.b = b;
	}
	
	
	
	
}
