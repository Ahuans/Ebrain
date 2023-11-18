
import com.netty_server.constants.Constants;
import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.server.NettyServer;
import com.netty_server.test.PortTest;
import com.netty_server.tool.ZooKeeperLog;
import com.netty_server.tool.ZooOp;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class ZooOpTest {


    @Test
    public void addTestChild() throws Exception {
        InetAddress inetAddress=InetAddress.getLocalHost();
        ZooOp.zooAddChild(Constants.Server_PATH,inetAddress.getHostAddress(),8062);
        ZooOp.zooAddChild(Constants.Server_PATH,inetAddress.getHostAddress(),8063);
    }
    @Test
    public void addTestParent() throws Exception {
        ZooOp.zooAddParent(Constants.Server_PATH);

    }
    @Test
    public void searchAllTest() throws Exception {
        List<String> res=ZooOp.zooSearch(Constants.Server_PATH,"all");
        for(String s:res)
        {
            System.out.println(s);
        }
    }
    @Test
    public void searchTest() throws Exception {
        List<String> res=ZooOp.zooSearch(Constants.Server_PATH,"8061");
        for(String s:res)
        {
            System.out.println(s);
        }
    }
    @Test
    public void testRemoveParent() throws Exception {
        ZookeeperFactory.initialCreate("localhost:2181");
        ZooOp.zooRemoveParent("test");

    }
    @Test
    public void testRemoveChildren() throws Exception {
        ZooOp.zooRemoveChildren(Constants.Server_PATH);

    }
    @Test
    public void testRemove() throws Exception {
        ZooOp.zooRemove(Constants.Server_PATH,"192.168.1.105#8063#");

    }
    @Test
    public void testChange() throws Exception {
        ZooOp.zooChange(Constants.Server_PATH,"192.168.1.105#8062#","192.168.1.105#8059#");
    }
    @Test
    public void testSearchForParents() throws Exception {
        ZookeeperFactory.initialCreate("localhost:2181");
        ZooOp.zooAddParent("test4");
        List<String> ans=ZooOp.zooSearchAllParents();
        for(String s:ans)
        {
            System.out.println(s);
        }
    }
    @Test
    public void logRead() throws Exception {

        String test=ZooKeeperLog.read();
        System.out.println(test);
    }
    @Test
    public void removeLog()
    {
        ZooKeeperLog.removeAll();
    }
    @Test
    public void testPort() throws UnknownHostException {
        InetAddress inetAddress=InetAddress.getLocalHost();
       String s= PortTest.testPort(inetAddress.getHostAddress(),8082);
        System.out.println(s);
    }
    @Test
    public void testConfig()
    {
        ZookeeperFactory.initialCreate("localhost:2181");
        System.out.println(ZookeeperFactory.getConfig());
    }
}
