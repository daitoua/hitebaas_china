package com.hitebaas.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.utils.Convert;

@Component
public class EtherscanApi {
    private Logger logger = LoggerFactory.getLogger(EtherscanApi.class);
    private String token="DPQDS5BDSD3N6GG4HFCHHSX312FN1I7BYD";
    //private String token = "9WAY9EDA7UUUFC2GAPZ4EBANGEZ6ECVVSC";
    

   /**
    * 获取gas
    * @return
    */
    public    EthGasPrice getGasPrice() {
		//String s =CommonHttpClientHelper.doGet("http://api-cn.etherscan.com/api?module=proxy&action=eth_gasPrice&apikey=DPQDS5BDSD3N6GG4HFCHHSX312FN1I7BYD", null);
		
		try {
			HttpResponse<String> response =  Unirest.post("https://api-cn.etherscan.com/api")
			        .field("module","proxy")
			        .field("action","eth_gasPrice")
			        .field("apikey",token)
			        .asString();
			
			// JSONObject result = JSON.parseObject(response.getBody());
			 EthGasPrice gasPrice= JSON.parseObject(response.getBody(),EthGasPrice.class);

			 //EthGasPrice gasPrice =JSON.parseObject(s, EthGasPrice.class);
		//	BigInteger baseGasPrice =  gasPrice.getGasPrice();
			/* String s = String.valueOf(gasPrice.getGasPrice());
             String n=s.split("0x")[1];
             BigInteger baseGasPrice=new BigInteger(n, 16);*/
			 
			 return gasPrice;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 return null;
	}
    /**
     * 获取交易
     * @return
     */
    public static Long getNetworkBlockHeight(){
        try {
        	
            HttpResponse<String> response =  Unirest.post("https://api-cn.etherscan.com/api")
                    .field("module","proxy")
                    .field("action","eth_blockNumber")
                    .asString();
         //   logger.info("getNetworkBlockHeight result = {}",response.getBody());
            JSONObject result = JSON.parseObject(response.getBody());
            String s=  (String) result.get("result");
            String n=s.split("0x")[1];
            BigInteger bigint=new BigInteger(n, 16);
            Long numb = bigint.longValue();
           
            return numb;
         //   JSONArray txs = result.getJSONArray("result");
          //  System.out.println(txs.toString());
            //Integer.toHexString(s);
          //  System.out.println(numb);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /**
     * 获取交易
     * @return
     */
    public  EthGetTransactionReceipt getTransactionReceipt(String hash){
        try {
        	
            HttpResponse<String> response =  Unirest.post("https://api-cn.etherscan.com/api")
                    .field("module","transaction")
                    .field("action","gettxreceiptstatus")
                    .field("txhash",hash)
                    .field("apikey", token)
                    .asString();
         //   logger.info("getNetworkBlockHeight result = {}",response.getBody());
            EthGetTransactionReceipt receipt =JSON.parseObject(response.getBody(), EthGetTransactionReceipt.class);
           
            return receipt;
         //   JSONArray txs = result.getJSONArray("result");
          //  System.out.println(txs.toString());
            //Integer.toHexString(s);
          //  System.out.println(numb);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
/**
 * 获取余额
 * @param address
 * @return
 */
    public   BigDecimal getBalance(String address) {
	//	String s =  CommonHttpClientHelper.doGet("http://api-cn.etherscan.com/api?module=account&action=balance&address=0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a&tag=latest&apikey=DPQDS5BDSD3N6GG4HFCHHSX312FN1I7BYD", null);

		 try {
			HttpResponse<String> response =  Unirest.post("https://api-cn.etherscan.com/api")
					 .field("module","account")
			         .field("action","balance")
			         .field("tag", "latest")
			         .field("address", address)
			         .field("apikey", token)
			         .asString();

			EthGetBalance getBalance= JSON.parseObject(response.getBody(),EthGetBalance.class);
			
			String str = getBalance.getResult().toString();
			

			BigDecimal big= new BigDecimal(str);
			
			
			return Convert.fromWei(big, Convert.Unit.ETHER);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
		
	}
    
   /**
    * 获取余额
    * @param address
    * @return
    */
       public   BigDecimal getTokenbalance(String contractaddress,String address) {
   	//	String s =  CommonHttpClientHelper.doGet("http://api-cn.etherscan.com/api?module=account&action=balance&address=0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a&tag=latest&apikey=DPQDS5BDSD3N6GG4HFCHHSX312FN1I7BYD", null);

   		 try {
   			HttpResponse<String> response =  Unirest.post("https://api-cn.etherscan.com/api")
   					 .field("module","account")
   			         .field("action","tokenbalance")
   			         .field("tag", "latest")
   			         .field("contractaddress", contractaddress)
   			      
   			         .field("address", address)
   			         .field("apikey", token)
   			         .asString();

   			EthGetBalance getBalance= JSON.parseObject(response.getBody(),EthGetBalance.class);
   			
   			String str = getBalance.getResult().toString();
   			

   			BigDecimal big= new BigDecimal(str);
   			
   			
   			return big;
   		} catch (UnirestException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		
   		return null;
   		
   		
   		
   	}
    
    /**
     * 根据has获取交易信息
     * @param hash
     * @return
     */
    
	public static EthTransaction ethGetTransactionByHash(String hash) {
		
		//String s =  CommonHttpClientHelper.doGet("http://api-cn.etherscan.com/api?module=proxy&action=eth_getTransactionByHash&txhash=0x1e2910a262b1008d0616a0beb24c1a491d78771baa54a33e66065e03b1f46bc1&apikey=DPQDS5BDSD3N6GG4HFCHHSX312FN1I7BYD", null);

		try {
			HttpResponse<String> response = Unirest.post("https://api-cn.etherscan.com/api")
						.field("module","account")
				         .field("action","eth_getTransactionByHash")
				         .field("txhash", hash)
				         .field("apikey", "DPQDS5BDSD3N6GG4HFCHHSX312FN1I7BYD")
				         .asString();
			EthTransaction transaction=JSON.parseObject(response.getBody(), EthTransaction.class);
			return transaction;
//			System.out.println(transaction.getId());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
    
	/**
	 * 获取以太坊日志
	 * @param blockHeight
	 * @param address
	 * @param topic0
	 * @param txid
	 * @return
	 */
    
    public boolean checkEventLog(final Long blockHeight,String address,String topic0,String txid){
        try {
            HttpResponse<String> response = Unirest.post("https://api-cn.etherscan.com/api")
                    .field("module", "logs")
                    .field("action", "getLogs")
                    .field("fromBlock", blockHeight)
                    .field("toBlock",blockHeight)
                    .field("address",address)
                    .field("topic0",topic0)
                    .field("apikey", token)
                    .asString();
            logger.info("getLogs result = {}",response.getBody());
            JSONObject result = JSON.parseObject(response.getBody());
            if(result.getInteger("status")==0){
                return false;
            }
            else{
                JSONArray txs = result.getJSONArray("result");
                for(int i=0;i<txs.size();i++){
                    JSONObject item = txs.getJSONObject(i);
                    if(item.getString("transactionHash").equalsIgnoreCase(txid))return true;
                }
                return false;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

   
/**
 * 广播交易
 * @param hexValue
 */
    public String sendRawTransaction(String hexValue){
        try {
            HttpResponse<String> response =  Unirest.post("https://api-cn.etherscan.com/api")
                    .field("module","proxy")
                    .field("action","eth_sendRawTransaction")
                    .field("hex",hexValue)
                    .field("apikey",token)
                    .asString();
            logger.info("sendRawTransaction result = {}",response.getBody());
            Object resultObject = JSON.parseObject(response.getBody()).get("result");//取哈希值
            if(resultObject != null) {
            	//转账成功返回的哈希值
            	return resultObject.toString();
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public EthCall getEthCall(String address,String data) {
    	try {
			HttpResponse<String> response = Unirest.post("https://api-cn.etherscan.com/api").
					field("module","proxy")
			        .field("action","eth_call")
			        .field("to", address)
			        .field("tag", "latest")
			        .field("data", data)
			        .field("apikey",token)
			        .asString();
			logger.info("getEthGetTransactionCount result = {}",response.getBody());
			EthCall ethCall = JSON.parseObject(response.getBody(), EthCall.class);
			return ethCall;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
    public EthGetTransactionCount getEthGetTransactionCount(String address) {
    	try {
			HttpResponse<String> response = Unirest.post("https://api-cn.etherscan.com/api").
					field("module","proxy")
			        .field("action","eth_getTransactionCount")
			        .field("address", address)
			        .field("tag", "latest")
			        .field("apikey",token)
			        .asString();
			logger.info("getEthGetTransactionCount result = {}",response.getBody());
			EthGetTransactionCount EthGetTransactionCount = JSON.parseObject(response.getBody(), EthGetTransactionCount.class);
			return EthGetTransactionCount;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
    public static void main(String[] args){
        /*EtherscanApi api = new EtherscanApi();
        //api.sendRawTransaction("0xf86e0585012a05f200830f4240950db4a46649c041b506e5d4965b8ed4f682f75b18ff8801c6fc1379856000801ca08e5e25623e588079f4fd795b48f34f128a07b63dc7385ca7d533671014417a11a00d093b1512b40265daf5db6bf3762188490a8a8d812a4756b599378e0d42855e");
        String txid = "0x4d95cdb7864f4aab4a349dbd2e3f8b9db1deb0f85f85d9a8c37a677958129c97";
        boolean ret = api.checkEventLog(6030689L,"0x0b42c73446e4090a7c1db8ac00ad46a38ccbc2ac","0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",txid);
        System.out.println(ret);*/
    	
    	/*String num="0x"+Long.toHexString(436);
    	getBlock(num);*/
    	/*getNetworkBlockHeight();*/
    	
    	//getBalance("0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a");
    //	getGasPrice();
    	System.out.println(getNetworkBlockHeight());
    	
    }
}
