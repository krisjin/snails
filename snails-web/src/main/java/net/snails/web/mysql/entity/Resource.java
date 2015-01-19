package net.snails.web.mysql.entity;

/**
 * @author krisjin
 * @date 2014-10-31
 */
public class Resource {
	
	private int id;

	private String actionUrl;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
