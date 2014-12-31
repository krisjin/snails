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
@TargetUrl("http://www.36kr.com/p/\\d+.html")
public class Kr36Crawler {

	@ExtractBy(value = "//h1[@class='single-post__title']/text()")
	private String title;

	@ExtractBy(value = "//section[@class='article']/outerHtml()")
	private String content;

	@ExtractBy(value = "//abbr[@class='timeago']/@title")
	private String date;

	@ExtractBy(value = "//div[@class='single-post__postmeta']/a/text()")
	private String author;

	@ExtractByUrl
	private String url;
	
	@ExtractBy(value="//div[@class='single-post-header__headline']/img/@src")
	private String imgUrl;

	public static void main(String[] args) {
		OOSpider.create(Site.me().setRetryTimes(3), new Kr36TechPageModelPipeline(),Kr36Crawler.class).thread(40).addUrl("http://www.36kr.com").run();
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

	public String getImgUrl() {
		return imgUrl;
	}

	
}
