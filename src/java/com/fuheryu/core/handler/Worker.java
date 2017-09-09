package com.fuheryu.core.handler;

import io.netty.handler.codec.http.HttpHeaders;

import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by fuheyu on 2017/8/31.
 */
public class Worker implements Runnable{

    // 这个id和分片锁的index对应
    private final int id;

    private final Object lock;

    private final Object pendingLock = new Object();

    private SelectionKey selectionKey;

    private Handler handler;

    private AtomicBoolean stop = new AtomicBoolean(false);

    public Worker(int id, Object lock) {

        this.id = id;
        this.lock = lock;
        this.handler = HttpHandler.createHander();
    }

    /**
     * 唤醒阻塞线程
     */
    public synchronized void wakeUp(SelectionKey selectionKey) {

        this.selectionKey = selectionKey;
        this.notify();
    }

    /**
     * 真正处理request请求的地方
     */
    private void doWork() {

        handler.onRead(selectionKey);
    }

    @Override
    public void run() {
        while(!stop.get()) {
            synchronized (this) {
                try {
                    this.wait();
                    this.doWork();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
