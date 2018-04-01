package com.fuheryu.futty;

import java.net.ServerSocket;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

public class NioServerWorker extends AbstractNioWorker{

    NioServerWorker(Executor executor) {
        super(executor);
    }

    @Override
    protected boolean read(SelectionKey k) {

        if(k.isReadable()) {
            System.out.println("readddd");
            return true;
        }

        return false;

    }

    @Override
    protected Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture) {

        try {
            System.out.println("task");
            ((SocketChannel)channel).register(selector, SelectionKey.OP_READ);
            new Thread(this).start();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void close(SelectionKey k) {

    }

    @Override
    public void shutDown() {

    }
}
