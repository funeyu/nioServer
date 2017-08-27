package com.fuheryu.core.buffer;


import com.fuheryu.core.connection.Connection;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by fuheyu on 2017/8/13.
 */
public class ChannelBuffer {

    private ByteBuffer container;
    private SocketChannel sc;
    private int writed = 0;  // 代表写入的字节数
    private int readed = 0;  // 代表读出的字节数
    private int size;
    private Connection conn;
    private BufferedReader reader;

    private ChannelBuffer(){}

    public static ChannelBuffer init(int size, SocketChannel sc, Connection conn) {
        ChannelBuffer cb = new ChannelBuffer();
        cb.container = ByteBuffer.allocate(size);
        cb.sc = sc;
        cb.size = size;
        cb.conn = conn;

        return cb;

    }

    public int read() {
        try {
            readed = sc.read(container);
            return readed;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void clear() {
        this.container = null;
    }

    public void initReader() {
        this.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(container.array(), 0, readed)));
    }

    public void write(ByteBuffer byteBuffer) {

        try {
            sc.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取输入信息的按行读
     * @return
     */
    public String nextLine() {

        try {
            String line = this.reader.readLine();
            this.increaseReaded(line);
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void increaseReaded(String line) {
        if(line != null) {
            this.readed += (line.getBytes().length + 1);
        }
    }

}
