package net.snails.scheduler.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author krisjin
 * @date 2015-1-13
 */
public class JobKey {

	private static final Map<String, JobInfo> map = Collections.synchronizedMap(new HashMap<String, JobInfo>());

	public void put(String key, JobInfo value) {

		map.put(key, value);

	}

	public static JobInfo get(String key) {
		return map.get(key);
	}

}
