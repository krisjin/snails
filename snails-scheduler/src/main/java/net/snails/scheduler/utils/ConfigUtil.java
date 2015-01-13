package net.snails.scheduler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.snails.scheduler.model.JobInfo;
import net.snails.scheduler.model.JobKey;

/**
 * @author krisjin
 * @date 2015-1-13
 */
public class ConfigUtil {

	public static JobKey read() {
		Properties prop = new Properties();
		InputStream is = ConfigUtil.class.getResourceAsStream("/job.properties");
		try {
			prop.load(is);
			int thread = Integer.valueOf((String) prop.get("csdn.thread"));

			String urls = (String) prop.get("csdn.urls");
			String[] urlsArr = urls.split("\\|");
			JobInfo job = new JobInfo();
			JobKey jk = new JobKey();

			job.setThread(thread);
			job.setUrls(urlsArr);
			jk.put("csdn", job);
			return jk;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
