package net.snails.scheduler.pipeline;

import com.mysql.jdbc.StringUtils;
import net.snails.scheduler.model.Company;
import net.snails.scheduler.service.CompanyService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author krisjin
 * @date 2015年1月9日
 */
public class LgouPipeLine implements Pipeline {


    private CompanyService companyService = new CompanyService();

    public void process(ResultItems result, Task task) {
        String companyName = result.get("companyName");
        String industry = result.get("industry");
        String financing = result.get("financing");
        String scale = result.get("scale");
        String location = result.get("location");
        String introduction = result.get("introduction");
        String site = result.get("site");

        Company company = new Company();
        company.setFinancing(financing);
        company.setIndustry(industry);
        company.setScale(scale);
        company.setCompanyName(companyName);
        company.setSite(site);
        company.setLocation(location);
        company.setIntroduction(introduction);
        if (StringUtils.isNullOrEmpty(companyName)) {
            return;
        }
        companyService.addCompany(company);
    }
}
