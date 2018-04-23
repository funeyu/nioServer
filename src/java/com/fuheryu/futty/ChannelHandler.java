package com.fuheryu.futty;

import com.fuheryu.futty.event.MessageEvent;

public interface ChannelHandler {

    public void onHandle(MessageEvent messageEvent);
}
