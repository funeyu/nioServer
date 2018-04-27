package com.fuheryu.futty.future;

import java.util.EventListener;

/**
 * Created by fuheyu on 2018/4/26.
 */
public interface ChannelFutureListener extends EventListener{


    void operationComplete(ChannelFuture future) throws Exception;
}
