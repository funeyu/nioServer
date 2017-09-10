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

//        long leadingCursor;  // 代表worker抢占到的任务index
//        long jobCursor;      // 代表分发job的index，
//                             // 如果 jobCursor == workingCursor则代表job分发的慢，worker处于饥饿状态
//                             // 如果 (jobCursor + RingBuffer.size) == workingCursor则代表job分发过快，worker工作饱和了

        while(true) {
            if(running.get()) {

                SelectionKey job = ringBuffer.haltForEntry();
                if(job == null) {
                    try {
                        Thread.sleep(1);
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
