package com.hitebaas.data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class NFTDataCacheUtil {

	/**
	 * 数据类型合约数据详情
	 */
	
	

private static final String PRE = "nft:contact:data";
	


	@Resource(name="masterRedisTemplate")
	private RedisTemplate<String,NftDetails> nftJsonRedisTemplate;
	
	/**
	 * 根据用户id获得缓存里面的用户信息
	 * @param uId
	 * @return
	 */
	public List<NftDetails> getNftDetails() {
		try {
			Boolean b = nftJsonRedisTemplate.hasKey(PRE );
			if(b) {
				return  nftJsonRedisTemplate.opsForList().range(PRE, 0,100);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把用户信息保存到缓存
	 * @param userInfo
	 */
	public void putNftCache(List<NftDetails> ct) {
		
		nftJsonRedisTemplate.opsForList().rightPushAll(PRE, ct);
	}
	
	public ListNftDataDetails getNftDataDetailsByPage(String type,int index, int count) {
		
		int start = index * count;
		int end = start + count -1;
		if(nftJsonRedisTemplate.hasKey(PRE+":"+type )) {
			Long len = nftJsonRedisTemplate.opsForList().size(PRE+":"+type);
			if(len > start) {
				if(end > len) {
					end = -1;
				}
				List<NftDetails> list= nftJsonRedisTemplate.opsForList().range(PRE+":"+type, start, end);
				ListNftDataDetails nfts=new ListNftDataDetails();
				nfts.setList(list);
				return nfts;
		}
		
	}
		return null;
	}
	
public ListNftDataDetails getNftDataDetailsByPage(int index, int count) {
		
		int start = index * count;
		int end = start + count -1;
		if(nftJsonRedisTemplate.hasKey(PRE )) {
			Long len = nftJsonRedisTemplate.opsForList().size(PRE);
			if(len > start) {
				if(end > len) {
					end = -1;
				}
				List<NftDetails> list= nftJsonRedisTemplate.opsForList().range(PRE, start, end);
				ListNftDataDetails nfts=new ListNftDataDetails();
				nfts.setList(list);
				return nfts;
		}
		
	}
		return null;
	}
}
