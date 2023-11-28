package com.iweb.client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author LYH
 * @date 2023/11/24 18:51
 */
public class SendThread extends Thread{
    Socket socket;
    public SendThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream os = null;
        DataOutputStream dos = null;
        try {
            Scanner sc = new Scanner(System.in);
            while (true){
                String str = sc.nextLine();
                // 如果socket对象对应的客户端正常使用
                if(socket.isConnected()){
                    os = socket.getOutputStream();
                    dos = new DataOutputStream(os);
                    dos.writeUTF(str);
                }else{
                    break;
                }
            }
        }catch (Exception e){

        }finally {
            try{
                socket.close();
                dos.close();
                os.close();
            }catch (Exception e){

            }
        }
    }
}
