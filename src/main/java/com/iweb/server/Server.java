package com.iweb.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/28 15:18
 */
public class Server {
    public volatile static ArrayList<Socket> list = new ArrayList<>();

    public static void main(String[] args) {
        // 创建服务器端 指定监听端口
        try {
            ServerSocket ss = new ServerSocket(8888);
            // 服务器端需要不断接受来自客户端的请求 并且将客户端发送过来的Socket对象存放在List中
            while (true){
                Socket socket = ss.accept();
                list.add(socket);
                System.out.println(socket.getInetAddress()+"已经成功连接!员工系统中的人数为"+list.size());
                // 每接受到一个客户端 就应该开启一个线程为其服务
                new ServerThread(socket,list).start();
            }
        } catch (IOException e) {
        }
    }
}
