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
import com.hitebaas.data.black.Block;
import com.hitebaas.data.black.service.impl.BlockServiceImpl;
import com.hitebaas.data.trade.CreateContractRecords;
import com.hitebaas.data.trade.TradeBodyPool;
import com.hitebaas.entity.CreateContractTradeBody;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.util.SQLiteHelper;



public class CreateContractRecordsServiceImpl {
	
	private static final Logger logger = Logger.getLogger(CreateContractRecordsServiceImpl.class);

	
	public static void save(CreateContractRecords CreateContractRecords) throws TokException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [CreateContractRecords]"
					+ "([tradeNo],[tradeAmount],[contractNumber],[unit],[contractAddress],[paymentAddress],[incomeAddress],[gas],[initContractAmount],[icoLimitTime],[ep],[ep2],[tradeTime],[content],[control],[intervalPeriod],[intervalPeriod2],[blockIndex],[confirmNumber],[tradeStatus],[amountType],[enlargeLimitTime])"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, CreateContractRecords.getTradeNo());
			statement.setString(2, CreateContractRecords.getTradeAmount());
			statement.setString(3, CreateContractRecords.getContractNumber());
			statement.setString(4, CreateContractRecords.getUnit());
			statement.setString(5, CreateContractRecords.getContractAddress());
			statement.setString(6, CreateContractRecords.getPaymentAddress());
			statement.setString(7, CreateContractRecords.getIncomeAddress());
			statement.setString(8, CreateContractRecords.getGas());
			statement.setString(9, CreateContractRecords.getInitContractAmount());
			statement.setString(10, CreateContractRecords.getIcoLimitTime());
			statement.setString(11, CreateContractRecords.getEp());
			statement.setString(12, CreateContractRecords.getEp2());
			statement.setString(13, CreateContractRecords.getTradeTime());
			statement.setString(14, CreateContractRecords.getContent());
			statement.setString(15, CreateContractRecords.getControl());
			statement.setString(16, CreateContractRecords.getIntervalPeriod());
			statement.setString(17, CreateContractRecords.getIntervalPeriod2());
			statement.setString(18, CreateContractRecords.getBlockIndex());
			statement.setString(19, CreateContractRecords.getConfirmNumber());
			statement.setString(20, CreateContractRecords.getTradeStatus());
			statement.setString(21, CreateContractRecords.getAmountType());
			statement.setString(22, CreateContractRecords.getEnlargeLimitTime());
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			logger.error("CreateContractRecordsServiceImpl.save() ERROR ："+e.getMessage(), e);
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
			String sql = "Delete from [CreateContractRecords] where tradeNo="+tradeNo;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("CreateContractRecordsServiceImpl.delete() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		
	}
	
	public static boolean deleteCreateContractRecords(List<CreateContractRecords> oldTrs) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement("delete from [CreateContractRecords] where [tradeNo]=?;");  
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
	public static List<CreateContractRecords> queryCreateContractRecords() {
		List<CreateContractRecords> trs = new ArrayList<CreateContractRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [CreateContractRecords] order by tradeTime desc limit 100";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				CreateContractRecords tr = new CreateContractRecords();
				tr.setAmountType(rs.getString("amountType"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setContent(rs.getString("content"));
				tr.setContractAddress(rs.getString("contractAddress"));
				tr.setContractNumber(rs.getString("contractNumber"));
				tr.setControl(rs.getString("control"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setGas(rs.getString("gas"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setIncomeAddress(rs.getString("incomeAddress"));
				tr.setInitContractAmount(rs.getString("initContractAmount"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setPaymentAddress(rs.getString("paymentAddress"));
				tr.setStatusDescription(rs.getString("statusDescription"));
				tr.setTradeAmount(rs.getString("tradeAmount"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setUnit(rs.getString("unit"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("CreateContractRecordsServiceImpl.queryCreateContractRecords() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
	
	public static List<CreateContractRecords> queryCreateContractRecordsNormal() {
		List<CreateContractRecords> trs = new ArrayList<CreateContractRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [CreateContractRecords] where [tradeStatus]='"+CreateContractRecords.TRADESTATUS_NORMAL+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				CreateContractRecords tr = new CreateContractRecords();
				tr.setAmountType(rs.getString("amountType"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setContent(rs.getString("content"));
				tr.setContractAddress(rs.getString("contractAddress"));
				tr.setContractNumber(rs.getString("contractNumber"));
				tr.setControl(rs.getString("control"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setGas(rs.getString("gas"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setIncomeAddress(rs.getString("incomeAddress"));
				tr.setInitContractAmount(rs.getString("initContractAmount"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setPaymentAddress(rs.getString("paymentAddress"));
				tr.setStatusDescription(rs.getString("statusDescription"));
				tr.setTradeAmount(rs.getString("tradeAmount"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setUnit(rs.getString("unit"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("CreateContractRecordsServiceImpl.queryCreateContractRecords() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
	
	public static List<CreateContractRecords> queryCreateContractRecordsByBlockIndex(List<Block> storeOld) {
		List<CreateContractRecords> trs = new ArrayList<CreateContractRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			StringBuffer sb = new StringBuffer("select * from [CreateContractRecords] where [blockIndex] in(");
			for(int i = 0;i < storeOld.size(); i++) {
				if(i != 0) {
					sb.append(",");
				}
				sb.append("'" + storeOld.get(i).getBlockIndex() + "'");
			}
			sb.append(")");
			sb.append(" and [CreateContractRecords] !='" + CreateContractRecords.TRADESTATUS_FAILURE + "'");
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				CreateContractRecords tr = new CreateContractRecords();
				tr.setAmountType(rs.getString("amountType"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setContent(rs.getString("content"));
				tr.setContractAddress(rs.getString("contractAddress"));
				tr.setContractNumber(rs.getString("contractNumber"));
				tr.setControl(rs.getString("control"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setGas(rs.getString("gas"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setIncomeAddress(rs.getString("incomeAddress"));
				tr.setInitContractAmount(rs.getString("initContractAmount"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setPaymentAddress(rs.getString("paymentAddress"));
				tr.setStatusDescription(rs.getString("statusDescription"));
				tr.setTradeAmount(rs.getString("tradeAmount"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setUnit(rs.getString("unit"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("CreateContractRecordsServiceImpl.queryCreateContractRecords() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
	
	public static CreateContractRecords queryCreateContractRecordsByTradeNo(String tradeNo) {
		CreateContractRecords tr = null;
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [CreateContractRecords] where [tradeNo] ='" + tradeNo + "'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				tr = new CreateContractRecords();
				tr.setAmountType(rs.getString("amountType"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setContent(rs.getString("content"));
				tr.setContractAddress(rs.getString("contractAddress"));
				tr.setContractNumber(rs.getString("contractNumber"));
				tr.setControl(rs.getString("control"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setGas(rs.getString("gas"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setIncomeAddress(rs.getString("incomeAddress"));
				tr.setInitContractAmount(rs.getString("initContractAmount"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setPaymentAddress(rs.getString("paymentAddress"));
				tr.setStatusDescription(rs.getString("statusDescription"));
				tr.setTradeAmount(rs.getString("tradeAmount"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setUnit(rs.getString("unit"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				break;
			}
			return tr;
		} catch (Exception e) {
			logger.error("CreateContractRecordsServiceImpl.queryCreateContractRecordsByTradeNo() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
		return null;
	}
	/**
	 * 更新blockIndex(或生成)交易记录。
	 * @param b
	 */
	public static void updateRecordByBlock(TradeBodyPool tbp, Block b, List<String> addressList) {
		//获得本块信息
		Map<String, CreateContractTradeBody> cctbMap = tbp.getCctbMap();
		for(Entry<String, CreateContractTradeBody> entry : cctbMap.entrySet()) {
			CreateContractTradeBody tb = entry.getValue();
			CreateContractRecords tr22 = null;
			//支出的交易
			if(addressList.contains(tb.getPaymentAddress())) {
				//如果钱包地址存在交易里面
				//更新blockIndex字段
				try {
					tr22 = queryCreateContractRecordsByTradeNo(tb.getTradeNo());
					if(tr22 == null) {
						
						tr22 = saveCreateContractRecords(tb, b, CreateContractRecords.AMOUNTTYPE_REDUCE);
					}	
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
			if(!addressList.contains(tb.getPaymentAddress()) && addressList.contains(tb.getIncomeAddress())){
				//如果钱包地址不存在交易里面-新增交易
				try {
					tr22 = queryCreateContractRecordsByTradeNo(tb.getTradeNo());
					if(tr22 == null) {
						tr22 = saveCreateContractRecords(tb, b, CreateContractRecords.AMOUNTTYPE_PLUS);
					}
				} catch (TokException e) {
					logger.warn(e.getMessage(), e);
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
			//2018-08-04 增加“本地合约列表”。接收到区块里面有创建合约交易就添加到该表。该表新增字段区块编号、确认书
			/**
			 * 1、检查本地Contract表是否已经存在该合约
			 * 2、若不存在，则新增
			 * 3、改变其确认数
			 */
			//从本地contract列表里面查询是否有该合约，没有则保存
			try {
				if(tr22 == null) {
					tr22 = genCreateContractRecords(tb, b, CreateContractRecords.AMOUNTTYPE_PLUS);
				}
				BlockServiceImpl.saveLocalContractList(tr22, b);
			} catch (TokException e) {
				logger.warn(e.getMessage(), e);
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
	public static CreateContractRecords saveCreateContractRecords(CreateContractTradeBody tb, Block b, String amountType) throws TokException{
		CreateContractRecords record = genCreateContractRecords(tb, b, amountType);
		CreateContractRecordsServiceImpl.save(record);
		return record;
	}
	
	private static CreateContractRecords genCreateContractRecords(CreateContractTradeBody tb, Block b, String amountType) throws TokException{
		CreateContractRecords record = new CreateContractRecords();
		record.setContent(tb.getContent());
		record.setContractAddress(tb.getContractAddress());
		record.setContractNumber(tb.getContractNumber());
		record.setControl(tb.getControl());
		record.setEp(tb.getEp());
		record.setEp2(tb.getEp2());
		record.setGas(tb.getGas());
		record.setIcoLimitTime(tb.getIcoLimitTime());
		record.setEnlargeLimitTime(tb.getEnlargeLimitTime());
		record.setIncomeAddress(tb.getIncomeAddress());
		record.setInitContractAmount(tb.getInitContractAmount());
		record.setIntervalPeriod(tb.getIntervalPeriod());
		record.setIntervalPeriod2(tb.getIntervalPeriod2());
		record.setPaymentAddress(tb.getPaymentAddress());
		record.setTradeAmount(tb.getTradeAmount());
		record.setTradeNo(tb.getTradeNo());
		record.setTradeTime(tb.getTradeTime());
		record.setUnit(tb.getUnit());
		record.setTradeStatus(CreateContractRecords.TRADESTATUS_NORMAL);
		if(b != null) {
			record.setBlockIndex(b.getBlockIndex());
			record.setConfirmNumber(b.getCounter() + "");
			if(HiteBassConstant.BLOCK_COUNTER == b.getCounter()) {
				record.setTradeStatus(CreateContractRecords.TRADESTATUS_COMPLETE);
			}
		}else {
			record.setConfirmNumber("0");
		}
		record.setAmountType(amountType);
		return record;
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
			String sql = "update [CreateContractRecords] set [blockIndex] = ? where tradeNo=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, blockIndex);
			statement.setString(2, tradeNo);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("CreateContractRecordsServiceImpl.updatBlockIndexByTradeNo() ERROR ："+e.getMessage(), e);

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
			String sql = "update [CreateContractRecords] set [confirmNumber] = ? where tradeNo=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, counter);
			statement.setString(2, tradeNo);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("CreateContractRecordsServiceImpl.updateCounterByTradeNo() ERROR ："+e.getMessage(), e);

		}finally {
			SQLiteHelper.close(connection);
		}
	}
	public static void updateCreateContractRecordsFailureforSwitch(List<CreateContractRecords> trs) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			StringBuffer sb = new StringBuffer("update [CreateContractRecords] set [tradeStatus] = ?,[statusDescription]=? where [tradeNo] in(");
			for(int i=0;i<trs.size();i++) {
				if(i != 0) {
					sb.append(",");
				}
				sb.append("'" + trs.get(i).getTradeNo() + "'");
			}
			sb.append(")");
			PreparedStatement statement = connection.prepareStatement(sb.toString());
			statement.setString(1, CreateContractRecords.TRADESTATUS_FAILURE);
			statement.setString(2, HiteBassConstant.FAILURE_SWITCH_REASON);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("CreateContractRecordsServiceImpl.updateCounterByTradeNo() ERROR ："+e.getMessage(), e);
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	
	public static void updateTradeStatus(String tradeNo, String tradeStatus) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [CreateContractRecords] set [tradeStatus] = ? where tradeNo=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tradeStatus);
			statement.setString(2, tradeNo);
			statement.execute();
			connection.commit();
		}catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("CreateContractRecordsServiceImpl.updateTradeStatus() ERROR ："+e.getMessage(), e);
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	public static boolean batchUpdateTradeStatus(List<CreateContractRecords> oldTrs, String tradeStatus) throws SQLException {

		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement("update [CreateContractRecords] set [tradeStatus] = ? where tradeNo=?;");  
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
	public static List<CreateContractRecords> queryOldCreateContractRecords() {
		List<CreateContractRecords> trs = new ArrayList<CreateContractRecords>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from CreateContractRecords where tradeTime>=datetime('now','start of year','-1 year','-0 day') AND  tradeTime <datetime('now','start of year','+0 year','-1 day') ;";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				CreateContractRecords tr = new CreateContractRecords();
				tr.setAmountType(rs.getString("amountType"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setContent(rs.getString("content"));
				tr.setContractAddress(rs.getString("contractAddress"));
				tr.setContractNumber(rs.getString("contractNumber"));
				tr.setControl(rs.getString("control"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setGas(rs.getString("gas"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setIncomeAddress(rs.getString("incomeAddress"));
				tr.setInitContractAmount(rs.getString("initContractAmount"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setPaymentAddress(rs.getString("paymentAddress"));
				tr.setStatusDescription(rs.getString("statusDescription"));
				tr.setTradeAmount(rs.getString("tradeAmount"));
				tr.setTradeNo(rs.getString("tradeNo"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setTradeTime(rs.getString("tradeTime"));
				tr.setUnit(rs.getString("unit"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("PendingServiceImpl.queryCreateContractRecords() ERROR ："+e.getMessage());

		}finally {
			SQLiteHelper.close(connection);
		}
		return trs;
	}
}
