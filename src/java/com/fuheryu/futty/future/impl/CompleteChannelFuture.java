package com.fuheryu.futty.future.impl;

import com.fuheryu.futty.future.ChannelFuture;
import com.fuheryu.futty.future.ChannelFutureListener;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;

/**
 * Created by fuheyu on 2018/4/26.
 */
public abstract class CompleteChannelFuture implements ChannelFuture{

    private final Channel channel;


    protected CompleteChannelFuture(Channel channel) {
        if(channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean setSuccess() {
        return false;
    }

    @Override
    public boolean setFailure() {
        return false;
    }

    @Override
    public boolean setProgress(long amount, long current, long total) {
        return false;
    }

    @Override
    public void addListener(com.fuheryu.futty.future.ChannelFutureListener listener) {
        try {
            listener.operationComplete(this);
        } catch (Throwable t) {
            System.out.println("An exception was thrown by");
        }
    }

    @Override
    public void removeListener(ChannelFutureListener listener) {

    }

    @Override
    public ChannelFuture await() throws InterruptedException {
        return this;
    }

    @Override
    public ChannelFuture awaitUninterruptibly() {
        return this;
    }

    @Override
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public boolean await(long timeoutMills) throws InterruptedException {
        return false;
    }

    @Override
    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        return false;
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMills) {
        return false;
    }
}
