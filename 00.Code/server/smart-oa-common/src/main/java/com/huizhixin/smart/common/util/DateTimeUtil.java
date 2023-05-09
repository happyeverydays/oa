/*
 * 文 件 名:  DateTimeUtil.java
 * 描    述:  日期和时间工具类文件
 */
package com.huizhixin.smart.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期和时间工具类
 * 
 * 提供日期和时间处理公共方法实现
 * 
 */
public final class DateTimeUtil {
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FARMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期格式：yyyyMMddHHmmss
	 */
	public static final String DATE_FORMAT_2 = "yyyyMMddHHmmss";

	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String DATE_FARMAT_3 = "yyyy-MM-dd";

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_FARMAT_4 = "yyyyMMdd";

	/**
	 * 日期格式：yyyy-MM
	 */
	public static final String DATE_FARMAT_5 = "yyyy-MM";

	/**
	 * 日期格式：yyMMdd
	 */
	public static final String DATE_FARMAT_6 = "yyMMdd";

	/**
	 * 日期格式：yyyyMMddHHmmssSSS 毫秒
	 */
	public static final String DATE_FORMAT_7 = "yyyyMMddHHmmssSSS";

	/**
	 * 日期格式：mm:ss
	 */
	public static final String DATE_FARMAT_8 = "HH:mm";

	/**
	 * 日期格式：yyyy-MM-dd HH:mm
	 */
	public static final String DATE_FARMAT_9 = "yyyy-MM-dd HH:mm";

	/**
	 * 日期格式 MM-dd HH:mm
	 */
	public static final String DATE_FARMAT_10 = "MM-dd HH:mm";

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_FARMAT_11 = "yyyy.MM.dd";

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_FARMAT_12 = "yyyy/MM/dd";

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_FARMAT_13 = "yyyy年MM月";
	
	/**
	 * 日期格式：yyyy-MM-dd HH:mm
	 */
	public static final String DATE_FARMAT14 = "yyyy-MM-dd HH:mm";

	/**
	 * 数据库存储的date类型字段转成JAVA的String类型为2006-06-11 03:35:13.0，需要去掉后面的.0
	 */
	private static final String DATE_STRING_EXTRA = ".0";

	private static final String DATE_STRING_EXTRA1 = ".997";
    /**
     * 时间字符串
     */
    public static final String STRING_DAY = "天";
    public static final String STRING_HOUR = "小时";
    public static final String STRING_MIN = "分";
    public static final String STRING_SEC = "秒";
    public static final String STRING_MSEC = "毫秒";
    public static final String STRING_YEAR = "年";

	/**
	 * 默认构造方法 不允许实例化
	 */
	private DateTimeUtil() {

	}

