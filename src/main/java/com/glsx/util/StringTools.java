package com.glsx.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;

import com.glsx.constant.Constant;


/**
 * 字符串工具类
 */
public class StringTools {
	public static void main(String args[]){
//		System.out.println(fillUserid("10000000000",14));
		System.out.println(getRandNum(4));
	}
	
	
	/**
	 * 填充userid
	 * @param id
	 * @param length
	 * @return
	 */
	public static String fillUserid(String userid,int length){
		if(userid==null){
			userid = "";
		}
		int idLength = userid.length();
		String returnId = "";
		for(int i=idLength;i<length;i++){
			returnId="0"+returnId;
		}
		returnId = returnId+userid;
		return returnId;
	}
	
	
	
	/**
	 * 生成随机的三位数字
	 * @return
	 */
	public static int getRandNum(){
		int random=(int)(Math.random()*900000000)+1;
		return random;
	}
	
	
	public static int getRandNum(int length){
		double num = Math.pow(10, length);
		int random=(int)(Math.random()*num);
		
		
		while((random+"").length()!=length){
			random=(int)(Math.random()*num);
		}
		
		return random;
	}
	
	/**
	 * 生成hbase表的rowkey值
	 * @param apType
	 * @param userid
	 * @param devtime
	 * @return
	 */
	public static String getRowKey(String apType,String userid,String devtime){
		StringBuffer buf = new StringBuffer();
		buf.append(apType);
		buf.append(Constant.ROWKEYSPLIT);
		buf.append(StringTools.fillUserid(userid, 14));
		buf.append(Constant.ROWKEYSPLIT);
		if(TimeDateTools.isValidDate(devtime, "yyyyMMdd")){
			devtime = TimeDateTools.formatKeyRowTime(devtime);
		}
		buf.append(devtime);
		return buf.toString();
	}
	
	/**
	 * 将字符串转换为整型
	 * 
	 * @param str
	 * @param defaultNumber
	 * @return
	 */
	public static int parseInt(String str, int defaultNumber) {
		int number = defaultNumber;
		if (str != null) {
			try {
				number = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				// 不处理
			}
		}
		return number;
	}

	/**
	 * 将字符串转换为长整型
	 * 
	 * @param str
	 * @param defaultNumber
	 * @return
	 */
	public static long parserLong(String str, long defaultNumber) {
		long number = defaultNumber;
		if (str != null) {
			try {
				number = Long.parseLong(str);
			} catch (NumberFormatException e) {
				// 不处理
			}
		}
		return number;
	}
	/**
	 * 空值(null)，返回空格("")
	 * @param str
	 * @return
	 */
	public static String nullToSpace(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}
		return str.trim();
	}
	
	/**
	 * 判断是否为空格或空值。
	 * @param str
	 * @return
	 */
	public static boolean checkNullOrSpace(String str){
		if (str == null || "".equals(str)) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否为空值或为0
	 * @param str
	 * @return，true:是；false:否;
	 */
	public static boolean checkZeroOrNull(String str){
		if (str == null || "".equals(str)||"0".equals(str)) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 将字符串根据字符集编码转换成byte数组
	 * 
	 * @param src
	 * @param encoding
	 * @return
	 */
	public static byte[] getBytes(String src, String encoding) {
		byte[] bytes = null;
		if (src == null) {
			return "".getBytes();
		}
		try {
			bytes = src.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			bytes = src.getBytes();
		}
		return bytes;
	}

	public static String getString(byte[] content, String encoding,
			Logger logger) {
		String html = null;
		if (content != null) {
			try {
				html = new String(content, encoding);
			} catch (UnsupportedEncodingException e) {
				logger.error("将字节数组转换为字符串失败。", e);
			}
		}
		return html;
	}

	/**
	 * 
	 * @Title: getStartTime
	 * @Description: 获取起始时间
	 * @author: jiangzheng
	 * @date: Oct 19, 2010 8:34:24 PM
	 * @param dateStr
	 * @param defaultTime
	 * @return
	 */
	public static Date getStartTime(String dateStr, String defaultTime) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date date = null;
		Calendar date2 = new GregorianCalendar();
		try {
			date = df.parse(dateStr);
		} catch (Exception e) {
			try {
				date = df.parse(defaultTime);
			} catch (Exception e1) {
				date = new Date();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		date2.set(GregorianCalendar.HOUR_OF_DAY, calendar
				.get(Calendar.HOUR_OF_DAY));
		date2.set(GregorianCalendar.MINUTE, calendar.get(Calendar.MINUTE));
		date2.set(GregorianCalendar.SECOND, calendar.get(Calendar.SECOND));
		date = date2.getTime();
		if (date.before(new GregorianCalendar().getTime())) {
			date2.add(Calendar.DATE, 1);
			date = date2.getTime();
		}
		return date;
	}


	public static String byteToString(byte[] bytes, String encoding){
		if(bytes==null){
			return "";
		}
		String str = "";
		try {
			str = new String(bytes,encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
