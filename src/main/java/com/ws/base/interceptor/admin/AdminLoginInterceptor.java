package com.ws.base.interceptor.admin;

import com.alibaba.fastjson.JSON;
import com.ws.base.bean.CodeMsg;
import com.ws.base.bean.LoginType;
import com.ws.base.config.AppConfig;
import com.ws.base.util.MenuUtil;
import com.ws.base.util.StringUtil;
//import com.ws.base.config.SiteConfig;
import com.ws.base.constant.SessionConstant;
import com.ws.base.entity.admin.Menu;
import com.ws.base.entity.admin.User;
import com.ws.base.entity.home.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 登录拦截器
 * @author Administrator
 *
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor{

	private Logger log = LoggerFactory.getLogger(AdminLoginInterceptor.class);
//	@Autowired
//	private SiteConfig siteConfig;

	@Override
	public boolean  preHandle(HttpServletRequest request,
							  HttpServletResponse response,
							  Object handler){
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute(SessionConstant.SESSION_USER_AUTH_KEY, AppConfig.ORDER_AUTH);
		Object type = session.getAttribute("type");
		Object attribute = null;
		if(type != null){
			if((Integer) type == LoginType.ADMINISTRATOR.getCode()){
				attribute = session.getAttribute(SessionConstant.SESSION_USER_LOGIN_KEY);
			}else if((Integer)type == LoginType.ORGANIZATION.getCode()){
				attribute = session.getAttribute(SessionConstant.SESSION_USER_ORGANIZATION);
			}
		}

		if(attribute == null){
			log.info("用户还未登录或者session失效,重定向到登录页面,当前URL=" + requestURI);
			//首先判断是否是ajax请求
			if(StringUtil.isAjax(request)){
				//表示是ajax请求
				try {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSON.toJSONString(CodeMsg.USER_SESSION_EXPIRED));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			//说明是普通的请求，可直接重定向到登录页面
			//用户还未登录或者session失效,重定向到登录页面
			try {
				response.sendRedirect("/home/index/index");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		log.info("该请求符合登录要求，放行" + requestURI);
		if(!StringUtil.isAjax(request)){
			if((Integer) type == LoginType.ADMINISTRATOR.getCode()){
				User user = (User)attribute;
				List<Menu> authorities = user.getRole().getAuthorities();
				request.setAttribute("userTopMenus", MenuUtil.getTopMenus(authorities));
				List<Menu> secondMenus = MenuUtil.getSecondMenus(user.getRole().getAuthorities());
				request.setAttribute("userSecondMenus", secondMenus);
				request.setAttribute("userThirdMenus", MenuUtil.getChildren(MenuUtil.getMenuIdByUrl(requestURI,secondMenus),authorities));
//				request.setAttribute("siteName", siteConfig.getSiteName());
//				request.setAttribute("siteUrl", siteConfig.getSiteUrl());
			}else if((Integer) type == LoginType.ORGANIZATION.getCode()){
				Organization organization = (Organization)attribute;
				List<Menu> authorities = organization.getRole().getAuthorities();
				request.setAttribute("userTopMenus", MenuUtil.getTopMenus(authorities));
				List<Menu> secondMenus = MenuUtil.getSecondMenus(organization.getRole().getAuthorities());
				request.setAttribute("userSecondMenus", secondMenus);
				request.setAttribute("userThirdMenus", MenuUtil.getChildren(MenuUtil.getMenuIdByUrl(requestURI,secondMenus),authorities));
//				request.setAttribute("siteName", siteConfig.getSiteName());
//				request.setAttribute("siteUrl", siteConfig.getSiteUrl());
			}
		}
		return true;
	}
}
