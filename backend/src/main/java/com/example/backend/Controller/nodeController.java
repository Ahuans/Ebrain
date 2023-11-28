package com.example.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netty_server.factory.ZookeeperFactory;
import com.netty_server.test.PortTest;
import com.netty_server.tool.Node;
import com.netty_server.tool.ZooKeeperLog;
import com.netty_server.tool.ZooOp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.net.InetAddress;
import java.util.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author BruceChen
 * @date 2023/11/01 11:46
 **/
@Controller
@CrossOrigin("*")
public class nodeController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public  String test(){
        return "forward:/index.html";
    }

    @ResponseBody
    @PostMapping("/transferApi")
    public  String sendHttpRequest(@RequestBody  Map<String, Object> requestData) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Map<String, String>> future = executorService.submit(new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
                StringBuilder response = new StringBuilder();
                Map<String, List<String>> headerFields=new HashMap<>();
                String type="";
                try {
                    String url = requestData.get("url").toString();
                    String method = requestData.get("method").toString().toUpperCase();
                    String requestBody = requestData.get("restBody").toString();
                    Map<String, Object> requestHeaders = (Map<String, Object>) requestData.get("header");

                    URL apiUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                    connection.setRequestMethod(method);

                    // Set request headers
                    if (requestHeaders != null) {
                        for (Map.Entry<String, Object> entry : requestHeaders.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue().toString();
                            connection.setRequestProperty(key, value);
                        }
                    }

                    // Set request body
                    if (!method.equalsIgnoreCase("GET")) {
                        String json_request=convertToJsonString(requestBody);

                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        OutputStream outputStream = connection.getOutputStream();
                        outputStream.write(json_request.getBytes());
                        outputStream.flush();
                    }

                    int responseCode = connection.getResponseCode();
                    headerFields = connection.getHeaderFields();

                    type = headerFields.get("Content-Type").get(0);
                    if(type.contains("html")){
                        type="/html";
                    }
                    else{
                        type="/json";
                    }

                    if(responseCode!=200){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                    }
                    else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                    }


                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("result",response.toString());
                dataMap.put("header", type);
                return dataMap;
            }
        });

        // 等待任务执行完毕并获取结果
        Map<String, String> Map = future.get();
        // 关闭线程池
        executorService.shutdown();

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("data",Map.get("result"));
        dataMap.put("header",Map.get("header"));
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(dataMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            json = "{}"; // 返回空对象的JSON
        }

        return json;
        }


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
        System.out.println("1");
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
    @PostMapping("/getAllParentNodes")
    public List<String> searchAddress() throws Exception {
        List<String> list = ZooOp.zooSearchAllParents();

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
    @PostMapping("/socketTest")
    public  ResponseEntity<?>  getDetailInformation(@RequestParam String ip, @RequestParam String port ){

        String res=PortTest.testPort(ip,Integer.parseInt(port));
        if (res.contains("open")){
            return ResponseEntity.ok().body("Success");
        }
        else{
            return ResponseEntity.badRequest().body("failed");
        }

    }

    @ResponseBody
    @PostMapping("/getLog")
    public String[] getLog(){
        String read = ZooKeeperLog.read();
        String[] log = read.split("\\n");

        return  log;
    }

    @ResponseBody
    @PostMapping("/clearLog")
    public String clearLog(){
        ZooKeeperLog.removeAll();
        return  "success";
    }



    private static String convertToJsonString(String input) {
        if(input.equals("{}")){
            return input;
        }
        // 去除首尾的大括号
        String content = input.substring(1, input.length() - 1);
        // 按逗号分割键值对
        String[] pairs = content.split(", ");

        StringBuilder jsonBuilder = new StringBuilder();
        for (String pair : pairs) {
            // 按等号分割键和值
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue[1];

            // 添加到 JSON 字符串中
            jsonBuilder.append("\"").append(key).append("\":\"").append(value).append("\",");
        }

        // 去除最后一个逗号
        String json = jsonBuilder.toString();
        json = json.substring(0, json.length() - 1);

        // 添加大括号
        json = "{" + json + "}";

        return json;
    }

}
