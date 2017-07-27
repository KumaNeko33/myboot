package com.shuai.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: (时间操作工具类)
 * @author: MiaoHongshuai
 * @Date: 2017/7/26
*/
public class DateUtils {
	
	/** 默认时间格式 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**无分隔符时间格式 */
	public static final String DATETIME_NO_SEPARATOR_FORMAT = "yyyyMMddHHmmss";
	
	/**
     * ISO8601 formatter for date-time with time zone.
     * The format used is <tt>yyyy-MM-dd'T'HH:mm:ssZZ</tt>.
     */
    public static final String ISO_DATETIME_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";
	
	/**
     * ISO8601 formatter for date-time without time zone.
     * The format used is <tt>yyyy-MM-dd'T'HH:mm:ss</tt>.
     */
	public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	/**
     * ISO8601 formatter for date without time zone.
     * The format used is <tt>yyyy-MM-dd</tt>.
     */
	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
	
    /**
     * ISO8601-like formatter for time without time zone.
     * The format used is <tt>HH:mm:ss</tt>.
     * This pattern does not comply with the formal ISO8601 specification
     * as the standard requires the 'T' prefix for times.
     */
    public static final String ISO_TIME_NO_T_FORMAT = "HH:mm:ss";
    
    /**
     * ISO8601 formatter for time without time zone.
     * The format used is <tt>'T'HH:mm:ss</tt>.
     */
    public static final String ISO_TIME_FORMAT = "'T'HH:mm:ss";
    
    /**
     * ISO8601 formatter for time with time zone.
     * The format used is <tt>'T'HH:mm:ssZZ</tt>.
     */
    public static final String ISO_TIME_TIME_ZONE_FORMAT = "'T'HH:mm:ssZZ";
    
    /**
     * 不可实例化
     */
    private DateUtils() {
    }

