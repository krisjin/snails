package net.snails.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.snails.scheduler.dao.TechArticleMapper;
import net.snails.scheduler.model.TechArticle;
import net.snails.scheduler.utils.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krisjin
 * @date 2014-7-9上午10:42:53
 */
public class TechArticleService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void addTechArticle(TechArticle techArticle) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			TechArticleMapper newsMapper = sqlSession.getMapper(TechArticleMapper.class);
			newsMapper.addTechArticle(techArticle);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	public long getTechArticleTotal() {
		long total = 0;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			TechArticleMapper tech = sqlSession.getMapper(TechArticleMapper.class);
			total = tech.getTechArticleTotal();
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

		return total;
	}

	public List getArticleUrls(long offset, long row) {
		List urlList = new ArrayList();
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			Map<String,Long>  param =new HashMap<String,Long>();
			TechArticleMapper tech = sqlSession.getMapper(TechArticleMapper.class);
			param.put("offset", offset);
			param.put("rows", row);
			urlList = tech.selectTechArticleUrls(param);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return urlList;
	}
}
