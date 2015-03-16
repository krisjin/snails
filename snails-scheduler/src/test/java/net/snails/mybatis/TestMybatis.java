package net.snails.mybatis;

import net.snails.scheduler.dao.TechArticleMapper;
import net.snails.scheduler.utils.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

public class TestMybatis {

	public static void main(String[] args) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			TechArticleMapper newsMapper = sqlSession.getMapper(TechArticleMapper.class);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

}
