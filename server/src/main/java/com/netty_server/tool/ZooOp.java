package com.netty_server.tool;


import com.netty_server.factory.ZookeeperFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
if you want to input any whole format like ip:port, please replace input with string: ip+#+port+#
the parameter named address mostly means the whole format of string:ip+#+port+#
and all parameter 'parent' is the name of parent node
 */
public class ZooOp {

    private static final Logger logger = LogManager.getLogger(ZooOp.class);


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
        try
        {
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/"+parent+"/"+inetAddress.getHostAddress()+"#"+port+"#");
        }
        catch (Exception e)
        {
            logger.info("ERROR:"+e.getMessage());
            throw e;
        }
        logger.info("create node:"+"/"+parent+"/"+inetAddress.getHostAddress()+"#"+port+"#");
    }
    public static void zooAddParent(String parent) throws Exception {
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+parent);
        }catch (Exception e)
        {
            logger.info("ERROR:"+e.getMessage());
            throw e;
        }
        logger.info("create parent node:"+"/"+parent+"/");
    }
    public static List<String> zooSearchAllParents() throws Exception {
        return curatorFramework.getChildren().forPath("/");
    }

    //search address which is enrolled in the zookeeper,notice: when input String "all", can output all address in specific parent
    //if there is no address that you want ,will output empty list
    //remember: the address in list output format is like :ip+#+port+#
    public static List<String> zooSearch(String parent,String address)  {
        List<String> res=new ArrayList<>();
        List<String> serverPaths= null;
        try {
            serverPaths = curatorFramework.getChildren().forPath("/"+parent);
        } catch (Exception e) {
            logger.info("ERROR:"+e.getMessage());
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
        try{
            curatorFramework.delete().forPath("/"+parent+"/"+address);
        }
        catch (Exception e)
        {
            logger.info("ERROR:"+e.getMessage());
            throw  e;
        }
        logger.info("delete node:"+"/"+parent+"/"+address);
    }
    public static void zooRemoveParent(String parent) throws Exception {
        try {
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/"+parent);
        }catch (Exception e)
        {
            logger.info("ERROR:"+e.getMessage());
            throw e;
        }
        logger.info("delete parent node:"+"/"+parent+"/");
    }
    public static void zooRemoveChildren(String parent) throws Exception {
        List<String> serverPaths= null;
        try {
            serverPaths = curatorFramework.getChildren().forPath("/"+parent);
        } catch (Exception e) {
            logger.info("ERROR:"+e.getMessage());
            throw new RuntimeException(e);
        }
        for(String s:serverPaths)
        {
            curatorFramework.delete().forPath("/"+parent+"/"+s);
            logger.info("delete node:"+"/"+parent+"/"+s);
        }
    }
    //ip address may throw the NoSuchAddress exception
    public static void zooChange(String parent,String addressBefore,String addressAfter) throws Exception {

        try {
            curatorFramework.delete().forPath("/"+parent + "/" + addressBefore);
            String[] address = addressAfter.split("#");
            InetAddress inetAddress = InetAddress.getByName(address[0]);
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/"+parent + "/" +  addressAfter);
        }catch (Exception e)
        {
            logger.info("ERROR:"+e.getMessage());
            throw  e;
        }
        logger.info("change node "+"/"+parent + "/" + addressBefore+"to "+"/"+parent + "/" +  addressAfter);
    }
}
