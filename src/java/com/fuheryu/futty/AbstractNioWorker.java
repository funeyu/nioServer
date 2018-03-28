package com.fuheryu.futty;

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * Created by fuheyu on 2018/3/28.
 */
public abstract class AbstractNioWorker extends AbstractNioSelector implements Worker {

    AbstractNioWorker(Executor executor) {super(executor);}

    @Override
    public void executeInIoThread(Runnable task) {

    }

    public void executeInIoThread(Runnable task, boolean alwaysAsync) {
        if(!alwaysAsync && isIoThread()) {
            task.run();
        } else {
            registerTask(task);
        }
    }

    void writeFromSelectorLoop(final SelectionKey k) {
        Channel ch = (Channel)k.attachment();
        write0(ch);
    }

    protected void write0(Channel chanel) {

    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if(selectedKeys.isEmpty()) {
            return ;
        }
        for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext();) {
            SelectionKey k = i.next();
            i.remove();

            try {
                int readyOps = k.readyOps();
                if((readyOps & SelectionKey.OP_READ) != 0 || readyOps == 0) {
                    if(!read(k)) {
                        continue;
                    }
                }
                if((readyOps & SelectionKey.OP_WRITE) != 0) {

                }
            } catch (CancelledKeyException e) {
                close(k);
            }
        }
    }


    protected abstract boolean read(SelectionKey k);
}
