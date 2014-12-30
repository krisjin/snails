package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.OscBlogPageProcessor;
import net.snails.scheduler.pipeline.OSCBlogPipeline;
import us.codecraft.webmagic.Spider;

public class OscBlogCrawler {
	public static void main(String[] args) {
		Spider.create(new OscBlogPageProcessor()).addUrl("http://my.oschina.net").addPipeline(new OSCBlogPipeline()).thread(30).run();
	}
}
//http://my.oschina.net/sincoder/blog/81197