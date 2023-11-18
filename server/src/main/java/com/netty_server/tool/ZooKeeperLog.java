package com.netty_server.tool;

import java.io.*;

public class ZooKeeperLog{
    public static void read(String logFilePath){
         try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 在这里可以对每一行日志进行处理，例如输出到控制台或者写入文件
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeAll(String logFilePath)
    {
        try {
            // 使用 PrintWriter 打开日志文件
            PrintWriter writer = new PrintWriter(new FileWriter(logFilePath));

            // 清空日志文件内容
            writer.print("");
            writer.close();

            System.out.println("log file is clear");
        } catch (IOException e) {
            System.out.println("can not clean the log file :" + e.getMessage());
        }
    }
}