package net.snails.scheduler.pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import net.snails.entity.mysql.TechNews;
import net.snails.scheduler.bloom.TechNewsBloomFilter;
import net.snails.scheduler.constant.Media;
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
public class SohuItPipeline implements Pipeline {
	private TechNewsService newsService = new TechNewsService();

	public void process(ResultItems result, Task task) {

		FileWriter writer = null;

		String title = result.get("title");
		String date = result.get("date");
		String content = result.get("content");
		String author = result.get("author");
		String url = result.get("url");

		TechNews news = new TechNews();
		news.setNewsMedia("搜狐IT");
		news.setNewsUrl(url);
		news.setNewsContent(content);
		news.setNewsInsertDate(new Date());

		if (StringUtils.isNullOrEmpty(title)) {
			return;
		}
		news.setNewsTitle(title.trim());

		TechNewsBloomFilter bloomFilter = TechNewsBloomFilter.newInstance();
		if (bloomFilter.contains(url)) {
			return;
		}
		
		if (StringUtils.isNullOrEmpty(author)) {
			news.setNewsAuthor("");
		} else {
			if (author.length() > 3) {
				news.setNewsAuthor(author.substring(3));
			} else {
				news.setNewsAuthor(author);
			}
		}

		if (date == null) {
			news.setNewsPostDate(new Date());
		} else {
			Date d = DateUtil.convertStringDateTimeToDate(date, "yyyy-MM-dd HH:mm:ss");
			news.setNewsPostDate(d);
		}
		newsService.addTechNews(news);
		bloomFilter.put(url);
		try {
			writer = new FileWriter("e:/tech-news.txt", true);
			writer.write(url + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
