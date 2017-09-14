package com.fuheryu.core.handler;

import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by fuheyu on 2017/8/31.
 */
public class Worker implements Runnable{

    private final RingBuffer<SelectionKey> ringBuffer;

    private AtomicBoolean running = new AtomicBoolean(true);

    private final Handler httpHandler;


    public Worker(RingBuffer<SelectionKey> ringBuffer) {

        this.ringBuffer = ringBuffer;
        this.httpHandler = HttpHandler.createHander();
    }


    /**
     * 真正处理request请求的地方
     */
    private void doWork(SelectionKey selectionKey) {

        httpHandler.onRead(selectionKey);
    }

    @Override
    public void run() {

        while(true) {
            if(running.get()) {

                SelectionKey job = ringBuffer.haltForEntry();
                if(job == null) {
                    try {
                        Thread.sleep(0, 10);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                doWork(job);
            }
        }
    }

}
