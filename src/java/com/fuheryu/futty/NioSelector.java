package com.fuheryu.futty;

import com.fuheryu.futty.future.ChannelFuture;

import java.nio.channels.Channel;

public interface NioSelector extends Runnable{

    void register(Channel channel, ChannelFuture channelFuture);

    void rebuildSelector();

    void shutDown();
}
