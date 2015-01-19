package net.snails.scheduler.dao;

import java.util.List;
import java.util.Map;

import org.snails.entity.mysql.TechNews;

/**
 * @author krisjin
 * @date 2015-1-19
 */
public interface TechNewsMapper {
	
	public void addTechNews(TechNews news);
	
	public List selectTechNewsUrls(Map<String, Long> map);
	
	public long getTechNewsTotal();
}
