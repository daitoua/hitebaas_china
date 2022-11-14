package com.hitebaas.data;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.hitebaas.entity.TradeCache;

/**
 * 用于将原来的ecache缓存改redis缓存
 * @author Administrator
 *
 */
@Component
public class NFTRedisCacheUtils {
	
private static final String PRE = "nft:trade:";
	
	@Resource(name="masterRedisTemplate")
	private RedisTemplate<String,Object> jsonRedisTemplate;

	
	
	/**
	 * 根据用户id获得缓存里面的用户信息
	 * @param uId
	 * @return
	 */
	public NftCache getNftredisCache(String addr) {
		try {
			Boolean b = jsonRedisTemplate.hasKey(PRE + addr);
			if(b) {
				return (NftCache) jsonRedisTemplate.opsForValue().get(PRE + addr);
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
	public void putNftCache(NftCache ct) {
		
			
		jsonRedisTemplate.opsForValue().set(PRE +ct.getAddr(), ct);
	}
}
