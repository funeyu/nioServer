package com.fuheryu.db.fupool;

import me.zzp.ar.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fuheyu on 2017/8/16.
 */
public class ConnectionPool extends DBConnection {

    private List<Connection> avaliableConnections ;

    private List<Connection> usedConnections ;

    private String name;

    private String pass;

    private int CONTAINER_SIZE ;

    private AtomicInteger created = new AtomicInteger(0);

    private ConnectionPool () {}

    /**
     * 根据size容量初创一定个数的connection 存放于avaliableConneciotns中
     * @param size
     * @param name
     * @param pass
     * @return
     * @throws SQLException
     */
    public static ConnectionPool bootStrap(int size, int maxSize, String name, String pass) throws SQLException {
        ConnectionPool pool = new ConnectionPool();
        List<Connection> avaliableConnections = new ArrayList<>();

        for(int i = 0; i < size ; i ++) {
            avaliableConnections.add(pool.getNonPooledConnection(name, pass));
        }


        pool.avaliableConnections = avaliableConnections;
        pool.usedConnections = new ArrayList<>();
        pool.CONTAINER_SIZE = size;
        pool.CONTAINER_SIZE = maxSize;
        pool.created.set(size);
        pool.name = name;
        pool.pass = pass;

        return pool;
    }

    private Connection findOne() {
        Connection connection = avaliableConnections.remove(0);
        usedConnections.add(connection);

        return connection;
    }

    public Connection getOne() throws InterruptedException, SQLException {
        synchronized (avaliableConnections) {

            if(avaliableConnections.isEmpty()) {

                if(created.get() < CONTAINER_SIZE) { // 未满可以再创建connection
                    Connection newed = getNonPooledConnection(name, pass);
                    avaliableConnections.add(newed);
                    return newed;
                }

                // 没有可以再创建的connection 只能等有其他的释放连接
                avaliableConnections.wait();
                return findOne();
            }
            else{

                if(created.get() >= CONTAINER_SIZE) { // 已满
                    avaliableConnections.wait();
                    return findOne();
                } else

                    return findOne();

            }
        }
    }


    public void returnedOne(Connection connection) {

        synchronized (avaliableConnections) {
            avaliableConnections.add(connection);
            avaliableConnections.notify();
            usedConnections.remove(connection);
        }

    }

}
