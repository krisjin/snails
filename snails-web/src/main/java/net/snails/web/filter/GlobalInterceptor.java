package net.snails.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.snails.web.constant.SystemConstant;
import net.snails.web.mysql.entity.User;
import net.snails.web.util.HttpUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class GlobalInterceptor implements HandlerInterceptor {


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		if (null == modelAndView) {
			return;
		}
		User user = (User) request.getSession().getAttribute(SystemConstant.USER_SESSION);
		if (user == null) {
			modelAndView.addObject("isAdmin", false);
		} else {
			modelAndView.addObject("isAdmin", true);
		}
		
		String basePath = HttpUtils.getBasePath(request);
		String contextPath = HttpUtils.getContextPath(request);
		modelAndView.addObject("basePath", basePath);
		modelAndView.addObject("contextPath", contextPath);
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
