import com.netty_server.constants.Constants;
import com.netty_server.tool.ZooOp;
import com.sun.org.apache.bcel.internal.Const;
import org.junit.Test;

import java.net.InetAddress;
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
        ZooOp.zooRemoveParent(Constants.Server_PATH);

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
}
