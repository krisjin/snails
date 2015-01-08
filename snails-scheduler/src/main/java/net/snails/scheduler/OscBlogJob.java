package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.OscBlogPageProcessor;
import net.snails.scheduler.pipeline.OSCBlogPipeline;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

/**
 * @author krisjin
 */
@Service("oscBlogJobService")
public class OscBlogJob {

	public void execute() {
		Spider.create(new OscBlogPageProcessor()).addUrl("http://my.oschina.net").addPipeline(new OSCBlogPipeline())
				.thread(30).run();
	}

}
