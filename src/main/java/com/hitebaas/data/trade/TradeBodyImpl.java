package com.hitebaas.data.trade;

import java.lang.reflect.Field;
import java.util.Map;

import com.hitebaas.entity.CreateContractTradeBody;

public class TradeBodyImpl {
	
	
	public static CreateContractTradeBody mapToCreateContractTradeBody(Map map) {
		CreateContractTradeBody tb = null;
		try {
			tb = new CreateContractTradeBody();
			Field[] fields = CreateContractTradeBody.class.getDeclaredFields();
			for(Field field : fields) {
				field.setAccessible(true);
				field.set(tb, map.get(field.getName()));
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return tb;
	}
	public static TradeBody mapToTradeBody(Map map) {
		TradeBody tb = null;
		try {
			tb = new TradeBody();
			Field[] fields = TradeBody.class.getDeclaredFields();
			for(Field field : fields) {
				field.setAccessible(true);
				field.set(tb, map.get(field.getName()));
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return tb;
	}
}
