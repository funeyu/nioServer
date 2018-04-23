package com.fuheryu.futty;

import com.fuheryu.futty.event.impl.DownstreamMessageEvent;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fuheyu on 2018/4/18.
 */
public class DefaultPipeLine implements ChannelPipeLine {

    private List<ChannelHandler> handlerChain = new ArrayList<>();
    private ChannelHandler head;
    private ChannelHandler tail;

    public DefaultPipeLine() {}

    @Override
    public void addHandler(ChannelHandler channelHandler) {
        handlerChain.add(channelHandler);
    }

    @Override
    public void handle(Channel channel) {
        if(handlerChain.size() < 1) {
            throw new NullPointerException("handlerChain was not inited!");
        }

        for(Iterator<ChannelHandler> it = handlerChain.iterator(); it.hasNext();) {
            ChannelHandler channelHandler = it.next();
            channelHandler.onHandle(new DownstreamMessageEvent(channel, null, new Object(), null));
        }
    }
}
