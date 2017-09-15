package com.fuheryu.core.handler;

import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by fuheyu on 2017/8/31.
 */
public final class Disruptor {

    private final static Worker[]workers;

    private final static RingBuffer ringBuffer;

    static {
        ringBuffer = new RingBuffer(1024);

//        int processors = Runtime.getRuntime().availableProcessors() - 1;
        int processors = 2;
        workers =  new Worker[processors];
        for(int i = 0; i < processors; i ++) {
            workers[i] = new Worker(ringBuffer);
            // 并且直接启动worker
            new Thread(workers[i]).start();
        }

    }

    /**
     * 处理selectionKey的处理请求
     * @param selectionKey
     */
    public static void receive(SelectionKey selectionKey) {

        ringBuffer.addHaltEntry(selectionKey);
    }

}
