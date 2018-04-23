package com.fuheryu.futty.event;

import java.net.SocketAddress;

/**
 * Created by fuheyu on 2018/4/22.
 */
public interface MessageEvent extends ChannelEvent{

    Object getMessage();

    SocketAddress getRemoteAddress();
}
