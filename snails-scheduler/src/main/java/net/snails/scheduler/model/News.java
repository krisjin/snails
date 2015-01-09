package net.snails.scheduler.model;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6523451592546475902L;

	private String title;

	private String content;

	private String user;

	private Date postDate;

	private Long folderId;

	/**
	 * 0:无效；1有效 Comment for <code>status</code>
	 */
	private int status;

	private String media;

	private String mediaUrl;

	private String thumbnailsUrl;

	private String author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getThumbnailsUrl() {
		return thumbnailsUrl;
	}

	public void setThumbnailsUrl(String thumbnailsUrl) {
		this.thumbnailsUrl = thumbnailsUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
}
