package net.snails.web.mongodb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.snails.common.algorithm.summary.Summary;
import net.snails.common.algorithm.summary.TextRankSummary;
import net.snails.web.mongodb.entity.News;
import net.snails.web.mongodb.service.NewsService;
import net.snails.web.util.DateUtil;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * @author krisjin
 * @date 2014-11-5
 */
@Controller("newsConrollerMongoDB")
@Scope("prototype")
@RequestMapping(value = "/admin/news")
public class NewsController {

	@Autowired
	private NewsService newsServiceMongoDB;

	@RequestMapping(value = "/detail.htm", method = RequestMethod.GET)
	public String getNews(@RequestParam(value = "id") String id, ModelMap model) {
		News news = newsServiceMongoDB.getNewsById(id);
		model.put("news", news);
		return "page/news/newsDetail";
	}

	@RequestMapping(value = "/list.htm", method = RequestMethod.GET)
	public String listNews(@RequestParam(value = "p", defaultValue = "1") int p,
			@RequestParam(value = "newsPosttime", defaultValue = "") String newsPosttime, ModelMap model) {

		Pagination<News> page = new Pagination<News>();
		page.setCurrentPageSize(p);
		page.setPerPageRecords(15);
		List<News> news = null;
		page.setTotalRecords(newsServiceMongoDB.getCountsNews());

		if (Strings.isNullOrEmpty(newsPosttime)) {
			news = newsServiceMongoDB.findNewsWithPage(page, null);
		} else {
			news = newsServiceMongoDB.findNewsWithPage(page, newsPosttime);
		}

		Summary summary =new TextRankSummary();
		for (News n : news) {
			String str = summary.toSummary(n.getNewsContent(), 12);
			n.setSummary(str);
		}
		page.setData(news);
		Map args = new HashMap();

		Date n = news.get(news.size() - 1).getNewsPosttime();
		if (n != null) {
			String a = DateUtil.format(n, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
			args.put("newsPosttime", a);
		}
		page.setArgs(args);
		model.put("page", page);
		return "page/news/listNews";
	}

	@RequestMapping(value = "/inflation.htm", method = RequestMethod.POST)
	public String getInflationNewsData(@RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate, ModelMap model) {

		List<News> newsList = newsServiceMongoDB.findInflationNewsData(startDate, endDate);
		model.put("newsList", newsList);
		return "page/inflation/inflation";
	}

	@RequestMapping(value = "/queryInflation.htm", method = RequestMethod.GET)
	public String queryInflationNewsData(ModelMap model) {
		List<News> newsList = new ArrayList<News>();
		model.put("newsList", newsList);
		return "page/inflation/inflation";
	}

	public NewsService getNewsSerivce() {
		return newsServiceMongoDB;
	}

	public void setNewsSerivce(NewsService newsSerivce) {
		this.newsServiceMongoDB = newsSerivce;
	}

}
