package com.glsx.thread;

import java.io.IOException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glsx.constant.Constant;
import com.glsx.service.BaseService;

public class KafkaConsumerThread implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerThread.class);
	
	private HConnection hConnection;
	private HTableInterface table;
	
	private KafkaStream<byte[], byte[]> stream;
	
	private BaseService service;
	
	public KafkaConsumerThread(KafkaStream stream, HConnection hConnection) {
		this.stream = stream;
		this.hConnection = hConnection;
		
		try {
			table = hConnection.getTable(Constant.TABLE_NAME);
			table.setAutoFlushTo(false);
			table.setWriteBufferSize(1024*1024);
			
			Class<?> className = Class.forName(Constant.MODE_CLASS_NAME);
			service = (BaseService) className.newInstance();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {
//			logger.info(Thread.currentThread().getName() + ": partition[" + it.next().partition() + "]," 
//					+ "offset[" + it.next().offset() + "], " + new String(it.next().message()));
			service.fromBytes(it.next().message(), table);
		}
        try {
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> service = Class.forName("com.glsx.service.BaseStationService");
		
		System.out.println(service.getName());
		BaseService ser = (BaseService) service.newInstance();
	}
}
