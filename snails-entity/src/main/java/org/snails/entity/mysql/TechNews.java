package org.snails.entity.mysql;

import java.io.Serializable;
import java.util.Date;

/**
 * 科技新闻/咨询类
 * 
 * @author krisjin
 * @date 2015-1-19
 */
public class TechNews implements Serializable {

	private static final long serialVersionUID = 610010800364818165L;

	private long id;

	private String tag;

	private String newsUrl;

	private String newsAuthor;

	private String newsTitle;

	private String newsMedia;

	private String newsMeidaCode;

	private String sourceNewsMeida;

	private String sourceNewsMediaCode;

	private String newsContent;

	private Date newsPostDate;

	private Date newsInsertDate;

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

	public String getNewsMedia() {
		return newsMedia;
	}

	public void setNewsMedia(String newsMedia) {
		this.newsMedia = newsMedia;
	}

	public String getNewsMeidaCode() {
		return newsMeidaCode;
	}

	public void setNewsMeidaCode(String newsMeidaCode) {
		this.newsMeidaCode = newsMeidaCode;
	}

	public String getSourceNewsMeida() {
		return sourceNewsMeida;
	}

	public void setSourceNewsMeida(String sourceNewsMeida) {
		this.sourceNewsMeida = sourceNewsMeida;
	}

	public String getSourceNewsMediaCode() {
		return sourceNewsMediaCode;
	}

	public void setSourceNewsMediaCode(String sourceNewsMediaCode) {
		this.sourceNewsMediaCode = sourceNewsMediaCode;
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

	public long getNewsCommentId() {
		return newsCommentId;
	}

	public void setNewsCommentId(long newsCommentId) {
		this.newsCommentId = newsCommentId;
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}

}
