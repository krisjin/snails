package net.snails.scheduler.dao;

import net.snails.scheduler.model.Company;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2015/8/11.
 */
@Repository
public interface CompanyMapper {
    public void add(Company company);
}
