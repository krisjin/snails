package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class TecentTechPageProcessor implements PageProcessor {

	private Site site = Site.me().setDomain("tech.qq.com");

	public void process(Page page) {

		List<String> links = page.getHtml().links().regex("http://tech.qq.com/a/\\d+/\\d+.htm").all();

		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//div[@class='hd']/h1/text()").toString());

		page.putField("content", page.getHtml().xpath("//div[@id='Cnt-Main-Article-QQ']/outerHtml()").toString());

		page.putField("date", page.getHtml().xpath("//span[@class='pubTime']/text()").toString());

		page.putField("url", page.getRequest().getUrl());

		page.putField("imgUrl", page.getHtml().xpath("//p[@align='center']/img/@src").toString());

		page.putField("date2", page.getHtml().xpath("//span[@class='article-time']/text()").toString());

		page.putField("author", page.getHtml().xpath("//span[@class='auth']/text()").toString());
		
		if (page.getResultItems().get("title") == null || page.getResultItems().get("content") == null) {
			page.setSkip(true);
		}

	}

	public Site getSite() {
		return site;
	}

}
