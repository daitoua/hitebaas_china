package com.hitebaas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitebaas.data.PetCache;
import com.hitebaas.data.PetCacheUtils;
import com.hitebaas.service.PetCacheUtil;



@Service
@Transactional
public class PetCacheUtlImpl implements PetCacheUtil{

	
	@Autowired
	private PetCacheUtils petUtil;
	
	@Override
	public void putPetCacheUtil(PetCache pt) {
		// TODO Auto-generated method stub
		petUtil.putPetCache(pt);
	}

	@Override
	public PetCache getPutCacheUtil(String addr) {
		// TODO Auto-generated method stub
		return petUtil.getPetCache(addr);
	}

}
