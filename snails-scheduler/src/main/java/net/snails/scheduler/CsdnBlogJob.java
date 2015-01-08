package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.CSDNBlogPageProcessor;
import net.snails.scheduler.pipeline.CSDNBlogPipeline;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

@Service("csdnBlogJobService")
public class CsdnBlogJob {

	public void execute() {
		Spider.create(new CSDNBlogPageProcessor()).addUrl("http://blog.csdn.net/pelick/article/details/7015369").addPipeline(new CSDNBlogPipeline())
				.thread(30).run();
	}

}
