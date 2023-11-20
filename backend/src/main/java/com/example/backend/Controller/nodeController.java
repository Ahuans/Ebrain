package com.example.backend.Controller;

import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.tool.Node;
import com.netty_server.tool.ZooKeeperLog;
import com.netty_server.tool.ZooOp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.net.InetAddress;
import java.util.List;


/**
 * @author BruceChen
 * @date 2023/11/01 11:46
 **/
@Controller
@CrossOrigin("*")
public class nodeController {


    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/updateNode")
    public ResponseEntity<?> updateNode(@RequestParam String parent, @RequestParam String addressBefore,
                                        @RequestParam String addressAfter){
        try {
            ZooOp.zooChange(parent,addressBefore,addressAfter);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        ZooKeeperLog.writeInfo("change node "+"/"+parent + "/" + addressBefore+"to "+"/"+parent + "/" +  addressAfter);
        return ResponseEntity.ok().body("Success");
    }

    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/removeParent")
    public  ResponseEntity<?> removeParent(@RequestParam String parent){
        try {
            ZooOp.zooRemoveParent(parent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        ZooKeeperLog.writeInfo("delete parent node:"+"/"+parent+"/");
        return ResponseEntity.ok().body("Success");
    }

    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/removeChildren")
    public  ResponseEntity<?> removeChildren(@RequestParam String parent){
        try {
            ZooOp.zooRemoveChildren(parent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        ZooKeeperLog.writeInfo("delete all the node of "+parent);
        return ResponseEntity.ok().body("Success");
    }

    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/removeAddress")
    public ResponseEntity<?> removeAddress(@RequestParam String parent, @RequestParam String address){
        try {
            ZooOp.zooRemove(parent,address);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        ZooKeeperLog.writeInfo("delete node:"+"/"+parent+"/"+address);
        return ResponseEntity.ok().body("Success");
    }

    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/addParent")
    public ResponseEntity<?> addParent(@RequestParam String parent) {
        try {
            ZooOp.zooAddParent(parent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        ZooKeeperLog.writeInfo("create parent node:"+"/"+parent+"/");
        return ResponseEntity.ok().body("Success");
    }


    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/addChild")
    public ResponseEntity<?> addChild(@RequestParam String parent,String ip,int port) {
        InetAddress inetAddress;
        try {
            ZooOp.zooAddChild(parent,ip,port);
             inetAddress = InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        ZooKeeperLog.writeInfo("create node:"+"/"+parent+"/"+inetAddress.getHostAddress()+"#"+port+"#");
        return ResponseEntity.ok().body("Success");
    }

    @ResponseBody
    @PostMapping("/getNodeInformation")
    public List<String> searchAddress(@RequestParam String parent, @RequestParam String address){
        List<String> list = ZooOp.zooSearch(parent, address);
        return list;
    }

    @ResponseBody
    @PostMapping("/getDetailNodeInformation")
    public String[] getDetailInformation(@RequestParam String parent,@RequestParam String ip,@RequestParam int port){
        try {
            Node temp=new Node(parent,ip,port);
            String[] nodeStatString = temp.getNodeStatString();
            return nodeStatString;
        } catch (Exception e) {
            e.printStackTrace();
            String[] fail = new String[1];
            fail[0]=e.getMessage();
            return fail;
        }
    }

    @ResponseBody
    @PostMapping("/connectServer")
    public String getDetailInformation(@RequestParam String port ){
        ZookeeperFactory.initialCreate("localhost:"+port);
        ZooKeeperLog.writeInfo("Connected to ZooKeeper");
        return "success";
    }
    @ResponseBody
    @PostMapping("/getLog")
    public String getLog(){

        return  ZooKeeperLog.read();
    }

    @ResponseBody
    @PostMapping("/clearLog")
    public String clearLog(){
        ZooKeeperLog.removeAll();
        return  "success";
    }
}
