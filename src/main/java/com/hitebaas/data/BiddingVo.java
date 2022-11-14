package com.hitebaas.data;

import java.math.BigDecimal;
import java.util.List;

import com.hitebaas.entity.AuctionOrder;

public class BiddingVo {
	
	private List<AuctionOrder> list;
	
	private BigDecimal amount;//总金额



	

	public List<AuctionOrder> getList() {
		return list;
	}

	public void setList(List<AuctionOrder> list) {
		this.list = list;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}



	
	
	
	

}
