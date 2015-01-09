package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
public class SohuItPageProcessor implements PageProcessor {

	private Site site = Site.me().setDomain("it.sohu.com");

	public void process(Page page) {
		
		List<String> links = page.getHtml().links().regex("http://it.sohu.com/\\d+/n\\d+.shtml").all();

		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//h1[@itemprop='headline']/text()").toString());

		page.putField("content", page.getHtml().xpath("//div[@id='contentText']/outerHtml()").toString());

		page.putField("date", page.getHtml().xpath("//span[@id='pubtime_baidu']/text()").toString());

		page.putField("author", page.getHtml().xpath("//span[@id='author_baidu']/text()").toString());

		page.putField("url", page.getRequest().getUrl());

		if (page.getResultItems().get("title") == null || page.getResultItems().get("content") == null) {
			page.setSkip(true);
		}

	}

	public Site getSite() {
		return site;
	}

}
