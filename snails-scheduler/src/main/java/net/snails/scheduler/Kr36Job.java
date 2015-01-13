package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.Kr36PageProcessor;
import net.snails.scheduler.pipeline.Kr36PipeLine;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
@Service("kr36JobService")
public class Kr36Job {

	public void execute() {

		Spider.create(new Kr36PageProcessor()).addUrl("http://www.36kr.com").addPipeline(new Kr36PipeLine()).thread(100)
				.run();

	}

}
