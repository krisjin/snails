package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
public class Kr36PageProcessor implements PageProcessor {
	private Site site = Site.me().setTimeOut(6000).setRetryTimes(3).setDomain("www.36kr.com");

	public void process(Page page) {

		List<String> links = page.getHtml().links().regex("http://www.36kr.com/p/\\d+.html").all();
		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//h1[@class='single-post__title']/text()").toString());

		page.putField("content", page.getHtml().xpath("//section[@class='article']/outerHtml()").toString());

		page.putField("date", page.getHtml().xpath("//abbr[@class='timeago']/@title").toString());

		page.putField("author", page.getHtml().xpath("//div[@class='single-post__postmeta']/a/text()").toString());

		page.putField("url", page.getRequest().getUrl());

		if (page.getResultItems().get("title") == null || page.getResultItems().get("content") == null) {
			page.setSkip(true);
		}

	}

	public Site getSite() {
		return site;
	}

}
