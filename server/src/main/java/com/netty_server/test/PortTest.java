package com.netty_server.test;

import java.net.Socket;

public class PortTest {
    static public String testPort(String ip,int port)
    {
        try (Socket socket = new Socket(ip, port)) {
            return "Port " + port + " is open on " + ip;
        } catch (Exception e) {
            return "Port " + port + " is closed or unreachable on " + ip;
        }
    }
}
