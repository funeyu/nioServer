package com.fuheryu.futty.future.impl;

import com.fuheryu.futty.future.ChannelFuture;

import java.nio.channels.Channel;

/**
 * Created by fuheyu on 2018/4/26.
 */
public class FailedChannelFuture extends CompleteChannelFuture {

    private final Throwable cause;

    public FailedChannelFuture(Channel channel, Throwable cause) {
        super(channel);
        this.cause = cause;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this;
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        return this;
    }

    private void rethrow() {

    }
}
