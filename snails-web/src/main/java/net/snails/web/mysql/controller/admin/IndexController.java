package net.snails.web.mysql.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
  * @author krisjin
 */
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String login(ModelMap modelMap){
		return "page/index";
	}

}
