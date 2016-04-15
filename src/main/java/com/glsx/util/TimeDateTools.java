package com.glsx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDateTools {

	public static void main(String[] args) {
//		Date now = new Date();
//		DateFormat d = DateFormat.getDateInstance();
//		DateFormat d = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
//		String str = d.format(now);
		System.out.println(isValidDate("2014-07-22 11:34:55","yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 获取系统日期时间
	 * 参数1 输出格式为yyyy-MM-dd.HH:mm:ss
	 * 参数2 输出格式为yyyyMMddHHmmss
	 * 参数3 输出格式为yyyyMMddHHmm
	 */
	public static String getCurDate(int type) {
		if (type == 1) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date currentTime_1 = new java.util.Date();
			return formatter.format(currentTime_1);
		}else if(type == 2){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			java.util.Date currentTime_1 = new java.util.Date();
			return formatter.format(currentTime_1);
		}else if(type == 3){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
			java.util.Date currentTime_1 = new java.util.Date();
			return formatter.format(currentTime_1);
		}else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			java.util.Date currentTime_1 = new java.util.Date();
			return formatter.format(currentTime_1);
		}
	}
	
	/**
	 * 取得N分钟前/后的时间串
	 * @param minute
	 * @return
	 */
	public static String getDateBefore(int minute){
		Date date = new Date(); 
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(date); 
        //加天 
        cal.add(Calendar.MINUTE, 10); 
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(cal.getTime()); 
	}
	
	
	/**
	 * 取得当时间串的毫秒数
	 * @param createtime
	 * @return
	 */
	public static long getLongDateFromStringDate(String createtime){
		Date date = new Date();
		long longDate = date.getTime();
		return longDate;
	}
	
	/**
	 * 
	 * 取得当前时间的毫秒数
	 * @return
	 */
	public static long getDateByLong(){
		Date date = new Date();
		return date.getTime();
	}
	
	/**
	 * 判断字符串是否满足格式要求
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String pattern) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(pattern);
		try {
			df.setLenient(false);
			df.parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	  
	
	
	/**
	 * 处理rowkey的时间格式
	 * @param date
	 * @return
	 */
	public static String formatKeyRowTime(String date){
		String fromFormat = "yyyy-MM-dd HH:mm:ss";
		String toFormat = "yyyyMMddHHmmss";
		return turnDateFormat(date,fromFormat,toFormat);
	}
	
	/**
	 * 处理track_history的DevTime格式
	 * @param date
	 * @return
	 */
	public static String formatTrackDevTime(String date){
		String fromFormat = "dd/MM/yy HH:mm:ss";
		String toFormat = "yyyy-MM-dd HH:mm:ss";
		return turnDateFormat(date,fromFormat,toFormat);
	}
	
	/**
	 * 处理rttrack_history的DevTime格式
	 * @param date
	 * @return
	 */
	public static String formatRttrackDevTime(String date){
		String fromFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		String toFormat = "yyyy-MM-dd HH:mm:ss";
		return turnDateFormat(date,fromFormat,toFormat);
	}
	
	/**
	 * 处理track_history的InTime格式
	 * @param date
	 * @return
	 */
	public static String formatInTime(String date){
		String fromFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		String toFormat = "yyyyMMddHHmmss";
		return turnDateFormat(date,fromFormat,toFormat);
	}
	
	/**
	 * 转换时间格式
	 * @param date：待转换时间串
	 * @param fromFormat：转换前格式定义
	 * @param toFormat：转换后格式定义
	 * @return
	 */
	public static String turnDateFormat(String date,String fromFormat,String toFormat){
		SimpleDateFormat fromSdf = new SimpleDateFormat(fromFormat);
		Date d = null;
		try {
			d = fromSdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat toSdf = new SimpleDateFormat(toFormat);
		return toSdf.format(d);
	}

	/**
	 * 检查是否达到更新条件
	 * 
	 * @param updateTime
	 * @param updateCycle
	 * @return
	 */
	public static boolean checkUpdateStatus(long updateTime, int updateCycle) {
		boolean flag = false;
		long iUpdateTime = updateTime; // 取得更新时间的毫秒数
		long nowDate = new Date().getTime(); // 取得当前时间的毫秒数
		
		if (nowDate - iUpdateTime > 60 * 60 * 24 * updateCycle) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	

}
