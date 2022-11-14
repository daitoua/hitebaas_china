package com.hitebaas.data.black;

import java.text.ParseException;

public interface BlockDataService {

	
	public void saveBlockInfo(Block block, int tradeCount) throws ParseException;
}
