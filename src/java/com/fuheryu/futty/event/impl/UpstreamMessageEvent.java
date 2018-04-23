package com.fuheryu.futty.event.impl;

import com.fuheryu.futty.future.ChannelFuture;
import com.fuheryu.futty.event.MessageEvent;

import java.net.SocketAddress;
import java.nio.channels.Channel;

/**
 * Created by fuheyu on 2018/4/22.
 */
public class UpstreamMessageEvent implements MessageEvent {

    private final Channel channel;
    private final Object message;
    private final SocketAddress remoteAddress;

    public UpstreamMessageEvent(Channel channel, Object message, SocketAddress remoteAddress) {
        this.channel = channel;
        this.message = message;
        if(remoteAddress != null) {
            this.remoteAddress = remoteAddress;
        } else {
            this.remoteAddress = null;
        }
    }
    @Override
    public Object getMessage() {
        return message;
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
        return null;
    }
}
