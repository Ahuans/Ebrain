package com.netty_server.client;

import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.tool.Node;
import com.netty_server.tool.ZooOp;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;

public class ClientTest {
    public static void main(String[] args) throws Exception {
//
        ZookeeperFactory.initialCreate("localhost:2181");
//        InetAddress inetAddress=InetAddress.getLocalHost();
//        ZooOp.zooAddChild("netty",inetAddress.getHostAddress(),8065);
////        ZooOp.zooAddChild(Constants.Server_PATH,inetAddress.getHostAddress(),8065);
////        ZooOp.zooRemoveChildren(Constants.Server_PATH);
//
//        Node node=new Node("netty",inetAddress.getHostAddress(),8064);
//        Stat stat=node.getNodeStat();
    }
}
