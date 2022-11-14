package com.hitebaas.util.init;



import com.hitebaas.data.dic.model.Dictionary;
import com.hitebaas.data.dic.service.impl.DicServiceImpl;

public class InitUtils {
	/**
	 * 初始化字典里面当前最大块
	 * @return
	 */
	public static Dictionary intiDic(){
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.BLOCKINDEX_KEY);
		if(dic == null) {
			dic = new Dictionary();
			dic.setModule(Dictionary.MODUAL_BLOCK);
			dic.setKey(Dictionary.BLOCKINDEX_KEY);
			dic.setValue("0");//first block index
			boolean b= DicServiceImpl.save(dic);
			if(!b) {
				return null;
			}
		}
		
		
		return dic;
	}
	/**
	 * 初始化字典里面当前最大块
	 * @return
	 */
	public static Dictionary intiMainChainDic(){
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.CURRENTBLOCKINDEX);
		if(dic == null) {
			dic = new Dictionary();
			dic.setModule(Dictionary.MODUAL_BLOCK);
			dic.setKey(Dictionary.CURRENTBLOCKINDEX);
			dic.setValue("-1");//first block index
			boolean b= DicServiceImpl.save(dic);
			if(!b) {
				return null;
			}
		}
		return dic;
	}
	
	/**
	 * 初始化字典每块收益
	 * @return
	 */
	public static Dictionary intiProfit(){
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.PROFIT);
		if(dic == null) {
			dic = new Dictionary();
			dic.setModule(Dictionary.MODUAL_BLOCK);
			dic.setKey(Dictionary.PROFIT);
			dic.setValue("20");//first block index
			boolean b= DicServiceImpl.save(dic);
			if(!b) {
				return null;
			}
		}
		return dic;
	}
	/**
	 * 初始化字典挖矿难度
	 * @return
	 */
	public static Dictionary intiDifficulty(){
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.DIFFICULTY);
		if(dic == null) {
			dic = new Dictionary();
			dic.setModule(Dictionary.MODUAL_BLOCK);
			dic.setKey(Dictionary.DIFFICULTY);
			dic.setValue("000");//first block index
			boolean b= DicServiceImpl.save(dic);
			if(!b) {
				return null;
			}
		}
		return dic;
	}
	/**
	 * 初始化zip序号
	 * @return
	 */
	public static Dictionary intiZipIndex(){
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.BLOCKZIPINDEX);
		if(dic == null) {
			dic = new Dictionary();
			dic.setModule(Dictionary.MODUAL_BLOCK);
			dic.setKey(Dictionary.BLOCKZIPINDEX);
			dic.setValue("0");
			boolean b= DicServiceImpl.save(dic);
			if(!b) {
				return null;
			}
		}
		return dic;
	}
}
