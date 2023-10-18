package com.netty_server.tool;

import com.netty_server.constants.Constants;
import com.netty_server.factory.ZookeeperFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/*
if you want to input any whole format like ip:port, please replace input with string: ip+#+port+#
and all parameter 'parent' is the name of parent node
 */
public class ZooOp {




    static CuratorFramework curatorFramework;// zookeeper embody itself in java by using curatorFramwork

    static {
        try {
            curatorFramework = ZookeeperFactory.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //when you want to use ZooOp API, please act function initialZooOp first


    //ip address may throw the NoSuchAddress exception
    //log in the ip and port on the zookeeper, may throw exception
    public static void zooAddChild(String parent,String ip,int port) throws Exception {
        InetAddress inetAddress = InetAddress.getByName(ip);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(parent+"/"+inetAddress.getHostAddress()+"#"+port+"#");
    }
    public static void zooAddParent(String parent) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(parent);
    }


    //search address which is enrolled in the zookeeper,notice: when input String "all", can output all address in zookeeper
    //if there is no address that you want ,will output empty list
    //remember: the address in list output format is like :ip+#+port+#
    public static List<String> zooSearch(String parent,String address)  {
        List<String> res=new ArrayList();
        List<String> serverPaths= null;
        try {
            serverPaths = curatorFramework.getChildren().forPath(parent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(address.equals("all"))
        {
            return serverPaths;
        }
        else
        {
            for(String s:serverPaths) {
                if (s.contains(address)) {
                    res.add(s);
                }
            }
        }
        return res;
    }


    //ip address may throw the NoSuchAddress exception
    public static void zooRemove(String parent,String address) throws Exception {
        curatorFramework.delete().forPath(parent+"/"+address);
    }
    public static void zooRemoveParent(String parent) throws Exception {
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(parent);
    }
    public static void zooRemoveChildren(String parent) throws Exception {
        List<String> serverPaths= null;
        try {
            serverPaths = curatorFramework.getChildren().forPath(parent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for(String s:serverPaths)
        {
            curatorFramework.delete().forPath(parent+"/"+s);
        }
    }
    //ip address may throw the NoSuchAddress exception
    public static void zooChange(String parent,String addressBefore,String addressAfter) throws Exception {

        curatorFramework.delete().forPath(Constants.Server_PATH+"/"+addressBefore);
        String[] address=addressAfter.split("#");
        InetAddress inetAddress = InetAddress.getByName(address[0]);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(parent+"/"+inetAddress.getHostAddress()+"#"+Integer.valueOf(address[1])+"#");
    }
}
