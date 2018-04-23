package com.fuheryu.futty;

import com.fuheryu.futty.event.MessageEvent;

import java.nio.channels.SocketChannel;

public abstract class AbstractChannelHandler implements ChannelHandler{

    public abstract void received(SocketChannel socketChannel);

    public abstract void opened(SocketChannel socketChannel);

    public abstract void connected(SocketChannel socketChannel);

}
