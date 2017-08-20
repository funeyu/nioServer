package com.fuheryu.fupool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fuheyu on 2017/8/16.
 */
public class FuPool extends DBConnection {

    private List<Connection> avaliableConnections ;

    private List<Connection> usedConnections ;

    private String name;

    private String pass;

    private int CONTAINER_SIZE ;

    private AtomicInteger created = new AtomicInteger(0);

    private static FuPool pool;

    private FuPool() {}


    /**
     * 根据size容量初创一定个数的connection 存放于avaliableConnections中
     * @return
     * @throws SQLException
     */
    public static synchronized FuPool bootStrap() throws SQLException {
        if(pool != null) {
            return pool;
        }

        String name = Utils.getProperty("dbuser");
        String pass = Utils.getProperty("dbpass");
        int initSize = Integer.parseInt(Utils.getProperty("dbpoolsize"));
        int maxSize = Integer.parseInt(Utils.getProperty("dbmaxconnection"));
        pool = init(initSize, maxSize, name, pass);

        return pool;
    }

    /**
     * 根据相应的设置得到一个数据库连接池
     * @param size
     * @param maxSize
     * @param pass
     * @param name
     * @return
     * @throws SQLException
     */
    private static FuPool init(int size, int maxSize, String name, String pass) throws SQLException {
        FuPool pool = new FuPool();
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

    public static void main(String[] args) {
        try {
            bootStrap();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
