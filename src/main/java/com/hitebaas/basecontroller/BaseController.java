package com.hitebaas.basecontroller;

import com.hitebaas.entity.HiteBassConstant;
import com.hitebaas.entity.copyright.User;
import com.hitebaas.service.UserCacheUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;



public class BaseController{
	private String ctx;
	
	

	@Autowired
	private UserCacheUtil userCacheUtil;	
	public BaseController() {
		super();
	}
	
	protected void write(String jsonString){
		PrintWriter out = null;
		try {
			this.getResponse().setContentType("text/json;charset=utf-8");  
			this.getResponse().setCharacterEncoding("utf-8");  
			this.getResponse().setHeader("Charset", "utf-8");  
			this.getResponse().setHeader("Cache-Control", "no-cache"); 
			out = this.getResponse().getWriter();
			out.write(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			  if(out != null){
				out.flush(); 
				out.close();
			}
		}
	}
	
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public ServletContext getServletContext(){
		return getRequest().getServletContext();
		/*WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		return webApplicationContext.getServletContext();*/
	}
	
	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}

	
	public String getCtx() {
		if(StringUtils.isBlank(ctx)){
			ctx = getRequest().getContextPath();
		}
		return ctx;
	}
	
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public User  getMineInfo() {
		HttpServletRequest request = getRequest();
		String agent = request.getHeader("agent");//获得客户端信息

		System.out.println("agent是======================：" + agent);

		if(StringUtils.isNotBlank(agent)) {
			if("ppclient/android".equals(agent)) {
				String uId =  request.getHeader("uId");
				if(StringUtils.isBlank(uId)) {
					return null;
				}
				int id = Integer.parseInt(uId);
				//UserInfo ui = PPCacheUtils.getUserInfo(id);
				User ui=userCacheUtil.getUser(Integer.valueOf(uId));
				return ui;
			}else if("ppclient/ios".equals(agent)) {
				String uId =  request.getHeader("uId");
				if(StringUtils.isBlank(uId)) {
					return null;
				}
				int id = Integer.parseInt(uId);
				//UserInfo ui = PPCacheUtils.getUserInfo(id);
				User ui=userCacheUtil.getUser(Integer.valueOf(uId));
				return ui;
			} else {
				return null;
			}
		}else {
			User ui = (User) getSession().getAttribute(HiteBassConstant.SESSION_USER_KEY);
			return ui;
		}
	}
}
