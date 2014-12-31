package net.snails.scheduler.pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import net.snails.scheduler.constant.Media;
import net.snails.scheduler.dao.TechArticleMapper;
import net.snails.scheduler.model.TechArticle;
import net.snails.scheduler.service.TechArticleService;
import net.snails.scheduler.utils.BloomFilter;
import net.snails.scheduler.utils.DateUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.mysql.jdbc.StringUtils;

public class CSDNBlogPipeline implements Pipeline {

	AtomicInteger count = new AtomicInteger(1);
	int capicity = 10000000;
	int initDataSize = 8000000;
	private BloomFilter bloomfilter = new BloomFilter(capicity, initDataSize, 8);
	
	private TechArticleService techArticleService = new TechArticleService();
	
	public void process(ResultItems result, Task task) {
		FileWriter writer = null;

		bloomfilter.init("e:/tech-article.txt");

		String title = result.get("title");
		String date = result.get("date");
		String content = result.get("content");
		String url = result.get("url");

		TechArticle article = new TechArticle();
		article.setArticleSite(Media.CSDN_BLOG);
		article.setArticleUrl(url);

		if (StringUtils.isNullOrEmpty(title)) {
			return;
		}

		if (bloomfilter.contains(url)) {
			return;
		}

		bloomfilter.put(url);
		if (date == null) {
			article.setArticlePostDate(new Date());
		} else {
			Date d = DateUtil.convertStringDateTimeToDate(date, "yyyy-MM-dd HH:mm");
			article.setArticlePostDate(d);
		}
		article.setArticleTitle(title.trim());
		article.setArticleContent(content);
		techArticleService.addTechArticle(article);
		try {
			writer = new FileWriter("e:/tech-article.txt", true);
			writer.write((url + "\n"));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("save..." + title.trim());

	}


}
