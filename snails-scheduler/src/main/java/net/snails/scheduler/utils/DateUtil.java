package net.snails.scheduler.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**日期工具类
 * @author krisjin
 */
public class DateUtil {

	private DateUtil() {
		super();
	}

	public static String getCurrentTimeAsString() {
		return getTimeAsString(System.currentTimeMillis());
	}

	public static String getCurrentDateAsString() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("yyyyMMdd");
		return dateFormat.format(new Date(date.getTime()));
	}

	public static String getTimeAsString(long timeMillis, String newFormat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(newFormat);
		return dateFormat.format(new Date(timeMillis));
	}

	public static String getTimeAsString(long timeMillis) {
		return getTimeAsString(timeMillis, "yyyyMMddHHmmss");
	}

	public static String getDateTimeWithLong(long dateTime, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(new Date());
	}

	/**
	 * @param timeString
	 *            日期格式的时间字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date convertStringDateTimeToDate(String timeString, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Return date format：{2014年6月1日 8:55:21}
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String format(long timeMillis) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
		return dateFormat.format(new Date(timeMillis));
	}

	/**
	 * Return date format：{2014年6月1日 星期日 上午08时57分32秒}
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String format1(long timeMillis) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG);
		return dateFormat.format(new Date(timeMillis));
	}

	/**
	 * Return date format：{2014-6-1 8:59:37}
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String format2(long timeMillis) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return dateFormat.format(new Date(timeMillis));
	}

	/**
	 * 获得日期格式：{2014年06月}
	 * @param timeMills
	 * @return
	 */
	public static String format3(long timeMills) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		return sdf.format(new Date(timeMills));
	}

	/**
	 * 获得日期格式：{2014年06月}
	 * 
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String format4(String dateTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月");
		
		Date df = sdf.parse(dateTime);
		return sdf2.format(df);

	}

	/**
	 * 获得当前时间的月份：{6月}
	 * @return
	 */
	public static String currentMonth() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(System.currentTimeMillis());
		int month = calendar.get(Calendar.MONTH) + 1;
		return month + "月";
	}

	public static long getTimeMillis(int days, int hour, int minutes) {
		return days * 24 * 60 * 60 * 1000L + hour * 60 * 60 * 1000L + minutes * 60 * 1000L;
	}

	public static long getDays(long timeMillis) {
		return timeMillis / (24 * 60 * 60 * 1000L);
	}

	public static long getHours(long timeMillis) {
		return timeMillis / (60 * 60 * 1000L);
	}

	public static long getMinutes(long timeMillis) {
		return timeMillis / (60 * 1000L);
	}

	public static String getDHM(long timeMillis) {
		return getDHM(timeMillis, Locale.getDefault());
	}

	public static String getDHM(long timeMillis, Locale locale) {
		long hours = timeMillis % (24 * 60 * 60 * 1000L);
		long minutes = hours % ((60 * 60 * 1000L));
		String day = " day(s)";
		String hour = " hour(s)";
		String minute = " minute(s)";
		try {
			ResourceBundle DatetimeSymbols = ResourceBundle.getBundle("DatetimeSymbols", locale);
			day = DatetimeSymbols.getString("day");
			hour = DatetimeSymbols.getString("hour");
			minute = DatetimeSymbols.getString("minute");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(getDays(timeMillis)) + day + "," + String.valueOf(getHours(hours)) + hour + "," + String.valueOf(getMinutes(minutes)) + minute;
	}

	public static int getHourOfDay(long timeMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timeMillis));
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(long timeMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timeMillis));
		return calendar.get(Calendar.MINUTE);
	}

	public static int getSecond(long timeMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timeMillis));
		return calendar.get(Calendar.SECOND);
	}

	public static String formatDate(Date date, String newFormat) {
		if ((date == null) || (newFormat == null)) {
			return null;
		}
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(newFormat);
		return formatter.format(date);
	}

	public static String parse36KrArticlePostDate(String date){
		StringBuilder sb = new StringBuilder();
		
		String[] dateArr =date.split("T");
		sb.append(dateArr[0]).append(" ");
		String[] time=dateArr[1].split("\\+");
		sb.append(time[0]);
		
		return sb.toString();
	}
	
	public static String parseIfanrPostDate(String date){
		StringBuilder sb = new StringBuilder();
		String[] dateArr = date.split("\\|")[0].split(",");
		sb.append(dateArr[0].trim()).append(" ");
		sb.append(dateArr[1].trim());
		return sb.toString();
	}
	
	public static String parseOscBlogPostDate(String date){
		String ret =date.substring(date.indexOf("(")+1, date.indexOf(")"));
		return ret;
	}
	
	public static void main(String[] args) {
		String dateStr="2014-12-18T12:00:38+08:00";
		
		String d=parse36KrArticlePostDate(dateStr);
		System.out.println(d);
	}
	

}
