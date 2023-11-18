package com.netty_server.tool;

import java.io.*;

public class ZooKeeperLog{
    public static String read(){
        StringBuilder ans= new StringBuilder();
         try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/zookeeper.log"))) {
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
            PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/zookeeper.log"));

            // 清空日志文件内容
            writer.print("");
            writer.close();

            System.out.println("log file is clear");
        } catch (IOException e) {
            System.out.println("can not clean the log file :" + e.getMessage());
        }
    }
}