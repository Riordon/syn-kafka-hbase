package com.glsx.Init;

import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import com.glsx.constant.Constant;

import kafka.consumer.ConsumerConfig;

public class InitManager {
	private static ConsumerConfig csConf;
	private static Configuration hbaseConf;
	
	public static void initKafka() {
        Properties props = new Properties();
        props.put("zookeeper.connect", Constant.KAFKA_ZOOKEEPER_QUORUM);
        props.put("group.id", Constant.KAFKA_GROUP_ID);
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        
        csConf = new ConsumerConfig(props);
	}
	
	public static void initHBase() {
		hbaseConf = HBaseConfiguration.create();
		hbaseConf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
	}
	
	public static ConsumerConfig getCsConf() {
		return csConf;
	}
	
	public static Configuration getHBaseConf() {
		return hbaseConf;
	}
}
