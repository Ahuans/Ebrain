package com.netty_server.tool;

import com.netty_server.factory.ZookeeperFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Node {
    private CuratorFramework curatorFramework;// zookeeper embody itself in java by using curatorFramwork
    private String ip;
    private int port;
    private String parent;


    Node(String parent,String address) throws Exception {
        String[] temp=address.split("#");
        this.ip=temp[0];
        this.port=Integer.valueOf(temp[1]);
        this.parent=parent;
        curatorFramework=ZookeeperFactory.create();
    }
    public Node(String parent, String ip, int port) throws Exception {
        this.ip=ip;
        this.port=port;
        this.parent=parent;
        curatorFramework=ZookeeperFactory.create();
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getParent() {
        return parent;
    }

    public  String getNodeData() throws Exception
    {
        byte[] data = curatorFramework.getData().forPath("/"+parent+"/"+ip+"#"+port+"#");
        String nodeData = new String(data, StandardCharsets.UTF_8);
        return nodeData;
    }
    public String[] getNodeStatString() throws Exception {
        Stat nodeStat = curatorFramework.checkExists().forPath("/"+parent+"/"+ip+"#"+port+"#");
        String[] statusArray=new String[11];
        if (nodeStat != null) {
            statusArray[0] = "Version: " + nodeStat.getVersion();
            statusArray[1] = "Data Length: " + nodeStat.getDataLength();
            statusArray[2] = "Num Children: " + nodeStat.getNumChildren();
            statusArray[3] = "Ephemeral Owner: " + nodeStat.getEphemeralOwner();
            statusArray[4]="czxid: "+nodeStat.getCzxid();
            statusArray[5]="mzxid: "+nodeStat.getMzxid();
            statusArray[6]="pzxid: "+nodeStat.getPzxid();
            statusArray[7]="ctime: "+nodeStat.getCtime();
            statusArray[8]="mtime: "+nodeStat.getMtime();
            statusArray[9]="cversion: "+nodeStat.getCversion();
            statusArray[10]="aversion: "+nodeStat.getAversion();

        }
        return statusArray;
    }
    public Stat getNodeStat() throws Exception {
        Stat nodeStat = curatorFramework.checkExists().forPath("/"+parent+"/"+ip+"#"+port+"#");
        return nodeStat;
    }

}
