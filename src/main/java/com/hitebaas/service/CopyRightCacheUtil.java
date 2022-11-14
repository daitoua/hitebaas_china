package com.hitebaas.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.hitebaas.data.NftCache;
import com.hitebaas.data.NftDetails;
import com.hitebaas.entity.PaperBean;


@Component
public class CopyRightCacheUtil {
	
private static final String PRE = "nft:data:";
	
	@Resource(name="masterRedisTemplate")
	private RedisTemplate<String,Object> jsonRedisTemplate;

	
	
	/**
	 * 根据用户id获得缓存里面的用户信息
	 * @param uId
	 * @return
	 */
	public NftCache getNftredisCache(String addr,String type) {
		try {
			Boolean b = jsonRedisTemplate.hasKey(PRE+type+":" + addr);
			if(b) {
				return (NftCache) jsonRedisTemplate.opsForValue().get(PRE+type+":" + addr);
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
		
			
		jsonRedisTemplate.opsForValue().set(PRE+ct.getTradeType()+":" +ct.getAddr(), ct);
	}
}
