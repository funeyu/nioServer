package com.fuheryu.futty;

import io.netty.channel.ServerChannel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
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
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if(k.isReadable()) {
            System.out.println("readddd");

            SocketChannel sc = (SocketChannel) k.channel();
            int count;
            try {
                while((count = sc.read(buffer)) > 0) {

                }

                if(count < 0) { // 这里是客户端主动关闭channel
                    sc.close();
                }
            } catch (IOException e) {

            }
            return true;
        }

        System.out.println("reeeeddd false");
        return false;

    }

    @Override
    protected Runnable createRegisterTask(Channel channel, ChannelFuture channelFuture) {

        try {
            System.out.println("task");
            ((SocketChannel)channel).register(selector, SelectionKey.OP_READ);
            System.out.println("socketChannel");
            new Thread(this).start();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("worker runnable！");
            }
        };
    }

    @Override
    protected void close(SelectionKey k) {

    }

    @Override
    public void shutDown() {

    }
}
