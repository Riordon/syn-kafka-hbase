package com.glsx.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glsx.constant.BaseStationConstant;
import com.glsx.constant.Constant;
import com.glsx.pb.BaseStationModel.BaseStationReqMessage;
import com.glsx.util.HBaseTool;

public class BaseStationService implements BaseService{
	private static Logger logger = LoggerFactory.getLogger(BaseStationService.class);
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private long count = 0L;
	
	public void fromBytes(byte[] message, HTableInterface table) {
		String curTime = df.format(new Date());
		BaseStationReqMessage parseFrom;
		try {
			parseFrom = BaseStationReqMessage.parseFrom(message);
			String rowKey = HBaseTool.generateRowkey(Constant.AP_TYPE, parseFrom.getSn(), parseFrom.getGpsTime());
			Put put = new Put(Bytes.toBytes(rowKey));
 
			put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.TYPE), Bytes.toBytes(Constant.AP_TYPE));
         	put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.GPSTIME), Bytes.toBytes(parseFrom.getGpsTime()));
        	put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.SN), Bytes.toBytes(parseFrom.getSn()));
    		put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.ACCESSTIME), Bytes.toBytes(parseFrom.getAccessTime()));
    		put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.RECEIVETIME), Bytes.toBytes(curTime));
    		put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.STORAGETIME), Bytes.toBytes(curTime));
    		put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.CELLID), Bytes.toBytes(parseFrom.getCellId()));
    		put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.LAC), Bytes.toBytes(parseFrom.getLac()));
        	put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.MCC), Bytes.toBytes(parseFrom.getMcc()));
        	put.add(Bytes.toBytes(Constant.FAMILY_NAME), Bytes.toBytes(BaseStationConstant.MNC), Bytes.toBytes(parseFrom.getMnc()));
       
        	table.put(put);
        	
        	count++;
        	if (count%10000 == 0) {
        		logger.info(Thread.currentThread().getName() + "current rowKey:" + rowKey + ", recordcount=" + count);
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
