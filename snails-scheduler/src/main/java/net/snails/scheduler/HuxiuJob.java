package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.HuxiuPageProcessor;
import net.snails.scheduler.pipeline.HuxiuPipeline;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
@Service("huxiuJobService")
public class HuxiuJob {

	public void execute() {
		Spider.create(new HuxiuPageProcessor()).addUrl("http://www.huxiu.com/").addPipeline(new HuxiuPipeline()).thread(100)
				.run();
	}

}
