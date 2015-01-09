package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.SohuItPageProcessor;
import net.snails.scheduler.pipeline.SohuItPipeline;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
@Service("sohuItJobService")
public class SohuItJob {

	public void execute() {
		Spider.create(new SohuItPageProcessor()).addUrl("http://it.sohu.com").addPipeline(new SohuItPipeline()).thread(100).run();
	}

}
