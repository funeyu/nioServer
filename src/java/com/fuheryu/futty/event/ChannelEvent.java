package com.fuheryu.futty.event;

import com.fuheryu.futty.future.ChannelFuture;

import java.nio.channels.Channel;

/**
 * Created by fuheyu on 2018/4/22.
 */
public interface ChannelEvent {

    Channel getChannel();

    ChannelFuture getFuture();
}
