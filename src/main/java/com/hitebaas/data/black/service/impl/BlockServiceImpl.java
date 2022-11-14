package com.hitebaas.data.black.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hitebaas.data.black.Block;
import com.hitebaas.data.trade.Contract;
import com.hitebaas.data.trade.ContractBaseRecords;
import com.hitebaas.data.trade.CreateContractRecords;
import com.hitebaas.data.trade.TradeBody;
import com.hitebaas.data.trade.TradeBodyImpl;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.service.ContractServiceImpl;
import com.hitebaas.util.DataUtils;
import com.hitebaas.util.FastDFSClient;
import com.hitebaas.util.SQLManager;
import com.hitebaas.util.SQLiteHelper;
import com.hitebaas.utils.FtpUtil;
import com.hitebaas.utils.HadoopUtils;



@Component
public class BlockServiceImpl{
	
	
	private static final Logger logger = Logger.getLogger(BlockServiceImpl.class);
	public static void checkBlockTable(String blockIndex) {
		Connection connection = null;
		try {
			String tableIndex = DataUtils.getBlockSerial(blockIndex);
			String sql0 = SQLManager.getBlockSql(tableIndex);
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			Statement statement0 =connection.createStatement(); 
			statement0.executeUpdate(sql0);
			connection.commit();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(e.getMessage(), e);
			}
			logger.error(e.getMessage(), e);
		} finally {
			SQLiteHelper.close(connection);
		}
	}
	
	
	/**
	 * 保存tok区块文件信息
	 * @param bdl
	 * @return
	 */
	public static boolean saveFastDfsBlockFile(String blockString,Block block ) {
		//String blockString = new Gson().toJson(map);
		boolean isWrite=false;
		try {
			
        	FastDFSClient f= new FastDFSClient(HiteBassConstant.FASTDFS_URL);
        	
        	String path=f.uploadFile(blockString.getBytes(), "tok");
        	block.setPath(path);
			
			//DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".tok"), blockString);
			//String str= new Gson().toJson(block, Block.class);
			//DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".txt"), str);
			isWrite=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		
		return isWrite;
	}
	
	
	/**
	 * 保存tok区块文件信息
	 * @param bdl
	 * @return
	 */
	public static boolean hadoopSaveBlockFile(String blockString,Block block ) {
		//String blockString = new Gson().toJson(map);
		boolean isWrite=false;
		try {
			HadoopUtils utils = new HadoopUtils();
			InputStream result = IOUtils.toInputStream(blockString);
			utils.Upload(result, block.getPath()+".tok");
			//DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".tok"), blockString);
			//String str= new Gson().toJson(block, Block.class);
			//DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".txt"), str);
			isWrite=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		
		return isWrite;
	}
	
	public static boolean save(Block block)  {
		Connection connection = null;
		try {
			String tableIndex = DataUtils.getBlockSerial(block.getBlockIndex());
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into [block" + tableIndex + "]([blockIndex],[headHash],[path],[createTime],[endHash],[counter],[randomNumber],[onMingChain],[merkleRoot],[fileMD5],[count]) values(?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, block.getBlockIndex() + "");
			statement.setString(2, block.getHeadHash());
			statement.setString(3, block.getPath());
			statement.setString(4, block.getCreateTime());
			statement.setString(5, block.getEndHash());
			statement.setInt(6, block.getCounter());
			statement.setString(7, block.getRandomNumber());
			statement.setInt(8, block.getOnMingChain());
			statement.setString(9, block.getMerkleRoot());
			statement.setString(10, block.getFileMD5().toString());
			statement.setInt(11, block.getCount());
			statement.execute();
			connection.commit();
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("CollectionServiceImpl.save() ERROR ："+e.getMessage());

				
				}
			}
			logger.error("save ERROR ："+e.getMessage());
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	/*
	 * 保存tok区块文件信息
	 * @param bdl
	 * @return
	 */
	public static boolean saveBlockFile2(String fileString,Block block ) {
		//String blockString = new Gson().toJson(map);
		boolean isWrite=false;
		try {
			
				
				DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".tok"), fileString);
				DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".txt"), block.toString());

			
			isWrite=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		
		return isWrite;
	}
	
	
	/**
	 * 保存tok区块文件信息
	 * @param bdl
	 * @return
	 */
	public static boolean saveBlockFile(String blockString,Block block ) {
		//String blockString = new Gson().toJson(map);
		boolean isWrite=false;
		try {
			DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".tok"), blockString);
			DataUtils.writeFile(DataUtils.getRelativePath(block.getPath()+".txt"), block.toString());
			isWrite=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		
		return isWrite;
	}
	

	
	
	
	private static String getBlockFile(Block b) {
		return DataUtils.readFile(DataUtils.getRelativePath(b.getPath()));
	}

	

	
	

	/**
	 * @param blockIndex
	 * @return
	 */
	public static List<Block> queryBlockByBlockIndex(String blockIndex) {
		List<Block> list = new ArrayList<Block>();
		Connection connection = null;
		try {
			String tableIndex = DataUtils.getBlockSerial(blockIndex);
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [block" + tableIndex + "] where blockIndex='" + blockIndex + "' order by blockIndex desc";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			Block block =null;
			while (rs.next()) {
				block = new Block();
				block.setId(rs.getInt("ID"));
				block.setBlockIndex(rs.getString("blockIndex"));
				block.setCounter(rs.getInt("counter"));
				block.setCreateTime(rs.getString("createTime"));
				block.setEndHash(rs.getString("endHash"));
				block.setHeadHash(rs.getString("headHash"));
				block.setPath(rs.getString("path"));
				block.setRandomNumber(rs.getString("randomNumber"));
				block.setOnMingChain(rs.getInt("onMingChain"));
				block.setMerkleRoot(rs.getString("merkleRoot"));
				block.setCount(rs.getInt("count"));
				Gson gson = new Gson();
				list.add(block);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			SQLiteHelper.close(connection);
		}
		return list;
	}
	/**
	 * 根据区块编号查询在主链上的那一块
	 * @param blockIndex
	 * @return
	 */
	public static Block queryBlockByBlockIndexOnMingChain(String blockIndex) {
		Connection connection = null;
		try {
			String tableIndex = DataUtils.getBlockSerial(blockIndex);
			connection = SQLiteHelper.getConnection();
			String sql = "select * from [block" + tableIndex + "] where blockIndex='" + blockIndex + "' and onMingChain='1'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			Block block =null;
			while (rs.next()) {
				block = new Block();
				block.setId(rs.getInt("ID"));
				block.setBlockIndex(rs.getString("blockIndex"));
				block.setCounter(rs.getInt("counter"));
				block.setCreateTime(rs.getString("createTime"));
				block.setEndHash(rs.getString("endHash"));
				block.setHeadHash(rs.getString("headHash"));
				block.setPath(rs.getString("path"));
				block.setRandomNumber(rs.getString("randomNumber"));
				block.setOnMingChain(rs.getInt("onMingChain"));
				block.setMerkleRoot(rs.getString("merkleRoot"));
				return block;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			SQLiteHelper.close(connection);
		}
		return null;
	}
	
	public static boolean updateCounter(Block block) {
		Connection connection = null;
		try {
			String tableIndex = DataUtils.getBlockSerial(block.getBlockIndex());
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [block" + tableIndex + "] set [counter] = ? where  [id] = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, block.getCounter());
			statement.setInt(2, block.getId());
			statement.execute();
			connection.commit();
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("CollectionServiceImpl.updateCounter() ERROR ："+e.getMessage());

				}
			}
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	/**
	 * 
	 * @return 查询所有block表名
	 */
	
	public static List<String> queryAllBlock(){
		Connection connection = null;
		List<String> strs=new ArrayList<String>();
	
		try {
			connection = SQLiteHelper.getConnection();
			String sql="SELECT name FROM sqlite_master WHERE type= 'table' and name like '%block%'";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				strs.add(rs.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return strs;
	}
	/**
	 * 
	 * @param blockName
	 * @return 查询表最后一条
	 */
	public static Block queryLastBlock(String blockName) {
		Connection connection = null;
		try { 
			connection = SQLiteHelper.getConnection();
			String sql = "select * from ["+blockName+"] order by id desc limit 0,1";
			Statement statement=connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			Block block =null;
			while (rs.next()) {
				block = new Block();
				block.setId(rs.getInt("ID"));
				block.setBlockIndex(rs.getString("blockIndex"));
				block.setCounter(rs.getInt("counter"));
				block.setCreateTime(rs.getString("createTime"));
				block.setEndHash(rs.getString("endHash"));
				block.setHeadHash(rs.getString("headHash"));
				block.setPath(rs.getString("path"));
				block.setRandomNumber(rs.getString("randomNumber"));
				block.setMerkleRoot(rs.getString("merkleRoot"));
				block.setCount(rs.getInt("count"));
				return block;
			}
		} catch (Exception e) {
			logger.error("CollectionServiceImpl.queryBlockByHeadHash() ERROR ："+e.getMessage());
		}finally {
			SQLiteHelper.close(connection);
		}
		return null;
	}
	public static Block getBlockObeject(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String blockStr = new String(bos.toByteArray());//get block object
				//Block block = (Block) SerializeHelper.derialize(blockStr);
				Block block = new Gson().fromJson(blockStr, Block.class);

			System.out.println("block-------------------:" + blockStr);

				return block;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockObeject() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockObeject() ERROR ："+e.getMessage());

				}
			}
		}
		return null;
	}
	
	public static ByteArrayOutputStream getOutputStream(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				
				return bos;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockObeject() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockObeject() ERROR ："+e.getMessage());

				}
			}
		}
		return null;
	}
	public static String getBlockFileStr(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String blockfileStr = new String(bos.toByteArray());//get block file str
				return blockfileStr;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

				}
			}
		}
		return "";
	}
	
	
	
	public static String getContractBlockFileStr(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String contractBlockFileStr = new String(bos.toByteArray());//get block file str
				return contractBlockFileStr;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

				}
			}
		}
		return "";
	}
	
	public static String getMaxBlockIndexStr(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
			
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String maxBlockIndex = new String(bos.toByteArray());//get block file str
				return maxBlockIndex;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getMaxBlockIndexStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getMaxBlockIndexStr() ERROR ："+e.getMessage());

				}
			}
		}
		return "";
	}
	
	/**
	 * 保存合约到本地合约列表(如果不存在)
	 * @param ccr
	 */
	public static void saveLocalContractList(CreateContractRecords ccr, Block block) {
		Contract contract = ContractServiceImpl.queryContractByContractNumber(ccr.getContractNumber());
		if(contract == null) {
			Contract c = new Contract();
			c.setContent(ccr.getContent());
			c.setContractName(ccr.getContractNumber());
			c.setControl(ccr.getControl());
			c.setCreateAddress(ccr.getPaymentAddress());
			c.setCreateTime(ccr.getTradeTime());
			c.setEp(ccr.getEp());
			c.setEp2(ccr.getEp2());
			c.setIcoLimitTime(ccr.getIcoLimitTime());
			c.setEnlargeLimitTime(ccr.getEnlargeLimitTime());
			c.setInitializeAmount(ccr.getInitContractAmount());
			c.setIntervalPeriod(ccr.getIntervalPeriod());
			c.setIntervalPeriod2(ccr.getIntervalPeriod2());
			c.setTokenAddress(ccr.getContractAddress());
			c.setUnit(ccr.getUnit());
			c.setBlockIndex(block.getBlockIndex());
			c.setConfirmNumber("0");
			c.setTradeStatus(ContractBaseRecords.TRADESTATUS_NORMAL);
			ContractServiceImpl.save(c);
		}
		
	}
	public static Map<String, TradeBody> getBlockFileMapInfo(Block block){
		String blockStr = BlockServiceImpl.getBlockFile(block);
		Map<String , Object> oMap = new Gson().fromJson(blockStr, Map.class);

		Map<String, TradeBody> tradeMap = new HashMap<String, TradeBody>();
		if(oMap!=null) {
			for(Entry<String, Object> entry : oMap.entrySet()) {
				tradeMap.put(entry.getKey(), TradeBodyImpl.mapToTradeBody( (Map) entry.getValue()));
			}
			oMap.clear();
		}
	
		
		return tradeMap;
	}	
	public static void fileSave(ZipInputStream zis,String name,String blockNo,ZipEntry ze) {
		ByteArrayOutputStream bos = null;
		FileOutputStream fileOutputStream = null;
		try {
			FtpUtil util = new FtpUtil();
			//if(zis.getNextEntry() != null) {
			String path = HiteBassConstant.BLOCK_ENTERPRISE_FILE_PATH_PREFIX +name+"//"+blockNo;
			String filePath=DataUtils.getRelativePath(path);
			File file =new File(filePath);    
			if(!file.exists() && !file .isDirectory()) {
				file.mkdirs();
				
			}
			file=new File(filePath+"//"+ze.getName());  
			file.createNewFile();    
			 fileOutputStream = new FileOutputStream(filePath+"//"+ze.getName());
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				fileOutputStream.write(bos.toByteArray());

			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getMaxBlockIndexStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getMaxBlockIndexStr() ERROR ："+e.getMessage());

				}
			}
		}
	}
