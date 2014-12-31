package net.snails.scheduler;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author krisjin
 * @date 2014-7-9下午2:28:54
 */

@TargetUrl("http://blog.csdn.net/\\w+/article/details/\\d+")
public class CSDNBlogCrawler {

	@ExtractBy(value = "//span[@class='link_title']/a/text()")
	private String title;

	@ExtractBy(value = "//div[@id='article_content']/outerHtml()")
	private String content;

	@ExtractBy(value = "//span[@class='link_postdate']/text()")
	private 
	String date;

	@ExtractByUrl
	private String url;
	

	public static void main(String[] args) {
		OOSpider.create(Site.me().setRetryTimes(2), new CSDNBlogPageModelPipeline(),CSDNBlogCrawler.class).thread(40).addUrl("http://blog.csdn.net/").run();
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

//	public String getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(String author) {
//		this.author = author;
//	}

	public String getUrl() {
		return url;
	}
	
}
