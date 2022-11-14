package com.hitebaas.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hitebaas.data.trade.Contract;
import com.hitebaas.data.trade.ContractBaseRecords;
import com.hitebaas.util.SQLiteHelper;


public class ContractServiceImpl {

	
	private static final Logger logger = Logger.getLogger(ContractServiceImpl.class);
	
	public static void save(Contract c) {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [Contract]([contractName],[tokenAddress],[createAddress],[content],[icoLimitTime],[ep],[ep2],[unit],[control],[intervalPeriod],[intervalPeriod2],[createTime],[initializeAmount],[blockIndex],[confirmNumber],[tradeStatus],[enlargeLimitTime]) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, c.getContractName());
			statement.setString(2, c.getTokenAddress());
			statement.setString(3, c.getCreateAddress());
			statement.setString(4, c.getContent());
			statement.setString(5, c.getIcoLimitTime());
			statement.setString(6, c.getEp());
			statement.setString(7, c.getEp2());
			statement.setString(8, c.getUnit());
			statement.setString(9, c.getControl());
			statement.setString(10, c.getIntervalPeriod());
			statement.setString(11, c.getIntervalPeriod2());
			statement.setString(12, c.getCreateTime());
			statement.setString(13, c.getInitializeAmount());
			statement.setString(14, c.getBlockIndex());
			statement.setString(15, c.getConfirmNumber());
			statement.setString(16, c.getTradeStatus());
			statement.setString(17, c.getEnlargeLimitTime());
			statement.execute();
			connection.commit(); 
		} catch (ClassNotFoundException | SQLException e) {
			try {
				logger.error(e.getMessage(), e);
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
		} finally {
			SQLiteHelper.close(connection);
		}
	}
	/**
	 * 查询状态为normal的所有合约
	 * @return
	 */
	public static List<Contract> queryContractListByStatusNormal(){
		List<Contract> cs = new ArrayList<Contract>();
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [Contract] where [tradeStatus] = '"+ContractBaseRecords.TRADESTATUS_NORMAL+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Contract tr = new Contract();
				tr.setContractName(rs.getString("contractName"));
				tr.setTokenAddress(rs.getString("tokenAddress"));
				tr.setCreateAddress(rs.getString("createAddress"));
				tr.setContent(rs.getString("content"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setUnit(rs.getString("unit"));
				tr.setControl(rs.getString("control"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setCreateTime(rs.getString("createTime"));
				tr.setId(rs.getInt("id"));
				tr.setInitializeAmount(rs.getString("initializeAmount"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				cs.add(tr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return cs;
	}
	/**
	 * 更新合约确认数
	 * @param contractName  合约名称
	 * @param newCounter	确认数
	 * @param tradeStatus	状态（与交易一致）
	 */
	public static void updateComfirmNumber(String contractName, String newCounter, String tradeStatus) {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [Contract] set [confirmNumber]=? where [contractName]=?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newCounter);
			statement.setString(2,contractName);
			statement.execute();
			connection.commit(); 
		} catch (ClassNotFoundException | SQLException e) {
			try {
				logger.error(e.getMessage(), e);
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
		} finally {
			SQLiteHelper.close(connection);
		}
	}
	
	
	public static Contract queryContractByContractNumber(String contractName) {
		Contract tr = null;
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [Contract] where [contractName] = '"+contractName+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				tr = new Contract();
				tr.setContractName(rs.getString("contractName"));
				tr.setTokenAddress(rs.getString("tokenAddress"));
				tr.setCreateAddress(rs.getString("createAddress"));
				tr.setContent(rs.getString("content"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setUnit(rs.getString("unit"));
				tr.setControl(rs.getString("control"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setCreateTime(rs.getString("createTime"));
				tr.setId(rs.getInt("id"));
				tr.setInitializeAmount(rs.getString("initializeAmount"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				return tr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		
		return null;
	}

	public static Contract queryContractByUnit(String unit) {
		Contract tr = null;
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [Contract] where [unit] = '"+unit+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				 tr = new Contract();
				 tr.setContractName(rs.getString("contractName"));
				tr.setTokenAddress(rs.getString("tokenAddress"));
				tr.setCreateAddress(rs.getString("createAddress"));
				tr.setContent(rs.getString("content"));
				tr.setIcoLimitTime(rs.getString("icoLimitTime"));
				tr.setEp(rs.getString("ep"));
				tr.setEp2(rs.getString("ep2"));
				tr.setUnit(rs.getString("unit"));
				tr.setControl(rs.getString("control"));
				tr.setIntervalPeriod(rs.getString("intervalPeriod"));
				tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
				tr.setCreateTime(rs.getString("createTime"));
				tr.setId(rs.getInt("id"));
				tr.setInitializeAmount(rs.getString("initializeAmount"));
				tr.setBlockIndex(rs.getString("blockIndex"));
				tr.setConfirmNumber(rs.getString("confirmNumber"));
				tr.setTradeStatus(rs.getString("tradeStatus"));
				tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				return tr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		
		return null;
	}
	public static Contract queryContractByAdd(String createAddress) {
		Contract tr = null;
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [Contract] where [createAddress] = '"+createAddress+"'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				 tr = new Contract();
				 tr.setContractName(rs.getString("contractName"));
					tr.setTokenAddress(rs.getString("tokenAddress"));
					tr.setCreateAddress(rs.getString("createAddress"));
					tr.setContent(rs.getString("content"));
					tr.setIcoLimitTime(rs.getString("icoLimitTime"));
					tr.setEp(rs.getString("ep"));
					tr.setEp2(rs.getString("ep2"));
					tr.setUnit(rs.getString("unit"));
					tr.setControl(rs.getString("control"));
					tr.setIntervalPeriod(rs.getString("intervalPeriod"));
					tr.setIntervalPeriod2(rs.getString("intervalPeriod2"));
					tr.setCreateTime(rs.getString("createTime"));
					tr.setId(rs.getInt("id"));
					tr.setInitializeAmount(rs.getString("initializeAmount"));
					tr.setBlockIndex(rs.getString("blockIndex"));
					tr.setConfirmNumber(rs.getString("confirmNumber"));
					tr.setTradeStatus(rs.getString("tradeStatus"));
					tr.setEnlargeLimitTime(rs.getString("enlargeLimitTime"));
				return tr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		
		return null;
	}
	
}
