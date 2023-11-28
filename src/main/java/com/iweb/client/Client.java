package com.iweb.client;

import java.net.Socket;
import java.util.Scanner;

/**
 * @author LYH
 * @date 2023/11/24 19:00
 */
public class Client {
    public static void main(String[] args) {
        try{
            String ip = "107.0.0.1";
            Socket socket = new Socket(ip,8888);
            // 启动发送消息线程 和 接收消息线程
            new SendThread(socket).start();
            new ReceiveThread(socket).start();
        }catch (Exception e){

        }
    }
}
