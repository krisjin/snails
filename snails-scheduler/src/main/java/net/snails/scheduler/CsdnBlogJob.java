package net.snails.scheduler;

import net.snails.scheduler.model.JobInfo;
import net.snails.scheduler.model.JobKey;
import net.snails.scheduler.pageprocessor.CSDNBlogPageProcessor;
import net.snails.scheduler.pipeline.CSDNBlogPipeline;
import net.snails.scheduler.utils.ConfigUtil;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

@Service("csdnBlogJobService")
public class CsdnBlogJob {

	public void execute() {
		JobKey jk =ConfigUtil.read();
		JobInfo job =jk.get("csdn");
		Spider.create(new CSDNBlogPageProcessor()).addUrl(job.getUrls()).addPipeline(new CSDNBlogPipeline())
				.thread(job.getThread()).run();
	}

}
