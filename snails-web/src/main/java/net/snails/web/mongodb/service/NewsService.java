package net.snails.web.mongodb.service;

import java.util.List;

import net.snails.web.mongodb.dao.NewsDao;
import net.snails.web.mongodb.entity.News;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author krisjin
 * @date 2014-11-5
 */
@Service("newsServiceMongoDB")
public class NewsService {

	@Autowired
	private NewsDao newsDaoMongoDB;

	public void addNews(News entity) {
		newsDaoMongoDB.save(entity);
	}

	public void updateNews(News news) {
		newsDaoMongoDB.update(news);
	}

	public News getNewsById(String id) {
		return newsDaoMongoDB.getNewsById(id);
	}

	public void deleteNews(News news) {
		newsDaoMongoDB.delete(news);
	}

	public void deleteNewsById(String id) {
		newsDaoMongoDB.deleteById(id);
	}
	
	
	public List<News> findNewsWithPage(Pagination<News> page ,String newsPosttime){
		return newsDaoMongoDB.getNewsWithPage(page);
		//return newsDao.getNewsWithPageByPosttime(page, newsPosttime);
	}
	
	public long getCountsNews(){
		
		return newsDaoMongoDB.count();
	}
	
	public List<News> findInflationNewsData(String startDate,String endDate){
		return newsDaoMongoDB.getInflationNewsData(startDate, endDate);
	} 
}
