package com.iweb.server;

import com.iweb.server.view.MainView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/** 服务器端开启的线程 用来和客户端通信提供服务的
 * @author LYH
 * @date 2023/11/24 18:28
 */
public class ServerThread extends Thread{
    Socket socket;
    ArrayList<Socket> list;
    InputStream is;
    OutputStream os;
    DataOutputStream dos;
    DataInputStream dis;
    // 提供构造方法方便服务器端 启动线程的时候 进行引用传递
    public ServerThread(Socket socket, ArrayList<Socket> list){
        this.socket = socket;
        this.list = list;
    }

    @Override
    public void run() {
        MainView mv = new MainView();
        try{

            while (true){
                mv.startView();
                String oldStr = MainView.allLog;

                // 获取当前接入的客户端接入的输入流并封装
                is = socket.getInputStream();
                dis = new DataInputStream(is);
                // 借助输入流 读取用户的socket对象所包含的信息
                String str = dis.readUTF();
                // 服务器端显示谁发送了这条信息
                System.out.println(socket.getInetAddress()+":"+str);
                // 遍历list集合 list集合中包含了所有客户端的socket对象
                // 遍历不需要加锁 为了方便后续删除 使用迭代器遍历
                Iterator<Socket> iterator = list.iterator();
                while (iterator.hasNext()){
                    // 取出当前遍历的socket
                    Socket st = iterator.next();
                    // 判断当前socket是否可用
                    if(st.isConnected()){
                        // 如果处于连接状态 则借助输出流写入信息
                        os = st.getOutputStream();
                        dos = new DataOutputStream(os);
                        dos.writeUTF(this.socket.getInetAddress()+":"+str);
                    }else{
                        // 如果遍历的socket不可用 应该从list集合中移除
                        synchronized (list){
                            iterator.remove();
                            System.out.println(st.getInetAddress()+"已经退出聊天室,当前人数为: "+list.size());
                            dos.writeUTF(st.getInetAddress()+"已经退出聊天室,当前人数为:"+list.size());
                        }
                    }
                }
            }
        }catch (Exception e){

        }finally {
            // 如果连接当前服务器的客户端 出现其他问题 也应该从list中删除
            try{
                synchronized (list){
                    socket.close();
                    list.remove(socket);
                    System.out.println(socket.getInetAddress()+"已经退出聊天室,当前人数为: "+list.size());
                }
            }catch (Exception e){

            }
        }
    }
}