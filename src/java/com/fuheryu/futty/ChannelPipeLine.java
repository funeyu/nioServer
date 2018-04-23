package com.fuheryu.futty;

import java.nio.channels.Channel;

/**
 * Created by fuheyu on 2018/4/18.
 */
public interface ChannelPipeLine {

    void addHandler(ChannelHandler channelHandler);

    void handle(Channel channel);
}
