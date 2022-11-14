package com.hitebaas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hitebaas.crypt.ECDSAUtils;
import com.hitebaas.entity.HiteBassConstant;

import net.sf.json.JSONObject;


public class DataUtils {
	public static boolean isNotice = false;
	protected static final Logger logger = Logger.getLogger(DataUtils.class);
	


	/**
	 * 把字符串写入文件 
	 * @param relativePath
	 * @param data
	 */
	public static boolean writeFile(String relativePath, String data) {
		try {
			File file = new File(relativePath);
			FileUtils.writeStringToFile(file, data);
		} catch (IOException e) {
			logger.error("DataUtils.writeFile ERROR ："+e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 根据创建
	 * @param createAddress   创建地址
	 * @param contractName	合约地址
	 * @return
	 */
	public static String genContractAddress(String createAddress, String contractName) {
		return HiteBassConstant.CONTRACT_ADDRESS_PRE + createAddress + contractName;
	}
	
	public static String readFile(String relativePath) {
		String result = "";
		try {
			
			File file = new File(relativePath);
			
			result = FileUtils.readFileToString(file);
		} catch (IOException e) {
			logger.error("DataUtils.readFile ERROR ："+e.getMessage());
		}
		return result;
	}
	public static boolean findFile(String relativePath) {

		File file = new File(relativePath);
		if(file.exists()) {
			return true;
		}else {
			try {
				file.mkdirs();
				String path=getRelativePath(HiteBassConstant.DB_TSC_PATH);
				File file2 = new File(path);
				file2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public static String getRelativePath(String relativePath) {
		//todo 修改配置
		return System.getProperty("user.dir") + relativePath;
		//return System.getProperty("user.dir") + "/copyright-client"+ relativePath;
	}
	/**
	 * dir: "block" + blockIndex
	 * table: "block" + blockIndex
	 * @param blockIndex
	 * @return
	 */
	public static String getBlockSerial(String blockIndex) {
		String index = "";
		if(blockIndex.length() < 5) {
			index = "0";
		}else {
			index = blockIndex.substring(0, blockIndex.length() - 4);
		}
		return index;
	}

	public static ArrayList<String> toArrayByFileReader1(String name) {
		// 使用ArrayList来存储每行读取到的字符串
		ArrayList<String> arrayList = new ArrayList<>();
		try {
			FileReader fr = new FileReader(name);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			// 按行读取字符串
			while ((str = bf.readLine()) != null) {
				arrayList.add(str);
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return arrayList;
	}
	
	
	
	public static String getBlockPath(String blockIndex, Date currentTime) {
		String path = HiteBassConstant.BLOCK_FILE_PATH_PREFIX + DataUtils.getBlockSerial(blockIndex);
		String blockName = HiteBassConstant.BLOCK_NAME_PREFIX + blockIndex + "-" + currentTime.getTime();
		return path + "/"+blockIndex+"/" + blockName;
	}
	public static String getBlockPath(String blockIndex, Date currentTime,String name) {
		String path = HiteBassConstant.BLOCK_FILE_PATH_PREFIX + DataUtils.getBlockSerial(blockIndex);
		String blockName = HiteBassConstant.BLOCK_NAME_PREFIX + blockIndex + "-" + currentTime.getTime();
		return path + "/"+blockIndex+"/" +name+"/"+ blockName;
	}
	public static String getTempBlockPath(String blockIndex) {
		String path = HiteBassConstant.BLOCK_TEMP_PATH_PREFIX;
		String blockName = HiteBassConstant.BLOCK_NAME_PREFIX + blockIndex ;
		return path + "/"+blockIndex+"/" + blockName;
	}
	
	public static String getBlockString(String blockPath) {
		return readFile(blockPath);
	}
	
	public static String getEndHash(String blockHash, String path, String time, String indexStr) {
		if(StringUtils.isBlank(blockHash)) {
			return "";
		}
		String a = blockHash + path + time + indexStr;
		return ECDSAUtils.encryptMD5(a);
	}
	/**
	 * 第一个10万块，每块1000币收益。
	 * 接下来的每10万块，收益从150开始减1，直至每块收益为10的时候，保持收益不变。
	 * @param index
	 * @return
	 */
	public static BigDecimal getMiningProfit(String index) {
		BigInteger indexI = new BigInteger(index);
		//<=1
		if(indexI.compareTo(BigInteger.ZERO) <= 0) {
			return new BigDecimal("100000000");
		}else {
			return new BigDecimal("40");
		}
		/*//1<indexI<=100000
		else if(indexI.compareTo(BigInteger.ONE) > 0 && indexI.compareTo(new BigInteger("100000")) <= 0){
			return new BigDecimal("100");
		}
		//100000<indexI<=200000
		else if(indexI.compareTo(new BigInteger("100000")) > 0 && indexI.compareTo(new BigInteger("200000")) <= 0){
			return new BigDecimal("80");
		}
		//200000<indexI<=300000
		else if(indexI.compareTo(new BigInteger("200000")) > 0 && indexI.compareTo(new BigInteger("300000")) <= 0){
			return new BigDecimal("60");
		}
		//300000<indexI<=400000
		else if(indexI.compareTo(new BigInteger("300000")) > 0 && indexI.compareTo(new BigInteger("400000")) <= 0){
			return new BigDecimal("40");
		}
		//400000<indexI<=500000
		else if(indexI.compareTo(new BigInteger("400000")) > 0 && indexI.compareTo(new BigInteger("500000")) <= 0){
			return new BigDecimal("20");
		}
		//600000<=indexI
		else {
			return new BigDecimal("10");
		}*/
	}
	
	public static String genAddress(String publicKey) {
		String address = ECDSAUtils.encryptSHA1(publicKey);
		return HiteBassConstant.WALLETADDRESS_PREFIX + ECDSAUtils.encryptMD5(address);
	}
	
	public static String genBourseAddress(String time, String bourseUserId) {
		return HiteBassConstant.WALLETADDRESS_PREFIX + ECDSAUtils.encryptSHA1(time + bourseUserId);
	}
	
	public static String getCurrentTime() {
		return getCurrentTime(new Date());
	}
	
	public static String getCurrentTime(Date date) {
		
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HiteBassConstant.DATE_FORMAT);

		simpleDateFormat.setTimeZone(timeZone);

		return simpleDateFormat.format(date);
	}
	/**
	 * 前10万块4个0.
	 * 10万后。变成6个0，每1万块难度增加一个0。增至10个0 ，为止。
	 * 按每年
	 * @param index
	 * @return
	 */
	public static String getStartZero(String index) {
		String start = "000";//7个0
		return start;
	}
	
	
	
	 public static String getAmountString(BigDecimal amount) {
    	DecimalFormat df = new DecimalFormat(HiteBassConstant.DECIMAL_FORMAT);
    	return df.format(amount);
    }
	 /**
	  * 用于区块打包时候gas处理用
	  * @param amount
	  * @return
	  */
	 public static String getGasString(BigDecimal amount) {
	    	DecimalFormat df = new DecimalFormat(HiteBassConstant.DECIMAL_GAS_FORMAT);


	    	return df.format(amount);
	    }
	public static boolean copy(String tempBlockPath, String blockPath) {
		try {
			String[] paths = blockPath.split("[/]");
			String blockName = paths[paths.length -1 ];
			String dir = blockPath.substring(0, blockPath.indexOf(blockName));
			String realTempBlockDir = DataUtils.getRelativePath(dir);
			String realBlockPath = DataUtils.getRelativePath(blockPath);
			File fileDir = new File(realTempBlockDir);
			if(!fileDir.exists()) {
				FileUtils.forceMkdir(fileDir);
			}
			File tempFile = new File(DataUtils.getRelativePath(tempBlockPath));
			FileUtils.copyFile(tempFile, new File(realBlockPath));
			//delete temp file
			tempFile.delete();
			return true;
		} catch (IOException e) {
			logger.error("DataUtils.copy ERROR ："+e.getMessage());

			//e.printStackTrace();
		}
		return false;
	}
	/*public static String getPriSecKey() {
		//String ip = IpUtils.getNativeIP();
		String mac = IpUtils.getNativeMac();
		String osInfo = IpUtils.getOsInfo();
		return ECDSAUtils.encryptMD5(mac + osInfo);
	}
	*//**
	 * 获得私钥加密后的字符串
	 * @param privateKey
	 * @return
	 *//*
	public static String getEncryptAES(String privateKey) {
		return ECDSAUtils.encryptAES(privateKey, getPriSecKey());
	}
	*//**
	 * 还原私钥
	 * @param
	 * @return
	 *//*
	public static String getDecryptAES(String ePrivateKey) {
		return ECDSAUtils.decryptAES(ePrivateKey, getPriSecKey());
	}
	*/

	public static String getRandom(int n) {
		String str="";
		char[] code = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
		        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		Random random = new Random();
		
		for(int i=0;i<18;i++) {
			str+=code[random.nextInt(code.length)];
		}
		return str;
	}
	 /**
		 * 判断date2时间是否在前1分钟之内。
		 * @param date1
		 * @param date2
		 * @throws
		 */
		public static boolean checkTime1Min(String date1,String date2)  {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MINUTE, -1);
				Date d11 = c.getTime();//获取一分钟之前的时间
				c.add(Calendar.MINUTE, 2);
				Date d12 = c.getTime();//获取一分钟之后的时间
				Date d2=simpleDateFormat.parse(date2);
				if(d2.before(d11) || d2.after(d12)) {
					return false;
				}
				return true;
				/*long cha = d1.getTime() - d2.getTime(); 
		          if(cha <=60000){ 
		  			throw new TokException(ErrorInfo.TRADE_TIME_IS_NOT_LEGAL, ErrorInfo.TTRADE_TIME_IS_NOT_LEGAL_MSG);
		        }*/
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}
			
	
	//判断时间是否超过三小时
	public static boolean getTime(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date now = df.parse(DataUtils.getCurrentTime());
			Date date=df.parse(time);
			Long l=now.getTime()-date.getTime();
			long hour=(l/(60*60*1000));
			if(hour-3>=0) {
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//判断时间是否超过二四小时
		public static boolean getTime1(String time) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date now = df.parse(DataUtils.getCurrentTime());
				Date date=df.parse(time);
				Long l=now.getTime()-date.getTime();
				long hour=(l/(60*60*1000));
				if(hour-24>=0) {
					return true;
				}else {
					return false;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	/**
	 * 比较d1和d2时间
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean checkTime(String d1,String d2) {
		
		SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建日期转换对象：年月日 时分秒
		try {
		    Date dateD1 = sdf.parse(d1);    //转换为 date 类型 Debug：Sun Nov 11 11:11:11 CST 2018
		    Date dateD2 = sdf.parse(d2);
		    boolean flag = dateD1.getTime() >= dateD2.getTime();
		    return  flag;
		} catch (ParseException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} 		
		
		return false;
	}
	
	/**
	 * 比较d1和d2时间相差1分钟
	 * @param d1
	 * @param
	 * @return
	 */
	public static boolean checkTime(String d1,Date dateD2) {
		
		SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建日期转换对象：年月日 时分秒
		try {
		    Date dateD1 = sdf.parse(d1);    //转换为 date 类型 Debug：Sun Nov 11 11:11:11 CST 2018
		    boolean flag = dateD1.getTime() >= dateD2.getTime();
		    return  flag;
		} catch (ParseException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} 		
		
		return false;
	}
	
	
}
