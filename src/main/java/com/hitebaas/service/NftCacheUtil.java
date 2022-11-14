package com.hitebaas.service;

import java.util.List;

import com.hitebaas.data.ListNftDataDetails;
import com.hitebaas.data.ListNftDetails;
import com.hitebaas.data.NftCache;
import com.hitebaas.data.NftDetails;

public interface NftCacheUtil {
	
	
	public void putNFTdataRedisCache(NftCache nft);
	
	public NftCache getNFTdataRedisCache(String addr);
	
	public void putNFTRedisCache(NftCache nft);
	
	public NftCache getNFTRedisCache(String addr);
	
	/**
	 * 保存新增货币类型合约
	 * @param n
	 */
	public void putNFTCacheUtil(List<NftDetails> n);
	/**
	 * 根据合约名称查找货币类型合约
	 * @param name
	 * @return
	 */
	public List<NftDetails> getNftDetails();
	
	/**
	 * 保存新增数据类型合约
	 * @param n
	 */
	public void putNFTDataCacheUtil(List<NftDetails> n);
	/**
	 * 根据合约名称查找数据类型合约
	 * @param name
	 * @return
	 */
	public List<NftDetails> getNFTDataCacheUtil();
	
	/**
	 * 分页查找货币类型合约
	 * @param index 页码
	 * @param count 几条
	 * @return
	 */
	public ListNftDetails getNftDetailsByPage(int index, int count);
	
	/**
	 * 分页查找数据类型合约
	 * @param index页码
	 * @param count 几条
	 * @return
	 */
	public ListNftDataDetails getNftDataDetailsByPage(int index, int count);
	public ListNftDataDetails getNftDataDetailsByPage(String type,int index, int count);

}
