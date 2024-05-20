package com.ws.base.config.admin;
/**
 * 用来配置拦截器的配置类
 */
import com.ws.base.constant.RuntimeConstant;
import com.ws.base.interceptor.admin.AdminAuthorityInterceptor;
import com.ws.base.interceptor.admin.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    @Autowired
    private AdminAuthorityInterceptor adminAuthorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/**").excludePathPatterns(RuntimeConstant.loginExcludePathPatterns);
        registry.addInterceptor(adminAuthorityInterceptor).addPathPatterns("/**").excludePathPatterns(RuntimeConstant.authorityExcludePathPatterns);
    }

}
