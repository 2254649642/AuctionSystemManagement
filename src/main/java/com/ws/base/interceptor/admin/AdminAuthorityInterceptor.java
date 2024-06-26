package com.ws.base.interceptor.admin;

import com.alibaba.fastjson.JSON;
import com.ws.base.bean.CodeMsg;
import com.ws.base.bean.LoginType;
import com.ws.base.util.MenuUtil;
import com.ws.base.util.SessionUtil;
import com.ws.base.util.StringUtil;
import com.ws.base.entity.admin.Menu;
import com.ws.base.entity.admin.User;
import com.ws.base.entity.home.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限统一管理拦截器
 * @author Administrator
 *
 */
@Component
public class AdminAuthorityInterceptor implements HandlerInterceptor{

	private Logger log = LoggerFactory.getLogger(AdminAuthorityInterceptor.class);
	
	@Override
	public boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String requestURI = request.getRequestURI();
		Object type = request.getSession().getAttribute("type");

		List<Menu> authorities = null;

		if((Integer) type == LoginType.ADMINISTRATOR.getCode()){
			User loginedUser = SessionUtil.getLoginedUser();
			log.info("进入权限控制拦截器" + requestURI);
			authorities = loginedUser.getRole().getAuthorities();
		}else if((Integer) type == LoginType.ORGANIZATION.getCode()){
			Organization loginedOrganization = SessionUtil.getLoginedOrganization();
			log.info("进入权限控制拦截器" + requestURI);
			authorities = loginedOrganization.getRole().getAuthorities();
		}

		if(!MenuUtil.isExistUrl(requestURI, authorities)){
			//进入这里，表示权限不存在，首先判断是否是ajax请求
			if(StringUtil.isAjax(request)){
				//表示是ajax请求
				try {
					log.info("该请求无权限，已ajax方式返回提示，url=" + requestURI);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSON.toJSONString(CodeMsg.ADMIN_NO_RIGHT));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			//说明是普通的请求，可直接重定向到无权限提示页面
			try {
				log.info("该请求无权限，重定向到无权限提示页面，url=" + requestURI);
				response.sendRedirect("/system/no_right");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		log.info("该请求符合权限要求，放行" + requestURI);
		return true;
	}
}
