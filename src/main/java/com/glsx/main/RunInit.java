package com.glsx.main;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glsx.conf.ConfigFactory;

public class RunInit {
	private static Logger logger = LoggerFactory.getLogger(RunInit.class);
	/**
	 * 初始化
	 * @param confPath
	 */
	public static void init(String confPath) {
		// 数据初始化
		logger.info("系统初始化start.");
		String configXmlPath = confPath + File.separator + "conf.xml";
		
		logger.info("初始化配置文件,configXmlPath:"+configXmlPath);
		ConfigFactory.init(configXmlPath);
//		logger.info("初始化mysql连接.");
//		DBConnectionFactory.init();
		
		logger.info("系统初始化finish.");
	}
	
	
}
