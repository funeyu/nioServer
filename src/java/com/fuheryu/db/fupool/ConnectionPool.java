package com.fuheryu.db.fupool;

import me.zzp.ar.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuheyu on 2017/8/16.
 */
public class ConnectionPool extends DBConnection {

    private List<Connection> avaliableConnections ;

    private List<Connection> usedConnections ;

    private int CONTAINER_SIZE = ;

    private ConnectionPool () {}

    /**
     * 根据size容量初创一定个数的connection 存放于avaliableConneciotns中
     * @param size
     * @param name
     * @param pass
     * @return
     * @throws SQLException
     */
    public static ConnectionPool bootStrap(int size, String name, String pass) throws SQLException {
        ConnectionPool pool = new ConnectionPool();
        List<Connection> avaliableConnections = new ArrayList<>();

        for(int i = 0; i < size ; i ++) {
            avaliableConnections.add(pool.getNonPooledConnection(name, pass));
        }

        avaliableConnections.notify();


        pool.avaliableConnections = avaliableConnections;
        pool.usedConnections = new ArrayList<>();
        pool.CONTAINER_SIZE = size;
        return pool;
    }

    public Connection getOne() throws InterruptedException {
        synchronized (avaliableConnections) {
            if(!avaliableConnections.isEmpty()) {

                Connection connection = avaliableConnections.remove(0);
                usedConnections.add(connection);
                return connection;
            }
            else{

                avaliableConnections.wait();
                Connection connection = avaliableConnections.remove(0);
                usedConnections.add(connection);
                return connection;
            }
        }
    }


    public void returnedOne(Connection connection) {

        synchronized (avaliableConnections) {
            avaliableConnections.add(connection);
            avaliableConnections.notify();
        }
    }

    
}
