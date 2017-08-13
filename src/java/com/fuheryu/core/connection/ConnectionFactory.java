package com.fuheryu.core.connection;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fuheyu on 2017/8/12.
 */
public class ConnectionFactory {

    private final static Map<String, Connection> connections = new ConcurrentHashMap<>();

    public static Connection init(int size, long id, SelectionKey sk) {
        Connection connection = Connection.init(id, size, sk);

        return connection;
    }

    public static boolean contains(String connectionId) {
        return connections.get(connectionId) != null;
    }

    public static Connection getById(String connectionId) {
        return connections.get(connectionId);
    }

    public static void feed(Connection connection) {
        // "Connection_${id}"作为key
        connections.put(connection.toString(), connection);
    }
}
