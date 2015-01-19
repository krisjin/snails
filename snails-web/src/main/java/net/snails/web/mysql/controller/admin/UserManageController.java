package net.snails.web.mysql.controller.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.snails.web.exception.ControllerValidateException;
import net.snails.web.mysql.entity.User;
import net.snails.web.util.JsonUtil;
import net.snails.web.util.MD5Util;
import net.snails.web.util.Pagination;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author krisjin
 * @date 2014-10-24
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/user")
public class UserManageController extends BaseController {

	@RequestMapping(value = "/listUser.htm", method = RequestMethod.GET)
	public String listUser(@RequestParam(value = "p", defaultValue = "1") int p, ModelMap model) {
		Pagination<User> page = userService.getUserWithPage(p, 15);
		model.put("page", page);
		return "page/user/listUser";
	}

	@RequestMapping(value = "/updateUser.htm", method = RequestMethod.GET)
	public String update(@RequestParam(value = "userId", defaultValue = "0") long userId, ModelMap modelMap, HttpServletRequest request) {
		User user = userService.getUserById(userId);

		modelMap.put("user", user);
		return "page/user/userModify";
	}

	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public JsonUtil<String> updateUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, @RequestParam(value = "userId") long userId,
			@RequestParam(value = "status") int status) {

		JsonUtil<String> json = new JsonUtil<String>();
		User user = userService.getUserById(userId);
		try {

			if (userName.equals(""))
				json.getErrors().put("userName", "用户名称不能为空");

			if (password.equals("") || password == null)
				json.getErrors().put("password", "密码不能为空");

			if (email.equals(""))
				json.getErrors().put("email", "电子邮箱不能为空");

			validate(json);
			user = userService.updateUser(userId, userName, password, status, email);
			json.setResult(true);
		} catch (Exception e) {
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/addUser.htm", method = RequestMethod.GET)
	public String addUser() {
		return "page/user/addUser";
	}

	@ResponseBody
	@RequestMapping(value = "/saveUser.json", method = RequestMethod.POST)
	public JsonUtil<String> saveUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, @RequestParam(value = "status") int status) {

		JsonUtil<String> json = new JsonUtil<String>();
		email = email.toLowerCase();
		User user = new User();
		user.setName(userName);
		user.setEmail(email);
		user.setStatus(status);
		user.setCreateTime(new Date());
		user.setPassword(MD5Util.encrypt(password));

		if (userName.equals(""))
			json.getErrors().put("userName", "用户名称不能为空");

		if (password.equals("") || password == null)
			json.getErrors().put("password", "密码不能为空");

		if (email.equals(""))
			json.getErrors().put("email", "电子邮箱不能为空");

		try {
			validate(json);
			userService.addUser(user);
			json.setResult(true);
		} catch (ControllerValidateException e) {
			e.printStackTrace();
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public JsonUtil<String> deleteUser(@RequestParam(value = "userId") String userId) {
		JsonUtil json = new JsonUtil<String>();

		userService.deleteUsers(userId);
		json.setResult(true);
		return json;
	}

}
