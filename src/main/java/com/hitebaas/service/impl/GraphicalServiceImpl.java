package com.hitebaas.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hitebaas.service.GraphicalService;
@Service
public class GraphicalServiceImpl implements GraphicalService {
	/**
	 * 保存图形验证码
	 */
	private Map<String, String> validcodeMap = new HashMap<>();
	@Override
	public Map<String, String> getValidcodeMap() {
		return validcodeMap;
	}

}
