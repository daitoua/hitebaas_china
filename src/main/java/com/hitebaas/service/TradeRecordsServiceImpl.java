package com.hitebaas.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.hitebaas.common.exception.ErrorInfo;
import com.hitebaas.common.exception.TokException;
import com.hitebaas.crypt.WalletAddress;
import com.hitebaas.data.black.Block;
import com.hitebaas.data.black.service.impl.BlockServiceImpl;
import com.hitebaas.data.trade.TradeBody;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.entity.TradeRecords;
import com.hitebaas.util.SQLiteHelper;



public class TradeRecordsServiceImpl {
	
	private static final Logger logger = Logger.getLogger(TradeRecordsServiceImpl.class);

	
	public static void save(TradeRecords tradeRecords) throws TokException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [tradeRecords]([blockIndex],[tradeNo],[from],[amount],[to],[gas],[tradeTime],[tradeStatus],[confirmNumber],[amountType],[contractName])values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tradeRecords.getBlockIndex());
			statement.setString(2, tradeRecords.getTradeNo());
			statement.setString(3, tradeRecords.getFrom());
			statement.setString(4, tradeRecords.getAmount());
			statement.setString(5, tradeRecords.getTo());
			statement.setString(6, tradeRecords.getGas());
			statement.setString(7, tradeRecords.getTradeTime());
			statement.setString(8, tradeRecords.getTradeStatus());
			statement.setString(9, tradeRecords.getConfirmNumber());
			statement.setString(10, tradeRecords.getAmountType());
			statement.setString(11, HiteBassConstant.TOK_CONTRACT_NUMBER);

			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			logger.error("TradeRecordsServiceImpl.save() ERROR ："+e.getMessage(), e);
			throw new TokException(ErrorInfo.TRADE_DB_RECORD_FAILTURE_CODE, ErrorInfo.TRADE_DB_RECORD_FAILTURE_CODE_MSG, e.getMessage());
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	public static void delete(String tradeNo) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "Delete from [tradeRecords] where tradeNo="+tradeNo;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("TradeRecordsServiceImpl.delete() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		
	}
	
	public static boolean deleteTradeRecords(List<TradeRecords> oldTrs) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement("delete from [tradeRecords] where [tradeNo]=?;");  
			for(int i = 0; i< oldTrs.size(); i++) {
				statement.setString(1, oldTrs.get(i).getTradeNo());
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
				logger.error("PendingServiceImpl.deletePendings() ERROR ："+e.getMessage());

			}
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}	
	/**
	 * query top 100  order by tradeTime
	 * @return
	 */
	public static List<TradeRecords> queryTradeRecords() {
		List<TradeRecords> trs = new ArrayList<TradeRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [tradeRecords] order by tradeTime desc limit 100";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				TradeRecords tr = new TradeRecords();
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setAmount(rs.getString("amount"));
				tr.setFrom(rs.getString("from"));
				tr.setGas(rs.getString("gas"));
				tr.setTo(rs.getString("to"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setAmountType(rs.getString("amountType"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("TradeRecordsServiceImpl.queryTradeRecords() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
	
	
	
	public static List<TradeRecords> queryTradeRecordsNormal() {
		List<TradeRecords> trs = new ArrayList<TradeRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [tradeRecords] where [tradeStatus]='"+TradeRecords.TRADESTATUS_NORMAL+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				TradeRecords tr = new TradeRecords();
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setAmount(rs.getString("amount"));
				tr.setFrom(rs.getString("from"));
				tr.setGas(rs.getString("gas"));
				tr.setTo(rs.getString("to"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setAmountType(rs.getString("amountType"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("TradeRecordsServiceImpl.queryTradeRecords() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
	
	public static List<TradeRecords> queryTradeRecordsByBlockIndex(List<Block> storeOld) {
		List<TradeRecords> trs = new ArrayList<TradeRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			StringBuffer sb = new StringBuffer("select * from [tradeRecords] where [blockIndex] in(");
			for(int i = 0;i < storeOld.size(); i++) {
				if(i != 0) {
					sb.append(",");
				}
				sb.append("'" + storeOld.get(i).getBlockIndex() + "'");
			}
			sb.append(")");
			sb.append(" and [tradeStatus] !='" + TradeRecords.TRADESTATUS_FAILURE + "'");
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				TradeRecords tr = new TradeRecords();
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setAmount(rs.getString("amount"));
				tr.setFrom(rs.getString("from"));
				tr.setGas(rs.getString("gas"));
				tr.setTo(rs.getString("to"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setAmountType(rs.getString("amountType"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("TradeRecordsServiceImpl.queryTradeRecords() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
	
	public static TradeRecords queryTradeRecordsByTradeNo(String tradeNo) {
		TradeRecords tr = null;
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [tradeRecords] where [tradeNo] ='" + tradeNo + "'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				tr = new TradeRecords();
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setAmount(rs.getString("amount"));
				tr.setFrom(rs.getString("from"));
				tr.setGas(rs.getString("gas"));
				tr.setTo(rs.getString("to"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setAmountType(rs.getString("amountType"));
				tr.setStatusDescription(rs.getString("statusDescription"));
				break;
			}
			return tr;
		} catch (Exception e) {
			logger.error("TradeRecordsServiceImpl.queryTradeRecordsByTradeNo() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return null;
	}
	public static void updateALLRecordByBlock(Block b) {
		
		
	}
	/**
	 * 更新blockIndex(或生成)交易记录。
	 * @param b
	 */
	public static void updateRecordByBlock(Block b, List<WalletAddress> was) {
		//获得本块信息
		Map<String, TradeBody> trademap = BlockServiceImpl.getBlockFileMapInfo(b);
		//转钱包地址
		List<String> addressList = new ArrayList<String>();
		for(WalletAddress wa : was) {
			addressList.add(wa.getAddress());
		}
		for(Entry<String, TradeBody> entry : trademap.entrySet()) {
			TradeBody tb = entry.getValue();
			if(addressList.contains(tb.getFrom())) {
				try {
					TradeRecords tr22 = queryTradeRecordsByTradeNo(tb.getTradeNo());
					if(tr22 == null) {
						
						saveTradeRecords(tb, b, TradeRecords.AMOUNTTYPE_REDUCE);
					}
				}catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
			if(addressList.contains(tb.getTo()) && !addressList.contains(tb.getFrom())){
				//如果钱包地址不存在交易里面-新增交易
				try {
					TradeRecords tr22 = queryTradeRecordsByTradeNo(tb.getTradeNo());
					if(tr22 == null) {
						saveTradeRecords(tb, b, TradeRecords.AMOUNTTYPE_PLUS);
					}
				} catch (TokException e) {
					logger.warn(e.getMessage(), e);
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}
	/**
	 * 新增交易记录，转账是调用。收到区块时调用
	 * @param tb
	 * @param b
	 * @param amountType
	 * @param confirmNumber 确认数
	 * @throws TokException
	 */
	public static void saveTradeRecords(TradeBody tb, Block b, String amountType) throws TokException{
		TradeRecords record = new TradeRecords();
		record.setAmount(tb.getTradeAmount());
		record.setFrom(tb.getFrom());
		record.setTradeTime(tb.getTradeTime());
		record.setGas(tb.getGas());
		record.setTo(tb.getTo());
		record.setTradeNo(tb.getTradeNo());
		record.setTradeTime(tb.getTradeTime());
		record.setContractName(HiteBassConstant.TOK_CONTRACT_NUMBER);
		record.setTradeStatus(TradeRecords.TRADESTATUS_NORMAL);
		if(b != null) {
			record.setBlockIndex(b.getBlockIndex());
			record.setConfirmNumber(b.getCounter() + "");
			if(HiteBassConstant.BLOCK_COUNTER == b.getCounter()) {
				record.setTradeStatus(TradeRecords.TRADESTATUS_COMPLETE);
			}
		}else {
			record.setConfirmNumber("0");
		}
		record.setAmountType(amountType);
		TradeRecordsServiceImpl.save(record);
	}

	
	
	
	/**
	 * 更新区块的确认数
	 * @param blockIndex
	 * @throws SQLException 
	 */
	public static void updatBlockIndexByTradeNo(String tradeNo, String blockIndex) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [tradeRecords] set [blockIndex] = ? where tradeNo=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, blockIndex);
			statement.setString(2, tradeNo);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("TradeRecordsServiceImpl.updatBlockIndexByTradeNo() ERROR ："+e.getMessage(), e);

		}finally {
			/*if(connection != null)
				connection.close();*/
			SQLiteHelper.close(connection);
		}
	}
	/**
	 * 更新区块的确认数
	 * @param blockIndex
	 * @throws SQLException 
	 */
	public static void updateCounterByTradeNo(String tradeNo, int counter) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [tradeRecords] set [confirmNumber] = ? where tradeNo=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, counter);
			statement.setString(2, tradeNo);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("TradeRecordsServiceImpl.updateCounterByTradeNo() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
	}
	public static void updateTradeRecordsFailureforSwitch(List<TradeRecords> trs) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			StringBuffer sb = new StringBuffer("update [tradeRecords] set [tradeStatus] = ?,[statusDescription]=? where [tradeNo] in(");
			for(int i=0;i<trs.size();i++) {
				if(i != 0) {
					sb.append(",");
				}
				sb.append("'" + trs.get(i).getTradeNo() + "'");
			}
			sb.append(")");
			PreparedStatement statement = connection.prepareStatement(sb.toString());
			statement.setString(1, TradeRecords.TRADESTATUS_FAILURE);
			statement.setString(2, HiteBassConstant.FAILURE_SWITCH_REASON);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("TradeRecordsServiceImpl.updateCounterByTradeNo() ERROR ："+e.getMessage(), e);
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	
	public static void updateTradeStatus(String tradeNo, String tradeStatus) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [tradeRecords] set [tradeStatus] = ? where tradeNo=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tradeStatus);
			statement.setString(2, tradeNo);
			statement.execute();
			connection.commit();
		}catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("TradeRecordsServiceImpl.updateTradeStatus() ERROR ："+e.getMessage(), e);
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	public static boolean batchUpdateTradeStatus(List<TradeRecords> oldTrs, String tradeStatus) throws SQLException {

		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement("update [tradeRecords] set [tradeStatus] = ? where tradeNo=?;");  
			for(int i = 0; i< oldTrs.size(); i++) {
				statement.setString(1, tradeStatus);
				statement.setString(2, oldTrs.get(i).getTradeNo());
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
				logger.error("PendingServiceImpl.deletePendings() ERROR ："+e.getMessage());

			}
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	
		
	}
	public static List<TradeRecords> queryOldTradeRecords() {
		List<TradeRecords> trs = new ArrayList<TradeRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from tradeRecords where tradeTime>=datetime('now','start of year','-1 year','-0 day') AND  tradeTime <datetime('now','start of year','+0 year','-1 day') ;";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				TradeRecords tr = new TradeRecords();
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setAmount(rs.getString("amount"));
				tr.setFrom(rs.getString("from"));
				tr.setGas(rs.getString("gas"));
				tr.setTo(rs.getString("to"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setAmountType(rs.getString("amountType"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("PendingServiceImpl.queryTradeRecords() ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
}
