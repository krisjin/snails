package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class IfanrPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(6000).setRetryTimes(3).setDomain("www.ifanr.com");

	public void process(Page page) {

		List<String> links = page.getHtml().links().regex("http://www.ifanr.com/\\d+").all();

		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//div[@class='entry-header']//h1//a/text()").toString());

		page.putField("content", page.getHtml().xpath("//div[@id='entry-content']/outerHtml()").toString());

		page.putField("date", page.getHtml().xpath("//div[@class='entry-meta']/tidyText()").toString());

		page.putField("author", page.getHtml().xpath("//div[@class='entry-meta']//a/text()").toString());

		page.putField("url", page.getRequest().getUrl());

		if (page.getResultItems().get("title") == null || page.getResultItems().get("content") == null) {
			page.setSkip(true);
		}
	}

	public Site getSite() {
		return site;
	}

}
