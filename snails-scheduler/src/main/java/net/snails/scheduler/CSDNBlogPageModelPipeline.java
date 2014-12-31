package net.snails.scheduler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import net.snails.scheduler.constant.Media;
import net.snails.scheduler.model.TechArticle;
import net.snails.scheduler.service.TechArticleService;
import net.snails.scheduler.utils.BloomFilter;
import net.snails.scheduler.utils.DateUtil;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import com.mysql.jdbc.StringUtils;

/**
 * @author krisjin
 * @date 2014-7-9上午11:31:23
 */

public class CSDNBlogPageModelPipeline implements PageModelPipeline {
	AtomicInteger count = new AtomicInteger(1);
	int capicity = 10000000;
	int initDataSize = 8000000;
	private BloomFilter bloomfilter = new BloomFilter(capicity, initDataSize, 8);
	private TechArticleService articleService = new TechArticleService();

	public void process(Object obj, Task task) {
		FileWriter writer = null;

		bloomfilter.init("e:/tech-article.txt");
		if (obj instanceof CSDNBlogCrawler) {
			CSDNBlogCrawler qqt = (CSDNBlogCrawler) obj;
			String title = qqt.getTitle();
			String date = qqt.getDate();
			String content = qqt.getContent();

			TechArticle article = new TechArticle();
			article.setArticleSite(Media.CSDN_BLOG);
			article.setArticleUrl(qqt.getUrl());

			if (StringUtils.isNullOrEmpty(title)) {
				return;
			}

			if (bloomfilter.contains(qqt.getUrl())) {
				return;
			}

			if (date == null) {
				article.setArticlePostDate(new Date());
			} else {
				Date d = DateUtil.convertStringDateTimeToDate(date, "yyyy-MM-dd HH:mm");
				article.setArticlePostDate(d);
			}
			article.setArticleTitle(title.trim());
			article.setArticleContent(content);
			articleService.addTechArticle(article);
			try {
				writer = new FileWriter("e:/tech-article.txt", true);
				writer.write((qqt.getUrl() + "\n"));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("save..." + title.trim());

		}
	}
}
