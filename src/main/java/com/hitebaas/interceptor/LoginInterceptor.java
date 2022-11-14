package com.hitebaas.interceptor;

import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.hitebaas.crypt.Base64Utils;
import com.hitebaas.crypt.ECDSAUtils;
import com.hitebaas.crypt.RSAUtils;
import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.entity.copyright.User;
import com.hitebaas.service.UserCacheUtil;
import com.hitebaas.util.DataUtils;
import com.hitebaas.utils.SM2;
import com.hitebaas.utils.SM2.Signature;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import org.apache.commons.lang3.StringUtils;
/**
 * 登录拦截器
 *
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(LoginInterceptor.class);

	
	
	@Value("${file.path}")
	private String path;
	@Autowired
	private UserCacheUtil userCacheUtil;	
	
	private String validCode;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*Object o =  request.getSession().getAttribute(PPContanct.SESSION_USER_KEY);
		if(o == null) {
			request.setAttribute("msg", "不存在的用户。");
			request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
			//response.sendRedirect(request.getContextPath()+"/visitor/unlogin");
			return false;
		}*/
		//logger.info("你访问的URL" + request.getRequestURI());
		String agent = request.getHeader("agent");//获得客户端信息

		System.out.println("agent是======================-----------------------：" + agent);



		if(StringUtils.isNotBlank(agent)) {
			if(!"ppclient/android".equals(agent) && !"ppclient/ios".equals(agent)) {
				request.setAttribute("msg", "非法客户端");
				request.getRequestDispatcher("/visitor/agent").forward(request, response);
				return false;
			}else if("ppclient/android".equals(agent)){
				//客户端不过期的session。
				try {
					String uId = request.getHeader("uId");

					System.out.println("uid==================:" + uId);

					if(uId==null) {
						request.setAttribute("msg", "不存在的用户。");
						request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
						return false;
					}
	
					int id = Integer.parseInt(uId);//用户id
					
					
//					String time = request.getHeader("time");
//					Date date=new Date(Long.parseLong(time+"000"));
//					String t=DataUtils.getCurrentTime(date);
//					boolean bt=DataUtils.checkTime1Min(DataUtils.getCurrentTime(),t);
//					if(!bt) {
//						request.setAttribute("msg", "时间异常。");
//						System.out.println("时间异常");
//						request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
//						return false;
//					}


					String json = request.getHeader("sign");
					String sbm = request.getHeader("sbm");
					Signature s = new Gson().fromJson(json, Signature.class);
					//UserInfo ui = PPCacheUtils.getUserInfo(id);
					User ui =userCacheUtil.getUser(id);
					if(ui == null) {
						request.setAttribute("msg", "不存在的用户。");
						System.out.println("为什么用户不存在啊");
						request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
						return false;
					}
					if(!sbm.equals(ui.getSbm())) {
						request.setAttribute("msg", "用户已在另外一个客户端登入");
						System.out.println("用户已在另外一个客户端登入");
						request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
						return false;
					}
					//logger.info(id  + "," + ui.getNickName() + " ,你访问的URL" + request.getRequestURI());
					//String publicKey = ui.getPuk();
					//String content = id + time;
					/**
					 * 进行国密算法加密 结果比对
					 */
					
//					try {
//
//						SM2 sm02 = new SM2();
//						ECPoint pub1=sm02.importPublicKey(path+ui.getAddress()+".pem");
//						boolean b= sm02.verify(content, s, pub1);
//
//						if(!b) {
//							System.out.println("错误aaaaaaaaaa");
//						}
//					} catch (Exception e) {
//						throw new Exception("签名验证不通过: " + e.getMessage());
//					}

					System.out.println("???????????????????????/我到中间了");
					
					
					
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					request.setAttribute("msg", "不存在的用户。");
					request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
					return false;
				}
			}else if("ppclient/ios".equals(agent)) {
				String uId = request.getHeader("uId");
				String time = request.getHeader("time");
				String json = request.getHeader("sign");
				String sbm = request.getHeader("sbm");
				int id = Integer.parseInt(uId);//用户id
				boolean bt=DataUtils.checkTime1Min(DataUtils.getCurrentTime(),time);
				if(!bt) {
					request.setAttribute("msg", "时间异常。");
					request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
					return false;
				}
				User ui =userCacheUtil.getUser(id);
				if(ui == null) {
					request.setAttribute("msg", "不存在的用户。");
					request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
					return false;
				}
				if(!sbm.equals(ui.getSbm())) {
					request.setAttribute("msg", "用户已在另外一个客户端登入");
					request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
					return false;
				}
				//logger.info(id  + "," + ui.getNickName() + " ,你访问的URL" + request.getRequestURI());
				//String publicKey = ui.getPuk();
				String content = id + time;

				//todo 测试sm2
				//SM2 sm02 = new SM2();
				
				//BigInteger prk1=sm02.importPrivateKey(path+ui.getAddress()+".txt");

				//String prk=prk1.toString();

				//String sign=ECDSAUtils.encryptMD5(prk+content);
//				if(!sign.equals(json)) {
//					throw new Exception("签名验证不通过: " );
//				}
				
				
				
			}else {
				request.setAttribute("msg", "非法客户端");
				request.getRequestDispatcher("/visitor/agent").forward(request, response);
				return false;
			}
		}else {
			//正常的session
			User o =  (User) request.getSession().getAttribute(HiteBassConstant.SESSION_USER_KEY);
			if(o == null) {
				request.setAttribute("msg", "不存在的用户。");
				request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
				//response.sendRedirect(request.getContextPath()+"/visitor/unlogin");
				return false;
			}
			String sbm = request.getHeader("sbm");
			if(!sbm.equals(o.getSbm())) {
				request.setAttribute("msg", "用户已在另外一个客户端登入");
				request.getRequestDispatcher("/visitor/unlogin").forward(request, response);
				return false;
			}
		}


		System.out.println("我到了最底最底了！！！！！！！！！！！！！！！");
		
		return super.preHandle(request, response, handler);
	}
}
