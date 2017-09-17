package com.fuheryu.core.handler;

import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by fuheyu on 2017/8/31.
 */
public class Worker implements Runnable{

    private final RingBuffer<SelectionKey> ringBuffer;

    private AtomicBoolean running = new AtomicBoolean(true);

    private final Handler httpHandler;

    private final static long INITIAL_VALUE = -1L;

    private long current;


    public Worker(RingBuffer<SelectionKey> ringBuffer) {

        this.ringBuffer = ringBuffer;
        this.httpHandler = HttpHandler.createHander();
        this.current = INITIAL_VALUE;
    }


    /**
     * 真正处理request请求的地方
     */
    private void doWork(SelectionKey selectionKey) {

        httpHandler.onRead(selectionKey);
    }


    public long getCurrent() {

        return this.current;
    }

    public Worker increase() {

        this.current ++;
        return this;
    }

    @Override
    public void run() {

        while(true) {
            if(running.get()) {

                SelectionKey job = ringBuffer.haltForEntry(this);
                if(job == null) {
                    LockSupport.parkNanos(1);
                }

                if(job != null) {
                    doWork(job);
                }
            }
        }
    }

}
