package com.glsx.util;

import com.glsx.constant.Constant;

public class HBaseTool {
	public static String generateRowkey(String type, String sn, String gpsTime) {
    	StringBuffer buf = new StringBuffer();
		buf.append(type);
		buf.append(Constant.ROWKEYSPLIT);
		buf.append(StringTools.fillUserid(sn, 14));
		buf.append(Constant.ROWKEYSPLIT);
		
		if(TimeDateTools.isValidDate(gpsTime, "yyyy-MM-dd HH:mm:ss")){
			gpsTime = TimeDateTools.formatKeyRowTime(gpsTime);
		}
		
		buf.append(gpsTime);
		
		return buf.toString();
	}
}
