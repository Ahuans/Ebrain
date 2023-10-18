package com.netty_server.client;



import com.netty_server.constants.Constants;
import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.handler.SimpleClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

import java.util.List;

public class NettyClient {
    static final Bootstrap b=new Bootstrap();
    static ChannelFuture future=null;
    //static Set<String> realServerPath=new HashSet<>();
    static {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup); // (2)
        b.channel(NioSocketChannel.class); // (3)
        b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters.lineDelimiter()[0]));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new SimpleClientHandler());
                ch.pipeline().addLast(new StringEncoder());
            }
        });

        // Start the client.

        try {
            CuratorFramework curatorFramework=ZookeeperFactory.create();
            List<String> serverPaths=curatorFramework.getChildren().forPath(Constants.Server_PATH);
            //加上zk监听服务器变化
            CuratorWatcher watcher=new ServerWathcer();

            curatorFramework.getChildren().usingWatcher(watcher).forPath(Constants.Server_PATH);

            for(String serverPath:serverPaths)
            {
                String[] str=serverPath.split("#");
                ChannelManager.realServerPath.add(str[0]+"#"+str[1]);
                ChannelFuture channelFuture=b.connect(str[0],Integer.valueOf(str[1])).sync();
                ChannelManager.add(channelFuture);
            }

//            if(ChannelManager.realServerPath.size()>0)
//            {
//                String[] hostAndPort=ChannelManager.realServerPath.toArray()[0].toString().split("#");
//                host=hostAndPort[0];
//                port=Integer.valueOf(hostAndPort[1]) ;
//            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    public static void send()
    {
        future=ChannelManager.get(ChannelManager.position);
        try{
            future.channel().writeAndFlush("hello server \r\n");
            future.channel().closeFuture().sync();
        }
        catch (Exception e) {}
        Object result=future.channel().attr(AttributeKey.valueOf("sssss")).get();
        System.out.println("获取到服务器数据"+result);
    }

}

