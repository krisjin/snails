package net.snails.entity.mysql;

/**
 * 功能组
 * 
 * @author krisjin
 * @date 2014-11-7
 */
public class FunctionGroup {
	
	private int id;

	private String name;

	private String actionUrl;

	private String[] functions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String[] getFunctions() {
		return functions;
	}

	public void setFunctions(String[] functions) {
		this.functions = functions;
	}

}
