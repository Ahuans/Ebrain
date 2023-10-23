package com.netty_server.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZookeeperFactory {
	public static CuratorFramework client;
	private static final Logger logger = LogManager.getLogger(ZookeeperFactory.class);
	// when user input the zookeeper Address you need to use function below to connect zookeeper
	public synchronized static void initialCreate(String zookeeperAddress) {

			RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000, 3);
			client=CuratorFrameworkFactory.newClient(zookeeperAddress, retryPolicy);
			client.start();
			logger.info("Connected to ZooKeeper");

	}
	public static CuratorFramework create () throws Exception {
		if(client==null)
		{

			throw new Exception("ERROR:you haven't connected to the zookeeper yet");
		}
		return  client;
	}

}
