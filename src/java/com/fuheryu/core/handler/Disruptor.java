package com.fuheryu.core.handler;

import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by fuheyu on 2017/8/31.
 */
public final class Disruptor {

    private final static int processors = Runtime.getRuntime().availableProcessors();

    private final static Object[] locks = new Object[processors];

    private final static Worker[] workers = new Worker[processors];

    private final static AtomicLong count = new AtomicLong(1L);

    static {
        for(int i = 0; i < locks.length; i ++) {
            locks[i] = new Object();
            workers[i] = new Worker(i, locks[i]);
            new Thread(workers[i]).start();
        }
    }

    /**
     * 处理selectionKey的处理请求
     * @param selectionKey
     */
    public static void receive(SelectionKey selectionKey) {

        long co = count.incrementAndGet();
        int segment = (int) (co % processors);

        synchronized (locks[segment]) {

            distribute(selectionKey, segment);
        }
    }


    /**
     *
     * @param selectionKey
     * @param id 为worker的id
     */
    private static void distribute(SelectionKey selectionKey, int id) {

        workers[id].wakeUp(selectionKey);
    }
}
