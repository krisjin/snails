package net.snails.web.mysql.service;

import java.util.List;

import net.snails.entity.mysql.Keyword;
import net.snails.web.mysql.dao.KeywordDao;
import net.snails.web.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeywordService {

	@Autowired
	private KeywordDao keywordDao;

	public void addKeyword(Keyword keyword) {
		keywordDao.addKeyword(keyword);
	}

	public void deleteKeywordById(int id) {
		keywordDao.deleteKeyword(id);
	}

	public void updateKeyword(Keyword keyword) {
		keywordDao.updateKeyword(keyword);
	}

	public Keyword getKeywordById(int id) {
		return keywordDao.getKeywordById(id);
	}

	public Pagination<Keyword> getKeywordWithPage(int pageNO, int perPageRecords) {
		Pagination<Keyword> pagination = new Pagination<Keyword>();
		pagination.setCurrentPageSize(pageNO);
		pagination.setPerPageRecords(perPageRecords);

		List<Keyword> keywords = keywordDao.getKeywordWithPage(pagination.getOffsetRecords(), perPageRecords);
		pagination.setTotalRecords(this.keywordDao.getTotalKeywordCounts());

		pagination.setData(keywords);

		return pagination;
	}

}
