package net.snails.web.mysql.controller.admin;

import javax.servlet.http.HttpServletRequest;

import net.snails.entity.mysql.User;
import net.snails.web.constant.SystemConstant;
import net.snails.web.exception.ControllerValidateException;
import net.snails.web.mysql.service.UserService;
import net.snails.web.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author krisjin
 */
public class BaseController {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected UserService userService;

	protected <T> void validate(JsonUtil<T> json) throws ControllerValidateException {
		if (json.getErrors().size() > 0) {
			json.setResult(false);
			throw new ControllerValidateException("有错误发生");
		} else {
			json.setResult(true);
		}
	}

	protected User getUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SystemConstant.USER_SESSION);
		return user;
	}
}