/*	*//**
	 * 挖矿方法  更新确认数，并更新钱包
	 * @param b
	 *//*
	@Deprecated
	public static void updateBlockCounter(Block b) {
		String blockIndex = b.getBlockIndex();
		BigInteger currentIndex = new BigInteger(blockIndex);
		if(currentIndex.compareTo(BigInteger.ZERO) > 0) {
			Block block = findPreBlock(b);
			if(block != null) {
				if(block.getCounter() >= 0 && block.getCounter() < BtcConstant.BLOCK_COUNTER) {
					int newCounter = block.getCounter() + 1;
					block.setCounter(newCounter);
					BlockServiceImpl.updateCounter(block);
					if(newCounter < BtcConstant.BLOCK_COUNTER) {
						//use this block to update the wallet 
						updateBlockCounter(block);
					}else {
						//当该块确认数达到预定数值，更新钱包
						WalletAddressServiceImpl.updateWalletByConfirmedBlock(block, BtcConstant.WALLET_UPDATE_MODE);
					}
				}
			}
			
		}
	}
	public static void updateAllBlockCounter2(Block b, TradeAllRecords tradeAllRecords) {
		String blockIndex = b.getBlockIndex();
		BigInteger currentIndex = new BigInteger(blockIndex);
		if(currentIndex.compareTo(BigInteger.ZERO) >= 0) {
			Block block = findPreBlock(b);
			if(block != null) {
				//确认数<10，且不在主链上。
				if(block.getCounter() >= 0 && block.getCounter() < BtcConstant.BLOCK_COUNTER) {
					int newCounter = b.getCounter() + 1;//上一区块=本区块号+1。
					block.setCounter(newCounter);
					BlockServiceImpl.updateCounter(block);//更新区块确认数。
					//更新交易确认数。
					updateAllReadeRecordsCounter(block, tradeAllRecords, newCounter);
					if(newCounter < BtcConstant.BLOCK_COUNTER) {
						//use this block to update the wallet 
						updateAllBlockCounter2(block, tradeAllRecords);
					}
				}else {
					//当该块确认数达到预定数值，更新钱包。
					//2018-05-18 改为从交易记录更新钱包。
					//WalletAddressServiceImpl.updateWalletByConfirmedBlock(block, BtcConstant.WALLET_UPDATE_MODE);
				}
			}
		}
	}
	*//**
	 * 接收到区块分支切换更新确认数,兼容updateBlockCounter方法。
	 * @param b
	 *//*
	@Deprecated
	public static void updateBlockCounter2(Block b, List<TradeRecords> trs) {
		String blockIndex = b.getBlockIndex();
		BigInteger currentIndex = new BigInteger(blockIndex);
		if(currentIndex.compareTo(BigInteger.ZERO) >= 0) {
			Block block = findPreBlock(b);
			if(block != null) {
				//确认数<10，且不在主链上
				if(block.getCounter() >= 0 && block.getCounter() < BtcConstant.BLOCK_COUNTER) {
					int newCounter = b.getCounter() + 1;//上一区块=本区块号+1
					block.setCounter(newCounter);
					BlockServiceImpl.updateCounter(block);//更新区块确认数
					//更新交易确认数
					updateReadeRecordsCounter(block, trs, newCounter);
					if(newCounter < BtcConstant.BLOCK_COUNTER) {
						//use this block to update the wallet 
						updateBlockCounter2(block, trs);
					}
				}else {
					//当该块确认数达到预定数值，更新钱包，
					//2018-05-18 改为从交易记录更新钱包。
					//WalletAddressServiceImpl.updateWalletByConfirmedBlock(block, BtcConstant.WALLET_UPDATE_MODE);
				}
			}
		}
	}
	*//**
	 * 更新交易记录确认数，（达到确认数后）并更新钱包余额
	 * @param b
	 * @param tradeAllRecords
	 * @param newCounter
	 *//*
	private static void updateAllReadeRecordsCounter(Block b, TradeAllRecords tradeAllRecords, int newCounter) {
		//tok
		updateReadeRecordsCounter(b, tradeAllRecords.getTradeRecords(), newCounter);
		//高级合约
		updateContractToContractRecordsCounter(b, tradeAllRecords.getContractToContractRecords(), newCounter);//代币转代币
		updateContractToContractAddressRecordsCounter(b, tradeAllRecords.getContractToContractAddressRecords(), newCounter);//代币转代币地址
		updateContractWithdrawRecordsCounter(b, tradeAllRecords.getContractWithdrawRecords(), newCounter);//提现
		updateCreateContractRecordsCounter(b, tradeAllRecords.getCreateContractRecords(), newCounter);//创建合约
		updateTokToContractAddressRecordsCounter(b, tradeAllRecords.getTokToContractAddressRecords(), newCounter);//ico
		updateTimeBankRecordsCounter(b, tradeAllRecords.getTimebankAllRecords(), newCounter);//时间银行
		updateContractConvertTradeRecordsCounter(b,tradeAllRecords.getContractConvertRecord(),newCounter);
		updateCreateIntelligenceContractRecordsCounter(b, tradeAllRecords.getCreateIntelligenceContractRecords(), newCounter);
		updateIntelligenceContractMethodRecordsCounter(b, tradeAllRecords.getIntelligenceContractMethodRecords(), newCounter);
	}
	*//**
	 * ico交易记录确认数修改
	 * @param b
	 * @param ccrs
	 * @param newCounter
	 *//*
	private static void updateTokToContractAddressRecordsCounter(Block b, List<TokToContractAddressRecords> ttcars, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(TokToContractAddressRecords tr : ttcars) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					TokToContractAddressRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//如果确认数达到10，更新钱包
						WalletAddress waf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getFrom());//ico支付tok的地址
						TokenAddress waf0 = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getFrom(), tr.getContractName());//支付地址对应得合约地址
						//转出为合约地址。
						ContractDatil cd = ContractDatilServiceImpl.queryContractByAdd( tr.getContractName());//根据创建地址找到合约详情信息
						Contract contract = ContractServiceImpl.queryContractByContractNumber(tr.getContractName());//获得合约信息
						String control = contract.getControl();
						BigDecimal amount = new BigDecimal(tr.getAmount());//ico支付的tok币
						if(cd != null) {
							//合约存在、合约地址收入该 tok。并支出合约代币
							BigDecimal tokBalanceNew = new BigDecimal(cd.getTokBalance()).add(new BigDecimal(tr.getAmount()));//合约地址收入tok
							ContractDatilServiceImpl.updateContractDatilTokBalance(tr.getContractName(), tr.getTo(),  DataUtils.getAmountString(tokBalanceNew));//更新“我创建的合约”里面的tok余额
							//计算代币支出
							if('1' == control.charAt(0)) {
								//需要分期返利
								IntervalPeriod intervalPeriod = BlockBaseUtils.getIntervalPeriod(amount, contract.getIntervalPeriod());
								BigDecimal zoomCount = amount.multiply(intervalPeriod.getEp());//放大后的数量
								BigDecimal contractBalanceNew = new BigDecimal(cd.getContractBalance()).subtract(zoomCount);//合约地址支出放大后的代币
								//扣除放大后的代币数
								ContractDatilServiceImpl.updateContractDatilContractBalance(tr.getContractName(), tr.getTo(), DataUtils.getAmountString(contractBalanceNew));
							}else if('0' == control.charAt(0)){
								//不需要分期返利，放大倍数直接返回.
								BigDecimal zoomCount = amount.multiply(new BigDecimal(contract.getEp()));//没有分期返利，倍数直接取ep
								BigDecimal contractBalanceNew = new BigDecimal(cd.getContractBalance()).subtract(zoomCount);//合约地址支出放大后的代币
								//扣除放大后的代币数
								ContractDatilServiceImpl.updateContractDatilContractBalance(tr.getContractName(), tr.getTo(), DataUtils.getAmountString(contractBalanceNew));
							}
							TokToContractAddressRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(waf != null) {
							//修改ico交易记录为完成
							TokToContractAddressRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							//如果是马上返回
							if('0' == control.charAt(0)){
								//不需要分期返利，放大倍数直接返回.
								BigDecimal zoomCount = amount.multiply(new BigDecimal(contract.getEp()));//没有分期返利，倍数直接取ep
								BigDecimal contractBalanceNew = new BigDecimal(waf0.getAmount()).add(zoomCount);//合约地址支出放大后的代币
								//扣除放大后的代币数
								TokenAddressServiceImpl.updateWalletAddress(waf0.getAddress(), DataUtils.getAmountString(contractBalanceNew), tr.getContractName());
							}
						}
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 创建合约交易记录
	 * @param b
	 * @param ccrs
	 * @param newCounter
	 *//*
	private static void updateCreateContractRecordsCounter(Block b, List<CreateContractRecords> ccrs, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(CreateContractRecords tr : ccrs) {
			//2018-08-04修改创建记录，本地没有则添加该高级合约
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					CreateContractRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//如果确认数到达6。保存合约信息到 “本地合约列表”
						//saveLocalContractList(tr);//接收到就保存。此处不在6个缺人候保存
						//如果确认数达到10，更新钱包
						//如果合约钱包里面没有，则查询tok钱包，若tok钱包有，则合约钱包也应该有。则插入
						WalletAddress waf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getPaymentAddress());
						//是我创建的高级合约
						if(waf != null) {
							//我的合约钱包添加该代币
							TokenAddress taf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getPaymentAddress(), tr.getContractNumber());
							if(taf == null) {
								//合约钱包 添加该合约
								List<WalletAddress> was = WalletAddressServiceImpl.queryWalletAddress();//获取所有的tok地址。
								for(WalletAddress wa : was) {
									TokenAddress ta = new TokenAddress();
									ta.setAddress(wa.getAddress());
									ta.setAmount(DataUtils.getAmountString(BigDecimal.ZERO));
									ta.setContractName(tr.getContractNumber());
									ta.setCreateTime(tr.getTradeTime());
									ta.setContractAddress(tr.getContractAddress());
									TokenAddressServiceImpl.save(ta);
								}
							}
							//我的 “高级合约列表” 添加我创建的这个合约
							saveContractDatil(tr);
						}
						//创建合约，要支付到的固定地址
						WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getIncomeAddress());
						if(wat != null) {
							//收入
							BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getTradeAmount()));
							WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
							//这里的tok收入交易在  创建代币的页面展示。
							//TradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							CreateContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(waf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							CreateContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 提现交易记录
	 * @param b
	 * @param cwrs
	 * @param newCounter
	 *//*
	private static void updateContractWithdrawRecordsCounter(Block b, List<ContractWithdrawRecords> cwrs, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(ContractWithdrawRecords tr : cwrs) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					ContractWithdrawRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						if(ContractTradeBodyType.WITHDRAWTRADEBODY_TOK.equals(tr.getType())) {
							//如果确认数达到10，更新钱包
							WalletAddress waf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getFrom());
							//提现转出为代币地址。
							String to = tr.getFrom();
							WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(to);
							if(wat != null) {
								//提现地址收入
								BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount()));
								WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
								//合约地址支出
								ContractDatil cd = ContractDatilServiceImpl.queryContractByAdd( tr.getContractName());
								BigDecimal tokBalanceNew = new BigDecimal(cd.getTokBalance()).subtract(new BigDecimal(tr.getAmount()));
								ContractDatilServiceImpl.updateContractDatilTokBalance(tr.getContractName(), tr.getTo(),  DataUtils.getAmountString(tokBalanceNew));
								ContractWithdrawRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}
							if(waf != null) {
								//支出。支出转账时候就扣掉了，这里不做处理。
								ContractWithdrawRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}
						}else {
							//提现合约币
							//如果确认数达到10，更新钱包
							TokenAddress waf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getFrom(), tr.getContractName());
							//提现转出为代币地址。
							//String to = tr.getTo().substring(BtcConstant.CONTRACT_ADDRESS_PRE.length(), BtcConstant.CONTRACT_ADDRESS_PRE.length() + BtcConstant.TOKEN_ADDRESS_LENGTH);
							String to = tr.getFrom();
							TokenAddress wat = TokenAddressServiceImpl.queryTokenAddressByContractNumber(to, tr.getContractName());
							if(wat != null) {
								//1、合约地址是自己的，更新“我的合约表”的余额---支出
								BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount()));//计算出新的金额
								//提现地址增加收入
								TokenAddressServiceImpl.updateWalletAddress(tr.getFrom(), DataUtils.getAmountString(newAmount), tr.getContractName());
								//合约地址减少 该数值的币额
								ContractDatil cd = ContractDatilServiceImpl.queryContractByAdd( tr.getContractName());
								BigDecimal contractBalanceNew = new BigDecimal(cd.getContractBalance()).subtract(new BigDecimal(tr.getAmount()));
								ContractDatilServiceImpl.updateContractDatilContractBalance(tr.getContractName(), tr.getTo(),  DataUtils.getAmountString(contractBalanceNew));
								ContractWithdrawRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}
							if(waf != null) {
								//支出。支出转账时候就扣掉了，这里不做处理。
								ContractWithdrawRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}
						}
						
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 更新高级合约 代币转合约地址 的确认数
	 *//*
	private static void updateContractToContractAddressRecordsCounter(Block b, List<ContractToContractAddressRecords>  ctcars, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(ContractToContractAddressRecords tr : ctcars) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					ContractToContractAddressRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//如果确认数达到10，更新钱包
						TokenAddress waf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getFrom(), tr.getContractName());//支付地址对应得合约地址
						//转出为合约地址。
						//String to = tr.getTo().substring(BtcConstant.CONTRACT_ADDRESS_PRE.length(), BtcConstant.CONTRACT_ADDRESS_PRE.length() + BtcConstant.TOKEN_ADDRESS_LENGTH);
						String to = tr.getFrom();
						//合约地址支出
						ContractDatil cd = ContractDatilServiceImpl.queryContractByAdd( tr.getContractName());//根据创建地址找到合约详情信息
						Contract contract = ContractServiceImpl.queryContractByContractNumber(tr.getContractName());//获得合约信息
						String control = contract.getControl();//控制信息
						BigDecimal amount = new BigDecimal(tr.getAmount());//ico支付的合约币
						if(cd != null) {
							//“我的合约”存在、合约地址收入该 代币。并支出放大后的合约代币
							//计算代币支出
							if('1' == control.charAt(1)) {
								//需要分期返利
								IntervalPeriod intervalPeriod = BlockBaseUtils.getIntervalPeriod(amount, contract.getIntervalPeriod2());
								BigDecimal zoomCount = amount.multiply(intervalPeriod.getEp());//放大后的数量
								BigDecimal contractBalanceNew = new BigDecimal(cd.getContractBalance()).subtract(zoomCount);//合约地址支出放大后的代币
								contractBalanceNew = contractBalanceNew.add(amount);//收入代币
								//扣除放大后的代币数
								ContractDatilServiceImpl.updateContractDatilContractBalance(tr.getContractName(), tr.getTo(), DataUtils.getAmountString(contractBalanceNew));
							}else if('0' == control.charAt(1)){
								//不需要分期返利，放大倍数直接返回.
								BigDecimal zoomCount = amount.multiply(new BigDecimal(contract.getEp2()));//没有分期返利，倍数直接取ep
								BigDecimal contractBalanceNew = new BigDecimal(cd.getContractBalance()).subtract(zoomCount);//合约地址支出放大后的代币
								contractBalanceNew = contractBalanceNew.add(amount);//收入代币
								//扣除放大后的代币数
								ContractDatilServiceImpl.updateContractDatilContractBalance(tr.getContractName(), tr.getTo(), DataUtils.getAmountString(contractBalanceNew));
							}
							ContractToContractAddressRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(waf != null) {
							//修改ico交易记录为完成
							ContractToContractAddressRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							//如果是马上返回
							if('0' == control.charAt(1)){
								//不需要分期返利，放大倍数直接返回.
								BigDecimal zoomCount = amount.multiply(new BigDecimal(contract.getEp2()));//没有分期返利，倍数直接取ep
								BigDecimal contractBalanceNew = new BigDecimal(waf.getAmount()).add(zoomCount);//支付地址直接收到放大后的代币
								//扣除放大后的代币数
								TokenAddressServiceImpl.updateWalletAddress(waf.getAddress(), DataUtils.getAmountString(contractBalanceNew), tr.getContractName());
							}
						}
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 更新高级合约 代币转代币 的确认数
	 *//*
	private static void updateContractToContractRecordsCounter(Block b, List<ContractToContractRecords> ctcrs, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(ContractToContractRecords tr : ctcrs) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					ContractToContractRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(tr);
						//如果确认数达到10，更新钱包
						TokenAddress waf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getFrom(), tr.getContractName());
						TokenAddress wat = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getTo(), tr.getContractName());
						if(wat != null) {
							//收入
							if(BtcConstant.ART_CONTRACT_NUMBER.equals(tr.getContractName())) {
								BigDecimal newTradeAmount = new BigDecimal(tr.getAmount()).multiply(new BigDecimal(BtcConstant.ART_PERCENT));
								//newTradeAmount.setScale(6);
								BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(newTradeAmount);
								TokenAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount), tr.getContractName());
								ContractToContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}else {
								BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount()));
								TokenAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount), tr.getContractName());
								ContractToContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
								}
							}
						if(waf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							ContractToContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	*//**
	 * 更新币币转换 的确认数
	 *//*
	private static void updateContractConvertTradeRecordsCounter(Block b, List<ContractConvertRecord> ctcrs, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(ContractConvertRecord tr : ctcrs) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					ContractConvertRecordServiceImpl.updateContractConvertByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(tr.getContractName01());
						saveTokenAddressContract(tr.getContractName02());
						if(tr.getContractName01().equals(BtcConstant.TOK_CONTRACT_NUMBER)) {
							WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getUserAddress02());//第一个币种为tok时 6块确认第二个地址获得tok
							if(wat!=null) {
								BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount01()));
								WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
								
							}

						} if(tr.getContractName02().equals(BtcConstant.TOK_CONTRACT_NUMBER)) {
							WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getUserAddress01());//第一个币种为tok时 6块确认第二个地址获得tok
							if(wat!=null) {
								BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount02()));
								WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
								
							}

						} if(!tr.getContractName01().equals(BtcConstant.TOK_CONTRACT_NUMBER)) {
							TokenAddress waf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getUserAddress02(), tr.getContractName01());
							if(waf!=null) {
								BigDecimal newAmount = new BigDecimal(waf.getAmount()).add(new BigDecimal(tr.getAmount01()));
								TokenAddressServiceImpl.updateWalletAddress(waf.getAddress(), DataUtils.getAmountString(newAmount),tr.getContractName01());
								
								}

						} if(!tr.getContractName02().equals(BtcConstant.TOK_CONTRACT_NUMBER)) {
							TokenAddress waf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getUserAddress01(), tr.getContractName02());
							if(waf!=null) {
								BigDecimal newAmount = new BigDecimal(waf.getAmount()).add(new BigDecimal(tr.getAmount02()));
								TokenAddressServiceImpl.updateWalletAddress(waf.getAddress(), DataUtils.getAmountString(newAmount),tr.getContractName02());
								
								}

						}
						ContractConvertRecordServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);

					}

				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	private static void updateReadeRecordsCounter(Block b, List<TradeRecords> trs, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(TradeRecords tr : trs) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					TradeRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//如果确认数达到10，更新钱包
						WalletAddress waf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getFrom());
						WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getTo());
						if(wat != null) {
							//收入
							BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount()));
							WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
							TradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(waf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							TradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 改变时间银行交易的确认数
	 * @param b
	 * @param timebankAllRecords
	 * @param newCounter
	 *//*
	private static void updateTimeBankRecordsCounter(Block b, TimebankAllRecords timebankAllRecords, int newCounter) {
		List<LoanByFBDTTradeRecords> lbFbdttr = timebankAllRecords.getLoanByFBDTTradeRecords();
		List<LoanByTOKTradeRecords> lbToktr = timebankAllRecords.getLoanByTOKTradeRecords();
		List<RepaymentTradeRecords> rtr = timebankAllRecords.getRepaymentTradeRecords();
		List<StorageTradeRecords> str = timebankAllRecords.getStorageTradeRecords();
		updateLoanByFbdtRecourdsCounter(b, lbFbdttr, newCounter);
		updateLoanByTOKTradeRecordsCounter(b, lbToktr, newCounter);
		udpateRepaymentTradeRecordsCounter(b, rtr, newCounter);
		updateStorageTradeRecords(b, str, newCounter);
	}
	*//**
	 * 使用FBDT贷款的记录确认数
	 * @param b
	 * @param lbFbdttr
	 * @param newCounter
	 *//*
	private static void updateLoanByFbdtRecourdsCounter(Block b, List<LoanByFBDTTradeRecords> lbFbdttr, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(LoanByFBDTTradeRecords tr : lbFbdttr) {
			//如果交易记录里面有该块
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					LoanByFBDTTradeRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(BtcConstant.FBDT_CONTRACT_NUMBER);//检查是否添加了FBDT
						//如果确认数达到10，更新钱包
						//用户地址
						TokenAddress tawaf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getUserAddress(), BtcConstant.FBDT_CONTRACT_NUMBER);//支付FBDT的地址
						WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getUserAddress());//支付fbdt并收入tok的地址
						if(wat != null) {
							//支付的gas在转出的时候已经扣掉了，这里收入贷款得到的tok
							//收入
							BigDecimal newAmount =new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getLoanAmount()));//加上贷款的钱
							WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
							LoanByFBDTTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(tawaf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							LoanByFBDTTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						//时间银行
						TokenAddress taWat = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getTbAddress(), BtcConstant.FBDT_CONTRACT_NUMBER);//收入FBDT的地址
						WalletAddress taWaf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getTbAddress());//支付tok的账户
						if(taWat != null) {
							//收入fbdt
							BigDecimal newAmount = new BigDecimal(taWat.getAmount()).add(new BigDecimal(tr.getPayFbdtAmount()));
							TokenAddressServiceImpl.updateWalletAddress(taWat.getAddress(), DataUtils.getAmountString(newAmount), BtcConstant.FBDT_CONTRACT_NUMBER);
							LoanByFBDTTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(taWaf != null) {
							//支付这里做特殊处理
							BigDecimal newAmount =new BigDecimal(taWaf.getAmount()).subtract(new BigDecimal(tr.getLoanAmount()));//支付用户贷款的钱
							WalletAddressServiceImpl.updateWalletAddress(taWaf.getAddress(), DataUtils.getAmountString(newAmount));
							LoanByFBDTTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 使用TOK贷款的记录确认数
	 * @param b
	 * @param lbToktr
	 * @param newCounter
	 *//*
	private static void updateLoanByTOKTradeRecordsCounter(Block b, List<LoanByTOKTradeRecords> lbToktr, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(LoanByTOKTradeRecords tr : lbToktr) {
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					LoanByTOKTradeRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(BtcConstant.FBDT_CONTRACT_NUMBER);//检查是否添加了FBDT
						//如果确认数达到10，更新钱包
						TokenAddress tawaf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getUserAddress(), BtcConstant.FBDT_CONTRACT_NUMBER);//支付FBDT的地址
						WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getUserAddress());//支付fbdt并收入tok的地址
						if(wat != null) {
							//支付的gas在转出的时候已经扣掉了，这里收入贷款得到的tok
							//收入
							BigDecimal newAmount =new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getLoanAmount()));//加上贷款的钱
							WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
							LoanByTOKTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(tawaf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							LoanByTOKTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						WalletAddress taWaf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getTbAddress());//
						if(taWaf != null) {
							//支付这里做特殊处理
							BigDecimal newAmount =new BigDecimal(taWaf.getAmount()).subtract(new BigDecimal(tr.getLoanAmount()));//支付用户贷款的钱
							WalletAddressServiceImpl.updateWalletAddress(taWaf.getAddress(), DataUtils.getAmountString(newAmount));
							LoanByFBDTTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 还款的确认数
	 * @param b
	 * @param rtr
	 * @param newCounter
	 *//*
	private static void udpateRepaymentTradeRecordsCounter(Block b, List<RepaymentTradeRecords> rtr, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(RepaymentTradeRecords tr: rtr) {
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					RepaymentTradeRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(BtcConstant.FBDT_CONTRACT_NUMBER);//检查是否添加了FBDT
						//如果确认数达到10，更新钱包
						if(newCounter == BtcConstant.BLOCK_COUNTER) {
							//检查本地是否有添加该合约代币，若没有则添加
							saveTokenAddressContract(BtcConstant.FBDT_CONTRACT_NUMBER);//检查是否添加了FBDT
							//如果确认数达到10，更新钱包
							//用户地址
							if(tr.getLoanTradeType() == ContractTradeBodyType.TIMEBANK_LOAN_BY_FBDT) {
								//如果是还fbdt贷款的交易，则收到当初抵押的fbdt
								TokenAddress tawaf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getUserAddress(), BtcConstant.FBDT_CONTRACT_NUMBER);//支付FBDT的地址
								if(tawaf != null) {
									BigDecimal newAmount =new BigDecimal(tawaf.getAmount()).add(new BigDecimal(tr.getRepaymentAmount()));
									TokenAddressServiceImpl.updateWalletAddress(tr.getUserAddress(), DataUtils.getAmountString(newAmount), BtcConstant.FBDT_CONTRACT_NUMBER);
									RepaymentTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
								}
							}
							//时间银行地址-收入tok，支出fbdt（时间银行后台自动打钱，不在这笔交易做处理）
							WalletAddress taWa = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getTbAddress());
							//TokenAddress tawaf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getTbAddress(), BtcConstant.FBDT_CONTRACT_NUMBER);//时间银行支付FBDT的地址
							if(taWa != null) {
								BigDecimal newAmount = new BigDecimal(taWa.getAmount()).add(new BigDecimal(tr.getRepaymentAmount()));
								WalletAddressServiceImpl.updateWalletAddress(tr.getTbAddress(), DataUtils.getAmountString(newAmount));
								RepaymentTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}
							if(tawaf != null) {
								BigDecimal newAmount = new BigDecimal(tawaf.getAmount()).subtract(new BigDecimal(tr.getMortgageFbdtAmount()));
								TokenAddressServiceImpl.updateWalletAddress(tr.getTbAddress(), DataUtils.getAmountString(newAmount), BtcConstant.FBDT_CONTRACT_NUMBER);
								RepaymentTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							}
						}
					}
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 存储的确认数
	 * @param b
	 * @param str
	 * @param newCounter
	 *//*
	private static void updateStorageTradeRecords(Block b, List<StorageTradeRecords> str, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(StorageTradeRecords tr: str) {
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					StorageTradeRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(BtcConstant.FBDT_CONTRACT_NUMBER);//检查是否添加了FBDT
						//用户支出gas和tok。支出这里不做处理
						//银行地址收入tok
						WalletAddress tbWa = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getTbAddress());
						if(tbWa != null) {
							BigDecimal newAmount = new BigDecimal(tbWa.getAmount()).add(new BigDecimal(tr.getStorageAmount()));
							WalletAddressServiceImpl.updateWalletAddress(tbWa.getAddress(), DataUtils.getAmountString(newAmount));
							StorageTradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	*//**
	 * 从分叉点回滚
	 * @param forking
	 *//*
	@Deprecated
	public static void rollbackWaFromOldChain(Block forking, Block oldBlock) {
		Block preBlock = findPreBlock(oldBlock);//获得上一块
		//旧链达到确认数，且未到分叉点的，区块钱包需要回滚
		//确认数<10，且还没到分叉点
		if(preBlock.getCounter() < BtcConstant.BLOCK_COUNTER && !forking.getBlockIndex().equals(preBlock.getBlockIndex())) {
			rollbackWaFromOldChain(forking, preBlock);
		}else {
			if(preBlock.getCounter() >= BtcConstant.BLOCK_COUNTER && !forking.getBlockIndex().equals(preBlock.getBlockIndex())) {
				//达到确认数且没到分叉点
				WalletAddressServiceImpl.updateWalletByConfirmedBlock(oldBlock, BtcConstant.WALLET_ROLLBACK_MODE);
			}
		}
	}
	
	public static Block findPreBlock(Block b) {
		BigInteger preIndex = new BigInteger(b.getBlockIndex()).add(new BigInteger("-1"));
		Block block = null;
		List<Block> lists = BlockServiceImpl.queryBlockByBlockIndex(preIndex.toString());
		//find really pre block
		for(Block preb : lists) {
			if(preb.getEndHash().equals(b.getHeadHash())) {
				block = preb;
				break;
			}
		}
		return block;
	}
	public static boolean updateOnMingChain(Block block, int onMingChain) {
		Connection connection = null;
		try {
			String tableIndex = DataUtils.getBlockSerial(block.getBlockIndex());
			connection = SQLiteHelper.getConnection();
			connection.setAutoCommit(false);
			String sql = "update [block" + tableIndex + "] set [onMingChain] = " + onMingChain + " where  [id] = " + block.getId();
			Statement statement = connection.createStatement();
			statement.execute(sql);
			connection.commit();
			return true;
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("CollectionServiceImpl.updateCounter() ERROR ："+e.getMessage());

				}
			}
			e.printStackTrace();
		}finally {
			SQLiteHelper.close(connection);
		}
		return false;
	}
	
	public static Block getBlockObeject(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String blockStr = new String(bos.toByteArray());//get block object
				//Block block = (Block) SerializeHelper.derialize(blockStr);
				Block block = new Gson().fromJson(blockStr, Block.class);
				return block;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockObeject() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockObeject() ERROR ："+e.getMessage());

				}
			}
		}
		return null;
	}
	public static String getBlockFileStr(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String blockfileStr = new String(bos.toByteArray());//get block file str
				return blockfileStr;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

				}
			}
		}
		return "";
	}
	public static String getContractBlockFileStr(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String contractBlockFileStr = new String(bos.toByteArray());//get block file str
				return contractBlockFileStr;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getBlockFileStr() ERROR ："+e.getMessage());

				}
			}
		}
		return "";
	}
	
	public static String getMaxBlockIndexStr(ZipInputStream zis) {
		ByteArrayOutputStream bos = null;
		try {
			//if(zis.getNextEntry() != null) {
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				String maxBlockIndex = new String(bos.toByteArray());//get block file str
				return maxBlockIndex;
			//}
		} catch (JsonSyntaxException | IOException e) {
			logger.error("CollectionServiceImpl.getMaxBlockIndexStr() ERROR ："+e.getMessage());

		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("CollectionServiceImpl.getMaxBlockIndexStr() ERROR ："+e.getMessage());

				}
			}
		}
		return "";
	}
	*//**
	 * 获得块的统计信息
	 * @param block
	 * @return
	 *//*
	public static Map<String , BlockAddressList> getBlockInFileMapInfo(Block block){
		String blockIn = BlockServiceImpl.getBlockInFile(block);
		Map<String , Object> balOMap = new Gson().fromJson(blockIn, Map.class);
		Map<String , BlockAddressList> balMap = new HashMap<String , BlockAddressList>();
		for(Entry<String, Object> entry : balOMap.entrySet()) {
			balMap.put(entry.getKey(), BlockAddressListServiceImpl.mapToBlockAddressList( (Map) entry.getValue()));
		}
		balOMap.clear();
		return balMap;
	}
	
	public static Map<String, TradeBody> getBlockFileMapInfo(Block block){
		String blockStr = BlockServiceImpl.getBlockFile(block);
		Map<String , Object> oMap = new Gson().fromJson(blockStr, Map.class);
		Map<String, TradeBody> tradeMap = new HashMap<String, TradeBody>();
		for(Entry<String, Object> entry : oMap.entrySet()) {
			tradeMap.put(entry.getKey(), TradeBodyImpl.mapToTradeBody( (Map) entry.getValue()));
		}
		oMap.clear();
		return tradeMap;
	}
	*//**
	 * 保存合约到本地合约列表(如果不存在)
	 * @param ccr
	 *//*
	public static void saveLocalContractList(CreateContractRecords ccr, Block block) {
		Contract contract = ContractServiceImpl.queryContractByContractNumber(ccr.getContractNumber());
		if(contract == null) {
			Contract c = new Contract();
			c.setContent(ccr.getContent());
			c.setContractName(ccr.getContractNumber());
			c.setControl(ccr.getControl());
			c.setCreateAddress(ccr.getPaymentAddress());
			c.setCreateTime(ccr.getTradeTime());
			c.setEp(ccr.getEp());
			c.setEp2(ccr.getEp2());
			c.setIcoLimitTime(ccr.getIcoLimitTime());
			c.setEnlargeLimitTime(ccr.getEnlargeLimitTime());
			c.setInitializeAmount(ccr.getInitContractAmount());
			c.setIntervalPeriod(ccr.getIntervalPeriod());
			c.setIntervalPeriod2(ccr.getIntervalPeriod2());
			c.setTokenAddress(ccr.getContractAddress());
			c.setUnit(ccr.getUnit());
			c.setBlockIndex(block.getBlockIndex());
			c.setConfirmNumber("0");
			c.setTradeStatus(ContractBaseRecords.TRADESTATUS_NORMAL);
			ContractServiceImpl.save(c);
		}
		
	}
	*//**
	 * 保存合约到本地合约列表(如果不存在)
	 * @param ccr
	 *//*
	public static void saveLocalContractList(CreateIntelligenceContractRecords ccr, Block block) {
		Contract contract = ContractServiceImpl.queryContractByContractNumber(ccr.getContractName());
		if(contract == null) {
			Contract c = new Contract();
			c.setContent(ccr.getContractCode());
			c.setContractName(ccr.getContractName());
			c.setControl("");
			c.setCreateAddress(ccr.getAddress());
			c.setCreateTime(ccr.getTradeTime());
			c.setEp("");
			c.setEp2("");
			c.setIcoLimitTime("");
			c.setEnlargeLimitTime("");
			c.setInitializeAmount(ccr.getInitContractAmount());
			c.setIntervalPeriod("");
			c.setIntervalPeriod2("");
			c.setTokenAddress(ccr.getContractAddress());
			c.setUnit(ccr.getUnit());
			c.setBlockIndex(block.getBlockIndex());
			c.setConfirmNumber("0");
			c.setTradeStatus(ContractBaseRecords.TRADESTATUS_NORMAL);
			ContractServiceImpl.save(c);
		}
		
	}
	*//**
	 * 更新本地高级合约列表，所有未确认的高级合约，确认数+1
	 * @param ccr
	 * @param block
	 *//*
	public static void updateLocalContract(Block block) {
		List<Contract> cs = ContractServiceImpl.queryContractListByStatusNormal();
		for(Contract c : cs) {
			BigInteger oldBlockIndex = new BigInteger(c.getBlockIndex());//区块编号
			c.getConfirmNumber();//确认数
			BigInteger newBlockIndex = new BigInteger(block.getBlockIndex());//新的区块编号
			if(newBlockIndex.compareTo(oldBlockIndex) > 0) {
				BigInteger newCounter = newBlockIndex.subtract(oldBlockIndex);//新的确认数
				String tradeStatus = ContractBaseRecords.TRADESTATUS_NORMAL;
				if(newCounter.compareTo(new BigInteger(BtcConstant.BLOCK_COUNTER + "")) <= 0) {
					if(newCounter.compareTo(new BigInteger(BtcConstant.BLOCK_COUNTER + "")) == 0) {
						tradeStatus = ContractBaseRecords.TRADESTATUS_COMPLETE;
						newCounter = new BigInteger(BtcConstant.BLOCK_COUNTER + "");
					}
					ContractServiceImpl.updateComfirmNumber(c.getContractName(), newCounter.toString(), tradeStatus);
				}
			}
		}
	}
	*//**
	 * 保存合约到 “我的高级合约列表”
	 * @param ccr
	 *//*
	private static void saveContractDatil(CreateContractRecords ccr) {
		ContractDatil c = new ContractDatil();
		c.setContent(ccr.getContent());
		c.setContractName(ccr.getContractNumber());
		c.setContractBalance(ccr.getInitContractAmount());
		c.setControl(ccr.getControl());
		c.setCreateAddress(ccr.getPaymentAddress());
		c.setCreateTime(ccr.getTradeTime());
		c.setEp(ccr.getEp());
		c.setEp2(ccr.getEp2());
		c.setIcoLimitTime(ccr.getIcoLimitTime());
		c.setEnlargeLimitTime(ccr.getEnlargeLimitTime());
		c.setInitializeAmount(ccr.getInitContractAmount());
		c.setIntervalPeriod(ccr.getIntervalPeriod());
		c.setIntervalPeriod2(ccr.getIntervalPeriod2());
		c.setTokBalance(DataUtils.getAmountString(BigDecimal.ZERO));
		c.setTokenAddress(ccr.getContractAddress());
		c.setUnit(ccr.getUnit());
		ContractDatilServiceImpl.save(c);
	}
	*//**
	 * 保存合约到 “我的高级合约列表”
	 * @param cicr
	 *//*
	private static void saveContractDatil(CreateIntelligenceContractRecords cicr) {
		ContractDatil c = new ContractDatil();
		c.setContent(cicr.getContractCode());
		c.setContractName(cicr.getContractName());
		c.setContractBalance(cicr.getInitContractAmount());
		c.setControl("");
		c.setCreateAddress(cicr.getAddress());
		c.setCreateTime(cicr.getTradeTime());
		c.setEp("");
		c.setEp2("");
		c.setIcoLimitTime("");
		c.setEnlargeLimitTime("");
		c.setInitializeAmount(cicr.getInitContractAmount());
		c.setIntervalPeriod("");
		c.setIntervalPeriod2("");
		c.setTokBalance(DataUtils.getAmountString(BigDecimal.ZERO));
		c.setTokenAddress(cicr.getContractAddress());
		c.setUnit(cicr.getUnit());
		ContractDatilServiceImpl.save(c);
	}
	*//**
	 * 交易达到6个确认的时候本地添加代币和合约列表。
	 * @param tr
	 *//*
	private static void saveTokenAddressContract(ContractToContractRecords tr) {
		saveTokenAddressContract(tr.getContractName());
	}
	
	
	public static void saveTokenAddressContract(String contractName) {
		//从本地contract列表里面查询是否有该合约
		Contract contract = ContractServiceImpl.queryContractByContractNumber(contractName);
		if(contract != null) {
			List<WalletAddress> was = WalletAddressServiceImpl.queryWalletAddress();//获取所有的tok地址。
			for(WalletAddress wa : was) {
				//循环遍历tok地址检查，是否每个地址都添加了该代币（防止添加了之后又新增了tok地址）
				TokenAddress tokenAddress = TokenAddressServiceImpl.queryTokenAddressByContractNumber(wa.getAddress(), contractName);
				if(tokenAddress == null) {
					//该地址没有新增代币
					TokenAddress ta = new TokenAddress();
					ta.setAddress(wa.getAddress());
					ta.setAmount(DataUtils.getAmountString(BigDecimal.ZERO));
					ta.setContractName(contractName);
					ta.setCreateTime(DataUtils.getCurrentTime());
					ta.setContractAddress(contract.getTokenAddress());
					TokenAddressServiceImpl.save(ta);
				}
			}
		}
	}
	*//**
	 * 
	 * @param wa  tok钱包地址
	 *//*
	public static void saveTokenAddressWhenAddTokAdd(WalletAddress wa) {
		List<String> tokenNames = TokenAddressServiceImpl.queryTokenAddress();
		for(String contractName : tokenNames) {
			Contract c = ContractServiceImpl.queryContractByContractNumber(contractName);
			TokenAddress ta = new TokenAddress();
			ta.setAddress(wa.getAddress());
			ta.setAmount(DataUtils.getAmountString(BigDecimal.ZERO));
			ta.setContractName(contractName);
			ta.setCreateTime(DataUtils.getCurrentTime());
			ta.setContractAddress(c.getTokenAddress());
			TokenAddressServiceImpl.save(ta);
		}
	}
	*//**
	 * 更新创建智能合约确认数
	 * @param b
	 * @param ttcars
	 * @param newCounter
	 *//*
	private static void updateCreateIntelligenceContractRecordsCounter(Block b, List<CreateIntelligenceContractRecords> ttcars, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(CreateIntelligenceContractRecords tr : ttcars) {
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					CreateIntelligenceContractRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						WalletAddress waf = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getPayAddress());
						//是我创建的高级合约
						if(waf != null) {
							//我的合约钱包添加该代币
							TokenAddress taf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getPayAddress(), tr.getContractName());
							if(taf == null) {
								//合约钱包 添加该合约
								List<WalletAddress> was = WalletAddressServiceImpl.queryWalletAddress();//获取所有的tok地址。
								for(WalletAddress wa : was) {
									TokenAddress ta = new TokenAddress();
									ta.setAddress(wa.getAddress());
									ta.setAmount(DataUtils.getAmountString(BigDecimal.ZERO));
									ta.setContractName(tr.getContractName());
									ta.setCreateTime(tr.getTradeTime());
									ta.setContractAddress(tr.getContractAddress());
									TokenAddressServiceImpl.save(ta);
								}
							}
							//我的 “高级合约列表” 添加我创建的这个合约
							saveContractDatil(tr);
						}
						//创建合约，要支付到的固定地址
						WalletAddress wat = WalletAddressServiceImpl.queryWalletAddressByAdd(tr.getIncomeAddress());
						if(wat != null) {
							//收入
							BigDecimal newAmount = new BigDecimal(wat.getAmount()).add(new BigDecimal(tr.getAmount()));
							WalletAddressServiceImpl.updateWalletAddress(wat.getAddress(), DataUtils.getAmountString(newAmount));
							//这里的tok收入交易在  创建代币的页面展示。
							//TradeRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
							CreateIntelligenceContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
						if(waf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							CreateIntelligenceContractRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				} catch (TokException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
	}
	*//**
	 * 更新方法调用确认数
	 * @param b
	 * @param ttcars
	 * @param newCounter
	 *//*
	private static void updateIntelligenceContractMethodRecordsCounter(Block b, List<IntelligenceContractMethodRecords> ttcars, int newCounter) {
		String blockIndex = b.getBlockIndex();
		for(IntelligenceContractMethodRecords tr : ttcars) {
			if(blockIndex.equals(tr.getBlockIndex())) {
				try {
					//更新交易确认数
					IntelligenceContractMethodRecordsServiceImpl.updateCounterByTradeNo(tr.getTradeNo(), newCounter);
					if(newCounter == BtcConstant.BLOCK_COUNTER) {
						//检查本地是否有添加该合约代币，若没有则添加
						saveTokenAddressContract(tr.getContractName());
						//如果确认数达到10，更新钱包
						TokenAddress waf = TokenAddressServiceImpl.queryTokenAddressByContractNumber(tr.getAddress(), tr.getContractName());
						if(waf != null) {
							//支出。支出转账时候就扣掉了，这里不做处理。
							IntelligenceContractMethodRecordsServiceImpl.updateTradeStatus(tr.getTradeNo(), TradeRecords.TRADESTATUS_COMPLETE);
						}
					}
				}  catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}*/
}
