package net.snails.scheduler.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author krisjin
 */
public class LagouPageProcessor implements PageProcessor {

    private Site site = Site.me().setTimeOut(6000).setRetryTimes(3).setDomain("www.lagou.com");

    public void process(Page page) {
        List<String> links1 = page.getHtml().links().regex("http://www.lagou.com/gongsi/\\w+.html").all();
        page.addTargetRequests(links1);

        page.putField("companyName", page.getHtml().xpath("//h1[@class='ellipsis']/a/text()").toString());
        
        page.putField("industry", page.getHtml().xpath("//div[@class='item_content']//li[@class='industry']/span/text()").toString());
        
        page.putField("financing", page.getHtml().xpath("//div[@class='item_content']//li[@class='financing']/span/text()").toString());
        
        page.putField("scale", page.getHtml().xpath("//div[@class='item_content']//li[@class='scale']/span/text()").toString());
        
        page.putField("location", page.getHtml().xpath("//div[@class='item_content']//li[@class='location']/span/text()").toString());
        
        page.putField("introduction", page.getHtml().xpath("//div[@class='company_intro_text']/outerHtml()").toString());
        
        page.putField("site", page.getHtml().xpath("//h1[@class='ellipsis']//a/@href").toString());

        if (page.getResultItems().get("companyName") == null) {
            page.setSkip(true);
        }

    }

    public Site getSite() {
        return site;
    }
}
