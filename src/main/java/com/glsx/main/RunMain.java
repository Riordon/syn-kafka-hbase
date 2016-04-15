package com.glsx.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glsx.Init.InitManager;
import com.glsx.constant.Constant;
import com.glsx.thread.KafkaConsumerThread;

public class RunMain {

	private static Logger logger = LoggerFactory.getLogger(RunMain.class);
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static ExecutorService executor = Executors.newFixedThreadPool(Constant.KAFKA_CONSUMER_NUMS);
	
	public static void main(String[] args) {
		String confPath = System.getProperty("syncConfHome");
		if(System.getProperty("syncConfHome")==null) {
			confPath = args[0];
		}
		confPath = confPath + File.separator + "conf";
		RunInit.init(confPath);
		
		InitManager.initKafka();
		InitManager.initHBase();
		
		ConsumerConfig csConf = InitManager.getCsConf();
		Configuration hbaseConf = InitManager.getHBaseConf();
		
//		BaseService service = new BaseStationService();
//		runService(csConf, hbaseConf, service);
		
		runService(csConf, hbaseConf);
	}

//	private static void runService(ConsumerConfig csConf, Configuration hbaseConf, BaseService service) {
//        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(csConf);
//        
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put(Constant.KAFKA_TOPIC_NAME, 1);
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//        
//        KafkaStream<byte[], byte[]> stream = consumerMap.get(Constant.KAFKA_TOPIC_NAME).get(0);
//        ConsumerIterator<byte[], byte[]> it = stream.iterator();
//        
//        try {
////        	HTable table = new HTable(hbaseConf, Bytes.toBytes(Constant.TABLE_NAME));
//        	HConnection hConnection = HConnectionManager.createConnection(hbaseConf);
//        	HTableInterface table = hConnection.getTable(Constant.TABLE_NAME);
//        	table.setAutoFlushTo(false);
//        	table.setWriteBufferSize(1024*1024);
//        	
//	        while (it.hasNext()) {
//	        	service.fromBytes(it.next().message(), table);
//	        }
//	        table.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//	}
	
	private static void runService(ConsumerConfig csConf, Configuration hbaseConf) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(Constant.KAFKA_TOPIC_NAME, new Integer(Constant.KAFKA_CONSUMER_NUMS));
        
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(csConf);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumerConnector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = messageStreams.get(Constant.KAFKA_TOPIC_NAME);
        
        try {
//        	int num = 0;
        	HConnection hConnection = HConnectionManager.createConnection(hbaseConf);     
        	ExecutorService executor = Executors.newFixedThreadPool(Constant.KAFKA_CONSUMER_NUMS);
        	for (final KafkaStream stream : streams) {
        		executor.submit(new KafkaConsumerThread(stream, hConnection));
//        		++num;
//        		logger.info("KafkaStream num=" + num);
        	}
//	        hConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
