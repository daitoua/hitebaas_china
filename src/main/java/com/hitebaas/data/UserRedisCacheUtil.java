package com.hitebaas.data;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.hitebaas.entity.copyright.User;


@Component
public class UserRedisCacheUtil {
	
	

private static final String PRE = "copyright:user:";
	
	@Resource(name="masterRedisTemplate")
	private RedisTemplate<String,Object> jsonRedisTemplate;

	
	
	/**
	 * 根据用户id获得缓存里面的用户信息
	 * @param uId
	 * @return
	 */
	public User getUserCache(int id) {
		try {
			Boolean b = jsonRedisTemplate.hasKey(PRE + id);
			if(b) {
				return (User) jsonRedisTemplate.opsForValue().get(PRE + id);
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
	public void putUserCache(User u) {
		
			
		jsonRedisTemplate.opsForValue().set(PRE +u.getId(), u);
	}
}
