package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.IfanrPageProcessor;
import net.snails.scheduler.pipeline.IfanrPipeline;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
@Service("ifanrJobService")
public class IfanrJob {

	public void execute() {
		Spider.create(new IfanrPageProcessor()).addUrl("http://www.ifanr.com/page/37").addPipeline(new IfanrPipeline())
				.thread(100).run();
	}

}
