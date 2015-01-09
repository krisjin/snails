package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author krisjin
 */
public class OscBlogPageProcessor implements PageProcessor {

	private Site site = Site.me().setDomain("my.oschina.net");

	public void process(Page page) {
		
		List<String> links1 = page.getHtml().links().regex("http://my\\.oschina\\.net/\\w+/blog").all();
		List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/\\w+/blog/\\d+").all();
		
		page.addTargetRequests(links1);
		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1/text()")
				.toString());
		page.putField("content", page.getHtml().xpath("//div[@class='BlogContent']/outerHtml()").toString());
		
		page.putField("date", page.getHtml().xpath("//div[@class='BlogStat']/text()").toString());
		
		page.putField("url", page.getRequest().getUrl());
		
		if (page.getResultItems().get("title") == null || page.getResultItems().get("content")==null) {
			page.setSkip(true);
		}
	}

	public Site getSite() {
		return site;
	}

}
