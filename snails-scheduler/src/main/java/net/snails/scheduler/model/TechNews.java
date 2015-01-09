package net.snails.scheduler.model;

import java.io.Serializable;
import java.util.Date;

public class TechNews implements Serializable {

	private static final long serialVersionUID = 610010800364818165L;

	private long id;

	private String title;

	private String author;

	private int media;

	private String mediaUrl;

	private String content;

	private Date postDate;

	private String thumbnailsUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getThumbnailsUrl() {
		return thumbnailsUrl;
	}

	public void setThumbnailsUrl(String thumbnailsUrl) {
		this.thumbnailsUrl = thumbnailsUrl;
	}

	public int getMedia() {
		return media;
	}

	public void setMedia(int media) {
		this.media = media;
	}

}
