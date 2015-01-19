package org.snails.entity.mysql;

import java.util.Date;

/**
 * @author krisjin
 * @date 2014年12月18日
 */
public class TechArticle {

	private long articleId;

	private String articleTitle;

	private Date articlePostDate;

	private String articleUrl;

	private String articleTag;

	private String articleContent;

	private int articleSite;

	private int articleCategory;

	private int articleReadNum;

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Date getArticlePostDate() {
		return articlePostDate;
	}

	public void setArticlePostDate(Date articlePostDate) {
		this.articlePostDate = articlePostDate;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getArticleTag() {
		return articleTag;
	}

	public void setArticleTag(String articleTag) {
		this.articleTag = articleTag;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public int getArticleSite() {
		return articleSite;
	}

	public void setArticleSite(int articleSite) {
		this.articleSite = articleSite;
	}

	public int getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(int articleCategory) {
		this.articleCategory = articleCategory;
	}

	public int getArticleReadNum() {
		return articleReadNum;
	}

	public void setArticleReadNum(int articleReadNum) {
		this.articleReadNum = articleReadNum;
	}

}
