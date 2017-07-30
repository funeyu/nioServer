package com.fuheryu.handler;

import java.io.ByteArrayOutputStream;
import java.net.HttpRetryException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuheyu on 2017/7/29.
 */
public class HttpHandler implements Handler {

    private ExecutorService excutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private int count = 0;

    private HttpHandler(){}

    public void onRead(final SelectionKey selectionKey) {

        excutors.execute(new Runnable() {

            public void run() {
                try {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readed = 0;
                    byte[] bytes;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    while((readed = sc.read(buffer)) > 0) {

                        buffer.flip();
                        bytes = new byte[readed];
                        buffer.get(bytes, 0 , readed);
                        bos.write(bytes);
                        buffer.clear();
                    }

                    System.out.println(new String(bos.toByteArray()));
                    selectionKey.selector().wakeup();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static Handler createHander() {
        Handler h = new HttpHandler();
        return h;
    }
}
