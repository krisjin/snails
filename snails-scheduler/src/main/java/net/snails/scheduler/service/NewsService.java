package net.snails.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.snails.scheduler.dao.NewsMapper;
import net.snails.scheduler.dao.TechNewsMapper;
import net.snails.scheduler.model.News;
import net.snails.scheduler.utils.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krisjin
 * @date 2014-7-9上午10:42:53
 */

public class NewsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public void addNews(News news) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NewsMapper newsMapper = sqlSession.getMapper(NewsMapper.class);
			newsMapper.addNews(news);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	public long getTechNewsTotal() {
		long total = 0;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			TechNewsMapper tech = sqlSession.getMapper(TechNewsMapper.class);
			total = tech.getTechNewsTotal();
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

		return total;
	}

	public List getMediaUrls(long offset, long row) {

		List urlList = new ArrayList();
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			Map<String,Long>  param =new HashMap<String,Long>();
			TechNewsMapper tech = sqlSession.getMapper(TechNewsMapper.class);
			param.put("offset", offset);
			param.put("rows", row);
			urlList = tech.selectTechNewsUrls(param);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

		return urlList;
	}

}
