package com.fuheryu.core.connection;

import com.fuheryu.core.buffer.ChannelBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by fuheyu on 2017/8/12.
 */
public class Connection {

    private ChannelBuffer cb;

    private SelectionKey sk;

    private long id;

    private Connection() {}

    public static Connection init(long id, int bufferSize, SelectionKey sk) {
        Connection connection = new Connection();
        connection.id = id;
        connection.sk = sk;
        connection.cb = ChannelBuffer.init(bufferSize, (SocketChannel) sk.channel(), connection);

        return connection;
    }

    public void close() {

        try {
            sk.channel().close();
            cb.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(ByteBuffer byteBuffer) throws IOException {

        SocketChannel sc = (SocketChannel)sk.channel();
        byteBuffer.flip();
        sc.write(byteBuffer);
        close();    // 这里不close client端就得不到数据
    }

    public ChannelBuffer getChannelBuffer() {
        return cb;
    }

    public String toString() {
        return "Connection_" + id;
    }
}
