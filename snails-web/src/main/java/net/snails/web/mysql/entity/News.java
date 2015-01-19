package net.snails.web.mysql.entity;

import java.util.Date;

public class News {

	private long id;

	private String newsTitle;

	private String newsAuthor;

	private String newsMedia;

	private String newsUrl;

	private int newsType;

	private Date newsPosttime;
	
	private String newsContent;
	
	private String summary;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}

	public String getNewsMedia() {
		return newsMedia;
	}

	public void setNewsMedia(String newsMedia) {
		this.newsMedia = newsMedia;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public int getNewsType() {
		return newsType;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	public Date getNewsPosttime() {
		return newsPosttime;
	}

	public void setNewsPosttime(Date newsPosttime) {
		this.newsPosttime = newsPosttime;
	}
	
	

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("News:").append("{id:").append(this.id).append(",newsTitle:").append(this.newsTitle).append("}").toString();
	}
}
