package net.snails.scheduler.service;

import net.snails.scheduler.dao.CompanyMapper;
import net.snails.scheduler.model.Company;
import net.snails.scheduler.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krisjin
 * @date 2014-7-9上午10:42:53
 */
public class CompanyService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void addCompany(Company company) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
            companyMapper.add(company);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
