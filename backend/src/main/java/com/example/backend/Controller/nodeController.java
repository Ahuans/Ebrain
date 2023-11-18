package com.example.backend.Controller;

import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.tool.Node;
import com.netty_server.tool.ZooOp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body("Success");
    }


    @PreAuthorize("hasRole('Manger')")
    @PostMapping("/addChild")
    public ResponseEntity<?> addChild(@RequestParam String parent,String ip,int port) {
        try {
            ZooOp.zooAddChild(parent,ip,port);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
        return "success";
    }

}
