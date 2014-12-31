package net.snails.scheduler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import net.snails.scheduler.model.News;
import net.snails.scheduler.service.NewsService;
import net.snails.scheduler.utils.BloomFilter;
import net.snails.scheduler.utils.DateUtil;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import com.mysql.jdbc.StringUtils;

/**
 * @author krisjin
 * @date 2014-7-9上午11:31:23
 */

public class HuXiuPageModelPipeline implements PageModelPipeline {
	int capicity = 1000000;
	int initDataSize = 800000;
	private BloomFilter bloomfilter = new BloomFilter(capicity, initDataSize, 8);
	private NewsService newsService = new NewsService();

	public void process(Object obj, Task task) {
		FileWriter writer = null;

		bloomfilter.init("e:/tech-news.txt");
		if (obj instanceof HuXiuCrawler) {
			HuXiuCrawler qqt = (HuXiuCrawler) obj;
			String title = qqt.getTitle();
			String date = qqt.getDate();
			String content = qqt.getContent();

			News news = new News();
			news.setFolderId(2L);
			news.setStatus(1);
			news.setMedia("虎嗅");
			news.setMediaUrl(qqt.getUrl());
			if (StringUtils.isNullOrEmpty(title)) {
				return;
			}

			if (bloomfilter.contains(qqt.getUrl())) {
				System.out.println(qqt.getUrl() +" have repeat...");
				return;
			}

			if (StringUtils.isNullOrEmpty(qqt.getImgUrl())) {
				news.setThumbnailsUrl("");
			} else {
				news.setThumbnailsUrl(qqt.getImgUrl());
			}
			if (StringUtils.isNullOrEmpty(qqt.getAuthor())) {
				news.setAuthor("");
			} else {
				news.setAuthor(qqt.getAuthor());
			}

			if (date == null) {
				news.setPostDate(new Date());
			} else {
				Date d = DateUtil.convertStringDateTimeToDate(date, "yyyy-MM-dd HH:mm:ss");
				news.setPostDate(d);
			}
			news.setTitle(title.trim());
			news.setContent(content);
			newsService.addNews(news);
			try {
				writer = new FileWriter("e:/tech-news.txt", true);
				writer.write((qqt.getUrl() + "\n"));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("保存:" + news.getTitle());

		}
	}
}
