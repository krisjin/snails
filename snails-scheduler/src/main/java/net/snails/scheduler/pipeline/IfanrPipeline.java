package net.snails.scheduler.pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import net.snails.entity.mysql.TechNews;
import net.snails.scheduler.bloom.TechNewsBloomFilter;
import net.snails.scheduler.constant.SystemConstant;
import net.snails.scheduler.service.TechNewsService;
import net.snails.scheduler.utils.DateUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.mysql.jdbc.StringUtils;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
public class IfanrPipeline implements Pipeline {
	private TechNewsService techNewsService = new TechNewsService();

	public void process(ResultItems result, Task task) {

		FileWriter writer = null;

		String title = result.get("title");
		String date = result.get("date");
		String content = result.get("content");
		String url = result.get("url");
		String author = result.get("author");

		TechNews techNews = new TechNews();

		techNews.setNewsMedia("爱范儿");
		techNews.setNewsUrl(url);
		techNews.setNewsContent(content);
		techNews.setNewsInsertDate(new Date());
		if (StringUtils.isNullOrEmpty(title)) {
			return;
		}
		techNews.setNewsTitle(title.trim());

		TechNewsBloomFilter bloomFilter = TechNewsBloomFilter.newInstance();
		if (bloomFilter.contains(url)) {
			return;
		}

		if (StringUtils.isNullOrEmpty(author)) {
			techNews.setNewsAuthor("");
		} else {
			techNews.setNewsAuthor(author);
		}

		if (date == null) {
			techNews.setNewsPostDate(new Date());
		} else {
			Date d = DateUtil.convertStringDateTimeToDate(DateUtil.parseIfanrPostDate(date), "yyyy-MM-dd HH:mm");
			techNews.setNewsPostDate(d);
		}
		techNewsService.addTechNews(techNews);
		bloomFilter.put(url);
		try {
			writer = new FileWriter(SystemConstant.BLOOM_FILTER_TECH_NEWS_FILE, true);
			writer.write(url + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
