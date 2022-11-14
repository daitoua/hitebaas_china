package com.hitebaas.data.walletAddress.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hitebaas.crypt.WalletAddress;
import com.hitebaas.util.SQLiteHelper;


public class WalletAddressServiceImpl {
	
	private static final Logger logger = Logger.getLogger(WalletAddressServiceImpl.class);

	
	/*public static boolean save(WalletAddress walletAddress,TokenAddress tok){
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [walletAddress] ([address],[publicKey],[privateKey],[createTime],[type],[amount]) values(?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, walletAddress.getAddress());
			statement.setString(2, walletAddress.getPublicKey());
			statement.setString(3, walletAddress.getPrivateKey());
			statement.setString(4, walletAddress.getCreateTime());
			statement.setString(5, walletAddress.getType());
			statement.setString(6, walletAddress.getAmount());
			statement.execute();
			
			String sql2 = "insert into [TokenAddress] ([blockIndex],[tradeNo],[amount],[createTime],[address],[publicKey],[privateKey],[freezeAmount] ) values(?,?,?,?,?,?,?,?);";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setString(1, tok.getBlockIndex());
			statement2.setString(2, tok.getTradeNo());
			statement2.setString(3, tok.getAmount());
			statement2.setString(4, tok.getCreateTime());
			statement2.setString(5, tok.getAddress());
			statement2.setString(6, tok.getPublicKey());
			statement2.setString(7, tok.getPrivateKey());
			statement2.setString(8, tok.getFreezeAmount());
			statement2.execute();
			connection.commit();
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("WalletAddressServiceImpl.save() ERROR ："+e.getMessage());

				}
			}
			logger.error("WalletAddressServiceImpl.save() ERROR ："+e.getMessage());

		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("WalletAddressServiceImpl.save() ERROR ："+e.getMessage());

				}
			}
		}
		return false;
	}*/
	public static boolean save(WalletAddress walletAddress){
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [walletAddress] ([address],[publicKey],[privateKey],[createTime],[type],[amount]) values(?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, walletAddress.getAddress());
			statement.setString(2, walletAddress.getPublicKey());
			statement.setString(3, walletAddress.getPrivateKey());
			statement.setString(4, walletAddress.getCreateTime());
			statement.setString(5, walletAddress.getType());
			statement.setString(6, walletAddress.getAmount());
			statement.execute();
			connection.commit();
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("WalletAddressServiceImpl.save(WalletAddress walletAddress) ERROR ："+e.getMessage());

				}
			}
			logger.error("WalletAddressServiceImpl.save(WalletAddress walletAddress) ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	private static Object lock = new Object();

	/**
	 * 更新钱包对应得地址余额
	 * @param address
	 * @param newAmount
	 * @throws TokException 
	 * @throws SQLException 
	 */
	public static void updateWalletAddress(String address, String newAmount) throws  Exception{
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [walletAddress] set [amount]=? where [address]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newAmount);
			statement.setString(2, address);
			statement.execute();
			connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
			if(connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			logger.error("WalletAddressServiceImpl.save() ERROR ："+e.getMessage());
			throw new Exception("更新钱包对应得地址余额失败");
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	/**
	 * 更新钱包key
	 * @param address
	 * @param privateKey
	 */
	public static void updatePrivateKeys(String address, String privateKey) {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [walletAddress] set [privateKey]=? where [address]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, privateKey);
			statement.setString(2, address);
			statement.execute();
			connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
			if(connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			logger.error("WalletAddressServiceImpl.updatePrivateKeys() ERROR ："+e.getMessage());
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	/*public static boolean batchUpdateWalletAddress(List<TradeRecords> trs) throws Exception{


		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement("update [walletAddress] set [amount]=? where [address]=?;");  
			for(int i = 0; i< trs.size(); i++) {
				statement.setString(1, trs.get(i).getAmount());
				statement.setString(2, trs.get(i).getFrom());
				statement.addBatch();
				if(i % 100 ==0) {
					statement.executeBatch();
					statement.clearBatch();
				}
			}
			statement.executeBatch();
			statement.clearBatch();
			connection.commit();
			return true;
		} catch (Exception e) {
			try {
				if(connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				logger.error("PendingServiceImpl.batchUpdateWalletAddress() ERROR ："+e.getMessage());

			}
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	
		
	
	}*/
	/**
	 * order by amount
	 * order by time
	 * @return
	 */
	public static List<WalletAddress> queryWalletAddress(){
		List<WalletAddress> was = new ArrayList<WalletAddress>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [walletAddress] order by [type] desc,[createTime] desc";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				WalletAddress wa = new WalletAddress();
				wa.setId(rs.getInt("id"));
				wa.setAddress(rs.getString("address"));
				wa.setCreateTime(rs.getString("createTime"));
				wa.setPrivateKey(rs.getString("privateKey"));
				wa.setPublicKey(rs.getString("publicKey"));
				wa.setType(rs.getString("type"));
				wa.setAmount(rs.getString("amount"));
				was.add(wa);
			}
		} catch (Exception e) {
			logger.error("WalletAddressServiceImpl.queryWalletAddress( ) ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return was;
	}

	/**
	 * 
	 * @param was
	 * @return
	 */
	public static boolean updateWallet(List<WalletAddress> was) {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			StringBuilder tokSql = new StringBuilder();
			StringBuilder contractSql = new StringBuilder();
			StringBuilder contractDetailSql = new StringBuilder();
			tokSql.append("update [walletAddress] set [amount] ='0.000000' where [id] in (");
			contractSql.append("update [TokenWallet] set [amount]  ='0.000000' where [address] in (");
			for(int i = 0; i < was.size(); i++) {
				if(i != 0) {
					tokSql.append(",");
					contractSql.append(",");
				}
				tokSql.append(was.get(i).getId());
				contractSql.append("'" + was.get(i).getAddress() + "'");
			}
			tokSql.append(")");
			contractSql.append(")");
			contractDetailSql.append("update [ContractDatil] set [tokBalance]='0.000000',[contractBalance]='0.00000'");
			Statement statement=connection.createStatement();
			statement.execute(tokSql.toString());
			statement.execute(contractSql.toString());
			statement.execute(contractDetailSql.toString());
			statement.execute("delete from tradeRecords");
			statement.execute("delete from dictionary");
			statement.execute("delete from pending");
			statement.execute("delete from collection");
			//2018-08-07 增加5个交易重启后的删除
			statement.execute("delete from contractToContractRecords");
			statement.execute("delete from contractToContractAddressRecords");
			statement.execute("delete from ContractWithdrawRecords");
			statement.execute("delete from TokToContractAddressRecords");
			statement.execute("delete from CreateContractRecords");
			//2018-09-30增加4个时间银行交易的删除
			statement.execute("delete from timebankFBDTTradeRecord");
			statement.execute("delete from timebankTOKTradeRecord");
			statement.execute("delete from repaymentTradeRecord");
			statement.execute("delete from storageTradeRecord");
			connection.commit();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	
	/**
	 * order by amount
	 * order by time
	 * @return
	 */
	public static WalletAddress queryWalletAddressByAdd(String address){
		Connection connection = null;
		WalletAddress wa = new WalletAddress();

		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [walletAddress] where [address]='"+address+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				wa.setId(rs.getInt("id"));
				wa.setAddress(rs.getString("address"));
				wa.setCreateTime(rs.getString("createTime"));
				wa.setPrivateKey(rs.getString("privateKey"));
				wa.setPublicKey(rs.getString("publicKey"));
				wa.setType(rs.getString("type"));
				wa.setAmount(rs.getString("amount"));
				return wa;
			}
		} catch (Exception e) {
			logger.error("WalletAddressServiceImpl.queryWalletAddressByAdd( ) ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return null;
	}
	public static boolean updateWalletByName(String byName,String add) throws Exception {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [walletAddress] set [byname]=? where [address]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, byName);
			statement.setString(2, add);
			statement.execute();
			connection.commit();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			if(connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			logger.error("WalletAddressServiceImpl.save() ERROR ："+e.getMessage());
			throw new Exception("修改失败");
		}finally {
			SQLiteHelper.close(connection);
			return false;
			}
		
	}
		
}