	/**
	 * 日期时间字符串格式转换
	 * 
	 * 数据库存储的date类型字段转成JAVA的String类型为2006-06-11 03:35:13.0，需要去掉后面的.0
	 * 
	 * @param dateStr 日期时间字符串
	 * @return 去掉.0的日期时间字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String formatDateString(String dateStr) {
		if (null != dateStr) {
			// 后面包含.0日期时间字符串才需要处理
			int index = dateStr.lastIndexOf(DATE_STRING_EXTRA);
			if (0 < index) {
				dateStr = dateStr.substring(0, index);
			}

			int index1 = dateStr.lastIndexOf(DATE_STRING_EXTRA1);
			if (0 < index1) {
				dateStr = dateStr.substring(0, index1);
			}
		}

		return dateStr;
	}

	/**
	 * 将date型日期转换为想要的字符格式 <一句话功能简述> <功能详细描述>
	 * 
	 * @param date       date日期
	 * @param dateFormat 日期格式：如yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String formatDateToString(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	/**
	 * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
	 * 
	 * @param currentTime currentTime
	 * @return Date
	 * @see [类、类#方法、类#成员]
	 */
	public static Date formatStringToDate(String currentTime) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT);
		try {
			date = df.parse(currentTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
	 * 
	 * @param time       currentTime
	 * @param dataFarmat
	 * @return Date
	 * @see [类、类#方法、类#成员]
	 */
	public static Date formatStringToDate(String time, String dataFarmat) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dataFarmat);
		try {
			date = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date formatStringToDate2(String time, String dataFarmat) throws ParseException {
		if(time == null) {
			return null;
		}
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dataFarmat);
		date = df.parse(time);
		return date;
	}

	/**
	 * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
	 * 
	 * @param time       currentTime
	 * @param dataFarmat
	 * @return Date
	 * @see [类、类#方法、类#成员]
	 */
	public static Date formatStringToDateAll(String time) {
		Date date = null;
		try {
			date = DateTimeUtil.formatStringToDate2(time, DateTimeUtil.DATE_FARMAT_3);

		} catch (Exception e) {
			try {
				date = DateTimeUtil.formatStringToDate2(time, DateTimeUtil.DATE_FARMAT_11);

			} catch (Exception e1) {
				try {
					date = DateTimeUtil.formatStringToDate2(time, DateTimeUtil.DATE_FARMAT_12);

				} catch (Exception e2) {
				}
			}

		}
		return date;
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis, String dayName, String hourName, String minName, String sName,
			String sssName) {
		String result = "";
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);

		if (day > 0) {
			result = day + dayName;
		}
		if (hourName != null && hour > 0) {
			result += hour + hourName;
		}
		if (minName != null && min > 0) {
			result += min + minName;
		}
		if ((sName != null && s > 0)) {
			result += s + sName;
		}

		if (sssName != null && sss > 0) {
			result += sss + sssName;
		}

		if (sName == null && sssName == null && StringUtils.isEmpty(result)) { // 结果空时默认一分钟
			result += "1" + minName;
		}
		return result;
	}

	/**
	 *
	 * TODO To describe the functionality of this method.
	 *
	 * @author Administrator
	 * @param dayName="天"，hourName="小时"
	 * @return
	 */
	public static String formatDateTime(Long timeMillis, String dayName, String hourName, String minName) {
		if (timeMillis != null) {
			return formatDateTime(timeMillis.longValue(), dayName, hourName, minName, null, null);
		} else {
			return null;
		}

	}

	public static Date getPreviousDay(Date date, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -interval);
		date = calendar.getTime();
		return date;
	}

	public static Date getAfterDay(Date date, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +interval);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
	 * 
	 * @param currentTime currentTime
	 * @return Date
	 * @see [类、类#方法、类#成员]
	 */
	public static Date formatStringToDate3(String currentTime) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT_3);
		try {
			date = df.parse(currentTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 得到本周周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT_3);
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return df.format(c.getTime());
	}

	/**
	 * 得到本周周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT_3);
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return df.format(c.getTime());
	}

	/**
	 * 得到当前格林威治的日期和时间
	 * 
	 * @return String
	 */
	public static String getUTCDateTimeNow() {
		// 取时区
		TimeZone zone = TimeZone.getDefault();

		Calendar c = Calendar.getInstance();

		Date date = new Date();

		// 计算时区偏差
		c.setTimeInMillis(date.getTime() - zone.getOffset(date.getTime()));

		// 格式化
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FARMAT);

		String dateString = formatter.format(c.getTime());

		return dateString;
	}

	// 获得当天0点时间
	public static Date getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	// 获得昨天0点时间
	public static Date getYesterdaymorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000);
		return cal.getTime();
	}

	// 获得当天近7天时间
	public static Date getWeekFromNow() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000 * 7);
		return cal.getTime();
	}

	// 获得当天24点时间
	public static Date getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 获得本周日24点时间
	public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 获得本月最后一天24点时间
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}

	// 获得上月第一天0点时间
	public static Date getLastMonthStartMorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesMonthmorning());
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	// 获得上月最后一天24点时间
	public static Date getLastTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesMonthnight());
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentQuarterStartTime());
		cal.add(Calendar.MONTH, 3);
		return cal.getTime();
	}

	/**
	 * 获取本年开始时间
	 * 
	 * @return
	 */
	public static Date getCurrentYearStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
		return cal.getTime();
	}

	/**
	 * 获取本年结束时间
	 * 
	 * @return
	 */
	public static Date getCurrentYearEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}

	/**
	 * 获取去年结束时间
	 * 
	 * @return
	 */
	public static Date getLastYearStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, -1);
		return cal.getTime();
	}

	/**
	 * 获取本年第一天日期
	 * 
	 * @param year 年份
	 * @return Date
	 */
	public static Date getCurrentYearFirst() {
		Calendar calendarNow = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, calendarNow.get(Calendar.YEAR));
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取两个时间的时间差（小时）
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static Long timeDiffForHour(Date time1, Date time2) {
		long diff = time2.getTime() - time1.getTime();

		long nh = 1000 * 60 * 60;// 一小时的毫秒数

		long diffHour = diff / nh; // 计算差多少小时

		return diffHour;
	}

	/**
	 * 获取两个时间的时间差（天）
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static Long timeDiffForDay(Date time1, Date time2) {
		long diff = time2.getTime() - time1.getTime();

		long nh = 1000 * 60 * 60 * 24;// 一小时的毫秒数

		long diffDay = diff / nh; // 计算差多少小时

		if (diff % nh > 0) {
			diffDay += 1;
		}

		return diffDay;
	}

	/**
	 * 获取两个时间的时间差（分钟）
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static Long timeDiffForMin(Date time1, Date time2) {
		long diff = time2.getTime() - time1.getTime();

		long nh = 1000 * 60;// 一分钟的毫秒数

		long diffHour = diff / nh; // 计算差多少分钟

		return diffHour;
	}

	/**
	 * 获取两个日期之间的毫秒数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long timeDiffForMills(Date startDate, Date endDate) {
		Long mills = null;
		if (startDate != null) {
			long startTime = startDate.getTime();
			if (endDate != null) {
				long endTime = endDate.getTime();
				mills = endTime - startTime;
			} else {
				mills = new Date().getTime() - startTime;
			}

		}

		return mills;
	}

	/**
	 * 获取两个时间的时间差（年 月）
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static String timeDiffForYear(Date time1, Date time2) {
		long diff = time2.getTime() - time1.getTime();

		diff = diff / 1000;

		long nh = 60 * 60 * 24 * 365;// 一年的毫秒数

		long diffYear = diff / nh; // 计算差多少年

		long mh = 60 * 60 * 24 * 30;// 一月的毫秒数

		long diffMonty = diff % nh / mh;

		return diffYear + "年 " + diffMonty + "月";
	}

	/**
	 * 分钟前
	 * 
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getPreviousMin(Date date, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -interval);
		date = calendar.getTime();
		return date;
	}

	private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获得本天的开始时间
	 * 
	 * @return
	 */
	public static Date getCurrentDayStartTime() {
		Date now = new Date();
		try {
			now = shortSdf.parse(shortSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本天的结束时间
	 * 
	 * @return
	 */
	public static Date getCurrentDayEndTime() {
		Date now = new Date();
		try {
			now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本周的第一天，周一
	 * 
	 * @return
	 */
	public static Date getCurrentWeekDayStartTime() {
		Calendar c = Calendar.getInstance();
		try {
			int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
			c.add(Calendar.DATE, -weekday);
			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}

	/**
	 * 获得本周的最后一天，周日
	 * 
	 * @return
	 */
	public static Date getCurrentWeekDayEndTime() {
		Calendar c = Calendar.getInstance();
		try {
			int weekday = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DATE, 8 - weekday);
			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}

	/**
	 * 获得本月的开始时间
	 * 
	 * @return
	 */
	public static Date getCurrentMonthStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.DATE, 1);
			now = shortSdf.parse(shortSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 本月的结束时间
	 * 
	 * @return
	 */
	public static Date getCurrentMonthEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.DATE, 1);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.DATE, -1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的开始时间
	 * 
	 * @return
	 */
	public static Date getCurrentQuarterStartTimeStar() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间
	 * 
	 * @return
	 */
	public static Date getCurrentQuarterEndTimeEnd() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前年的开始时间
	 * 
	 * @return
	 */
	public static Date getCurrentYearStartTimeStar() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
			now = shortSdf.parse(shortSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前年的结束时间
	 * 
	 * @return
	 */
	public static Date getCurrentYearEndTimeEnd() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static void main(String[] args) {
		Date d = DateTimeUtil.getCurrentQuarterStartTime();

		System.out.println(DateTimeUtil.formatDateToString(d, DATE_FARMAT));

	}

}
