package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
public class HuxiuPageProcessor implements PageProcessor {

	private Site site = Site.me().setTimeOut(6000).setRetryTimes(3).setDomain("www.huxiu.com");

	public void process(Page page) {

		List<String> links = page.getHtml().links().regex("http://www.huxiu.com/article/\\d+/\\d+.html").all();
		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//div[@class='center-ctr-box']//h1/text()").toString());

		page.putField("content", page.getHtml().xpath("//div[@id='neirong_box']/outerHtml()").toString());

		page.putField("date", page.getHtml().xpath("//time[@id='pubtime_baidu']/text()").toString());

		page.putField("author", page.getHtml().xpath("//span[@class='recommenders']//a[@id='author_baidu']/text()").toString());

		page.putField("url", page.getRequest().getUrl());

		if (page.getResultItems().get("title") == null || page.getResultItems().get("content") == null) {
			page.setSkip(true);
		}

	}

	public Site getSite() {
		return site;
	}

}
