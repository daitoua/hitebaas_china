package com.hitebaas.data.dic.service.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hitebaas.data.dic.model.Dictionary;
import com.hitebaas.util.SQLiteHelper;


public class DicServiceImpl {
	
	private static final Logger logger = Logger.getLogger(DicServiceImpl.class);

	
	public static boolean save(Dictionary dic) {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [dictionary]([module],[key],[value]) values(?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, dic.getModule());
			statement.setString(2, dic.getKey());
			statement.setString(3, dic.getValue());
			statement.execute();
			connection.commit(); 
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("DicServiceImpl.save() ERROR ："+e.getMessage());

				}
			}
			logger.error("DicServiceImpl.save() ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	//private static Map<String, Dictionary> dicMap = new HashedMap();
	public static Dictionary queryDic(String module, String key) {
		Dictionary dic = null;
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [dictionary] where [module]=? and [key]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, module);
			statement.setString(2, key);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				dic = new Dictionary();
				dic.setModule(rs.getString("module"));
				dic.setKey(rs.getString("key"));
				dic.setValue(rs.getString("value"));
			}
		} catch (Exception e) {
			logger.error("DicServiceImpl.queryDic() ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return dic;
	}
	public static List<Dictionary> queryAllDics(){
		List<Dictionary> list = new ArrayList<Dictionary>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [dictionary]";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Dictionary dic = new Dictionary();
				dic.setModule(rs.getString("module"));
				dic.setKey(rs.getString("key"));
				dic.setValue(rs.getString("value"));
				list.add(dic);
			}
		} catch (Exception e) {
			logger.error("DicServiceImpl.queryAllDics() ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return list;
	}
	
	public static boolean update(Dictionary dic) {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [dictionary] set [value] = ? where [module]=? and [key]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, dic.getValue());
			statement.setString(2, dic.getModule());
			statement.setString(3, dic.getKey());
			statement.execute();
			connection.commit();
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e.getMessage(), e);

				}
			}
			logger.error(e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	public static boolean updateDicBlockIndex(String blockIndex) {
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.BLOCKINDEX_KEY);
		BigInteger bblockIndex = new BigInteger(blockIndex);
		BigInteger bvalue =  new BigInteger(dic.getValue());
		
		if(bblockIndex.compareTo(bvalue) > 0) {
			dic.setValue(blockIndex);
			return DicServiceImpl.update(dic);
		}
		return true;
	}
	public static boolean updateDicMainBockIndex(String blockIndex) {
		Dictionary dic = DicServiceImpl.queryDic(Dictionary.MODUAL_BLOCK, Dictionary.CURRENTBLOCKINDEX);
		BigInteger bblockIndex = new BigInteger(blockIndex);
		BigInteger bvalue =  new BigInteger(dic.getValue());
		
		if(bblockIndex.compareTo(bvalue) > 0) {
			dic.setValue(blockIndex);
			return DicServiceImpl.update(dic);
		}
		return true;
	}
}
