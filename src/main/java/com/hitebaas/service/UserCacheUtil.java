package com.hitebaas.service;

import com.hitebaas.entity.copyright.User;

public interface UserCacheUtil {

	
	public void putUser(User u);
	
	public User getUser(int  id);
	
}
