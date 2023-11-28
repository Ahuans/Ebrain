package com.netty_server.client;

import java.util.List;

import com.netty_server.constants.Constants;
import com.netty_server.factory.ZookeeperFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;


import io.netty.channel.ChannelFuture;

public class ServerWathcer implements CuratorWatcher {



	@Override
	public void process(WatchedEvent event) throws Exception {
		CuratorFramework client= ZookeeperFactory.create();
		String path=event.getPath();
		client.getChildren().usingWatcher(this);
		List<String> serverPaths=client.getChildren().forPath(Constants.Server_PATH);
		
	//	TCPClient.realServerPath=new HashSet<String>();
		for(String serverPath:serverPaths)
		{
			String[] str=serverPath.split("#");
			ChannelManager.realServerPath.add(str[0]+"#"+str[1]);
			
		}
		ChannelManager.clear();
		for(String realServer:ChannelManager.realServerPath)
		{
			String[] str=realServer.split("#");
			ChannelFuture channelFuture=NettyClient.b.connect(str[0],Integer.valueOf(str[1]));
			ChannelManager.add(channelFuture);
			
		}
		
	}

}
