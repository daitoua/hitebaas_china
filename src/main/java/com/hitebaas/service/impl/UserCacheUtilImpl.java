package com.hitebaas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitebaas.data.UserRedisCacheUtil;
import com.hitebaas.entity.copyright.User;
import com.hitebaas.service.UserCacheUtil;


@Service
@Transactional
public class UserCacheUtilImpl implements UserCacheUtil{

	@Autowired
	private UserRedisCacheUtil userRedisCacheUtil;
	
	
	
	
	@Override
	public void putUser(User u) {
		// TODO Auto-generated method stub
		userRedisCacheUtil.putUserCache(u);
	}

	@Override
	public User getUser(int id ) {
		// TODO Auto-generated method stub
		return userRedisCacheUtil.getUserCache(id);
	}

}
