package com.netty_server.client;

import com.netty_server.constants.Constants;
import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.tool.ZooKeeperLog;
import com.netty_server.tool.ZooOp;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTest {
    public static void main(String[] args) throws Exception {
        ZookeeperFactory.initialCreate("localhost:2181");
        InetAddress inetAddress=InetAddress.getLocalHost();
        ZooOp.zooAddChild("/Constants.Server_PATH",inetAddress.getHostAddress(),8064);
//        ZooOp.zooAddChild(Constants.Server_PATH,inetAddress.getHostAddress(),8065);
//        ZooOp.zooRemoveChildren(Constants.Server_PATH);
        System.out.println("");
        ZooKeeperLog.removeAll("server/src/main/resources/zookeeper.log");
    }
}
