package net.snails.scheduler;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author krisjin
 * @date 2014-7-9下午2:28:54
 */

@HelpUrl("http://www.ifanr.com/page/\\w+")
@TargetUrl("http://www.ifanr.com/\\d+")
public class IfanrCrawler {

	@ExtractBy(value = "//div[@class='entry-header']//h1//a/text()")
	private String title;

	@ExtractBy(value = "//div[@id='entry-content']/outerHtml()")
	private String content;

	@ExtractBy(value = "//div[@class='entry-meta']/tidyText()")
	private String date;

	@ExtractBy(value = "//div[@class='entry-meta']//a/text()")
	private String author;

	@ExtractByUrl
	private String url;
	

	public static void main(String[] args) {
		OOSpider.create(Site.me(), new IfanrPageModelPipeline(),IfanrCrawler.class).thread(30).addUrl("http://www.ifanr.com/page/37").run();
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		return date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}


	
}
