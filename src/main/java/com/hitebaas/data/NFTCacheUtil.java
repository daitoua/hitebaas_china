package com.hitebaas.data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class NFTCacheUtil {
	/**
	 * 货币类型合约详情缓存
	 */
		
	private static final String PRE = "nft:contact:coin";
		


		@Resource(name="masterRedisTemplate")
		private RedisTemplate<String,NftDetails> nftJsonRedisTemplate;
		
		/**
		 * 根据用户id获得缓存里面的用户信息
		 * @param uId
		 * @return
		 */
		public List<NftDetails>  getNftDetails() {
			try {
				Boolean b = nftJsonRedisTemplate.hasKey(PRE );
				if(b) {
					return nftJsonRedisTemplate.opsForList().range(PRE, 0, 100);
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
		public void putNftCache(List<NftDetails> list) {
			
				
			nftJsonRedisTemplate.opsForList().rightPushAll(PRE, list);
		}
		
		public ListNftDetails getNftDetailsByPage(int index, int count) {
			
			int start = index * count;
			int end = start + count -1;
			if(nftJsonRedisTemplate.hasKey(PRE )) {
				Long len = nftJsonRedisTemplate.opsForList().size(PRE);
				if(len > start) {
					if(end > len) {
						end = -1;
					}
					List<NftDetails> list= nftJsonRedisTemplate.opsForList().range(PRE, start, end);
					ListNftDetails nfts=new ListNftDetails();
					nfts.setList(list);
					return nfts;
			}
			
		}
			return null;
		}
}
