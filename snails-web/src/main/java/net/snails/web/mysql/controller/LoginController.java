package net.snails.web.mysql.controller;

import net.snails.entity.mysql.User;
import net.snails.web.constant.SystemConstant;
import net.snails.web.constant.ValidateInfoConstant;
import net.snails.web.mysql.controller.admin.BaseController;
import net.snails.web.mysql.service.UserService;
import net.snails.web.util.HttpUtils;
import net.snails.web.util.JsonUtil;
import net.snails.web.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/auth/admin")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String adminLogin(HttpServletRequest request, ModelMap modelMap) {
        return "page/login";
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String adminLogout(HttpServletRequest request, ModelMap modelMap, HttpServletResponse response) {
        request.getSession().removeAttribute(SystemConstant.USER_SESSION);
        String path = HttpUtils.getBasePath(request) + File.separator + "auth/admin/login.htm";

        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    public JsonUtil<String> userLogin(@RequestParam(value = "email") String userName,
                                      @RequestParam(value = "password") String password, HttpServletRequest request, ModelMap modelMap) {
        HttpSession session = request.getSession();
        JsonUtil<String> json = new JsonUtil<String>();
        User user = null;
        try {

            if (userName == null || userName.equals("")) {
                json.getErrors().put("email", ValidateInfoConstant.USER_EMAIL);
            } else {
                user = userService.getUserByName(userName);
                if (user == null) {
                    json.getErrors().put("email", ValidateInfoConstant.USER_EMAIL);
                }
            }


            if (StringUtils.isBlank(password)) {
                json.getErrors().put("password", ValidateInfoConstant.PASSWORD);
            }

            if (null == user) {
                json.getErrors().put("password", ValidateInfoConstant.PASSWORD);
            }

            if (!user.getPassword().equals(MD5Util.encrypt(password))) {
                json.getErrors().put("password", ValidateInfoConstant.PASSWORD);
            }

            session.setAttribute(SystemConstant.USER_SESSION, user);
            json.setResult(true);
        } catch (Exception e) {
            json.setResult(false);
        }
        return json;
    }
}
