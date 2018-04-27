package com.fuheryu.futty.future.impl;

import com.fuheryu.futty.future.ChannelFuture;

import java.nio.channels.Channel;

/**
 * Created by fuheyu on 2018/4/26.
 */
public class SucceedChannelFuture extends CompleteChannelFuture{
    protected SucceedChannelFuture(Channel channel) {
        super(channel);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public Throwable getCause() {
        return null;
    }

    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this;
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        return this;
    }
}
