package net.snails.scheduler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import net.snails.scheduler.model.TechNews;
import net.snails.scheduler.service.TechNewsService;
import net.snails.scheduler.utils.BloomFilter;
import net.snails.scheduler.utils.DateUtil;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import com.mysql.jdbc.StringUtils;

/**
 * @author krisjin
 * @date 2014-7-9上午11:31:23
 */

public class TencentTechPageModelPipeline implements PageModelPipeline {
	int capicity = 1000000;
	int initDataSize = 800000;
	private BloomFilter bloomfilter = new BloomFilter(capicity, initDataSize, 8);
	private TechNewsService newsService = new TechNewsService();

	public void process(Object obj, Task task) {
		FileWriter writer = null;

		bloomfilter.init("e:/tech-news.txt");
		if (obj instanceof TencentTechCrawler) {
			TencentTechCrawler qqt = (TencentTechCrawler) obj;
			String title = qqt.getTitle();
			String date = qqt.getDate();
			String content = qqt.getContent();

			TechNews techNews = new TechNews();
			techNews.setMedia("腾讯科技");
			techNews.setMediaUrl(qqt.getUrl());
			if (StringUtils.isNullOrEmpty(title)) {
				return;
			}

			if (bloomfilter.contains(qqt.getUrl())) {
				System.out.println(qqt.getUrl() + " have repeat...");
				return;
			}

			if (StringUtils.isNullOrEmpty(qqt.getImgUrl())) {
				techNews.setThumbnailsUrl("");
			} else {
				techNews.setThumbnailsUrl(qqt.getImgUrl());
			}
			if (StringUtils.isNullOrEmpty(qqt.getAuthor())) {
				techNews.setAuthor("");
			} else {
				techNews.setAuthor(qqt.getAuthor());
			}

			if (date == null) {
				if (qqt.getDate2() != null) {
					techNews.setPostDate(DateUtil.convertStringDateTimeToDate(qqt.getDate2(), "yyyy-MM-dd HH:mm"));
				} else {
					techNews.setPostDate(new Date());
				}
			} else {
				Date d = DateUtil.convertStringDateTimeToDate(date, "yyyy年MM月dd日HH:mm");
				techNews.setPostDate(d);
			}
			techNews.setTitle(title.trim());
			techNews.setContent(content);
			newsService.addTechNews(techNews);
			try {
				writer = new FileWriter("e:/tech-news.txt", true);
				writer.write((qqt.getUrl() + "\n"));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("保存:" + techNews.getTitle());

		}
	}
}
