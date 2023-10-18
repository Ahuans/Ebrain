package com.netty_server.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperFactory {
	public static CuratorFramework client;

	// when user input the zookeeper Address you need to use function below to connect zookeeper
	public synchronized static void initialCreate(String zookeeperAddress) {

			RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000, 3);
			client=CuratorFrameworkFactory.newClient(zookeeperAddress, retryPolicy);
			client.start();

	}
	public static CuratorFramework create () throws Exception {
		if(client==null)
		{
			throw new Exception("未连接到zookeeper服务器");
		}
		return  client;
	}

}
