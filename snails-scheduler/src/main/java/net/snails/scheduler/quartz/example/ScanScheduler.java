package net.snails.scheduler.quartz.example;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Job;
import static org.quartz.JobBuilder.*;
public class ScanScheduler {

	public static void main(String[] args) throws SchedulerException {
		ScanScheduler ss = new ScanScheduler();
		Scheduler scheduler = SchedulerHelper.getInstance();
		ss.execute(scheduler);
		scheduler.start();

	}

	public void execute(Scheduler scheduler) {
		JobDetail job = newJob(ScanDirectoryJob.class)
				  .withIdentity("myJob", "group1") // name "myJob", group "group1"
				  .build();
              
	}
}
