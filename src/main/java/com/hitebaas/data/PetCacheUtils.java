package com.hitebaas.data;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PetCacheUtils {

	
private static final String PRE = "pet:trade:";
	
	@Resource(name="masterRedisTemplate")
	private RedisTemplate<String,Object> jsonRedisTemplate;

	
	/**
	 * 根据用户id获得缓存里面的用户信息
	 * @param uId
	 * @return
	 */
	public PetCache getPetCache(String addr) {
		try {
			Boolean b = jsonRedisTemplate.hasKey(PRE + addr);
			if(b) {
				return (PetCache) jsonRedisTemplate.opsForValue().get(PRE + addr);
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
	public void putPetCache(PetCache ct) {
		
		jsonRedisTemplate.opsForValue().set(PRE +ct.getAddr(), ct);
		
	}
}
