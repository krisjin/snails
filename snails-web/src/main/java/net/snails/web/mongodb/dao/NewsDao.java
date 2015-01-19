package net.snails.web.mongodb.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.snails.web.mongodb.entity.News;
import net.snails.web.util.Pagination;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * @author krisjin
 * @date 2014-11-6
 */
@Repository("newsDaoMongoDB")
public class NewsDao extends BaseDao<News> {

	public News getNewsById(String id) {
		return this.getMongoTemplate().findOne(new Query(Criteria.where("id").is(id)), News.class);
	}

	public List<News> getNewsWithPageByPosttime(Pagination<News> page, String newsPosttime) {

		Direction direction = Direction.DESC;
		Query query = new Query();

		query.with(new Sort(direction, "newsPosttime"));
		if (page.getCurrentPageSize() > 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(newsPosttime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			query.addCriteria(Criteria.where("newsPosttime").lt(date)).limit(page.getPerPageRecords());
		} else {
			query.limit(page.getPerPageRecords());
		}
		return this.getMongoTemplate().find(query, News.class);
	}

	public List<News> getNewsWithPage(Pagination<News> page) {
		Direction direction = Direction.DESC;
		Query query = new Query();
		query.with(new Sort(direction, "newsPosttime"));
		query.skip(page.getOffsetRecords());
		query.limit(page.getPerPageRecords());

		return this.getMongoTemplate().find(query, News.class);
	}

	public List<News> getInflationNewsData(String startDate, String endDate) {
		Direction direction = Direction.ASC;
		Query query = new Query();
		query.with(new Sort(direction, "newsPosttime"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		Date startDate1 = null;
		Date endDate1 = null;
		try {
			startDate1 = sdf.parse(startDate.replaceAll("-", "/"));
			endDate1 = sdf.parse(endDate.replaceAll("-", "/"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		query.addCriteria(Criteria.where("newsPosttime").gte(startDate1).lte(endDate1));
		query.fields().include("newsTitle");
		query.fields().include("newsPosttime");
		
		return this.getMongoTemplate().find(query, News.class);

	}

}
