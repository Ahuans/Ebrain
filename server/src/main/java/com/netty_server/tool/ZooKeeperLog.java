package com.netty_server.tool;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZooKeeperLog{
    public static String read(){
        StringBuilder ans= new StringBuilder();
         try (BufferedReader reader = new BufferedReader(new FileReader("server/src/main/resources/zookeeper_log.txt"))) {
             String line;
            while ((line = reader.readLine()) != null) {
                ans.append(line).append("\n");

            }
        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
         return ans.toString();
    }
    public static void removeAll()
    {
        try {
            // 使用 PrintWriter 打开日志文件
            PrintWriter writer = new PrintWriter(new FileWriter("server/src/main/resources/zookeeper_log.txt"));

            // 清空日志文件内容
            writer.print("");
            writer.close();

            System.out.println("log file is clear");
        } catch (IOException e) {
            System.out.println("can not clean the log file :" + e.getMessage());
        }
    }

    public static void writeInfo(String info){
        Date currentDate = new Date();

        // 创建日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // 格式化日期
        String formattedDate = dateFormat.format(currentDate);

        String Message=formattedDate+"   "+info+"\n";

        String filePath = "server/src/main/resources/zookeeper_log.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            writer.write(Message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}