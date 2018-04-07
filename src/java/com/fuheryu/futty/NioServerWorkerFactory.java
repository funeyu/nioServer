package com.fuheryu.futty;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class NioServerWorkerFactory {

    private final static AtomicLong increment= new AtomicLong(0);
    private static NioServerWorker[] workers = new NioServerWorker[4];

    static {
        for(int i = 0; i < 4; i ++) {
            workers[i] = new NioServerWorker(new Executor() {
                @Override
                public void execute(Runnable command) {

                }
            });
        }
    }


    public static NioServerWorker next() {
        long id = increment.incrementAndGet();
        return workers[(int)(id % 4)];
    }

}
