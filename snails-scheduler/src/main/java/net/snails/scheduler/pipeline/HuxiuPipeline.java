package net.snails.scheduler.pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import net.snails.scheduler.constant.Media;
import net.snails.scheduler.constant.SystemConstant;
import net.snails.scheduler.model.TechNews;
import net.snails.scheduler.service.TechNewsService;
import net.snails.scheduler.utils.BloomFilter;
import net.snails.scheduler.utils.DateUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.mysql.jdbc.StringUtils;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
public class HuxiuPipeline implements Pipeline {

	private TechNewsService techNewsService = new TechNewsService();

	public void process(ResultItems result, Task task) {

		FileWriter writer = null;

		String title = result.get("title");
		String date = result.get("date");
		String content = result.get("content");
		String author = result.get("author");
		String url = result.get("url");

		TechNews news = new TechNews();
		news.setMedia(Media.HUXIU);
		news.setMediaUrl(url);
		news.setContent(content);

		if (StringUtils.isNullOrEmpty(title)) {
			return;
		}
		news.setTitle(title.trim());

		BloomFilter bloomFilter = BloomFilter.newInstance();

		if (bloomFilter.contains(url)) {
			return;
		}

		if (StringUtils.isNullOrEmpty(author)) {
			news.setAuthor("");
		} else {
			news.setAuthor(author);
		}

		if (date == null) {
			news.setPostDate(new Date());
		} else {
			Date d = DateUtil.convertStringDateTimeToDate(date, "yyyy-MM-dd HH:mm:ss");
			news.setPostDate(d);
		}
		techNewsService.addTechNews(news);
		try {
			writer = new FileWriter(SystemConstant.BLOOM_FILTER_TECH_NEWS_FILE, true);
			writer.write(url + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
