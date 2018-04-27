package com.fuheryu.futty.future;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;

public interface ChannelFuture {

    Channel getChannel();

    boolean isDone();

    boolean isSuccess();

    boolean isCancelled();

    Throwable getCause();

    boolean cancel();

    boolean setSuccess();

    boolean setFailure(Throwable cause);

    boolean setProgress(long amount, long current, long total);

    void addListener(ChannelFutureListener listener);

    void removeListener(ChannelFutureListener listener);

    ChannelFuture sync() throws InterruptedException;

    ChannelFuture syncUninterruptibly();

    ChannelFuture await() throws InterruptedException;

    ChannelFuture awaitUninterruptibly();

    boolean await(long timeout, TimeUnit unit) throws InterruptedException;

    boolean await(long timeoutMills) throws InterruptedException;

    boolean awaitUninterruptibly(long timeout, TimeUnit unit);

    boolean awaitUninterruptibly(long timeoutMills);
}
