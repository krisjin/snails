package net.snails.web.mysql.dao;

import java.util.List;

import net.snails.web.mysql.entity.News;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author krisjin
 * @date 2014-11-18
 */
@Repository
public interface NewsDao {

	public void addNews(News news);

	public void updateNews(News news);

	public void deleteNews(@Param("id") long id);

	public void deleteNewses(String id);

	public News getNewsOneById(@Param("id") long id);

	public List<News> getNewsById(@Param("id") String id);

	public List<News> getNewsWithPage(@Param("offset") long offset, @Param("rows") long rows);

	public long getTotalNewsCounts();

	public List<News> getInflationNews(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
