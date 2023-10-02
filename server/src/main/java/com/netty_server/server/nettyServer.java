package com.netty_server.server;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import com.netty_server.constants.Constants;
import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.handler.SimpleServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Hello world!
 *
 */
public class nettyServer 
{


	public static void main(String[] args) {	
		EventLoopGroup parentGroup=new NioEventLoopGroup();
		EventLoopGroup childGroup=new NioEventLoopGroup();
			try {	
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(parentGroup,childGroup);
			bootstrap.option(ChannelOption.SO_BACKLOG,128)
			.childOption(ChannelOption.SO_KEEPALIVE, false)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535,Delimiters.lineDelimiter()[0]));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new IdleStateHandler(60, 45,20 ,TimeUnit.SECONDS));
					ch.pipeline().addLast(new SimpleServerHandler());
					ch.pipeline().addLast(new StringEncoder());
					
					
				}
			
			});
			ChannelFuture f=bootstrap.bind(8082).sync();
			
			CuratorFramework curatorFramework=ZookeeperFactory.create();
			InetAddress inetAddress=InetAddress.getLocalHost();
			int port=8082;
			curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(Constants.Server_PATH+"/"+inetAddress.getHostAddress()+"#"+port+"#");
			f.channel().closeFuture().sync();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	    
	    
	}
    
    
}
