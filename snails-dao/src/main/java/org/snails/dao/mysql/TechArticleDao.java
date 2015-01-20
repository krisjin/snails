package org.snails.dao.mysql;

import java.util.List;

import net.snails.entity.mysql.TechArticle;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 技术文章数据访问接口
 * 
 * @author krisjin
 * @date 2015-1-15
 */
@Repository
public interface TechArticleDao {

	public void addTechArticle(TechArticle techArticle);

	public void updateTechArticle(TechArticle techArticle);

	public void deleteTechArticle(@Param("articleId") long articleId);

	public TechArticle getTechArticleById(@Param("articleId") long articleId);

	public List<TechArticle> getTechArticleWithPage(@Param("offset") long offset, @Param("rows") long rows);

	public long getCounts();

	public List<TechArticle> getChartTechArticle(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
