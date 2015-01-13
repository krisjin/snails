package net.snails.scheduler.model;


public class JobInfo {

	private int thread;

	private String[] urls;

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

}
