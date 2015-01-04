package net.snails.scheduler.pageprocessor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author krisjin
 */
public class CSDNBlogPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(6000).setRetryTimes(3).setDomain("blog.csdn.net");

	public void process(Page page) {
		
		List<String> links = page.getHtml().links().regex("http://blog.csdn.net/\\w+/article/details/\\d+").all();
		page.addTargetRequests(links);
		
		page.putField("title", page.getHtml().xpath("//span[@class='link_title']/a/text()").toString());
		
		page.putField("content", page.getHtml().xpath("//div[@id='article_content']/outerHtml()").toString());
		
		page.putField("date", page.getHtml().xpath("//span[@class='link_postdate']/text()").toString());
		
		page.putField("url", page.getRequest().getUrl().toString());
		
		if(page.getResultItems().get("title")==null){
			page.setSkip(true);
		}
		
	}

	public Site getSite() {
		return site;
	}
}
