package net.snails.scheduler.pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import net.snails.scheduler.constant.Media;
import net.snails.scheduler.constant.SystemConstant;
import net.snails.scheduler.model.TechArticle;
import net.snails.scheduler.service.TechArticleService;
import net.snails.scheduler.utils.BloomFilter;
import net.snails.scheduler.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.mysql.jdbc.StringUtils;

public class CSDNBlogPipeline implements Pipeline {
	
	private Logger logger = LoggerFactory.getLogger(CSDNBlogPipeline.class);
	
	AtomicInteger count = new AtomicInteger(1);

	private TechArticleService techArticleService = new TechArticleService();

	public void process(ResultItems result, Task task) {
		FileWriter writer = null;


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

		BloomFilter bloomfilter =BloomFilter.newInstance();
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
			writer = new FileWriter(SystemConstant.BLOOM_FILTER_FILE, true);
			writer.write((url + "\n"));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("save..." + title.trim());
	}

}
