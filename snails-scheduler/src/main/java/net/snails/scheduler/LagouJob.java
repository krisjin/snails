package net.snails.scheduler;

import net.snails.scheduler.pageprocessor.LagouPageProcessor;
import net.snails.scheduler.pipeline.LgouPipeLine;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

/**
 * Created by admin on 2015/8/10.
 */
@Service("lagouJobService")
public class LagouJob {

    public void execute() {

        Spider.create(new LagouPageProcessor()).addUrl("http://www.lagou.com/").addPipeline(new LgouPipeLine()).thread(20)
                .run();
    }
}
