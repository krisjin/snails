package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.GithubUserPageProcessor;
import net.snails.scheduler.pipeline.GithubUserPipeline;
import us.codecraft.webmagic.Spider;

public class GithubUserCrawler {
	public static void main(String[] args) {
		Spider.create(new GithubUserPageProcessor()).addUrl("https://github.com/krisjin").addPipeline(new GithubUserPipeline())
				.thread(5).run();
	}
}
