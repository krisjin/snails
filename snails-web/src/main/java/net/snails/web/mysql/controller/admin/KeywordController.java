package net.snails.web.mysql.controller.admin;

import net.snails.web.mysql.entity.Keyword;
import net.snails.web.mysql.service.KeywordService;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author krisjin
 */
@Controller
@RequestMapping("/admin/ms")
public class KeywordController {

	@Autowired
	private KeywordService keywordService;

	@RequestMapping(value = "/keyword.htm", method = RequestMethod.GET)
	public String listNews(@RequestParam(value = "p", defaultValue = "1") int p, ModelMap model) {
		Pagination<Keyword> page = keywordService.getKeywordWithPage(p, 15);
		model.put("page", page);
		return "page/keyword/listKeyword";
	}

	@RequestMapping(value = "/keyword/{id}.htm", method = RequestMethod.GET)
	public String keywordDetail(@PathVariable("id") int id, ModelMap model) {
		Keyword keyword = keywordService.getKeywordById(id);
		model.put("keyword", keyword);
		return "page/keyword/keywordDetail";
	}


	@RequestMapping(value = "/keyword/add.htm", method = RequestMethod.GET)
	public String addKeyword() {

		return "page/keyword/addKeyword";
	}
	
	@RequestMapping(value = "/keyword/save.htm", method = RequestMethod.POST)
	public String saveKeyword(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "content") String content, ModelMap model) {
		Keyword keyword = new Keyword();
		keyword.setName(name);
		keyword.setContent(content);
		keywordService.addKeyword(keyword);
		return "redirect:/admin/ms/keyword.htm";
	}

	@RequestMapping(value = "/keyword/updateKeyword.htm", method = RequestMethod.GET)
	public String updateKeyword(@RequestParam(value = "id", defaultValue = "0") int id, ModelMap model) {
		Keyword keyword = keywordService.getKeywordById(id);
		model.put("keyword", keyword);
		return "page/keyword/updateKeyword";
	}
	
	@RequestMapping(value = "/keyword/updateSave.htm", method = RequestMethod.POST)
	public String updateSaveKeyword(@RequestParam(value="id") int id,@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "content") String content, ModelMap model) {
		Keyword keyword = new Keyword();
		keyword.setId(id);
		keyword.setName(name);
		keyword.setContent(content);
		keywordService.updateKeyword(keyword);
		return "redirect:/admin/ms/keyword.htm";
	}
	
	
	
}
