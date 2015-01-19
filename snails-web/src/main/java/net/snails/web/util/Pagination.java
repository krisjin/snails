package net.snails.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class Pagination<T> {

	/**
	 * 当前页(初始第一页)
	 */
	private int currentPageSize = 1;

	/**
	 * 总页数
	 */
	private int totalPageNum;

	/**
	 * 总记录数
	 */
	private long totalRecords;

	/**
	 * 偏移记录数
	 */
	private int offsetRecords;

	/**
	 * 每页记录数
	 */
	private int perPageRecords;

	private List<T> data;

	private Map<String, String> args = new HashMap<String, String>();

	public int getCurrentPageSize() {
		return currentPageSize;
	}

	public void setCurrentPageSize(int currentPageSize) {
		this.currentPageSize = currentPageSize;
	}

	public int getTotalPageNum() {
		this.totalPageNum = (int) (Math.ceil((double) this.getTotalRecords() / (double) this.getPerPageRecords()));
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getOffsetRecords() {
		this.offsetRecords = (this.getCurrentPageSize() - 1) * this.getPerPageRecords();
		return offsetRecords;
	}

	public void setOffsetRecords(int offset) {
		this.offsetRecords = offset;
	}

	public int getPerPageRecords() {
		return perPageRecords;
	}

	public void setPerPageRecords(int perPageRecords) {
		this.perPageRecords = perPageRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getUrl(int pageNO) {
		Iterator<Entry<String, String>> iter = this.getArgs().entrySet().iterator();
		List<String> values = new ArrayList<String>();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			values.add(key + "=" + val);
		}
		values.add("p=" + pageNO);
		return "?" + StringUtils.join(values.toArray(), "&");
	}

	public void setPaginationStr(String paginationStr) {
	}

	public String getPaginationStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ul class=\"pagination\">");
		// 首页，上一页
		if (this.getCurrentPageSize() != 1) {
			sb.append("<li><a href='" + this.getUrl(1) + "' title='首页'>&lt;&lt;</a></li>");
			sb.append("<li><a href='" + this.getUrl(this.getCurrentPageSize() - 1) + "' title='上一页'>&lt;</a></li>");
		}
		// 页码
		if (this.getTotalPageNum() > 1) {
			int startNum = this.getCurrentPageSize() - 3 <= 1 ? 1 : this.getCurrentPageSize() - 3;
			int endNum = this.getCurrentPageSize() + 3 >= this.getTotalPageNum() ? this.getTotalPageNum() : this.getCurrentPageSize() + 3;
			if (startNum > 1) {
				sb.append("<li><a href='javascript:void(0);'>...</a></li>");
			}
			for (int i = startNum; i <= endNum; i++) {
				if (i == currentPageSize) {
					sb.append("<li class='active'><a   href='" + this.getUrl(i) + "' class='number current' title='" + i + "'>" + i
							+ "</a></li>");
				} else {
					sb.append("<li><a href='" + this.getUrl(i) + "' class='number' title='" + i + "'>" + i + "</a></li>");
				}
			}
			if (endNum < this.getTotalPageNum()) {
				sb.append("<li><a href='javascript:void(0);'>...</a></li>");
			}
		}
		// 下一页，尾页
		if (this.getCurrentPageSize() < this.getTotalPageNum()) {
			sb.append("<li><a href='" + this.getUrl(this.getCurrentPageSize() + 1) + "' title='下一页'>&gt;</a></li>");
			sb.append("<li><a href='" + this.getUrl(this.getTotalPageNum()) + "' title='末页'>&gt;&gt;</a></li>");
		}
		sb.append("</ul>");
		return sb.toString();
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Map<String, String> getArgs() {
		return args;
	}

	public void setArgs(Map<String, String> args) {
		this.args = args;
	}

}
