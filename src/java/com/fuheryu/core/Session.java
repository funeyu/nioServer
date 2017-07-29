package com.fuheryu.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 *
 * Created by fuheyu on 2017/7/29.
 */
public class Session {

    private ServerSocketChannel ssc;
    private Selector selector;

    private Session(ServerSocketChannel ssc, Selector selector) {

        this.ssc = ssc;
        this.selector = selector;
    }

    /**
     * 创建一个Session 对象
     * @param isNio true：用NIO，否则为false
     * @return
     */
    public static Session create(Boolean isNio) {

        if(isNio) {
            try {
                ServerSocketChannel acceptChannel = ServerSocketChannel.open();
                acceptChannel.configureBlocking(false);

                Selector selector = Selector.open();
                return new Session(acceptChannel, selector);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    public Session bind(int port) {

        try {
            ssc.socket().bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public Session register(int event) {

        try {
            ssc.register(selector, event);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }

        return this;
    }

    public Selector getSelector() {

        return selector;
    }

}
