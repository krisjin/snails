package net.snails.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.snails.scheduler.dao.TechNewsMapper;
import net.snails.scheduler.utils.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;
import org.snails.entity.mysql.TechNews;

/**
 * @author krisjin
 */
public class TechNewsService {

	public void addTechNews(TechNews techNews) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			TechNewsMapper techNewsMapper = sqlSession.getMapper(TechNewsMapper.class);
			techNewsMapper.addTechNews(techNews);
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
			Map<String, Long> param = new HashMap<String, Long>();
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
