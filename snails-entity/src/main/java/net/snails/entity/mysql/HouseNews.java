package net.snails.entity.mysql;

import java.util.Date;

/**
 * 房产新闻类模型
 * 
 * @author krisjin
 * @date 2015-1-19
 */
public class HouseNews {

	private long id;

	private String tag;

	private String newsTitle;

	private String newsUrl;

	private String newsContent;

	private Date newsPostDate;

	private Date newsInsertDate;

	private String newsAuthor;

	/**
	 * 0:无效；1有效 Comment for <code>status</code>
	 */
	private int newsStatus;

	private String newsMedia;

	private String newsMediaCode;

	private String sourceNewsMedia;

	private String sourceNewsMeidaCode;

	private long newsCommentId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public Date getNewsPostDate() {
		return newsPostDate;
	}

	public void setNewsPostDate(Date newsPostDate) {
		this.newsPostDate = newsPostDate;
	}

	public Date getNewsInsertDate() {
		return newsInsertDate;
	}

	public void setNewsInsertDate(Date newsInsertDate) {
		this.newsInsertDate = newsInsertDate;
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}

	public int getNewsStatus() {
		return newsStatus;
	}

	public void setNewsStatus(int newsStatus) {
		this.newsStatus = newsStatus;
	}

	public String getNewsMedia() {
		return newsMedia;
	}

	public void setNewsMedia(String newsMedia) {
		this.newsMedia = newsMedia;
	}

	public String getNewsMediaCode() {
		return newsMediaCode;
	}

	public void setNewsMediaCode(String newsMediaCode) {
		this.newsMediaCode = newsMediaCode;
	}

	public String getSourceNewsMedia() {
		return sourceNewsMedia;
	}

	public void setSourceNewsMedia(String sourceNewsMedia) {
		this.sourceNewsMedia = sourceNewsMedia;
	}

	public String getSourceNewsMeidaCode() {
		return sourceNewsMeidaCode;
	}

	public void setSourceNewsMeidaCode(String sourceNewsMeidaCode) {
		this.sourceNewsMeidaCode = sourceNewsMeidaCode;
	}

	public long getNewsCommentId() {
		return newsCommentId;
	}

	public void setNewsCommentId(long newsCommentId) {
		this.newsCommentId = newsCommentId;
	}

}
