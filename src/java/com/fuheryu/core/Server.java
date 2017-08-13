package com.fuheryu.core;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import com.fuheryu.core.handler.Handler;
import com.fuheryu.core.handler.HttpHandler;
import com.fuheryu.core.http.Session;

/**
 * Created by fuheyu on 2017/7/29.
 */
public class Server {

    private static Server singleServer;
    private AtomicBoolean listenning = new AtomicBoolean(true);
    private Handler handler;
    private Session session;
    private Selector selector;
    private long connectionCount = 0;

    public static Server initServer(int port) {
        if(singleServer == null) {
            singleServer = new Server();
        }

        singleServer.session = Session.create(true)
                .bind(port)
                .register(SelectionKey.OP_ACCEPT);

        return singleServer;
    }

    public Server setHandler(Handler handler) {

        this.handler = handler;
        return this;
    }

    public void start() {


        try {
            while(listenning.get()) {
                int n = session.getSelector().select();
                if(n == 0) { // 没有指定的I/O事件发生
                    continue;
                }

                Iterator<SelectionKey> selectionKeys = session.getSelector().selectedKeys().iterator();
                while (selectionKeys.hasNext()) {
                    SelectionKey key = selectionKeys.next();
                    selectionKeys.remove();

                    if(key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(session.getSelector(), SelectionKey.OP_READ);
                    }

                    if(key.isReadable() && key.isValid()) {
                        key.attach(connectionCount ++);
                        key.cancel();
                        handler.onRead(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        Server s = Server.initServer(8099);
        s.setHandler(HttpHandler.createHander());

        s.start();
    }

}
