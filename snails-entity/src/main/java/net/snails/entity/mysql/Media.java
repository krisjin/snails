package net.snails.entity.mysql;

/**
 * @author krisjin
 * @date 2014-12-17
 */
public class Media {

	private int id;
	private String mediaUrl;
	private String mediaName;
	private String mediaKeyword;
	private String mdediaDescription;
	private short mediaType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaKeyword() {
		return mediaKeyword;
	}

	public void setMediaKeyword(String mediaKeyword) {
		this.mediaKeyword = mediaKeyword;
	}

	public String getMdediaDescription() {
		return mdediaDescription;
	}

	public void setMdediaDescription(String mdediaDescription) {
		this.mdediaDescription = mdediaDescription;
	}

	public short getMediaType() {
		return mediaType;
	}

	public void setMediaType(short mediaType) {
		this.mediaType = mediaType;
	}

}
