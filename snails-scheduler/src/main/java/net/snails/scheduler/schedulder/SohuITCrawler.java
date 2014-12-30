package net.snails.scheduler.schedulder;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author krisjin
 * @date 2014-7-9下午2:28:54
 */

@TargetUrl("http://it.sohu.com/\\d+/n\\d+.shtml")
public class SohuITCrawler {

	@ExtractBy(value = "//h1[@itemprop='headline']/text()")
	private String title;

	@ExtractBy(value = "//div[@id='contentText']/outerHtml()")
	private String content;

	@ExtractBy(value = "//span[@id='pubtime_baidu']/text()")
	private String date;

	@ExtractBy(value = "//span[@id='author_baidu']/text()")
	private String author;

	@ExtractByUrl
	private String url;
	
//	@ExtractBy(value="//span[@class='span-img']/img/@src")
//	private String imgUrl;

	public static void main(String[] args) {
		OOSpider.create(Site.me(), new SohuITPageModelPipeline(),SohuITCrawler.class).thread(60).addUrl("http://it.sohu.com/20140225/n395601377.shtml").run();
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
