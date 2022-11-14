package com.hitebaas.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.hitebaas.common.exception.ErrorInfo;
import com.hitebaas.common.exception.TokException;
import com.hitebaas.crypt.ECDSAUtils;
import com.hitebaas.crypt.RSAUtils;
import com.hitebaas.data.NFT;
import com.hitebaas.data.NftCache;
import com.hitebaas.data.NftDetails;
import com.hitebaas.data.NftTrade;
import com.hitebaas.data.NftTradeBody;
import com.hitebaas.data.trade.TokScriptContractTradeBody;
import com.hitebaas.data.trade.TokScriptFunctionTradeBody;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.entity.TradeBody;

public class ValidateUtil {

	
	public static void validateTradeNo(TradeBody tb) throws TokException {
		String tradeNo = genTradeNo(tb);
		if(!tradeNo.equals(tb.getTradeNo())) {
			throw new TokException(ErrorInfo.TRADE_NO_NOT_CORRECT_CODE, ErrorInfo.TTRADE_NO_NOT_CORRECT_CODE_MSG);
		}
	}
	public static String genTradeNo(TradeBody tb) {
		String a = tb.genString();
		return genTradeNo(a);
	}
	public static String genTradeNo(String tbstring) {
		return HiteBassConstant.TRADE_NUMBER_PREFIX + ECDSAUtils.encryptSHA1(tbstring);
	}
	public static void validateAddress(TradeBody tb) throws TokException {
		String address = DataUtils.genAddress(tb.getPublicKey());
		if(!tb.getFrom().equals(address)) {
			throw new TokException(ErrorInfo.TRADE_PUBLICKEY_ADDRESS_NOT_MATCH_CODE, ErrorInfo.TRADE_PUBLICKEY_ADDRESS_NOT_MATCH_CODE_MSG);
		}
	}
	public static void validateSign(TradeBody tb) throws TokException {
		boolean sign = false;
		try {
			sign = RSAUtils.verify(tb.genString().getBytes(), tb.getPublicKey(), tb.getSign());
			if(!sign) {
				throw new TokException(ErrorInfo.TRADE_SIGN_CODE, ErrorInfo.TRADE_SIGN_CODE_MSG);
			}
		} catch (TokException e) {
			throw e;
		} catch (Exception e) {
			throw new TokException(ErrorInfo.TRADE_SIGN_CODE, ErrorInfo.TRADE_SIGN_CODE_MSG);
		}
	}
		private void checkTime(String date1,String date2) throws TokException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HiteBassConstant.DATE_FORMAT);
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, -1);
			Date d11 = c.getTime();//获取一分钟之前的时间
			c.add(Calendar.MINUTE, 2);
			Date d12 = c.getTime();//获取一分钟之后的时间
			Date d2=simpleDateFormat.parse(date2);
			if(d2.before(d11) || d2.after(d12)) {
				throw new TokException(ErrorInfo.TRADE_TIME_IS_NOT_LEGAL, ErrorInfo.TTRADE_TIME_IS_NOT_LEGAL_MSG);
			}
			/*long cha = d1.getTime() - d2.getTime(); 
	          if(cha <=60000){ 
	  			throw new TokException(ErrorInfo.TRADE_TIME_IS_NOT_LEGAL, ErrorInfo.TTRADE_TIME_IS_NOT_LEGAL_MSG);
	        }*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
		
	/**
	 * 生成hash
	 * @param tokScriptContractTradeBody
	 * @return
	 */
	public static String generateTradeNo(TokScriptContractTradeBody tokScriptContractTradeBody) {
		String a = tokScriptContractTradeBody.genString();
		return genTradeNo(a);
	}
	/**
	 * 生成hash
	 * @param tokScriptFunctionTradeBody
	 * @return
	 */
	public static String generateTradeNo(TokScriptFunctionTradeBody tokScriptFunctionTradeBody) {
		String a = tokScriptFunctionTradeBody.genString();
		return genTradeNo(a);
	}
	/**
	 * 验证合约创建方法HASH
	 * @param tokScriptContractTradeBody
	 * @throws Exception 
	 */
	public static void validateTradeNo(TokScriptContractTradeBody tokScriptContractTradeBody) throws Exception {
		String a = tokScriptContractTradeBody.genString();
		String tradeNo =  genTradeNo(a);
		if(!tradeNo.equals(tokScriptContractTradeBody.getTradeNo())) {
			throw new Exception("交易编号不正确");
		}
	}
	
	/**
	 * 验证合约方法调用HASH
	 * @param tokScriptFunctionTradeBody
	 * @throws Exception 
	 */
	public static void validateTradeNo(TokScriptFunctionTradeBody tokScriptFunctionTradeBody) throws Exception {
		String a = tokScriptFunctionTradeBody.genString();
		String tradeNo =  genTradeNo(a);
		if(!tradeNo.equals(tokScriptFunctionTradeBody.getTradeNo())) {
			throw new Exception("交易编号不正确");
		}
	}
	/**
	 * 验证合约创建方法Address
	 * @param tokScriptContractTradeBody
	 * @throws TokException 
	 */
	public static void validateAddress(TokScriptContractTradeBody tokScriptContractTradeBody) throws TokException {
		String address = DataUtils.genAddress(tokScriptContractTradeBody.getPublicKey());
		if(!tokScriptContractTradeBody.getAddress().equals(address)) {
			throw new TokException(ErrorInfo.TRADE_PUBLICKEY_ADDRESS_NOT_MATCH_CODE, ErrorInfo.TRADE_PUBLICKEY_ADDRESS_NOT_MATCH_CODE_MSG);
		}
	}
	/**
	 * 验证合约方法调用Address
	 * @param tokScriptFunctionTradeBody
	 * @throws TokException 
	 */
	public static void validateAddress(TokScriptFunctionTradeBody tokScriptFunctionTradeBody) throws TokException {
		String address = DataUtils.genAddress(tokScriptFunctionTradeBody.getPublicKey());
		if(!tokScriptFunctionTradeBody.getAddress().equals(address)) {
			throw new TokException(ErrorInfo.TRADE_PUBLICKEY_ADDRESS_NOT_MATCH_CODE, ErrorInfo.TRADE_PUBLICKEY_ADDRESS_NOT_MATCH_CODE_MSG);
		}
	}
	
	/**
	 * 生成签名
	 * @param tokScriptContractTradeBody
	 * @return
	 * @throws Exception 
	 */
	public static String generateSign(TokScriptContractTradeBody tb, String privateKey) throws Exception {
		return RSAUtils.sign(tb.genString().getBytes(), privateKey);
	}
	/**
	 * 生成签名
	 * @param TokScriptFunctionTradeBody
	 * @return
	 * @throws Exception 
	 */
	public static String generateSign(TokScriptFunctionTradeBody tb, String privateKey) throws Exception {
		return RSAUtils.sign(tb.genString().getBytes(), privateKey);
	}
	/**
	 * 验证合约创建方法签名
	 * @param tokScriptContractTradeBody
	 * @throws TokException 
	 */
	public static void validateSign(TokScriptContractTradeBody tb) throws TokException {
		boolean sign = false;
		try {
			sign = RSAUtils.verify(tb.genString().getBytes(), tb.getPublicKey(), tb.getSign());
			if(!sign) {
				throw new TokException(ErrorInfo.TRADE_SIGN_CODE, ErrorInfo.TRADE_SIGN_CODE_MSG);
			}
		} catch (TokException e) {
			throw e;
		} catch (Exception e) {
			throw new TokException(ErrorInfo.TRADE_SIGN_CODE, ErrorInfo.TRADE_SIGN_CODE_MSG);
		}
	}
	/**
	 * 验证合约方法调用签名
	 * @param tokScriptFunctionTradeBody
	 * @throws TokException 
	 */
	public static void validateSign(TokScriptFunctionTradeBody tb) throws TokException {
		boolean sign = false;
		try {
			sign = RSAUtils.verify(tb.genString().getBytes(), tb.getPublicKey(), tb.getSign());
			if(!sign) {
				throw new TokException(ErrorInfo.TRADE_SIGN_CODE, ErrorInfo.TRADE_SIGN_CODE_MSG);
			}
		} catch (TokException e) {
			throw e;
		} catch (Exception e) {
			throw new TokException(ErrorInfo.TRADE_SIGN_CODE, ErrorInfo.TRADE_SIGN_CODE_MSG);
		}
	}
}
