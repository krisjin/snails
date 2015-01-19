package net.snails.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.snails.entity.mysql.User;
import net.snails.web.constant.SystemConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 后台登陆拦截
 * 
 * @author krisjin
 */
public class LoginFilter implements Filter {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		User user = (User) request.getSession().getAttribute(SystemConstant.USER_SESSION);

		if (user == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
			response.sendRedirect(basePath + "/auth/admin/login.htm");
		} else {
			chain.doFilter(request, response);
		}

	}

	public void destroy() {

	}

}
