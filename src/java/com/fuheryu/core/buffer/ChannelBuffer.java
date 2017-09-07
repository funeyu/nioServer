package com.fuheryu.core.buffer;


import com.fuheryu.core.connection.Connection;
import io.netty.channel.ChannelFuture;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

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
            byte[] bytes = new byte[readed];
            container.get(bytes, 0, readed);

            System.out.print(new String(bytes, StandardCharsets.UTF_8));
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

    public byte[] remaining() {

        if(readed < container.limit()) {
            byte[] b = new byte[container.remaining()];
            container.get(b);
            System.out.print(new String(b, StandardCharsets.UTF_8));
            return b;
        }

        return null;
    }

    private void increaseReaded(String line) {
        if(line != null) {
            this.readed += (line.getBytes().length + 1);
        }
    }

    /**
     * 跳出空行
     */
    private void skipEmptyLine() {
        try {
            String line = this.reader.readLine();
            this.increaseReaded(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ChannelBuffer forward() {

        this.container.position(this.readed);
        return this;
    }

}
