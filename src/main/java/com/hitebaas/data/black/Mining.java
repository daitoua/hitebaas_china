package com.hitebaas.data.black;


public class  Mining {
	
	public static String blockHash="";
	
	public static Block block =null ;
	
	public static  MiningInfo mi =null;

	public static String getBlockHash() {
		return blockHash;
	}

	public static void setBlockHash(String blockHash) {
		Mining.blockHash = blockHash;
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		Mining.block = block;
	}

	public static MiningInfo getMi() {
		return mi;
	}

	public static void setMi(MiningInfo mi) {
		Mining.mi = mi;
	}


	
	

}
