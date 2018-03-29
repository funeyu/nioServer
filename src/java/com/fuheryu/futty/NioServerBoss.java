package com.fuheryu.futty;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * Created by fuheyu on 2018/3/28.
 */
public final class NioServerBoss extends AbstractNioSelector implements Boss{

    NioServerBoss(Executor bossExecutor) {super(bossExecutor);}

    @Override
    public void shutDown() {

    }

    @Override
    protected Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture) {
        return null;
    }

    @Override
    protected void close(SelectionKey k) {

    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if(selectedKeys.isEmpty()) {
            return ;
        }

        for (Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext();) {
            SelectionKey k = i.next();
            i.remove();

            ServerSocketChannel channel = (ServerSocketChannel) k.attachment();

            try {
                SocketChannel acceptedSocket = channel.accept();
                if(acceptedSocket == null) {
                    break;
                }
            } catch (CancelledKeyException e) {
                k.cancel();
                channel.close();
            }
        }
    }


    private static void registerAcceptedChannel() {

    }
}
