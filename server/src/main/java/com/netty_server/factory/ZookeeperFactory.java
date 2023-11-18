package com.netty_server.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class ZookeeperFactory {
	private static CuratorFramework client;
	private static String config;
	private static final Logger logger = LogManager.getLogger(ZookeeperFactory.class);
	// when user input the zookeeper Address you need to use function below to connect zookeeper
	public synchronized static void initialCreate(String zookeeperAddress) {

			RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000, 3);
			client=CuratorFrameworkFactory.newClient(zookeeperAddress, retryPolicy);
			client.start();
			logger.info("Connected to ZooKeeper");
//		Watcher watcher = new Watcher() {
//			public void process(WatchedEvent event) {
//				//System.out.println(event.getPath());
//			}
//		};
//		int sessionTimeout = 4000;
//		try {
//			ZooKeeper zooKeeper = new ZooKeeper(zookeeperAddress, sessionTimeout, watcher);
//			List<String> list = zooKeeper.getChildren("/zookeeper/config", false);
//			for (String path : list) {
//				System.out.println(path);
//				byte[] b = zooKeeper.getData("/zookeeper/config/" + path, false, null);
//				config=new String(b);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
	public static CuratorFramework create () throws Exception {
		if(client==null)
		{

			throw new Exception("ERROR:you haven't connected to the zookeeper yet");
		}
		return  client;
	}
	static public String getConfig() {
		return config;
	}

}
