package com.hitebaas.data.black.service.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hitebaas.dao.BlockDataDao;
import com.hitebaas.data.black.Block;
import com.hitebaas.data.black.BlockDataService;
import com.hitebaas.entity.BlockData;

public class BlockDataServiceImpl implements BlockDataService{


	@Autowired
	private BlockDataDao blockDataDao;
	
	@Override
	public void saveBlockInfo(Block block, int tradeCount) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String blockIndex = block.getBlockIndex();
		String createTime = block.getCreateTime();
		/*List<BlockData> preBds = blockDataDao.queryNewBlockData(new BigInteger(block.getBlockIndex()).subtract(BigInteger.ONE));
		BlockData preBd = null;
		if(preBds !=null && preBds.size() > 0) {
			preBd = preBds.get(preBds.size() -1);
		}
		//BlockData preBd = blockDataDao.queryNewBlockData(new BigInteger(block.getBlockIndex()).subtract(BigInteger.ONE));
		Date preDate = null;
		Date nowDate = df.parse(createTime);
		if(preBd != null) {
			preDate = preBd.getCreateTime();
		}else {
			//6秒前
			preDate = new Date(nowDate.getTime() - 6 * 1000);
		}*/
		Date preDate = null;
		Date nowDate = df.parse(createTime);
		BlockData preBd = blockDataDao.queryBlockData(new BigInteger(block.getBlockIndex()).subtract(BigInteger.ONE));
		if(preBd == null) {
			preDate = new Date(nowDate.getTime() - 6 * 1000);
		}else {
			preDate = preBd.getCreateTime();
		}
		double costTime =  ((double)(nowDate.getTime() - preDate.getTime()))/1000;
		BlockData blockData = new BlockData();
		blockData.setBlockIndex(blockIndex);
		blockData.setCostTime(costTime);
		blockData.setCreateTime(nowDate);
		blockData.setTradeCount(tradeCount);
		blockDataDao.save(blockData);
	}
	
	

}
