package com.hitebaas.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.hitebaas.data.NoticeParams;
import com.hitebaas.data.black.BlockDownLoad;
import com.hitebaas.util.http.client.HttpClientHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hitebaas.client.MiningClient;
import com.hitebaas.crypt.ECDSAUtils;
import com.hitebaas.crypt.WalletAddress;
import com.hitebaas.data.dic.model.Dictionary;
import com.hitebaas.data.walletAddress.service.impl.WalletAddressServiceImpl;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.util.SQLManager;
import com.hitebaas.util.SQLiteHelper;
import com.hitebaas.util.init.InitUtils;

@Component
public class ApplicationStartup implements CommandLineRunner {

	
	private static final Logger logger = Logger.getLogger(ApplicationStartup.class);

	
	@Autowired
	private MiningClient miningClient;
	@Override
	public void run(String... args) throws Exception {


//		logger.error("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//		logger.info("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//		logger.info("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//		logger.info("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//		logger.info("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//		logger.error("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//		logger.info("i am go-------66666----dfsdfdsf---dsfsdfsdfsdfsdfsdfsdfsdfsdf-----dsfsdfdsfsdfsdfsdfsdfdsf------");
//
//
//		BlockDownLoad bdl = null;
//
//		String blockIndex = "580";
//
//		String ip = "139.186.77.248";
//
//		NoticeParams np = new NoticeParams(blockIndex, ip, "");
//
//		bdl = HttpClientHelper.downLoadFastDfsBlock(ip,HiteBassConstant.HTTP_PORT, np);
//
//		logger.info("i am go-------66666------------------" + bdl.getFileString());
//		logger.info("i am go-------444444------------------" + bdl.getFileString());
//		logger.info("i am go--------444333-----------------" + bdl.getFileString());
//		logger.info("i am go---------32222----------------" + bdl.getFileString());
//		logger.info("i am go----------1111---------------" + bdl.getFileString());
//		logger.info("i am go----------6666---------------" + bdl.getFileString());




		// TODO Auto-generated method stub
		System.out.println(123123);
		init();
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");

		/*MiningThread.mining = true;
		MiningThread mt = new MiningThread();
		mt.start();*/
		//MiningClient mining=new MiningClient();
		miningClient.Mining();
	}
	
	
	

	private static void init() throws  Exception {
		dbInit();
		intiDic();
		List<WalletAddress> was =WalletAddressServiceImpl.queryWalletAddress();
		if(was==null ||was.size()<0) {
			WalletAddress wa=ECDSAUtils.genWalletAddresst();
			WalletAddressServiceImpl.save(wa);
		}
	}
	private static void intiDic() throws Exception {
		Dictionary dic = InitUtils.intiDic();
		
		dic = InitUtils.intiMainChainDic();
		if(dic == null) {
			throw new Exception(  HiteBassConstant.INIT_DIC_BLOCKINDEX_CODE_MSG);
		}
		dic = InitUtils.intiDifficulty();
		if(dic == null) {
			throw new Exception( HiteBassConstant.INIT_DIC_BLOCKINDEX_CODE_MSG);
		}
		dic = InitUtils.intiZipIndex();
		if(dic == null) {
			throw new Exception( "初始化压缩序号出错");
		}
	}
	
	/**
	 * 旧tok钱包的创建表sql
	 * @throws SQLException
	 */
	private static void dbInit() throws SQLException {
		Connection connection = null;
		try {
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			Statement statement=connection.createStatement(); 
			//statement.executeUpdate(SQLManager.getBlockSql("0"));  
			statement.executeUpdate(SQLManager.getPendingSql());  
			statement.executeUpdate(SQLManager.getFriendsSql()); 
			statement.executeUpdate(SQLManager.getCollectionSql()); 
			statement.executeUpdate(SQLManager.getTokenAddressSql()); 
			statement.executeUpdate(SQLManager.getContract()); 
			statement.executeUpdate(SQLManager.getContractDatil()); 
			statement.executeUpdate(SQLManager.getDicSql()); 
			statement.executeUpdate(SQLManager.getIpAddressSql()); 
			statement.executeUpdate(SQLManager.getWalletAddressSql()); 
			statement.executeUpdate(SQLManager.getCreateContractSql()); 
			statement.executeUpdate(SQLManager.getContractToContractRecordsSql()); 
			connection.commit();
		} catch (Exception e) {
			if(connection != null)
				connection.rollback();
			logger.error("init ERROR ："+e.getMessage());
		}finally {
			SQLiteHelper.close(connection);
		}
	}
	
}
