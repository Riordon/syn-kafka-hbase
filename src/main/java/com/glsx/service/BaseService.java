package com.glsx.service;

import org.apache.hadoop.hbase.client.HTableInterface;

public interface BaseService {
	public void fromBytes(byte[] message, HTableInterface table);
}