    /**
     * 获取每月第一天
     * 
     * @param date
     *            月份时间,为空时 取当前时间
     * @param month
     *            要减去的月份数, 1表示往前一个月, -1表示往后一个月
     * @return 时间格式:2015-06-01 00:00:01
     */
    public static Date getMonthFirstDay(Date date, int month) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - month, 1, 0, 0, 1);
        return calendar.getTime();
    }
    
    /**
     * 获取当前月最后一天
     * @param date
     * @param month
     * @return
     */
    public static Date getMonthLastDay(Date date){
    	if (date == null) {
            date = new Date();
        }
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1); 
        calendar.set(Calendar.DATE, 1); 
        calendar.add(Calendar.DATE, -1); 
        return calendar.getTime();
    }
    public static void main(String[] args) {
    	//SimpleDateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
    	
//    	System.out.println((df.format(getCurrentQuarterStartTime(2015,2))));
    	
    	
    	Date startDate = DateUtils.getCurrentQuarterStartTime(2016, 1);
		Date endDate = DateUtils.getCurrentQuarterEndTime(2016, 1);
		System.out.println(startDate.getTime());
		System.out.println(endDate.getTime());
    	
	}

    /**
     * 格式化时间
     * 
     * @param date
     *            时间,为空时 取当前时间
     * @param format
     *            格式
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        if (format == null) {
            format = DATETIME_FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * String 字符串转时间类型
     * 
     * @param dateStr
     *            时间字符串
     * @param format
     *            格式化
     * @return
     */
    public static Date StringToDate(String dateStr, String format) {
        if (format == null) {
            format = DATETIME_FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     * 
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
    	Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH);
		int season = (month / 3) + 1 ;
		month =  (season % 4) * 3 - 2;
		month =  month == -2 ? 10 : month;
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
    }
    
    /**
     * 根据传入季度获取当前季度的开始时间
     * @param quarter
     * @return
     */
    public static Date getCurrentQuarterStartTime(Integer year,Integer quarter) {
    	Calendar c = Calendar.getInstance();
    	if(null!=year){
    		c.set(Calendar.YEAR, year);
    	}
		int month = c.get(Calendar.MONTH);
		if(null==quarter){
			quarter = (month / 3) + 1 ;
		}
		month =  (quarter % 4) * 3 - 2;
		month =  month == -2 ? 10 : month;
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
    }
    
    /**
     * 根据传入季度获取当前季度的结束时间
     * @param quarter 季度
     * @return
     */
    public static Date getCurrentQuarterEndTime(Integer year,Integer quarter) {
		Calendar c = Calendar.getInstance();
		if(null!=year){
    		c.set(Calendar.YEAR, year);
    	}
		int currentMonth = c.get(Calendar.MONTH);
		if(null==quarter){
			quarter = (currentMonth / 3) + 1 ;
		}
		int month = (quarter % 4) * 3 -1;
        if(quarter == 4){
            month = 11;
        }
        c.set(Calendar.MONTH, month);;
//		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
    }
    
    /**
     * 获取年
     * 
     * @param date
     *            可以为空
     */
    public static Integer getYear(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    public static List<Integer> getPastYears(Date date, int lasts) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer currYear = calendar.get(Calendar.YEAR);
        
        List<Integer> yearList = new ArrayList<Integer>();
        yearList.add(currYear);
		for (int i = 1; i < lasts; i++) {
			yearList.add(currYear - i);
		}
        return yearList;
    }
    
    /**
     * 获取月
     * 
     * @param date
     *            可以为空
     */
    public static Integer getMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    
    /**
     * 获取当前季度
     * 
     * @param date
     *            可以为空
     * @return
     */
    public static Integer getQuarter(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return (month/3) + 1 ;
    }

    /**
     * 获取一天的开始时间
     * 
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
    	if(null == date) {
    		return null;
    	}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的结束时间
     * 
     * @param date
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
    	if(null == date) {
    		return null;
    	}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    
    /**
     * 获取一天的结束时间的时间戳
     * 
     * @param date
     * @return
     */
    public static long getStartTimeOfDayMill(String date) {
    	SimpleDateFormat sdf=new SimpleDateFormat(DATETIME_FORMAT);
        Date d;
		try {
			d = sdf.parse(date+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
        return d.getTime();
    }
    
    /**
     * 获取一天的结束时间的时间戳
     * 
     * @param date
     * @return
     */
    public static long getEndTimeOfDayMill(String date) {
    	SimpleDateFormat sdf=new SimpleDateFormat(DATETIME_FORMAT);
        Date d;
		try {
			d = sdf.parse(date+" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
        return d.getTime();
    }
    
    /**
     * 获取制定年份制定月份月末最后一天的的时间戳
     * 
     * @param date
     * @return
     */
    public static long getMonthEndTimeOfDayMill(int year,int month) {
    	Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);
        return getEndTimeOfDayMill(year+"-"+month+"-"+maxDate);
    }
    
    /**
     * 获取制定年份制定月份月初第一天的的时间戳
     * 
     * @param date
     * @return
     */
    public static long getMonthStartTimeOfDayMill(int year,int month) {
    	 return getStartTimeOfDayMill(year+"-"+month+"-01");
    }
    
    /**
     * 获取该季度的开始月份，即01,04,07,10
     * 
     * @return
     */
    public static String getCurrentQuarterStartMonth(Integer quarter) {
        if(quarter <= 0 || quarter > 4){
    		return null;
    	}
		String[] quarters = { "01", "04", "07", "10" };
		return quarters[quarter - 1];
    }
    
    /**
     * 获取该季度的结束月份，即03,06,09,12
     * 
     * @return
     */
    public static String getCurrentQuarterEndMonth(Integer quarter) {
    	if(quarter <= 0 || quarter > 4){
    		return null;
    	}
		String[] quarters = { "03", "06", "09", "12" };
		return quarters[quarter - 1];
    }
    
	/**
	 * 获取当前季度开始月份
	 * @param season
	 * @return
	 */
	public static int getSeasonStartMonth(int season) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, (season % 4) * 3 - 2);
		return c.get(Calendar.MONTH);
	}

	/**
	 * 获取当前季度结束月份
	 * @param season
	 * @return
	 */
	public static int getSeasonEndMonth(int season) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, (season % 4) * 3);
		int month = c.get(Calendar.MONTH);
		return month == 0 ? 12 : month;
	}
	
    /**
     * 获取制定年份制定月份最大天数
     * 
     * @param date
     * @return
     */
    public static int getMaxDate(int year,int month) {
    	Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        return a.get(Calendar.DATE);
    }
    
    
    
    
    /**
     * 根据传入月份获取当前月份的开始时间
     * @param month 月份
     * @return
     */
    public static Date getCurrentMonthStartTime(Integer year,Integer month) {	
		Calendar c = Calendar.getInstance();
		if(null!=year){
    		c.set(Calendar.YEAR, year);
    	}
		c.set(Calendar.MONTH, month-1 );
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
    }
    
    /**
     * 根据传入月份获取当前月份的结束时间
     * @param month 月份
     * @return
     */
    public static Date getCurrentMonthEndTime(Integer year,Integer month) {
    	Calendar c = Calendar.getInstance();
		if(null!=year){
    		c.set(Calendar.YEAR, year);
    	}
		c.set(Calendar.MONTH, month-1 );
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
    }
    
    
}
