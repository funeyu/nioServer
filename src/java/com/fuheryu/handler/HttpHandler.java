package com.fuheryu.handler;

import java.nio.channels.SelectionKey;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuheyu on 2017/7/29.
 */
public class HttpHandler implements Handler {

    private ExecutorService excutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private int count = 0;

    public void onRead(SelectionKey selectionKey) {

        excutors.execute(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(3000);
                    count ++;
                    System.out.println("excute:" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
