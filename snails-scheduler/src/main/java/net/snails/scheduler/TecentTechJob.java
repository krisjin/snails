package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.TecentTechPageProcessor;
import net.snails.scheduler.pipeline.TecentTechPipeline;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
@Service("tecentTechJobService")
public class TecentTechJob {

	public void execute() {
		Spider.create(new TecentTechPageProcessor()).addUrl("http://tech.qq.com").addPipeline(new TecentTechPipeline())
				.thread(50).run();
	}

}
