package com.iweb.server.service;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/** 数据库连接池
 * @author LYH
 * @date 2023/11/26 14:27
 */
public class ConnectionPool {
    List<Connection> cs = new ArrayList<>();
    int size;
    private volatile static ConnectionPool connectionPool;
    public static ConnectionPool getInstance(){
        if(connectionPool == null){
            synchronized (ConnectionPool.class){
                if(connectionPool == null){
                    connectionPool = new ConnectionPool();
                }
            }
        }
        return connectionPool;
    }
    private ConnectionPool(){
        this.size = 10;
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void init() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        for (int i = 0; i <size; i++) {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/employsystem?characterEncoding=utf8",
                    "root","a12345");
            cs.add(c);
        }
    }
    public synchronized Connection getConnection(){
        while (cs.isEmpty()){
            try{
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Connection c = cs.remove(0);
        return c;
    }

    public synchronized void returnConnection(Connection c){
        cs.add(c);
        this.notifyAll();
    }
}
