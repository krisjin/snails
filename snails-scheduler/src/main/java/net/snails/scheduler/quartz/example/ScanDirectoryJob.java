package net.snails.scheduler.quartz.example;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScanDirectoryJob implements Job {

	private static Log logger = LogFactory.getLog(ScanDirectoryJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();

		String desc = jobDetail.getDescription();

		JobDataMap dataMap = jobDetail.getJobDataMap();

		String dir = dataMap.getString("SCAN_DIR");

		if (dir == null) {
			throw new JobExecutionException("Directory not configured");
		}

		File file = new File(dir);
		if (!file.exists()) {
			throw new JobExecutionException("Invalid dir " + dir);
		}
		
		FileFilter filter = new FileExtensionFileFilter(".xml");
		
		File[] files =file.listFiles(filter);
		
		if(files ==null || files.length<=0){
			throw new JobExecutionException("no xml files found in "+dir );
		}
		
		for(File f:files){
			System.out.println(f.getAbsolutePath());
		}
		
		
	}

	static class FileExtensionFileFilter implements FileFilter {
		private String extension;

		public FileExtensionFileFilter(String extension) {
			this.extension = extension;
		}

		public boolean accept(File file) {
			String fileName = file.getName().toLowerCase();
			boolean ret = false;
			ret = (file.isFile() && fileName.indexOf(extension) > 0) ? true : false;
			return ret;
		}

	}
}
