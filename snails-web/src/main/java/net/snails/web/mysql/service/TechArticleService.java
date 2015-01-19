package net.snails.web.mysql.service;

import java.util.List;

import net.snails.web.mysql.dao.TechArticleDao;
import net.snails.web.mysql.entity.TechArticle;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 技术文章数据服务
 * 
 * @author krisjin
 * @date 2015-1-15
 */
@Service
public class TechArticleService {

	@Autowired
	private TechArticleDao techArticleDao;

	public void addTechArticle(TechArticle techArticle) {
		techArticleDao.addTechArticle(techArticle);
	}

	public void deleteTechArticle(long articleId) {
		techArticleDao.deleteTechArticle(articleId);
	}

	public void updateTechArticle(TechArticle techArticle) {
		techArticleDao.updateTechArticle(techArticle);

	}

	public TechArticle getTechArticleById(long articleId) {
		return techArticleDao.getTechArticleById(articleId);
	}

	public List<TechArticle> getTechArticleWithPage(Pagination<TechArticle> page) {
		return techArticleDao.getTechArticleWithPage(page.getOffsetRecords(), page.getPerPageRecords());
	}

	public long getCounts() {
		return techArticleDao.getCounts();
	}

	public Pagination<TechArticle> getTechArticleWithPage(int pageNO, int perPageRecords) {
		Pagination<TechArticle> page = new Pagination<TechArticle>();
		page.setCurrentPageSize(pageNO);
		page.setPerPageRecords(perPageRecords);
		List<TechArticle> list = this.getTechArticleWithPage(page);
		page.setData(list);
		page.setTotalRecords(this.getCounts());
		return page;
	}

	public List<TechArticle> getChartTechArticle(String startDate, String endDate) {
		return techArticleDao.getChartTechArticle(startDate, endDate);

	}
}
