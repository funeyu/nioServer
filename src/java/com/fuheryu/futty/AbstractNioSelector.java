package com.fuheryu.futty;

import com.fuheryu.futty.future.ChannelFuture;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.*;

public abstract class AbstractNioSelector implements NioSelector {

    protected final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
    protected volatile Selector selector;
    private final Executor executor;
    protected volatile Thread thread;
    private final CountDownLatch shutDown = new CountDownLatch(1);

    public AbstractNioSelector(Executor executor) {this(executor, null);}

    public AbstractNioSelector(Executor executor, Thread thread) {
        this.executor = executor;
        this.openSelector();
    }
    public void register(Channel channel, ChannelFuture channelFuture) {
        Runnable task = createRegisterTask(channel, channelFuture);
        registerTask(task);
    }

    protected final boolean isIoThread() {return Thread.currentThread() == thread;}

    protected final void registerTask(Runnable task) {
        taskQueue.add(task);

        Selector selector = this.selector;

        if(selector != null) {
            //selector.wakeup();
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
        for(;;) {
            try {
                int i = selector.select();
                if(i == 0) {
                    // 这里出现了epoll空转的bug
                    continue;
                }

                processTaskQueue();
                process(selector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processTaskQueue() {
        for(;;) {
            final Runnable task = taskQueue.poll();
            if(task == null) {
                break;
            }
            task.run();
        }
    }

    private void openSelector() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture);

    protected abstract void close(SelectionKey k);

    protected abstract void process(Selector selector) throws IOException;

}
