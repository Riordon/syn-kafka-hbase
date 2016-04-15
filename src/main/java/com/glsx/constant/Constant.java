package com.glsx.constant;

import com.glsx.conf.ConfigFactory;

public class Constant {
	public static final String ROWKEYSPLIT = "-";

	/**************************hbase相关配置**************************/
	// HBase连接配置
	public static final String HBASE_ZOOKEEPER_QUORUM = ConfigFactory.getString("hbase.zookeeperQuorum");
	public static final String HBASE_ZOOKEEPER_CLIENTPORT = ConfigFactory.getString("hbase.zookeeperClientPort");
	
	// HBase表列族配置
	public static final String TABLE_NAME = ConfigFactory.getString("tableLoad.tableName");
	public static final String FAMILY_NAME = ConfigFactory.getString("tableLoad.familyName");
	public static final String AP_TYPE = ConfigFactory.getString("tableLoad.apType");
	
	// gps sync config
	public static final String GPS_SYNC_START_DATE = ConfigFactory.getString("gpsSyncConfig.startDate");
	public static final String GPS_SYNC_END_DATE = ConfigFactory.getString("gpsSyncConfig.endDate");
	
	// kafka config
	public static final String KAFKA_ZOOKEEPER_QUORUM = ConfigFactory.getString("kafkaConfig.zookeeperQuorum");
	public static final String KAFKA_TOPIC_NAME = ConfigFactory.getString("kafkaConfig.topicName");
	public static final String KAFKA_BROKER_LIST = ConfigFactory.getString("kafkaConfig.brokerList");
	public static final String KAFKA_GROUP_ID = ConfigFactory.getString("kafkaConfig.groupId");
	public static final int KAFKA_CONSUMER_NUMS = ConfigFactory.getInt("kafkaConfig.threadNum");
	public static final String KAFKA_SERIALIZER_CLASS = ConfigFactory.getString("kafkaConfig.serializerClass");
	
	// mdoe config
	public static final String MODE_CLASS_NAME = ConfigFactory.getString("modeConfig.className");
}
