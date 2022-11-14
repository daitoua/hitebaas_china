package com.hitebaas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitebaas.data.ListNftDataDetails;
import com.hitebaas.data.ListNftDetails;
import com.hitebaas.data.NFTCacheUtil;
import com.hitebaas.data.NFTDataCacheUtil;
import com.hitebaas.data.NFTRedisCacheUtils;
import com.hitebaas.data.NFTdataRedisCacheUtils;
import com.hitebaas.data.NftCache;
import com.hitebaas.data.NftDetails;
import com.hitebaas.service.NftCacheUtil;


@Service
@Transactional
public class NftCacheUtilImpl implements NftCacheUtil {

	@Autowired
	private NFTdataRedisCacheUtils nftdataUtil;
	
	@Autowired
	private NFTRedisCacheUtils nftUtil;
	
	@Autowired
	private NFTCacheUtil nFTCacheUtil;
	
	@Autowired
	private NFTDataCacheUtil nFTDataCacheUtil;
	
	@Override
	public void putNFTdataRedisCache(NftCache nft) {
		// TODO Auto-generated method stub
		nftdataUtil.putNftCache(nft);
	}

	@Override
	public NftCache getNFTdataRedisCache(String addr) {
		// TODO Auto-generated method stub
		return nftdataUtil.getNftredisCache(addr);
	}

	@Override
	public void putNFTRedisCache(NftCache nft) {
		// TODO Auto-generated method stub
		nftUtil.putNftCache(nft);
	}

	@Override
	public NftCache getNFTRedisCache(String addr) {
		// TODO Auto-generated method stub
		return  nftUtil.getNftredisCache(addr);
	}

	@Override
	public void putNFTCacheUtil(List<NftDetails> n) {
		// TODO Auto-generated method stub
		nFTCacheUtil.putNftCache(n);
	}

	@Override
	public List<NftDetails> getNftDetails() {
		// TODO Auto-generated method stub
		return nFTCacheUtil.getNftDetails();
	}

	@Override
	public void putNFTDataCacheUtil(List<NftDetails> n) {
		// TODO Auto-generated method stub
		nFTDataCacheUtil.putNftCache(n);
	}

	@Override
	public List<NftDetails> getNFTDataCacheUtil() {
		// TODO Auto-generated method stub
		return nFTDataCacheUtil.getNftDetails();
	}

	@Override
	public ListNftDetails getNftDetailsByPage(int index, int count) {
		// TODO Auto-generated method stub
		return nFTCacheUtil.getNftDetailsByPage(index, count);
	}

	@Override
	public ListNftDataDetails getNftDataDetailsByPage(int index, int count) {
		// TODO Auto-generated method stub
		return nFTDataCacheUtil.getNftDataDetailsByPage(index, count);
	}

	@Override
	public ListNftDataDetails getNftDataDetailsByPage(String type, int index, int count) {
		// TODO Auto-generated method stub
		return nFTDataCacheUtil.getNftDataDetailsByPage( type,index, count);
	}
	
}
