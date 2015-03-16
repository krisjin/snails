package net.snails.web.mysql.controller.admin;

import java.util.List;

import net.snails.common.algorithm.summary.Summary;
import net.snails.common.algorithm.summary.TextRankSummary;
import net.snails.web.mysql.entity.TechArticle;
import net.snails.web.mysql.service.TechArticleService;
import net.snails.web.util.HtmlUtil;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Joiner;

/**
 * @author krisjin
 * @date 2015-1-15
 */
@Controller
@RequestMapping("/admin/ms")
public class TechArticleController {

	@Autowired
	private TechArticleService techArticleService;

	@RequestMapping(value = "/article.htm", method = RequestMethod.GET)
	public String listArticle(@RequestParam(value = "p", defaultValue = "1") int p, ModelMap model) {
		Pagination<TechArticle> page = techArticleService.getTechArticleWithPage(p, 20);
		List<TechArticle> articleList = page.getData();

		if (articleList != null) {
			Summary summary = new TextRankSummary();
			for (TechArticle art : articleList) {
				String str = summary.toSummary(art.getArticleContent(), 12);
				art.setArticleSummary(str);
			}
		}

		page.setData(articleList);

		model.put("page", page);
		return "page/article/listArticle";
	}

	@RequestMapping(value = "/article/{articleId}.htm", method = RequestMethod.GET)
	public String newsDetail(@PathVariable("articleId") long articleId, ModelMap model) {
		TechArticle art = techArticleService.getTechArticleById(articleId);
		model.put("article", art);
		return "page/article/articleDetail";
	}

	// @RequestMapping(value = "/chart.htm", method = RequestMethod.GET)
	// public ModelAndView inflationStatistics(ModelMap model,
	// HttpServletRequest request) {
	// ModelAndView mav = new ModelAndView();
	//
	// List<TechArticle> newsList = new ArrayList<TechArticle>();
	// model.put("articleList", newsList);
	//
	// mav.addObject("cate", getCategoryJSONArray().toString());
	// mav.addObject("vals", getValueData().toString());
	// mav.setViewName("page/inflation/inflation-mysql");
	//
	// return mav;
	// }

	// @RequestMapping(value = "/inflation/chart.htm", method =
	// RequestMethod.POST)
	// public String inflationStatisticsChart(@RequestParam(value = "startDate")
	// String startDate,
	// @RequestParam(value = "endDate") String endDate, ModelMap model) {
	// List<TechArticle> newsMap =
	// techArticleService.getChartTechArticle(startDate, endDate);
	//
	// model.put("category", getCategoryJSONArray());
	// model.put("value", getValueData());
	//
	// model.put("newsList", newsMap);
	// return "page/inflation/inflation-mysql";
	// }
	//
	// @RequestMapping(value = "/positivenegative.htm")
	// public String statPositivNegative(@RequestParam(value = "startDate")
	// String startDate, @RequestParam(value = "endDate") String endDate,
	// ModelMap model) {
	// List<TechArticle> newsMap =
	// techArticleService.getChartTechArticle(startDate, endDate);
	//
	// return null;
	// }
	//
	//
	// public JSONArray getCategoryJSONArray() {
	// List<Object> cates = new ArrayList<Object>();
	// for (int i = 1; i < 30; i++) {
	// cates.add("9." + i);
	// }
	// return JSONArray.fromObject(cates);
	// }
	//
	// public JSONArray getValueData() {
	// List<Integer> nums = new ArrayList<Integer>();
	// for (int i = 1; i < 30; i++) {
	// nums.add(10 + i);
	// }
	// return JSONArray.fromObject(nums);
	// }

}
