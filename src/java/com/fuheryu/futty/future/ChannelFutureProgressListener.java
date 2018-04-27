package com.fuheryu.futty.future;

import io.netty.channel.ChannelFutureListener;

/**
 * Created by fuheyu on 2018/4/26.
 */
public interface ChannelFutureProgressListener extends ChannelFutureListener {

    void operationProgressed(ChannelFuture future, long amout, long current, long total) throws Exception;
}
