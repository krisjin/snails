package net.snails.web.mysql.service;

import java.util.List;

import net.snails.web.mysql.dao.NewsDao;
import net.snails.web.mysql.entity.News;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author krisjin
 * @date 2014-11-18
 */
@Service
public class NewsService {

	@Autowired
	private NewsDao newsDao;

	public void addNews(News news) {
		newsDao.addNews(news);
	}

	public void deleteNews(long id) {
		newsDao.deleteNews(id);
	}

	public void updateNews(News news) {
		newsDao.updateNews(news);

	}

	public News getNewsById(long id) {
		return newsDao.getNewsOneById(id);
	}

	public List<News> getNewsWithPage(Pagination<News> page) {
		return newsDao.getNewsWithPage(page.getOffsetRecords(), page.getPerPageRecords());
	}

	public long getTotalNewsCounts() {
		return newsDao.getTotalNewsCounts();
	}

	public Pagination<News> getNewsWithPage(int pageNO, int perPageRecords) {
		Pagination<News> page = new Pagination<News>();
		page.setCurrentPageSize(pageNO);
		page.setPerPageRecords(perPageRecords);
		List<News> list = this.getNewsWithPage(page);
		page.setData(list);
		page.setTotalRecords(this.getTotalNewsCounts());
		return page;
	}
	
	
	public List<News> getInfationNews(String startDate,String endDate){
		return newsDao.getInflationNews(startDate, endDate);
		
	}
	
}
