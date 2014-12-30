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

@TargetUrl("http://www.huxiu.com/article/\\d+/\\d+.html")
public class HuXiuCrawler {

	@ExtractBy(value = "//div[@class='center-ctr-box']//h1/text()")
	private String title;

	@ExtractBy(value = "//div[@id='neirong_box']/outerHtml()")
	private String content;

	@ExtractBy(value = "//time[@id='pubtime_baidu']/text()")
	private String date;

	@ExtractBy(value = "//span[@class='recommenders']//a[@id='author_baidu']/text()")
	private String author;

	@ExtractByUrl
	private String url;
	
	@ExtractBy(value="//span[@class='span-img']/img/@src")
	private String imgUrl;

	public static void main(String[] args) {
		OOSpider.create(Site.me(), new HuXiuPageModelPipeline(),HuXiuCrawler.class).thread(10).addUrl("http://www.huxiu.com/").run();
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
