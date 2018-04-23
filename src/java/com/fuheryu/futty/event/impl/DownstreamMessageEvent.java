package com.fuheryu.futty.event.impl;

import com.fuheryu.futty.future.ChannelFuture;
import com.fuheryu.futty.event.MessageEvent;

import java.net.SocketAddress;
import java.nio.channels.Channel;

/**
 * Created by fuheyu on 2018/4/22.
 */
public class DownstreamMessageEvent implements MessageEvent {

    private final Channel channel;
    private final ChannelFuture future;
    private final Object message;
    private final SocketAddress remoteAddress;

    public DownstreamMessageEvent(Channel channel, ChannelFuture future, Object message, SocketAddress remoteAddress) {

        if(channel == null) {
            throw new NullPointerException("channel");
        }
//        if(future == null) {
//            throw new NullPointerException("future");
//        }
        if(message == null) {
            throw new NullPointerException("message");
        }

        this.channel = channel;
        this.future = future;
        this.message = message;

        if(remoteAddress != null) {
            this.remoteAddress = remoteAddress;
        } else {
            // todo remoteAddress 要设置
            this.remoteAddress = null;
        }
    }
    @Override
    public Object getMessage() {
        return null;
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public ChannelFuture getFuture() {
        return future;
    }
}
