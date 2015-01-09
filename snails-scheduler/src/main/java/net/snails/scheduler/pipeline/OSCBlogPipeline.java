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
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class OSCBlogPipeline implements Pipeline {
	
	
	AtomicInteger count = new AtomicInteger(1);
	
	private TechArticleService articleService = new TechArticleService();
	
	
	public void process(ResultItems result, Task task) {
		FileWriter writer = null;
		String title = result.get("title");
		String date = result.get("date");
		String content = result.get("content");
		String url = result.get("url");
		Date d =DateUtil.convertStringDateTimeToDate(DateUtil.parseOscBlogPostDate(date),"yyyy-MM-dd HH:mm");
		
		
		BloomFilter bloomfilter = BloomFilter.newInstance();
		
		if(bloomfilter.contains(url)){
			return;
		}
		bloomfilter.put(url);
		
		TechArticle art=new TechArticle();
		art.setArticleTitle(title.trim());
		art.setArticleUrl(url);
		art.setArticleContent(content);
		art.setArticlePostDate(d);
		art.setArticleSite(Media.OSC_BLOG);
		
		articleService.addTechArticle(art);
		
		try {
			writer = new FileWriter(SystemConstant.BLOOM_FILTER_FILE, true);
			writer.write((art.getArticleUrl() + "\n"));
			writer.close();
			System.out.println("save "+art.getArticleUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
