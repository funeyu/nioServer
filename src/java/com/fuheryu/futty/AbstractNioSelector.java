package com.fuheryu.futty;

import java.nio.channels.Channel;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;

public abstract class AbstractNioSelector implements NioSelector {

    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
    protected volatile Selector selector;
    protected volatile Thread thread;
    private final CountDownLatch shutDown = new CountDownLatch(1);

    public void register(Channel channel, ChannelFuture channelFuture) {
        Runnable task = createRegisterTask(channel, channelFuture);
        registerTask(task);
    }

    protected final boolean isIoThread() {return Thread.currentThread() == thread;}

    protected final void registerTask(Runnable task) {
        taskQueue.add(task);

        Selector selector = this.selector;

        if(selector != null) {
            selector.wakeup();
        } else {
            if(taskQueue.remove(task)) {
                throw new RejectedExecutionException("worker has already been shutdown!");
            }
        }
    }

    public void rebuildSelector() {
        // to do
        if(!isIoThread()) {}


        final Selector oldSelector = selector;
        final Selector newSelector;
        if(oldSelector == null)
            return ;
        try {
            newSelector = Selector.open();
        } catch (Exception e) {

        }

    }

    public void run() {

    }

    protected abstract Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture);

}
